/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.batch.engine.internal.bundle;

import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.Dictionary;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;
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

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private BatchEngineUnitReader _batchEngineUnitReader;

	private BundleTracker<Void> _bundleTracker;

	private class BatchEngineBundleTrackerCustomizer
		implements BundleTrackerCustomizer<Void> {

		@Override
		public Void addingBundle(Bundle bundle, BundleEvent bundleEvent) {
			Dictionary<String, String> headers = bundle.getHeaders(
				StringPool.BLANK);

			String batchPath = headers.get("Liferay-Client-Extension-Batch");

			if ((batchPath != null) && !_isAlreadyProcessed(bundle)) {
				_batchEngineUnitProcessor.processBatchEngineUnits(
					_batchEngineUnitReader.getBatchEngineUnits(bundle));
			}

			return null;
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent bundleEvent, Void unused) {
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent bundleEvent, Void unused) {
		}

	}

}