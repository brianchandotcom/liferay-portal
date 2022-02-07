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
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class FriendlyURLMapperProvider {

	public FriendlyURLMapperProvider(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		ClassNameLocalService classNameLocalService) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_classNameLocalService = classNameLocalService;
	}

	public FriendlyURLMapper getFriendlyURLMapper(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

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
				(FriendlyURLMapper)new AssetDisplayPageFriendlyURLMapper(
					_assetDisplayPageFriendlyURLProvider,
					_classNameLocalService, layoutDisplayPageObjectProvider,
					themeDisplay)
		).orElseGet(
			() -> new DefaultPageFriendlyURLMapper()
		);
	}

	public static class AssetDisplayPageFriendlyURLMapper
		implements FriendlyURLMapper {

		public String getMappedFriendlyURL(String url, Locale locale)
			throws PortalException {

			if (_layoutDisplayPageObjectProvider == null) {
				return url;
			}

			ClassName className = _classNameLocalService.getClassName(
				_layoutDisplayPageObjectProvider.getClassNameId());

			return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				className.getClassName(),
				_layoutDisplayPageObjectProvider.getClassPK(), locale,
				_themeDisplay);
		}

		@Override
		public Map<Locale, String> getMappedFriendlyURLs(
				Map<Locale, String> friendlyURLs)
			throws PortalException {

			if (_layoutDisplayPageObjectProvider == null) {
				return friendlyURLs;
			}

			Map<Locale, String> mappedFriendlyURLs = new HashMap<>();

			for (Map.Entry<Locale, String> entry : friendlyURLs.entrySet()) {
				mappedFriendlyURLs.put(
					entry.getKey(),
					getMappedFriendlyURL(entry.getValue(), entry.getKey()));
			}

			return mappedFriendlyURLs;
		}

		protected AssetDisplayPageFriendlyURLMapper(
			AssetDisplayPageFriendlyURLProvider
				assetDisplayPageFriendlyURLProvider,
			ClassNameLocalService classNameLocalService,
			LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider,
			ThemeDisplay themeDisplay) {

			_assetDisplayPageFriendlyURLProvider =
				assetDisplayPageFriendlyURLProvider;
			_classNameLocalService = classNameLocalService;
			_layoutDisplayPageObjectProvider = layoutDisplayPageObjectProvider;
			_themeDisplay = themeDisplay;
		}

		private final AssetDisplayPageFriendlyURLProvider
			_assetDisplayPageFriendlyURLProvider;
		private final ClassNameLocalService _classNameLocalService;
		private final LayoutDisplayPageObjectProvider<?>
			_layoutDisplayPageObjectProvider;
		private final ThemeDisplay _themeDisplay;

	}

	public static class DefaultPageFriendlyURLMapper
		implements FriendlyURLMapper {

		@Override
		public String getMappedFriendlyURL(String url, Locale locale) {
			return url;
		}

		@Override
		public Map<Locale, String> getMappedFriendlyURLs(
			Map<Locale, String> friendlyURLs) {

			return friendlyURLs;
		}

	}

	public interface FriendlyURLMapper {

		public String getMappedFriendlyURL(String url, Locale locale)
			throws PortalException;

		public Map<Locale, String> getMappedFriendlyURLs(
				Map<Locale, String> friendlyURLs)
			throws PortalException;

	}

	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private ClassNameLocalService _classNameLocalService;

}