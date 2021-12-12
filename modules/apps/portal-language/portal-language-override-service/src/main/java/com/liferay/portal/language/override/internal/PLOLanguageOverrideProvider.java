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

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.language.LanguageOverrideProvider;
import com.liferay.portal.language.override.internal.provider.PLOOriginalTranslationThreadLocal;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.service.PLOEntryLocalService;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
			(PortalCache<PLOCacheKey, HashMap<String, String>>)
				_multiVMPool.getPortalCache(_CACHE_KEY);
	}

	protected void clear(long companyId, Locale locale) {
		_portalCache.remove(
			new PLOCacheKey(companyId, LanguageUtil.getLanguageId(locale)));
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(_CACHE_KEY);
	}

	private Map<String, String> _getOverrideMap(long companyId, Locale locale) {
		PLOCacheKey ploCacheKey = new PLOCacheKey(
			companyId, LanguageUtil.getLanguageId(locale));

		Map<String, String> overrideMap = _portalCache.get(ploCacheKey);

		if (overrideMap != null) {
			return overrideMap;
		}

		List<PLOEntry> ploEntries =
			_ploEntryLocalService.getPLOEntriesByLanguageId(
				ploCacheKey.companyId, ploCacheKey.languageId);

		Stream<PLOEntry> ploEntryStream = ploEntries.stream();

		overrideMap = ploEntryStream.collect(
			Collectors.toMap(PLOEntry::getKey, PLOEntry::getValue));

		_portalCache.put(ploCacheKey, (HashMap<String, String>)overrideMap);

		return overrideMap;
	}

	private static final String _CACHE_KEY = "PORTAL_LANGUAGE_OVERRIDE_CACHE";

	@Reference
	private MultiVMPool _multiVMPool;

	@Reference
	private PLOEntryLocalService _ploEntryLocalService;

	private PortalCache<PLOCacheKey, HashMap<String, String>> _portalCache;

	private static class PLOCacheKey implements Serializable {

		public PLOCacheKey(long companyId, String languageId) {
			this.companyId = companyId;
			this.languageId = languageId;
		}

		@Override
		public boolean equals(Object object) {
			if (object instanceof PLOCacheKey) {
				PLOCacheKey ploCacheKey = (PLOCacheKey)object;

				if ((companyId == ploCacheKey.companyId) &&
					Objects.equals(languageId, ploCacheKey.languageId)) {

					return true;
				}
			}

			return false;
		}

		@Override
		public int hashCode() {
			int localeHashCode = HashUtil.hash(0, languageId);

			return HashUtil.hash(localeHashCode, companyId);
		}

		public final long companyId;
		public final String languageId;

	}

}