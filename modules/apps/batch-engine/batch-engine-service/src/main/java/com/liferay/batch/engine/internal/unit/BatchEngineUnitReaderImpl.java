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
import com.liferay.batch.engine.unit.BatchEngineUnitMetaInfo;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.petra.io.Deserializer;
import com.liferay.petra.io.Serializer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import java.net.URL;

import java.nio.ByteBuffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

	private String _getBatchEngineBundleEntryKey(URL url) {
		String zipEntryName = url.getPath();

		if (_isBatchEngineTechnical(zipEntryName)) {
			return zipEntryName;
		}

		if (!zipEntryName.contains(StringPool.SLASH)) {
			return StringPool.SLASH;
		}

		return zipEntryName.substring(
			0, zipEntryName.lastIndexOf(StringPool.SLASH) + 1);
	}

	private Collection<BatchEngineUnit> _getBatchEngineBundleUnitsCollection(
		Bundle bundle, String batchPath) {

		List<BatchEngineUnit> batchEngineUnits = _loadBatchEngineUnits(bundle);

		if (batchEngineUnits != null) {
			return batchEngineUnits;
		}

		batchEngineUnits = new ArrayList<>();

		Map<String, List<URL>> bundleBatchEngineUnitURLs = new TreeMap<>(
			new NaturalOrderStringComparator());

		Enumeration<URL> enumeration = bundle.findEntries(batchPath, "*", true);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			if (StringUtil.endsWith(url.getPath(), StringPool.SLASH)) {
				continue;
			}

			bundleBatchEngineUnitURLs.compute(
				_getBatchEngineBundleEntryKey(url),
				(k, urls) -> {
					if (urls == null) {
						urls = new ArrayList<>();
					}

					urls.add(url);

					return urls;
				});
		}

		for (Map.Entry<String, List<URL>> entry :
				bundleBatchEngineUnitURLs.entrySet()) {

			List<URL> urls = entry.getValue();

			if (_isBatchEngineTechnical(entry.getKey())) {
				URL url = urls.get(0);

				AdvancedBundleBatchEngineUnitImpl
					advancedBundleBatchEngineUnitImpl =
						new AdvancedBundleBatchEngineUnitImpl(bundle, url);

				if (advancedBundleBatchEngineUnitImpl.isValid()) {
					advancedBundleBatchEngineUnitImpl.
						setBatchEngineUnitMetaInfo(
							_toBatchEngineUnitMetaInfo(
								advancedBundleBatchEngineUnitImpl,
								Arrays.asList(url)));

					batchEngineUnits.add(advancedBundleBatchEngineUnitImpl);
				}
			}
			else {
				ClassicBundleBatchEngineUnitImpl
					classicBundleBatchEngineUnitImpl =
						new ClassicBundleBatchEngineUnitImpl(bundle, urls);

				if (classicBundleBatchEngineUnitImpl.isValid()) {
					classicBundleBatchEngineUnitImpl.setBatchEngineUnitMetaInfo(
						_toBatchEngineUnitMetaInfo(
							classicBundleBatchEngineUnitImpl, urls));

					batchEngineUnits.add(classicBundleBatchEngineUnitImpl);
				}
			}
		}

		_saveBatchEngineUnits(bundle, batchEngineUnits);

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

	private boolean _isBatchEngineTechnical(String zipEntryName) {
		if (zipEntryName.endsWith(
				BatchEngineTaskContentType.JSONT.getFileExtension())) {

			return true;
		}

		return false;
	}

	private List<BatchEngineUnit> _loadBatchEngineUnits(Bundle bundle) {
		File file = bundle.getDataFile("batchEngineUnits.data");

		if (!file.exists()) {
			return null;
		}

		try {
			Deserializer deserializer = new Deserializer(
				ByteBuffer.wrap(FileUtil.getBytes(file)));

			if (deserializer.readLong() == bundle.getLastModified()) {
				List<BatchEngineUnit> batchEngineUnits = new ArrayList<>();

				int size = deserializer.readInt();

				for (int i = 0; i < size; i++) {
					BatchEngineUnitMetaInfo batchEngineUnitMetaInfo =
						BatchEngineUnitMetaInfo.readFrom(deserializer);

					batchEngineUnits.add(
						_toBatchEngineUnit(bundle, batchEngineUnitMetaInfo));
				}

				return batchEngineUnits;
			}
		}
		catch (IOException ioException) {
			_log.error("Unable to read batch engine units", ioException);
		}

		return null;
	}

	private void _saveBatchEngineUnits(
		Bundle bundle, List<BatchEngineUnit> batchEngineUnits) {

		Serializer serializer = new Serializer();

		serializer.writeLong(bundle.getLastModified());
		serializer.writeInt(batchEngineUnits.size());

		try (OutputStream outputStream = new FileOutputStream(
				bundle.getDataFile("batchEngineUnits.data"))) {

			for (BatchEngineUnit batchEngineUnit : batchEngineUnits) {
				BatchEngineUnitMetaInfo batchEngineUnitMetaInfo =
					batchEngineUnit.getBatchEngineUnitMetaInfo();

				batchEngineUnitMetaInfo.writeTo(serializer);
			}

			serializer.writeTo(outputStream);
		}
		catch (IOException ioException) {
			_log.error("Unable to write batch engine units", ioException);
		}
	}

	private BatchEngineUnit _toBatchEngineUnit(
		Bundle bundle, BatchEngineUnitMetaInfo batchEngineUnitMetaInfo) {

		String[] paths = batchEngineUnitMetaInfo.getPaths();

		if (batchEngineUnitMetaInfo.isAdvanced()) {
			AdvancedBundleBatchEngineUnitImpl
				advancedBundleBatchEngineUnitImpl =
					new AdvancedBundleBatchEngineUnitImpl(
						bundle, bundle.getEntry(paths[0]));

			advancedBundleBatchEngineUnitImpl.setBatchEngineUnitMetaInfo(
				batchEngineUnitMetaInfo);

			return advancedBundleBatchEngineUnitImpl;
		}

		List<URL> urls = new ArrayList<>();

		for (String path : paths) {
			urls.add(bundle.getEntry(path));
		}

		ClassicBundleBatchEngineUnitImpl classicBundleBatchEngineUnitImpl =
			new ClassicBundleBatchEngineUnitImpl(bundle, urls);

		classicBundleBatchEngineUnitImpl.setBatchEngineUnitMetaInfo(
			batchEngineUnitMetaInfo);

		return classicBundleBatchEngineUnitImpl;
	}

	private BatchEngineUnitMetaInfo _toBatchEngineUnitMetaInfo(
		BatchEngineUnit batchEngineUnit, List<URL> urls) {

		try {
			BatchEngineUnitConfiguration batchEngineUnitConfiguration =
				batchEngineUnit.getBatchEngineUnitConfiguration();

			String[] paths = new String[urls.size()];

			for (int i = 0; i < urls.size(); i++) {
				URL url = urls.get(i);

				paths[i] = url.getPath();
			}

			return new BatchEngineUnitMetaInfo(
				batchEngineUnit instanceof AdvancedBundleBatchEngineUnitImpl,
				batchEngineUnitConfiguration.getCompanyId(),
				_getFeatureFlagKey(batchEngineUnitConfiguration),
				batchEngineUnitConfiguration.isMultiCompany(), paths);
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to parse batch engine unit configuration", ioException);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineUnitReaderImpl.class);

}