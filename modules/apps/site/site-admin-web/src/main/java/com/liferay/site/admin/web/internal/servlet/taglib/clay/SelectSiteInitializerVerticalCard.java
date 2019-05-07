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

package com.liferay.site.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.admin.web.internal.constants.SiteAdminConstants;
import com.liferay.site.admin.web.internal.util.SiteInitializerItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectSiteInitializerVerticalCard implements VerticalCard {

	public SelectSiteInitializerVerticalCard(
		SiteInitializerItem siteInitializerItem, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_siteInitializerItem = siteInitializerItem;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	@Override
	public Map<String, String> getData() {
		long parentGroupId = ParamUtil.getLong(
			_httpServletRequest, "parentGroupId");

		Map<String, String> data = new HashMap<>();

		PortletURL addSiteURL = _renderResponse.createActionURL();

		addSiteURL.setParameter(ActionRequest.ACTION_NAME, "addGroup");

		addSiteURL.setParameter(
			"mvcPath", "/select_layout_set_prototype_entry.jsp");
		addSiteURL.setParameter("parentGroupId", String.valueOf(parentGroupId));
		addSiteURL.setParameter("creationType", _siteInitializerItem.getType());
		addSiteURL.setParameter(
			"siteInitializerKey", _siteInitializerItem.getSiteInitializerKey());

		data.put("add-site-url", addSiteURL.toString());

		String checkboxFieldName = StringPool.BLANK;

		if (Objects.equals(
				_siteInitializerItem.getType(),
				SiteAdminConstants.CREATION_TYPE_SITE_TEMPLATE)) {

			checkboxFieldName = "layoutSetVisibilityPrivate";
		}

		data.put("checkbox-field-name", checkboxFieldName);

		data.put(
			"layout-set-prototype-id",
			String.valueOf(_siteInitializerItem.getLayoutSetPrototypeId()));

		return data;
	}

	@Override
	public String getElementClasses() {
		return "add-site-action-option card-interactive " +
			"card-interactive-secondary";
	}

	@Override
	public String getIcon() {
		return "site-template";
	}

	@Override
	public String getImageSrc() {
		if (_siteInitializerItem.isCreationTypeSiteTemplate()) {
			return null;
		}

		if (Validator.isNull(_siteInitializerItem.getIcon())) {
			return null;
		}

		return _siteInitializerItem.getIcon();
	}

	@Override
	public String getTitle() {
		return _siteInitializerItem.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;
	private final SiteInitializerItem _siteInitializerItem;

}