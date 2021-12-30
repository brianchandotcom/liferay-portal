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

package com.liferay.commerce.product.content.web.internal.asset.display.page.portlet;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.display.page.portlet.BaseAssetDisplayPageFriendlyURLResolver;
import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.commerce.product.configuration.CPDisplayLayoutConfiguration;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.web.internal.asset.display.page.portlet.helper.AssetDisplayPageFriendlyURLResolverHelper;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alec Sloan
 * @author Ivica Cardic
 */
@Component(enabled = false, service = FriendlyURLResolver.class)
public class AssetCategoryAssetDisplayPageFriendlyURLResolver
	extends BaseAssetDisplayPageFriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		String urlTitle = friendlyURL.substring(
			_assetDisplayPageFriendlyURLResolverHelper.getURLSeparatorLength(
				getURLSeparator()));

		urlTitle = FriendlyURLNormalizerUtil.normalizeWithEncoding(urlTitle);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				companyGroup.getGroupId(),
				_portal.getClassNameId(AssetCategory.class), urlTitle);

		if (friendlyURLEntry == null) {
			return null;
		}

		AssetCategory assetCategory = _assetCategoryService.fetchCategory(
			friendlyURLEntry.getClassPK());

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(assetCategory);

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutLocalService.fetchCPDisplayLayout(
				groupId, AssetCategory.class, assetCategory.getCategoryId());

		if ((cpDisplayLayout == null) &&
			(layoutDisplayPageObjectProvider != null) &&
			AssetDisplayPageUtil.hasAssetDisplayPage(
				groupId, layoutDisplayPageObjectProvider.getClassNameId(),
				layoutDisplayPageObjectProvider.getClassPK(),
				layoutDisplayPageObjectProvider.getClassTypeId())) {

			ThemeDisplay themeDisplay = new ThemeDisplay();

			themeDisplay.setScopeGroupId(groupId);
			themeDisplay.setSiteGroupId(groupId);

			String assetFriendlyURL =
				_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
					_portal.getClassName(
						layoutDisplayPageObjectProvider.getClassNameId()),
					layoutDisplayPageObjectProvider.getClassPK(),
					_portal.getLocale(
						(HttpServletRequest)requestContext.get("request")),
					themeDisplay);

			if (Validator.isNotNull(assetFriendlyURL)) {
				return assetFriendlyURL;
			}

			return super.getActualURL(
				companyId, groupId, privateLayout, mainPath, friendlyURL,
				params, requestContext);
		}

		return _getBasicLayoutURL(
			groupId, privateLayout, mainPath, params, requestContext,
			assetCategory);
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		String urlTitle = friendlyURL.substring(
			_assetDisplayPageFriendlyURLResolverHelper.getURLSeparatorLength(
				getURLSeparator()));

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				companyGroup.getGroupId(),
				_portal.getClassNameId(AssetCategory.class), urlTitle);

		if (friendlyURLEntry == null) {
			return null;
		}

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		HttpSession httpSession = httpServletRequest.getSession();

		Locale locale = (Locale)httpSession.getAttribute(WebKeys.LOCALE);

		if (locale == null) {
			locale = _portal.getLocale(httpServletRequest);
		}

		String languageId = LanguageUtil.getLanguageId(locale);

		if (Validator.isBlank(friendlyURLEntry.getUrlTitle(languageId))) {
			return null;
		}

		AssetCategory assetCategory = _assetCategoryService.fetchCategory(
			friendlyURLEntry.getClassPK());

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(assetCategory);

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutLocalService.fetchCPDisplayLayout(
				groupId, AssetCategory.class, assetCategory.getCategoryId());

		if ((cpDisplayLayout == null) &&
			(layoutDisplayPageObjectProvider != null) &&
			AssetDisplayPageUtil.hasAssetDisplayPage(
				groupId, layoutDisplayPageObjectProvider.getClassNameId(),
				layoutDisplayPageObjectProvider.getClassPK(),
				layoutDisplayPageObjectProvider.getClassTypeId())) {

			return super.getLayoutFriendlyURLComposite(
				companyId, groupId, privateLayout, friendlyURL, params,
				requestContext);
		}

		Layout layout = _getAssetCategoryLayout(
			groupId, privateLayout, assetCategory.getCategoryId());

		return new LayoutFriendlyURLComposite(
			layout,
			getURLSeparator() + friendlyURLEntry.getUrlTitle(languageId));
	}

	@Override
	public String getURLSeparator() {
		return _cpFriendlyURL.getAssetCategoryURLSeparator(
			CompanyThreadLocal.getCompanyId());
	}

	private Layout _getAssetCategoryLayout(
			long groupId, boolean privateLayout, long categoryId)
		throws PortalException {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutLocalService.fetchCPDisplayLayout(
				groupId, AssetCategory.class, categoryId);

		if ((cpDisplayLayout == null) ||
			Validator.isNull(cpDisplayLayout.getLayoutUuid())) {

			CommerceChannel commerceChannel =
				_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
					groupId);

			CPDisplayLayoutConfiguration cpDisplayLayoutConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					CPDisplayLayoutConfiguration.class,
					new GroupServiceSettingsLocator(
						commerceChannel.getGroupId(),
						CPConstants.RESOURCE_NAME_CP_DISPLAY_LAYOUT));

			String layoutUuid =
				cpDisplayLayoutConfiguration.assetCategoryLayoutUuid();

			if (Validator.isNotNull(layoutUuid)) {
				Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
					layoutUuid, groupId, false);

				if (layout == null) {
					layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
						layoutUuid, groupId, true);
				}

				if (layout != null) {
					return layout;
				}
			}

			long plid =
				_assetDisplayPageFriendlyURLResolverHelper.getPlidFromPortletId(
					groupId, privateLayout,
					CPPortletKeys.CP_CATEGORY_CONTENT_WEB);

			return _layoutLocalService.getLayout(plid);
		}

		return _layoutLocalService.getLayoutByUuidAndGroupId(
			cpDisplayLayout.getLayoutUuid(), groupId, privateLayout);
	}

	private String _getBasicLayoutURL(
			long groupId, boolean privateLayout, String mainPath,
			Map<String, String[]> params, Map<String, Object> requestContext,
			AssetCategory assetCategory)
		throws PortalException {

		Layout layout = _getAssetCategoryLayout(
			groupId, privateLayout, assetCategory.getCategoryId());

		String layoutActualURL = _portal.getLayoutActualURL(layout, mainPath);

		InheritableMap<String, String[]> actualParams = new InheritableMap<>();

		if (params != null) {
			actualParams.setParentMap(params);
		}

		actualParams.put(
			"p_p_id", new String[] {CPPortletKeys.CP_CATEGORY_CONTENT_WEB});
		actualParams.put("p_p_lifecycle", new String[] {"0"});
		actualParams.put("p_p_mode", new String[] {"view"});

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		httpServletRequest.setAttribute(WebKeys.ASSET_CATEGORY, assetCategory);

		String queryString = _http.parameterMapToString(actualParams, false);

		if (layoutActualURL.contains(StringPool.QUESTION)) {
			layoutActualURL =
				layoutActualURL + StringPool.AMPERSAND + queryString;
		}
		else {
			layoutActualURL =
				layoutActualURL + StringPool.QUESTION + queryString;
		}

		String languageId = LanguageUtil.getLanguageId(
			_portal.getLocale(httpServletRequest));

		_portal.addPageSubtitle(
			assetCategory.getTitle(languageId), httpServletRequest);
		_portal.addPageDescription(
			assetCategory.getDescription(languageId), httpServletRequest);

		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			AssetCategory.class.getName(), assetCategory.getPrimaryKey());

		if (!assetTags.isEmpty()) {
			_portal.addPageKeywords(
				ListUtil.toString(assetTags, AssetTag.NAME_ACCESSOR),
				httpServletRequest);
		}

		return layoutActualURL;
	}

	private LayoutDisplayPageObjectProvider<?>
		_getLayoutDisplayPageObjectProvider(AssetCategory assetCategory) {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(
					AssetCategory.class.getName());

		InfoItemReference infoItemReference = new InfoItemReference(
			AssetCategory.class.getName(), assetCategory.getCategoryId());

		return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
			infoItemReference);
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private AssetDisplayPageFriendlyURLResolverHelper
		_assetDisplayPageFriendlyURLResolverHelper;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

	@Reference
	private CPFriendlyURL _cpFriendlyURL;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}