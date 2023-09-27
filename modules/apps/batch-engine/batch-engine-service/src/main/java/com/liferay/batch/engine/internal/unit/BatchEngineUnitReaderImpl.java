/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.unit;

import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.internal.bundle.AdvancedBundleBatchEngineUnitImpl;
import com.liferay.batch.engine.internal.bundle.ClassicBundleBatchEngineUnitImpl;
import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
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

		Enumeration<URL> enumeration = bundle.findEntries(batchPath, "*", true);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			if (StringUtil.endsWith(url.getPath(), StringPool.SLASH)) {
				continue;
			}

			String key = _getBatchEngineBundleEntryKey(url);

			if (_isAdvancedBundleBatchEngineUnit(url.toString())) {
				batchEngineUnits.add(
					new AdvancedBundleBatchEngineUnitImpl(bundle, url));

				continue;
			}

			classicBundleBatchEngineUnitURLs.computeIfAbsent(
				key, k -> new ArrayList<>());

			List<URL> urls = classicBundleBatchEngineUnitURLs.get(key);

			urls.add(url);
		}

		for (List<URL> urls : classicBundleBatchEngineUnitURLs.values()) {
			batchEngineUnits.add(
				new ClassicBundleBatchEngineUnitImpl(bundle, urls));
		}

		return batchEngineUnits;
	}

	private boolean _isAdvancedBundleBatchEngineUnit(String url) {
		return url.endsWith(
			BatchEngineTaskContentType.JSONT.getFileExtension());
	}

}