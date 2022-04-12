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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
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

import org.osgi.service.component.annotations.Activate;
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
		if (PLOOriginalTranslationThreadLocal.isUseOriginalTranslation()) {
			return null;
		}

		Map<String, String> overrideMap = _getOverrideMap(
			CompanyThreadLocal.getCompanyId(), locale);

		return overrideMap.get(key);
	}

	@Override
	public Set<String> keySet(Locale locale) {
		if (PLOOriginalTranslationThreadLocal.isUseOriginalTranslation()) {
			return Collections.emptySet();
		}

		Map<String, String> overrideMap = _getOverrideMap(
			CompanyThreadLocal.getCompanyId(), locale);

		return overrideMap.keySet();
	}

	@Activate
	protected void activate() {
		_ploEntriesMap = new ConcurrentHashMap<>();

		for (PLOEntry ploEntry :
				_ploEntryLocalService.getPLOEntries(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			add(ploEntry);
		}
	}

	protected void add(PLOEntry ploEntry) {
		_ploEntriesMap.compute(
			_encodeKey(ploEntry.getCompanyId(), ploEntry.getLanguageId()),
			(key, value) -> {
				if (value == null) {
					value = new HashMap<>();
				}

				value.put(ploEntry.getKey(), ploEntry.getValue());

				return value;
			});
	}

	protected void remove(PLOEntry ploEntry) {
		_ploEntriesMap.computeIfPresent(
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
		_ploEntriesMap.computeIfPresent(
			_encodeKey(ploEntry.getCompanyId(), ploEntry.getLanguageId()),
			(key, value) -> {
				value.put(ploEntry.getKey(), ploEntry.getValue());

				return value;
			});
	}

	private String _encodeKey(long companyId, String languageId) {
		return StringBundler.concat(companyId, StringPool.POUND, languageId);
	}

	private Map<String, String> _getOverrideMap(long companyId, Locale locale) {
		if (_ploEntriesMap.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<String, String> overrideMap = _ploEntriesMap.get(
			_encodeKey(companyId, LanguageUtil.getLanguageId(locale)));

		if (overrideMap == null) {
			return Collections.emptyMap();
		}

		return overrideMap;
	}

	private Map<String, HashMap<String, String>> _ploEntriesMap;

	@Reference
	private PLOEntryLocalService _ploEntryLocalService;

}