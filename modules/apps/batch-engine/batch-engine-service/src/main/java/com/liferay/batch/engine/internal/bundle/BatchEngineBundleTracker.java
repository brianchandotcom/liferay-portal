/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.bundle;

import com.liferay.batch.engine.internal.unit.MultiCompanyBatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Raymond Augé
 */
@Component(service = {})
public class BatchEngineBundleTracker {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE,
			new BatchEngineBundleTrackerCustomizer());

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private boolean _isAlreadyProcessed(Bundle bundle) {
		String lastModifiedString = String.valueOf(bundle.getLastModified());

		File batchMarkerFile = bundle.getDataFile(
			".liferay-client-extension-batch");

		try {
			if ((batchMarkerFile != null) && batchMarkerFile.exists() &&
				Objects.equals(
					FileUtil.read(batchMarkerFile), lastModifiedString)) {

				return true;
			}

			if (!batchMarkerFile.exists()) {
				batchMarkerFile.createNewFile();
			}

			FileUtil.write(batchMarkerFile, lastModifiedString, true);
		}
		catch (IOException ioException) {
			ReflectionUtil.throwException(ioException);
		}

		return false;
	}

	@Reference
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Reference
	private BatchEngineUnitReader _batchEngineUnitReader;

	private BundleTracker<Bundle> _bundleTracker;

	@Reference(target = ModuleServiceLifecycle.PORTLETS_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private MultiCompanyBatchEngineUnitProcessor
		_multiCompanyBatchEngineUnitProcessor;

	private class BatchEngineBundleTrackerCustomizer
		implements BundleTrackerCustomizer<Bundle> {

		@Override
		public Bundle addingBundle(Bundle bundle, BundleEvent bundleEvent) {
			Dictionary<String, String> headers = bundle.getHeaders(
				StringPool.BLANK);

			if (headers.get("Liferay-Client-Extension-Batch") == null) {
				return null;
			}

			List<BatchEngineUnit> multiCompanyBatchEngineUnits =
				new ArrayList<>();
			List<BatchEngineUnit> singleCompanyBatchEngineUnits =
				new ArrayList<>();

			Iterable<BatchEngineUnit> batchEngineUnits =
				_batchEngineUnitReader.getBatchEngineUnits(bundle);

			for (BatchEngineUnit batchEngineUnit : batchEngineUnits) {
				if (!batchEngineUnit.isValid()) {
					continue;
				}

				try {
					BatchEngineUnitConfiguration batchEngineUnitConfiguration =
						batchEngineUnit.getBatchEngineUnitConfiguration();

					if (batchEngineUnitConfiguration.isMultiCompany()) {
						multiCompanyBatchEngineUnits.add(batchEngineUnit);
					}
					else {
						singleCompanyBatchEngineUnits.add(batchEngineUnit);
					}
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}

			boolean alreadyProcessed = _isAlreadyProcessed(bundle);

			if (!alreadyProcessed) {
				_batchEngineUnitProcessor.processBatchEngineUnits(
					singleCompanyBatchEngineUnits);
			}

			if (multiCompanyBatchEngineUnits.isEmpty()) {
				return null;
			}

			_multiCompanyBatchEngineUnitProcessor.registerBatchEngineUnits(
				bundle, multiCompanyBatchEngineUnits, !alreadyProcessed);

			return bundle;
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent bundleEvent, Bundle unusedBundle) {
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent bundleEvent, Bundle unusedBundle) {

			_multiCompanyBatchEngineUnitProcessor.unregister(bundle);
		}

	}

}