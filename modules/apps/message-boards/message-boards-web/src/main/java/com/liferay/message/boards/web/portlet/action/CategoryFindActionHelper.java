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

package com.liferay.message.boards.web.portlet.action;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.struts.BaseFindActionHelper;
import com.liferay.portal.struts.BasePortletPageFinder;
import com.liferay.portal.struts.FindActionHelper;
import com.liferay.portal.struts.PortletPageFinder;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portlet.messageboards.model.MBCategory",
	service = FindActionHelper.class
)
public class CategoryFindActionHelper extends BaseFindActionHelper {

	@Override
	public long getGroupId(long primaryKey) throws Exception {
		MBCategory category = MBCategoryLocalServiceUtil.getCategory(
			primaryKey);

		return category.getGroupId();
	}

	@Override
	public String getPrimaryKeyParameterName() {
		return "mbCategoryId";
	}

	@Override
	public String[] initPortletIds() {
		return _PORTLET_IDS;
	}

	@Override
	public PortletURL processPortletURL(
			HttpServletRequest request, PortletURL portletURL)
		throws Exception {

		return portletURL;
	}

	@Override
	public void setPrimaryKeyParameter(PortletURL portletURL, long primaryKey)
		throws Exception {

		portletURL.setParameter(
			getPrimaryKeyParameterName(), String.valueOf(primaryKey));
	}

	@Override
	protected void addRequiredParameters(
		HttpServletRequest request, String portletId, PortletURL portletURL) {

		portletURL.setParameter(
			"mvcRenderCommandName", "/message_boards/view_category");
	}

	@Override
	protected PortletPageFinder getPortletPageFinder() {
		return new CategoryPortletPageFinder();
	}

	// Order is important. See LPS-23770.

	private static final String[] _PORTLET_IDS = new String[] {
		MBPortletKeys.MESSAGE_BOARDS_ADMIN, MBPortletKeys.MESSAGE_BOARDS
	};

	private static class CategoryPortletPageFinder
		extends BasePortletPageFinder {

		@Override
		protected String[] getPortletIds() {
			return _PORTLET_IDS;
		}

	}

}