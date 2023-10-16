/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.unit;

import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.internal.bundle.AdvancedBundleBatchEngineUnitImpl;
import com.liferay.batch.engine.internal.bundle.ClassicBundleBatchEngineUnitImpl;
import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.petra.io.Deserializer;
import com.liferay.petra.io.Serializer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import java.net.URL;

import java.nio.ByteBuffer;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = BatchEngineUnitReader.class)
public class BatchEngineUnitReaderImpl implements BatchEngineUnitReader {

	@Override
	public Collection<BatchEngineUnit> getBatchEngineUnits(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String batchPath = headers.get("Liferay-Client-Extension-Batch");

		if (batchPath != null) {
			if (batchPath.isEmpty()) {
				batchPath = StringPool.PERIOD;
			}

			if (StringUtil.startsWith(batchPath, StringPool.SLASH)) {
				batchPath = batchPath.substring(1);
			}

			if (!StringUtil.endsWith(batchPath, StringPool.SLASH)) {
				batchPath = batchPath.concat(StringPool.SLASH);
			}

			return _getBatchEngineBundleUnitsCollection(bundle, batchPath);
		}

		return Collections.emptyList();
	}

	public boolean isBatchEngineTechnical(String zipEntryName) {
		if (zipEntryName.endsWith(
				BatchEngineTaskContentType.JSONT.getFileExtension())) {

			return true;
		}

		return false;
	}

	private void _addBundleBatchEngineUnit(
		BatchEngineUnit batchEngineUnit, List<URL> urls,
		List<BatchEngineUnit> batchEngineUnits,
		List<Map.Entry<String, URL>> featureFlagURLs) {

		if (!batchEngineUnit.isValid()) {
			return;
		}

		try {
			String featureFlagKey = _getFeatureFlagKey(
				batchEngineUnit.getBatchEngineUnitConfiguration());

			for (URL url : urls) {
				featureFlagURLs.add(
					new AbstractMap.SimpleImmutableEntry<>(
						featureFlagKey, url));
			}

			if (!_isFeatureFlagDisabled(featureFlagKey)) {
				batchEngineUnits.add(batchEngineUnit);
			}
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}
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

		Map<String, List<URL>> classicBundleBatchEngineUnitURLs =
			new HashMap<>();
		List<BatchEngineUnit> batchEngineUnits = new ArrayList<>();

		List<URL> entryURLs = _loadEntryURLs(bundle);

		boolean needSave = false;

		if (entryURLs == null) {
			needSave = true;

			entryURLs = Collections.list(
				bundle.findEntries(batchPath, "*", true));
		}

		List<Map.Entry<String, URL>> featureFlagURLs = new ArrayList<>();

		for (URL url : entryURLs) {
			if (StringUtil.endsWith(url.getPath(), StringPool.SLASH)) {
				continue;
			}

			String key = _getBatchEngineBundleEntryKey(url);

			if (_isAdvancedBundleBatchEngineUnit(url.toString())) {
				_addBundleBatchEngineUnit(
					new AdvancedBundleBatchEngineUnitImpl(bundle, url),
					Arrays.asList(url), batchEngineUnits, featureFlagURLs);

				continue;
			}

			classicBundleBatchEngineUnitURLs.computeIfAbsent(
				key, k -> new ArrayList<>());

			List<URL> urls = classicBundleBatchEngineUnitURLs.get(key);

			urls.add(url);
		}

		for (List<URL> urls : classicBundleBatchEngineUnitURLs.values()) {
			_addBundleBatchEngineUnit(
				new ClassicBundleBatchEngineUnitImpl(bundle, urls), urls,
				batchEngineUnits, featureFlagURLs);
		}

		if (needSave) {
			_saveEntryURLs(bundle, featureFlagURLs);
		}

		return batchEngineUnits;
	}

	private String _getFeatureFlagKey(
		BatchEngineUnitConfiguration batchEngineUnitConfiguration) {

		Map<String, Serializable> parameters =
			batchEngineUnitConfiguration.getParameters();

		if (parameters == null) {
			return StringPool.BLANK;
		}

		return GetterUtil.getString(parameters.get("featureFlag"));
	}

	private boolean _isAdvancedBundleBatchEngineUnit(String url) {
		return url.endsWith(
			BatchEngineTaskContentType.JSONT.getFileExtension());
	}

	private boolean _isFeatureFlagDisabled(String featureFlagKey) {
		if (Validator.isNotNull(featureFlagKey) &&
			!FeatureFlagManagerUtil.isEnabled(featureFlagKey)) {

			return true;
		}

		return false;
	}

	private List<URL> _loadEntryURLs(Bundle bundle) {
		File file = bundle.getDataFile("entryURLs.data");

		if (file.exists()) {
			try {
				Deserializer deserializer = new Deserializer(
					ByteBuffer.wrap(FileUtil.getBytes(file)));

				if (deserializer.readLong() == bundle.getLastModified()) {
					int size = deserializer.readInt();

					List<URL> urls = new ArrayList<>();

					for (int i = 0; i < size; i++) {
						String featureFlagKey = deserializer.readString();
						String path = deserializer.readString();

						if (!_isFeatureFlagDisabled(featureFlagKey)) {
							urls.add(bundle.getEntry(path));
						}
					}

					return urls;
				}
			}
			catch (IOException ioException) {
				_log.error(
					"Unable to read batch engine entry urls", ioException);
			}
		}

		return null;
	}

	private void _saveEntryURLs(
		Bundle bundle, List<Map.Entry<String, URL>> featureFlagURLs) {

		Serializer serializer = new Serializer();

		serializer.writeLong(bundle.getLastModified());
		serializer.writeInt(featureFlagURLs.size());

		for (Map.Entry<String, URL> entry : featureFlagURLs) {
			serializer.writeString(entry.getKey());

			URL url = entry.getValue();

			serializer.writeString(url.getPath());
		}

		try (OutputStream outputStream = new FileOutputStream(
				bundle.getDataFile("entryURLs.data"))) {

			serializer.writeTo(outputStream);
		}
		catch (IOException ioException) {
			_log.error("Unable to write batch engine entry urls", ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineUnitReaderImpl.class);

}