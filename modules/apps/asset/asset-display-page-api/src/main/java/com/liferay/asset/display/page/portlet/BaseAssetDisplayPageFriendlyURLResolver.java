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

package com.liferay.asset.display.page.portlet;

import com.liferay.asset.display.page.configuration.AssetDisplayPageConfiguration;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.util.AssetHelper;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.info.display.url.provider.InfoEditURLProviderTracker;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
public abstract class BaseAssetDisplayPageFriendlyURLResolver
	implements FriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		InfoDisplayContributor infoDisplayContributor =
			_getInfoDisplayContributor(friendlyURL);

		InfoDisplayObjectProvider infoDisplayObjectProvider =
			_getInfoDisplayObjectProvider(
				infoDisplayContributor, groupId, friendlyURL);

		if (infoDisplayObjectProvider != null) {
			httpServletRequest.setAttribute(
				AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER,
				infoDisplayObjectProvider);

			InfoEditURLProvider infoEditURLProvider =
				infoEditURLProviderTracker.getInfoEditURLProvider(
					portal.getClassName(
						infoDisplayObjectProvider.getClassNameId()));

			httpServletRequest.setAttribute(
				AssetDisplayPageWebKeys.INFO_EDIT_URL_PROVIDER,
				infoEditURLProvider);
		}

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR,
			infoDisplayContributor);

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.VERSION_CLASS_PK,
			_getVersionClassPK(friendlyURL));

		Locale locale = portal.getLocale(httpServletRequest);

		portal.setPageDescription(
			HtmlUtil.unescape(
				HtmlUtil.stripHtml(
					infoDisplayObjectProvider.getDescription(locale))),
			httpServletRequest);
		portal.setPageKeywords(
			infoDisplayObjectProvider.getKeywords(locale), httpServletRequest);
		portal.setPageTitle(
			infoDisplayObjectProvider.getTitle(locale), httpServletRequest);

		AssetEntry assetEntry = _getAssetEntry(infoDisplayObjectProvider);

		httpServletRequest.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);

		Layout layout = _getInfoDisplayObjectProviderLayout(
			infoDisplayObjectProvider);

		return portal.getLayoutActualURL(layout, mainPath);
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		InfoDisplayContributor infoDisplayContributor =
			_getInfoDisplayContributor(friendlyURL);

		InfoDisplayObjectProvider infoDisplayObjectProvider =
			_getInfoDisplayObjectProvider(
				infoDisplayContributor, groupId, friendlyURL);

		if (infoDisplayObjectProvider == null) {
			throw new PortalException();
		}

		Layout layout = _getInfoDisplayObjectProviderLayout(
			infoDisplayObjectProvider);

		return new LayoutFriendlyURLComposite(layout, friendlyURL);
	}

	@Reference
	protected AssetDisplayPageEntryLocalService
		assetDisplayPageEntryLocalService;

	@Reference
	protected AssetEntryService assetEntryLocalService;

	@Reference
	protected AssetHelper assetHelper;

	@Reference
	protected InfoDisplayContributorTracker infoDisplayContributorTracker;

	@Reference
	protected InfoEditURLProviderTracker infoEditURLProviderTracker;

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected LayoutPageTemplateEntryService layoutPageTemplateEntryService;

	@Reference
	protected Portal portal;

	private AssetEntry _getAssetEntry(
		InfoDisplayObjectProvider infoDisplayObjectProvider) {

		String classNameId = PortalUtil.getClassName(
			infoDisplayObjectProvider.getClassNameId());

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				classNameId);

		if (assetRendererFactory == null) {
			return null;
		}

		long classPK = infoDisplayObjectProvider.getClassPK();

		try {
			AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
				classNameId, classPK);
			AssetDisplayPageConfiguration assetDisplayPageConfiguration =
				ConfigurationProviderUtil.getSystemConfiguration(
					AssetDisplayPageConfiguration.class);

			if ((assetEntry != null) &&
				assetDisplayPageConfiguration.enableIncrementViewCounter()) {

				assetEntryLocalService.incrementViewCounter(assetEntry);
			}

			return assetEntry;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return null;
	}

	private InfoDisplayContributor _getInfoDisplayContributor(
			String friendlyURL)
		throws PortalException {

		String infoURLSeparator = _getInfoURLSeparator(friendlyURL);

		InfoDisplayContributor infoDisplayContributor =
			infoDisplayContributorTracker.
				getInfoDisplayContributorByURLSeparator(infoURLSeparator);

		if (infoDisplayContributor == null) {
			throw new PortalException(
				"Info display contributor is not available for " +
					infoURLSeparator);
		}

		return infoDisplayContributor;
	}

	private InfoDisplayObjectProvider _getInfoDisplayObjectProvider(
			InfoDisplayContributor infoDisplayContributor, long groupId,
			String friendlyURL)
		throws PortalException {

		return infoDisplayContributor.getInfoDisplayObjectProvider(
			groupId, _getUrlTitle(friendlyURL));
	}

	private Layout _getInfoDisplayObjectProviderLayout(
		InfoDisplayObjectProvider infoDisplayObjectProvider) {

		AssetDisplayPageEntry assetDisplayPageEntry =
			assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				infoDisplayObjectProvider.getGroupId(),
				infoDisplayObjectProvider.getClassNameId(),
				infoDisplayObjectProvider.getClassPK());

		if (assetDisplayPageEntry == null) {
			return null;
		}

		if (assetDisplayPageEntry.getType() !=
				AssetDisplayPageConstants.TYPE_DEFAULT) {

			return layoutLocalService.fetchLayout(
				assetDisplayPageEntry.getPlid());
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryService.fetchDefaultLayoutPageTemplateEntry(
				infoDisplayObjectProvider.getGroupId(),
				infoDisplayObjectProvider.getClassNameId(),
				infoDisplayObjectProvider.getClassTypeId());

		if (layoutPageTemplateEntry != null) {
			return layoutLocalService.fetchLayout(
				layoutPageTemplateEntry.getPlid());
		}

		return null;
	}

	private String _getInfoURLSeparator(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		return CharPool.SLASH + paths.get(0) + CharPool.SLASH;
	}

	private String _getUrlTitle(String friendlyURL) {
		String infoURLSeparator = _getInfoURLSeparator(friendlyURL);

		String urlTitle = friendlyURL.substring(infoURLSeparator.length());

		long versionClassPK = _getVersionClassPK(friendlyURL);

		if (versionClassPK > 0) {
			String versionClassPKValue = String.valueOf(versionClassPK);

			urlTitle = friendlyURL.substring(
				infoURLSeparator.length(),
				friendlyURL.length() - versionClassPKValue.length() - 1);
		}

		return urlTitle;
	}

	private long _getVersionClassPK(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		if (paths.size() < 3) {
			return 0;
		}

		return GetterUtil.getLong(paths.get(paths.size() - 1));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAssetDisplayPageFriendlyURLResolver.class);

}