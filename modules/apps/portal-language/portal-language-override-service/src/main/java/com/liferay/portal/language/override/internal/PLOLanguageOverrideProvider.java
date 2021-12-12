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
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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
	@SuppressWarnings("unchecked")
	protected void activate() {
		_portalCache =
			(PortalCache<String, HashMap<String, String>>)
				_multiVMPool.getPortalCache(_CACHE_KEY);
	}

	protected void clear(long companyId, Locale locale) {
		_portalCache.remove(
			_encodeKey(companyId, LanguageUtil.getLanguageId(locale)));
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(_CACHE_KEY);
	}

	private String _encodeKey(long companyId, String languageId) {
		return StringBundler.concat(companyId, StringPool.POUND, languageId);
	}

	private Map<String, String> _getOverrideMap(long companyId, Locale locale) {
		String languageId = LanguageUtil.getLanguageId(locale);

		String key = _encodeKey(companyId, languageId);

		HashMap<String, String> overrideMap = _portalCache.get(key);

		if (overrideMap != null) {
			return overrideMap;
		}

		overrideMap = new HashMap<>();

		for (PLOEntry ploEntry :
				_ploEntryLocalService.getPLOEntriesByLanguageId(
					companyId, languageId)) {

			overrideMap.put(ploEntry.getKey(), ploEntry.getValue());
		}

		_portalCache.put(key, overrideMap);

		return overrideMap;
	}

	private static final String _CACHE_KEY = "PORTAL_LANGUAGE_OVERRIDE_CACHE";

	@Reference
	private MultiVMPool _multiVMPool;

	@Reference
	private PLOEntryLocalService _ploEntryLocalService;

	private PortalCache<String, HashMap<String, String>> _portalCache;

}