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

package com.liferay.layout.seo.internal.canonical.url;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.layout.seo.canonical.url.LayoutSEOCanonicalURLProvider;
import com.liferay.layout.seo.internal.configuration.LayoutSEOCompanyConfiguration;
import com.liferay.layout.seo.internal.util.FriendlyURLMapperProvider;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = LayoutSEOCanonicalURLProvider.class)
public class LayoutSEOCanonicalURLProviderImpl
	implements LayoutSEOCanonicalURLProvider {

	@Override
	public String getCanonicalURL(
			Layout layout, Locale locale, String canonicalURL,
			Map<Locale, String> alternateURLs)
		throws PortalException {

		String layoutCanonicalURL = _getLayoutCanonicalURL(locale, layout);

		if (Validator.isNotNull(layoutCanonicalURL)) {
			return layoutCanonicalURL;
		}

		return _getDefaultCanonicalURL(
			layout, locale, canonicalURL, alternateURLs);
	}

	@Override
	public Map<Locale, String> getCanonicalURLMap(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		Map<Locale, String> alternateURLs = _portal.getAlternateURLs(
			_portal.getCanonicalURL(
				_portal.getLayoutFullURL(layout, themeDisplay), themeDisplay,
				layout, false, false),
			themeDisplay, layout);

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId());

		if ((layoutSEOEntry == null) ||
			!layoutSEOEntry.isCanonicalURLEnabled()) {

			return alternateURLs;
		}

		return HashMapBuilder.create(
			alternateURLs
		).putAll(
			layoutSEOEntry.getCanonicalURLMap()
		).build();
	}

	@Override
	public String getDefaultCanonicalURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		String canonicalURL = _portal.getCanonicalURL(
			_portal.getLayoutFullURL(layout, themeDisplay), themeDisplay,
			layout, false, false);

		return _getDefaultCanonicalURL(
			layout, themeDisplay.getLocale(), canonicalURL,
			_portal.getAlternateURLs(canonicalURL, themeDisplay, layout));
	}

	@Activate
	protected void activate() {
		_friendlyURLMapperProvider = new FriendlyURLMapperProvider(
			_assetDisplayPageFriendlyURLProvider, _classNameLocalService);
	}

	@Deactivate
	protected void deactivate() {
		_friendlyURLMapperProvider = null;
	}

	private String _getDefaultCanonicalURL(
			Layout layout, Locale locale, String canonicalURL,
			Map<Locale, String> alternateURLs)
		throws PortalException {

		LayoutSEOCompanyConfiguration layoutSEOCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				LayoutSEOCompanyConfiguration.class, layout.getCompanyId());

		FriendlyURLMapperProvider.FriendlyURLMapper friendlyURLMapper =
			_friendlyURLMapperProvider.getFriendlyURLMapper(
				_getHttpServletRequest());

		if (Objects.equals(
				layoutSEOCompanyConfiguration.canonicalURL(),
				"default-language-url")) {

			return friendlyURLMapper.getMappedFriendlyURL(
				canonicalURL, LocaleUtil.getDefault());
		}

		return friendlyURLMapper.getMappedFriendlyURL(
			alternateURLs.get(locale), locale);
	}

	private HttpServletRequest _getHttpServletRequest() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getRequest();
		}

		return null;
	}

	private String _getLayoutCanonicalURL(Locale locale, Layout layout) {
		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId());

		if ((layoutSEOEntry == null) ||
			!layoutSEOEntry.isCanonicalURLEnabled()) {

			return StringPool.BLANK;
		}

		return layoutSEOEntry.getCanonicalURL(locale);
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	private FriendlyURLMapperProvider _friendlyURLMapperProvider;

	@Reference
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

	@Reference
	private Portal _portal;

}