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

import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

	public boolean isBatchEngineTechnical(String zipEntryName) {
		if (zipEntryName.endsWith(".batch-engine-data.json")) {
			return true;
		}

		return false;
	}

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

	private String _getBatchEngineBundleEntryKey(URL url) {
		String zipEntryName = url.getPath();

		if (isBatchEngineTechnical(zipEntryName)) {
			return zipEntryName;
		}

		if (!zipEntryName.contains(StringPool.SLASH)) {
			return StringPool.BLANK;
		}

		return zipEntryName.substring(
			0, zipEntryName.lastIndexOf(StringPool.SLASH));
	}

	private Collection<BatchEngineUnit> _getBatchEngineBundleUnitsCollection(
		Bundle bundle, String batchPath) {

		Map<String, URL> batchEngineURLs = new HashMap<>();
		Map<String, BatchEngineUnit> batchEngineUnits = new HashMap<>();

		Enumeration<URL> enumeration = bundle.findEntries(batchPath, "*", true);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			if (StringUtil.endsWith(url.getPath(), StringPool.SLASH)) {
				continue;
			}

			String key = _getBatchEngineBundleEntryKey(url);

			URL complementURL = batchEngineURLs.get(key);

			if (complementURL == null) {
				batchEngineURLs.put(key, url);

				batchEngineUnits.put(
					key, new AdvancedBatchEngineBundleUnitImpl(bundle, url));

				continue;
			}

			batchEngineUnits.put(
				key,
				new ClassicBatchEngineBundleUnitImpl(
					bundle, url, complementURL));

			batchEngineURLs.remove(key);
		}

		return batchEngineUnits.values();
	}

	private Iterable<BatchEngineUnit> _getBatchEngineUnits(
		Bundle bundle, String batchPath) {

		return new Iterable<BatchEngineUnit>() {

			@Override
			public Iterator<BatchEngineUnit> iterator() {
				return new BatchEngineUnitIterator(bundle, batchPath);
			}

		};
	}

	private boolean _isAlreadyProcessed(Bundle bundle) {
		File batchMarkerFile = bundle.getDataFile(
			".liferay-client-extension-batch");

		if ((batchMarkerFile != null) && batchMarkerFile.exists()) {
			return true;
		}

		try {
			if (!batchMarkerFile.exists()) {
				batchMarkerFile.createNewFile();
			}
		}
		catch (IOException ioException) {
			ReflectionUtil.throwException(ioException);
		}

		return false;
	}

	private void _processBatchEngineBundle(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String batchPath = headers.get("Liferay-Client-Extension-Batch");

		if (batchPath != null) {
			if (_isAlreadyProcessed(bundle)) {
				return;
			}

			if (batchPath.isEmpty()) {
				batchPath = StringPool.PERIOD;
			}

			if (StringUtil.startsWith(batchPath, StringPool.SLASH)) {
				batchPath = batchPath.substring(1);
			}

			if (!StringUtil.endsWith(batchPath, StringPool.SLASH)) {
				batchPath = batchPath.concat(StringPool.SLASH);
			}

			_batchEngineUnitProcessor.processBatchEngineUnits(
				_getBatchEngineUnits(bundle, batchPath));
		}
	}

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	private BundleTracker<Void> _bundleTracker;

	private class BatchEngineBundleTrackerCustomizer
		implements BundleTrackerCustomizer<Void> {

		@Override
		public Void addingBundle(Bundle bundle, BundleEvent bundleEvent) {
			_processBatchEngineBundle(bundle);

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

	private class BatchEngineUnitIterator implements Iterator<BatchEngineUnit> {

		public BatchEngineUnitIterator(Bundle bundle, String batchPath) {
			Collection<BatchEngineUnit> batchEngineZipUnits =
				_getBatchEngineBundleUnitsCollection(bundle, batchPath);

			_iterator = batchEngineZipUnits.iterator();
		}

		@Override
		public boolean hasNext() {
			return _iterator.hasNext();
		}

		@Override
		public BatchEngineUnit next() {
			return _iterator.next();
		}

		private final Iterator<BatchEngineUnit> _iterator;

	}

}