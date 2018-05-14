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

package com.liferay.staging.processes.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Alius
 */
public class StagingProcessesWebPublishTemplatesToolbarDisplayContext {

	public StagingProcessesWebPublishTemplatesToolbarDisplayContext(
		HttpServletRequest request, PageContext pageContext,
		LiferayPortletResponse portletResponse) {

		_request = request;

		_pageContext = pageContext;

		_portletResponse = portletResponse;
	}

	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				Group stagingGroup = (Group)_pageContext.getAttribute(
					"stagingGroup");

				long stagingGroupId = stagingGroup.getGroupId();

				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							getRenderURL(), "mvcRenderCommandName",
							"editPublishConfiguration", "groupId",
							String.valueOf(stagingGroupId), "layoutSetBranchId",
							ParamUtil.getString(_request, "layoutSetBranchId"),
							"layoutSetBranchName",
							ParamUtil.getString(_request, "layoutSetBranchName"),
							"privateLayout", Boolean.FALSE.toString());

						dropdownItem.setLabel(
							LanguageUtil.get(_request, "new"));
					});
			}
		};
	}

	public String getSearchActionURL() {
		PortletURL searchActionURL = getRenderURL();

		return searchActionURL.toString();
	}

	protected PortletURL getRenderURL() {
		return _portletResponse.createRenderURL();
	}

	private final PageContext _pageContext;
	private final LiferayPortletResponse _portletResponse;
	private final HttpServletRequest _request;

}