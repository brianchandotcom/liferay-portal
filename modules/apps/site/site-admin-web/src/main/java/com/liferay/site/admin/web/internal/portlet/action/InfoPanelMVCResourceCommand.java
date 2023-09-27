/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.site.admin.web.internal.constants.SiteAdminPortletKeys;
import com.liferay.site.admin.web.internal.constants.SiteAdminWebKeys;
import com.liferay.site.constants.SiteWebKeys;
import com.liferay.site.util.GroupSearchProvider;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"javax.portlet.name=" + SiteAdminPortletKeys.SITE_ADMIN,
		"mvc.command.name=/site_admin/info_panel"
	},
	service = MVCResourceCommand.class
)
public class InfoPanelMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		List<Group> groups = new ArrayList<>();

		long[] groupIds = ParamUtil.getLongValues(resourceRequest, "rowIds");

		for (long groupId : groupIds) {
			groups.add(_groupService.getGroup(groupId));
		}

		resourceRequest.setAttribute(SiteAdminWebKeys.GROUP_ENTRIES, groups);

		resourceRequest.setAttribute(
			SiteWebKeys.GROUP_SEARCH_PROVIDER, _groupSearchProvider);

		include(resourceRequest, resourceResponse, "/info_panel.jsp");
	}

	@Reference
	private GroupSearchProvider _groupSearchProvider;

	@Reference
	private GroupService _groupService;

}