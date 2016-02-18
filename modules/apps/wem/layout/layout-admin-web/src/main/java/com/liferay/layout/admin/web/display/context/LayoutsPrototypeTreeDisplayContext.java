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

package com.liferay.layout.admin.web.display.context;

import com.liferay.layout.admin.web.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Julio Camarero
 */
public class LayoutsPrototypeTreeDisplayContext
	extends BaseLayoutDisplayContext {

	public LayoutsPrototypeTreeDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		super(liferayPortletRequest, liferayPortletResponse);
	}

	public String getEditLayoutURL() {
		PortletURL editLayoutURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, LayoutAdminPortletKeys.LAYOUT_PROTOTYPE_PAGE,
			PortletRequest.RENDER_PHASE);

		Group group = getSelGroup();

		editLayoutURL.setParameter(
			"groupId", String.valueOf(group.getGroupId()));

		Layout layout = getLayout();

		editLayoutURL.setParameter("selPlid", String.valueOf(layout.getPlid()));

		editLayoutURL.setParameter("privateLayout", Boolean.TRUE.toString());
		editLayoutURL.setParameter("viewLayout", Boolean.TRUE.toString());

		return editLayoutURL.toString();
	}

	public Layout getLayout() {
		if (_layout != null) {
			return _layout;
		}

		Group group = getSelGroup();

		_layout = LayoutLocalServiceUtil.fetchFirstLayout(
			group.getGroupId(), true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		return _layout;
	}

	public String getLayoutName() throws PortalException {
		Layout layout = getLayout();

		return layout.getName(themeDisplay.getLocale());
	}

	public String getLayoutURL() throws PortalException {
		Layout layout = getLayout();

		return layout.getRegularURL(
			PortalUtil.getHttpServletRequest(liferayPortletRequest));
	}

	public boolean isLayoutSelected() {
		Layout layout = getLayout();

		if ((layout.getPlid() == themeDisplay.getPlid()) ||
			Validator.equals(
				LayoutAdminPortletKeys.LAYOUT_PROTOTYPE_PAGE,
				themeDisplay.getPpid())) {

			return true;
		}

		return false;
	}

	private Layout _layout;

}