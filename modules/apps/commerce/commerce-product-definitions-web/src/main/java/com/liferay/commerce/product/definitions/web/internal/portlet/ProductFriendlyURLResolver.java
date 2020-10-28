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

package com.liferay.commerce.product.definitions.web.internal.portlet;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = FriendlyURLResolver.class)
public class ProductFriendlyURLResolver implements FriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		Locale locale = _portal.getLocale(httpServletRequest);

		String languageId = LanguageUtil.getLanguageId(locale);

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		long classNameId = _portal.getClassNameId(CProduct.class);

		String urlTitle = friendlyURL.substring(
			CPConstants.SEPARATOR_PRODUCT_URL.length());

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				companyGroup.getGroupId(), classNameId, urlTitle);

		if (friendlyURLEntry == null) {
			return null;
		}

		CProduct cProduct = _cProductLocalService.getCProduct(
			friendlyURLEntry.getClassPK());

		CPCatalogEntry cpCatalogEntry = _cpDefinitionHelper.getCPCatalogEntry(
			_getCommerceAccountId(groupId, httpServletRequest), groupId,
			cProduct.getPublishedCPDefinitionId(), locale);

		Layout layout = getProductLayout(
			groupId, privateLayout, cpCatalogEntry.getCPDefinitionId());

		String layoutActualURL = _portal.getLayoutActualURL(layout, mainPath);

		InheritableMap<String, String[]> actualParams = new InheritableMap<>();

		if (params != null) {
			actualParams.setParentMap(params);
		}

		actualParams.put("p_p_lifecycle", new String[] {"0"});
		actualParams.put("p_p_mode", new String[] {"view"});

		httpServletRequest.setAttribute(
			CPWebKeys.CP_CATALOG_ENTRY, cpCatalogEntry);

		String queryString = _http.parameterMapToString(actualParams, false);

		if (layoutActualURL.contains(StringPool.QUESTION)) {
			layoutActualURL =
				layoutActualURL + StringPool.AMPERSAND + queryString;
		}
		else {
			layoutActualURL =
				layoutActualURL + StringPool.QUESTION + queryString;
		}

		String description = cpCatalogEntry.getMetaDescription(languageId);

		if (Validator.isNull(description)) {
			description = cpCatalogEntry.getShortDescription();
		}

		if (Validator.isNotNull(description)) {
			_portal.addPageDescription(description, httpServletRequest);
		}

		String keywords = cpCatalogEntry.getMetaKeywords(languageId);

		if (Validator.isNull(keywords)) {
			List<AssetTag> assetTags = _assetTagLocalService.getTags(
				CPDefinition.class.getName(),
				cpCatalogEntry.getCPDefinitionId());

			if (ListUtil.isNotEmpty(assetTags)) {
				keywords = ListUtil.toString(assetTags, AssetTag.NAME_ACCESSOR);
			}
		}

		if (Validator.isNotNull(keywords)) {
			_portal.addPageKeywords(keywords, httpServletRequest);
		}

		String subtitle = cpCatalogEntry.getMetaTitle(languageId);

		if (Validator.isNull(subtitle)) {
			subtitle = cpCatalogEntry.getName();
		}

		_portal.addPageSubtitle(subtitle, httpServletRequest);

		return layoutActualURL;
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		String languageId = LanguageUtil.getLanguageId(
			_portal.getLocale(httpServletRequest));

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		String urlTitle = friendlyURL.substring(
			CPConstants.SEPARATOR_PRODUCT_URL.length());

		long classNameId = _portal.getClassNameId(CProduct.class);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				companyGroup.getGroupId(), classNameId, urlTitle);

		if (friendlyURLEntry == null) {
			return null;
		}

		if (Validator.isBlank(friendlyURLEntry.getUrlTitle(languageId))) {
			return null;
		}

		CProduct cProduct = _cProductLocalService.getCProduct(
			friendlyURLEntry.getClassPK());

		Layout layout = getProductLayout(
			groupId, privateLayout, cProduct.getPublishedCPDefinitionId());

		return new LayoutFriendlyURLComposite(
			layout,
			getURLSeparator() + friendlyURLEntry.getUrlTitle(languageId));
	}

	@Override
	public String getURLSeparator() {
		return CPConstants.SEPARATOR_PRODUCT_URL;
	}

	protected Layout getProductLayout(
			long groupId, boolean privateLayout, long cpDefinitionId)
		throws PortalException {

		String layoutUuid = _cpDefinitionLocalService.getLayoutUuid(
			cpDefinitionId);

		if (Validator.isNotNull(layoutUuid)) {
			return _layoutLocalService.getLayoutByUuidAndGroupId(
				layoutUuid, groupId, privateLayout);
		}

		// Use custom implementation instead of _portal.getPlidFromPortletId to
		// work around LPS-101469. Switch back after 7.2 SP1 is released.

		long plid = _getPlidFromPortletId(
			groupId, privateLayout, CPPortletKeys.CP_CONTENT_WEB);

		try {
			return _layoutLocalService.getLayout(plid);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			throw portalException;
		}
	}

	private long _getCommerceAccountId(
			long groupId, HttpServletRequest httpServletRequest)
		throws PortalException {

		// Passing the groupId is mandatory here. See COMMERCE-728.

		CommerceAccount commerceAccount =
			_commerceAccountHelper.getCurrentCommerceAccount(
				_commerceChannelLocalService.
					getCommerceChannelGroupIdBySiteGroupId(groupId),
				httpServletRequest);

		long commerceAccountId = 0;

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		return commerceAccountId;
	}

	private long _getPlidFromPortletId(
		List<Layout> layouts, String portletId, long scopeGroupId) {

		for (Layout layout : layouts) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			if (_hasNonstaticPortletId(layoutTypePortlet, portletId) &&
				(_portal.getScopeGroupId(layout, portletId) == scopeGroupId)) {

				return layout.getPlid();
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	private long _getPlidFromPortletId(
		long groupId, boolean privateLayout, String portletId) {

		long scopeGroupId = groupId;

		try {
			Group group = _groupLocalService.getGroup(groupId);

			if (group.isLayout()) {
				Layout scopeLayout = _layoutLocalService.getLayout(
					group.getClassPK());

				groupId = scopeLayout.getGroupId();
			}

			String[] validLayoutTypes = {
				LayoutConstants.TYPE_PORTLET,
				LayoutConstants.TYPE_FULL_PAGE_APPLICATION,
				LayoutConstants.TYPE_PANEL
			};

			for (String layoutType : validLayoutTypes) {
				List<Layout> layouts = _layoutLocalService.getLayouts(
					groupId, privateLayout, layoutType);

				long plid = _getPlidFromPortletId(
					layouts, portletId, scopeGroupId);

				if (plid != LayoutConstants.DEFAULT_PLID) {
					return plid;
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	private boolean _hasNonstaticPortletId(
		LayoutTypePortlet layoutTypePortlet, String portletId) {

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		for (String columnId : layoutTemplate.getColumns()) {
			String[] columnValues = StringUtil.split(
				layoutTypePortlet.getTypeSettingsProperty(columnId));

			for (String nonstaticPortletId : columnValues) {
				if (nonstaticPortletId.equals(portletId)) {
					return true;
				}

				String decodedNonstaticPortletName =
					PortletIdCodec.decodePortletName(nonstaticPortletId);

				if (decodedNonstaticPortletName.equals(portletId)) {
					return true;
				}
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductFriendlyURLResolver.class);

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CProductLocalService _cProductLocalService;

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

	@Reference
	private UserLocalService _userLocalService;

}