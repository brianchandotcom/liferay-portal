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

package com.liferay.layout.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class CollectionProvidersVerticalCard extends BaseVerticalCard {

	public CollectionProvidersVerticalCard(
		long groupId, InfoListProvider infoListProvider,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(null, renderRequest, null);

		_groupId = groupId;
		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_infoListProvider = infoListProvider;
		_renderResponse = renderResponse;
	}

	@Override
	public String getAspectRatioCssClasses() {
		return "aspect-ratio-item-center-middle " +
			"aspect-ratio-item-vertical-fluid";
	}

	@Override
	public String getElementClasses() {
		return "card-interactive card-interactive-secondary";
	}

	public String getHref() {
		long selPlid = ParamUtil.getLong(_httpServletRequest, "selPlid");
		boolean privateLayout = ParamUtil.getBoolean(
			_httpServletRequest, "privateLayout");

		PortletURL selectLayoutMasterLayoutURL =
			_renderResponse.createRenderURL();

		selectLayoutMasterLayoutURL.setParameter(
			"mvcPath", "/select_layout_master_layout.jsp");

		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		selectLayoutMasterLayoutURL.setParameter("redirect", redirect);

		selectLayoutMasterLayoutURL.setParameter(
			"backURL", themeDisplay.getURLCurrent());
		selectLayoutMasterLayoutURL.setParameter(
			"groupId", String.valueOf(_groupId));
		selectLayoutMasterLayoutURL.setParameter(
			"selPlid", String.valueOf(selPlid));
		selectLayoutMasterLayoutURL.setParameter(
			"privateLayout", String.valueOf(privateLayout));
		selectLayoutMasterLayoutURL.setParameter(
			"privateLayout", String.valueOf(privateLayout));
		selectLayoutMasterLayoutURL.setParameter(
			"collectionType",
			InfoListProviderItemSelectorReturnType.class.getName());
		selectLayoutMasterLayoutURL.setParameter(
			"collectionPK", String.valueOf(_infoListProvider.getKey()));

		return selectLayoutMasterLayoutURL.toString();
	}

	@Override
	public String getIcon() {
		return "list";
	}

	@Override
	public String getImageSrc() {
		return StringPool.BLANK;
	}

	@Override
	public String getSubtitle() {
		String className = _getClassName(_infoListProvider);

		if (Validator.isNotNull(className)) {
			return ResourceActionsUtil.getModelResource(
				themeDisplay.getLocale(), className);
		}

		return StringPool.BLANK;
	}

	@Override
	public String getTitle() {
		return _infoListProvider.getLabel(themeDisplay.getLocale());
	}

	private String _getClassName(InfoListProvider infoListProvider) {
		Class<?> clazz = infoListProvider.getClass();

		Type[] genericInterfaceTypes = clazz.getGenericInterfaces();

		for (Type genericInterfaceType : genericInterfaceTypes) {
			ParameterizedType parameterizedType =
				(ParameterizedType)genericInterfaceType;

			Class<?> typeClazz =
				(Class<?>)parameterizedType.getActualTypeArguments()[0];

			return typeClazz.getName();
		}

		return StringPool.BLANK;
	}

	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final InfoListProvider _infoListProvider;
	private final RenderResponse _renderResponse;

}