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

package com.liferay.layout.seo.internal.util;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class AlternateURLMapperProvider {

	public AlternateURLMapperProvider(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		ClassNameLocalService classNameLocalService, Portal portal) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_classNameLocalService = classNameLocalService;
		_portal = portal;
	}

	public AlternateURLMapperProvider.AlternateURLMapper getAlternateURLMapper(
		HttpServletRequest httpServletRequest) {

		return Optional.ofNullable(
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER)
		).filter(
			layoutDisplayPageObjectProvider ->
				AssetDisplayPageUtil.hasAssetDisplayPage(
					layoutDisplayPageObjectProvider.getGroupId(),
					layoutDisplayPageObjectProvider.getClassNameId(),
					layoutDisplayPageObjectProvider.getClassPK(),
					layoutDisplayPageObjectProvider.getClassTypeId())
		).map(
			layoutDisplayPageObjectProvider ->
				(AlternateURLMapperProvider.AlternateURLMapper)
					new AlternateURLMapperProvider.
						AssetDisplayPageAlternateURLMapper(
							_assetDisplayPageFriendlyURLProvider,
							_classNameLocalService,
							layoutDisplayPageObjectProvider, _portal)
		).orElseGet(
			() -> new AlternateURLMapperProvider.DefaultPageAlternateURLMapper(
				_portal)
		);
	}

	public static class AssetDisplayPageAlternateURLMapper
		implements AlternateURLMapperProvider.AlternateURLMapper {

		@Override
		public String getAlternateURL(
				String canonicalURL, ThemeDisplay themeDisplay, Locale locale,
				Layout layout)
			throws PortalException {

			return _portal.getAlternateURL(
				_getMappedFriendlyURL(canonicalURL, locale, themeDisplay),
				themeDisplay, locale, layout);
		}

		protected AssetDisplayPageAlternateURLMapper(
			AssetDisplayPageFriendlyURLProvider
				assetDisplayPageFriendlyURLProvider,
			ClassNameLocalService classNameLocalService,
			LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider,
			Portal portal) {

			_assetDisplayPageFriendlyURLProvider =
				assetDisplayPageFriendlyURLProvider;
			_classNameLocalService = classNameLocalService;
			_layoutDisplayPageObjectProvider = layoutDisplayPageObjectProvider;
			_portal = portal;
		}

		private String _getMappedFriendlyURL(
				String url, Locale locale, ThemeDisplay themeDisplay)
			throws PortalException {

			if (_layoutDisplayPageObjectProvider == null) {
				return url;
			}

			ClassName className = _classNameLocalService.getClassName(
				_layoutDisplayPageObjectProvider.getClassNameId());

			return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				className.getClassName(),
				_layoutDisplayPageObjectProvider.getClassPK(), locale,
				themeDisplay);
		}

		private final AssetDisplayPageFriendlyURLProvider
			_assetDisplayPageFriendlyURLProvider;
		private final ClassNameLocalService _classNameLocalService;
		private final LayoutDisplayPageObjectProvider<?>
			_layoutDisplayPageObjectProvider;
		private final Portal _portal;

	}

	public static class DefaultPageAlternateURLMapper
		implements AlternateURLMapperProvider.AlternateURLMapper {

		@Override
		public String getAlternateURL(
				String canonicalURL, ThemeDisplay themeDisplay, Locale locale,
				Layout layout)
			throws PortalException {

			return _portal.getAlternateURL(
				canonicalURL, themeDisplay, locale, layout);
		}

		protected DefaultPageAlternateURLMapper(Portal portal) {
			_portal = portal;
		}

		private final Portal _portal;

	}

	public interface AlternateURLMapper {

		public String getAlternateURL(
				String canonicalURL, ThemeDisplay themeDisplay, Locale locale,
				Layout layout)
			throws PortalException;

		public default Map<Locale, String> getAlternateURLs(
				String canonicalURL, ThemeDisplay themeDisplay, Layout layout,
				Set<Locale> locales)
			throws PortalException {

			Map<Locale, String> alternateURLs = new HashMap<>();

			for (Locale locale : locales) {
				alternateURLs.put(
					locale,
					getAlternateURL(
						canonicalURL, themeDisplay, locale, layout));
			}

			return alternateURLs;
		}

	}

	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private ClassNameLocalService _classNameLocalService;
	private final Portal _portal;

}