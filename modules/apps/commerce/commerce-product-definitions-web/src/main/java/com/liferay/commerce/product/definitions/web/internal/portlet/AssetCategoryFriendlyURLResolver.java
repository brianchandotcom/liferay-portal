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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(enabled = false, service = FriendlyURLResolver.class)
public class AssetCategoryFriendlyURLResolver implements FriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		String languageId = LanguageUtil.getLanguageId(
			_portal.getLocale(httpServletRequest));

		long classNameId = _portal.getClassNameId(AssetCategory.class);

		String urlTitle = friendlyURL.substring(
			CPConstants.SEPARATOR_ASSET_CATEGORY_URL.length());

		urlTitle = FriendlyURLNormalizerUtil.normalizeWithEncoding(urlTitle);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				companyGroup.getGroupId(), classNameId, urlTitle);

		if (friendlyURLEntry == null) {
			return null;
		}

		AssetCategory assetCategory = _assetCategoryService.fetchCategory(
			friendlyURLEntry.getClassPK());

		if (assetCategory == null) {
			return null;
		}

		Layout layout = getAssetCategoryLayout(
			groupId, privateLayout, friendlyURLEntry.getClassPK());

		String layoutActualURL = _portal.getLayoutActualURL(layout, mainPath);

		InheritableMap<String, String[]> actualParams = new InheritableMap<>();

		if (params != null) {
			actualParams.setParentMap(params);
		}

		actualParams.put(
			"p_p_id", new String[] {CPPortletKeys.CP_CATEGORY_CONTENT_WEB});
		actualParams.put("p_p_lifecycle", new String[] {"0"});
		actualParams.put("p_p_mode", new String[] {"view"});

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

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		String languageId = LanguageUtil.getLanguageId(
			_portal.getLocale(httpServletRequest));

		long classNameId = _portal.getClassNameId(AssetCategory.class);

		String urlTitle = friendlyURL.substring(
			CPConstants.SEPARATOR_ASSET_CATEGORY_URL.length());

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				companyGroup.getGroupId(), classNameId, urlTitle);

		if (friendlyURLEntry == null) {
			return null;
		}

		AssetCategory assetCategory = _assetCategoryService.fetchCategory(
			friendlyURLEntry.getClassPK());

		if (assetCategory == null) {
			return null;
		}

		Layout layout = getAssetCategoryLayout(
			groupId, privateLayout, friendlyURLEntry.getClassPK());

		return new LayoutFriendlyURLComposite(
			layout,
			getURLSeparator() + friendlyURLEntry.getUrlTitle(languageId));
	}

	@Override
	public String getURLSeparator() {
		return CPConstants.SEPARATOR_ASSET_CATEGORY_URL;
	}

	protected Layout getAssetCategoryLayout(
			long groupId, boolean privateLayout, long categoryId)
		throws PortalException {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutLocalService.fetchCPDisplayLayout(
				AssetCategory.class, categoryId);

		if ((cpDisplayLayout == null) ||
			Validator.isNull(cpDisplayLayout.getLayoutUuid())) {

			long plid = _portal.getPlidFromPortletId(
				groupId, privateLayout, CPPortletKeys.CP_CATEGORY_CONTENT_WEB);

			return _layoutLocalService.getLayout(plid);
		}

		return _layoutLocalService.getLayoutByUuidAndGroupId(
			cpDisplayLayout.getLayoutUuid(), groupId, privateLayout);
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

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