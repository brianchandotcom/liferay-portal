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
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPFriendlyURLEntry;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = FriendlyURLResolver.class)
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

		String urlTitle = friendlyURL.substring(
			CPConstants.SEPARATOR_PRODUCT_URL.length());

		long classNameId = _portal.getClassNameId(CPDefinition.class);

		CPFriendlyURLEntry cpFriendlyURLEntry =
			_cpFriendlyURLEntryLocalService.getCPFriendlyURLEntry(
				groupId, classNameId, languageId, urlTitle);

		if (!cpFriendlyURLEntry.isMain()) {
			cpFriendlyURLEntry =
				_cpFriendlyURLEntryLocalService.fetchCPFriendlyURLEntry(
					groupId, classNameId, cpFriendlyURLEntry.getPrimaryKey(),
					languageId, true);
		}

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpFriendlyURLEntry.getClassPK());

		if ((cpDefinition != null) && !cpDefinition.isApproved()) {
			throw new NoSuchCPDefinitionException(
				"{cpDefinitionId=" + cpFriendlyURLEntry.getClassPK() + "}");
		}

		Layout layout = getProductLayout(
			groupId, privateLayout, cpDefinition.getCPDefinitionId());

		String layoutActualURL = _portal.getLayoutActualURL(layout, mainPath);

		InheritableMap<String, String[]> actualParams = new InheritableMap<>();

		if (params != null) {
			actualParams.setParentMap(params);
		}

		actualParams.put("p_p_lifecycle", new String[] {"0"});
		actualParams.put("p_p_mode", new String[] {"view"});

		httpServletRequest.setAttribute(CPWebKeys.CP_DEFINITION, cpDefinition);

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
			cpDefinition.getTitle(languageId), httpServletRequest);
		_portal.addPageDescription(
			cpDefinition.getShortDescription(languageId), httpServletRequest);

		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			CPDefinition.class.getName(), cpDefinition.getPrimaryKey());

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

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		Locale locale = _portal.getLocale(httpServletRequest);

		String languageId = LanguageUtil.getLanguageId(locale);

		String urlTitle = friendlyURL.substring(
			CPConstants.SEPARATOR_PRODUCT_URL.length());
		long classNameId = _portal.getClassNameId(CPDefinition.class);

		CPFriendlyURLEntry cpFriendlyURLEntry =
			_cpFriendlyURLEntryLocalService.getCPFriendlyURLEntry(
				groupId, classNameId, languageId, urlTitle);

		if (!cpFriendlyURLEntry.isMain()) {
			cpFriendlyURLEntry =
				_cpFriendlyURLEntryLocalService.fetchCPFriendlyURLEntry(
					groupId, classNameId, cpFriendlyURLEntry.getPrimaryKey(),
					languageId, true);
		}

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpFriendlyURLEntry.getClassPK());

		Layout layout = getProductLayout(
			groupId, privateLayout, cpDefinition.getCPDefinitionId());

		return new LayoutFriendlyURLComposite(
			layout, getURLSeparator() + cpFriendlyURLEntry.getUrlTitle());
	}

	@Override
	public String getURLSeparator() {
		return CPConstants.SEPARATOR_PRODUCT_URL;
	}

	protected Layout getProductLayout(
			long groupId, boolean privateLayout, long cpDefinitionId)
		throws PortalException {

		String layoutUuid = _cpDefinitionService.getLayoutUuid(cpDefinitionId);

		if (Validator.isNotNull(layoutUuid)) {
			return _layoutLocalService.getLayoutByUuidAndGroupId(
				layoutUuid, groupId, privateLayout);
		}

		long plid = _portal.getPlidFromPortletId(
			groupId, privateLayout, CPPortletKeys.CP_CONTENT_WEB);

		return _layoutLocalService.getLayout(plid);
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPFriendlyURLEntryLocalService _cpFriendlyURLEntryLocalService;

	@Reference
	private Http _http;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}