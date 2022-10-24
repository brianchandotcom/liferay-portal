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

import com.liferay.asset.display.page.portlet.BaseAssetDisplayPageFriendlyURLResolver;
import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.configuration.CPDisplayLayoutConfiguration;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alec Sloan
 * @author Ivica Cardic
 */
@Component(service = FriendlyURLResolver.class)
public class CPDefinitionAssetDisplayPageFriendlyURLResolver
	extends BaseAssetDisplayPageFriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		long classNameId = _portal.getClassNameId(CProduct.class);

		String urlSeparator = getURLSeparator();

		String urlTitle = friendlyURL.substring(urlSeparator.length());

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				companyGroup.getGroupId(), classNameId, urlTitle);

		if (friendlyURLEntry == null) {
			return null;
		}

		CProduct cProduct = _cProductLocalService.getCProduct(
			friendlyURLEntry.getClassPK());

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cProduct.getPublishedCPDefinitionId());

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		CPCatalogEntry cpCatalogEntry = _cpDefinitionHelper.getCPCatalogEntry(
			_getCommerceAccountId(groupId, httpServletRequest), groupId,
			cpDefinition.getCPDefinitionId(),
			_portal.getLocale(httpServletRequest));

		httpServletRequest.setAttribute(
			CPWebKeys.CP_CATALOG_ENTRY, cpCatalogEntry);

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(cpDefinition);

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutLocalService.fetchCPDisplayLayout(
				groupId, CPDefinition.class, cpDefinition.getCPDefinitionId());

		if ((cpDisplayLayout == null) &&
			(layoutDisplayPageObjectProvider != null) &&
			AssetDisplayPageUtil.hasAssetDisplayPage(
				groupId, layoutDisplayPageObjectProvider.getClassNameId(),
				layoutDisplayPageObjectProvider.getClassPK(),
				layoutDisplayPageObjectProvider.getClassTypeId())) {

			return super.getActualURL(
				companyId, groupId, privateLayout, mainPath, friendlyURL,
				params, requestContext);
		}

		return _getBasicLayoutURL(
			groupId, privateLayout, mainPath, params, requestContext,
			cpDefinition);
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		String urlSeparator = getURLSeparator();

		String urlTitle = friendlyURL.substring(urlSeparator.length());

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				companyGroup.getGroupId(),
				_portal.getClassNameId(CProduct.class), urlTitle);

		if (friendlyURLEntry == null) {
			return null;
		}

		String languageId = _language.getLanguageId(getLocale(requestContext));

		if (Validator.isBlank(friendlyURLEntry.getUrlTitle(languageId))) {
			return null;
		}

		CProduct cProduct = _cProductLocalService.getCProduct(
			friendlyURLEntry.getClassPK());

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cProduct.getPublishedCPDefinitionId());

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(cpDefinition);

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutLocalService.fetchCPDisplayLayout(
				groupId, CPDefinition.class, cpDefinition.getCPDefinitionId());

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

		Layout layout = _getProductLayout(
			groupId, privateLayout, cpDefinition.getCPDefinitionId());

		return new LayoutFriendlyURLComposite(
			layout,
			getURLSeparator() + friendlyURLEntry.getUrlTitle(languageId),
			false);
	}

	@Override
	public String getURLSeparator() {
		return _cpFriendlyURL.getProductURLSeparator(
			CompanyThreadLocal.getCompanyId());
	}

	private String _getBasicLayoutURL(
			long groupId, boolean privateLayout, String mainPath,
			Map<String, String[]> params, Map<String, Object> requestContext,
			CPDefinition cpDefinition)
		throws PortalException {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		Locale locale = _portal.getLocale(httpServletRequest);

		CPCatalogEntry cpCatalogEntry = _cpDefinitionHelper.getCPCatalogEntry(
			_getCommerceAccountId(groupId, httpServletRequest), groupId,
			cpDefinition.getCPDefinitionId(), locale);

		Layout layout = _getProductLayout(
			groupId, privateLayout, cpCatalogEntry.getCPDefinitionId());

		String layoutActualURL = _portal.getLayoutActualURL(layout, mainPath);

		InheritableMap<String, String[]> actualParams = new InheritableMap<>();

		if (params != null) {
			actualParams.setParentMap(params);
		}

		actualParams.put("p_p_lifecycle", new String[] {"0"});
		actualParams.put("p_p_mode", new String[] {"view"});

		String queryString = HttpComponentsUtil.parameterMapToString(
			actualParams, false);

		if (layoutActualURL.contains(StringPool.QUESTION)) {
			layoutActualURL =
				layoutActualURL + StringPool.AMPERSAND + queryString;
		}
		else {
			layoutActualURL =
				layoutActualURL + StringPool.QUESTION + queryString;
		}

		String languageId = _language.getLanguageId(locale);

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

	private long _getCommerceAccountId(
			long groupId, HttpServletRequest httpServletRequest)
		throws PortalException {

		long commerceAccountId = CommerceAccountConstants.ACCOUNT_ID_GUEST;

		CommerceAccount commerceAccount =
			_commerceAccountHelper.getCurrentCommerceAccount(
				_commerceChannelLocalService.
					getCommerceChannelGroupIdBySiteGroupId(groupId),
				httpServletRequest);

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		return commerceAccountId;
	}

	private LayoutDisplayPageObjectProvider<?>
		_getLayoutDisplayPageObjectProvider(CPDefinition cpDefinition) {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(
					CPDefinition.class.getName());

		InfoItemReference infoItemReference = new InfoItemReference(
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
			infoItemReference);
	}

	private Layout _getProductLayout(
			long groupId, boolean privateLayout, long cpDefinitionId)
		throws PortalException {

		String layoutUuid = _cpDefinitionLocalService.getLayoutUuid(
			groupId, cpDefinitionId);

		if (Validator.isNotNull(layoutUuid)) {
			return _layoutLocalService.getLayoutByUuidAndGroupId(
				layoutUuid, groupId, privateLayout);
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				groupId);

		CPDisplayLayoutConfiguration cpDisplayLayoutConfiguration =
			_configurationProvider.getConfiguration(
				CPDisplayLayoutConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
					CPConstants.RESOURCE_NAME_CP_DISPLAY_LAYOUT));

		layoutUuid = cpDisplayLayoutConfiguration.productLayoutUuid();

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

		long plid = _portal.getPlidFromPortletId(
			groupId, privateLayout, CPPortletKeys.CP_CONTENT_WEB);

		try {
			return _layoutLocalService.getLayout(plid);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			throw portalException;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionAssetDisplayPageFriendlyURLResolver.class);

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

	@Reference
	private CPFriendlyURL _cpFriendlyURL;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}