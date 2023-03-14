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

package com.liferay.portal.language.override.internal;

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.language.LanguageOverrideProvider;
import com.liferay.portal.language.override.internal.provider.PLOOriginalTranslationThreadLocal;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.service.PLOEntryLocalService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	service = {
		LanguageOverrideProvider.class, PLOLanguageOverrideProvider.class
	}
)
public class PLOLanguageOverrideProvider implements LanguageOverrideProvider {

	@Override
	public String get(String key, Locale locale) {
		Map<String, HashMap<String, String>> ploEntriesMap =
			_ploEntriesMapDCLSingleton.getSingleton(_supplier);

		if (ploEntriesMap.isEmpty() ||
			PLOOriginalTranslationThreadLocal.isUseOriginalTranslation()) {

			return null;
		}

		Map<String, String> overrideMap = _getOverrideMap(
			ploEntriesMap, CompanyThreadLocal.getCompanyId(), locale);

		return overrideMap.get(key);
	}

	@Override
	public Set<String> keySet(Locale locale) {
		Map<String, HashMap<String, String>> ploEntriesMap =
			_ploEntriesMapDCLSingleton.getSingleton(_supplier);

		if (ploEntriesMap.isEmpty() ||
			PLOOriginalTranslationThreadLocal.isUseOriginalTranslation()) {

			return Collections.emptySet();
		}

		Map<String, String> overrideMap = _getOverrideMap(
			ploEntriesMap, CompanyThreadLocal.getCompanyId(), locale);

		return overrideMap.keySet();
	}

	protected void add(PLOEntry ploEntry) {
		_add(_ploEntriesMapDCLSingleton.getSingleton(_supplier), ploEntry);
	}

	protected void remove(PLOEntry ploEntry) {
		Map<String, HashMap<String, String>> ploEntriesMap =
			_ploEntriesMapDCLSingleton.getSingleton(_supplier);

		ploEntriesMap.computeIfPresent(
			_encodeKey(ploEntry.getCompanyId(), ploEntry.getLanguageId()),
			(key, value) -> {
				value.remove(ploEntry.getKey());

				if (value.isEmpty()) {
					return null;
				}

				return value;
			});
	}

	protected void update(PLOEntry ploEntry) {
		Map<String, HashMap<String, String>> ploEntriesMap =
			_ploEntriesMapDCLSingleton.getSingleton(_supplier);

		ploEntriesMap.computeIfPresent(
			_encodeKey(ploEntry.getCompanyId(), ploEntry.getLanguageId()),
			(key, value) -> {
				value.put(ploEntry.getKey(), ploEntry.getValue());

				return value;
			});
	}

	private void _add(
		Map<String, HashMap<String, String>> ploEntriesMap, PLOEntry ploEntry) {

		ploEntriesMap.compute(
			_encodeKey(ploEntry.getCompanyId(), ploEntry.getLanguageId()),
			(key, value) -> {
				if (value == null) {
					value = new HashMap<>();
				}

				value.put(ploEntry.getKey(), ploEntry.getValue());

				return value;
			});
	}

	private Map<String, HashMap<String, String>> _createPLOEntriesMap() {
		Map<String, HashMap<String, String>> ploEntriesMap =
			new ConcurrentHashMap<>();

		_companyLocalService.forEachCompanyId(
			companyId -> {
				for (PLOEntry ploEntry :
						_ploEntryLocalService.getPLOEntries(companyId)) {

					_add(ploEntriesMap, ploEntry);
				}
			});

		return ploEntriesMap;
	}

	private String _encodeKey(long companyId, String languageId) {
		return StringBundler.concat(companyId, StringPool.POUND, languageId);
	}

	private Map<String, String> _getOverrideMap(
		Map<String, HashMap<String, String>> ploEntriesMap, long companyId,
		Locale locale) {

		Map<String, String> overrideMap = ploEntriesMap.get(
			_encodeKey(companyId, _language.getLanguageId(locale)));

		if (overrideMap == null) {
			return Collections.emptyMap();
		}

		return overrideMap;
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Language _language;

	private final DCLSingleton<Map<String, HashMap<String, String>>>
		_ploEntriesMapDCLSingleton = new DCLSingleton<>();

	@Reference
	private PLOEntryLocalService _ploEntryLocalService;

	private final Supplier<Map<String, HashMap<String, String>>> _supplier =
		this::_createPLOEntriesMap;

}