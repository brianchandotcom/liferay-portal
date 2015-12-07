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

package com.liferay.layout.admin.web.portlet;

import com.liferay.layout.admin.web.constants.LayoutAdminPortletKeys;
import com.liferay.mobile.device.rules.service.MDRActionLocalService;
import com.liferay.mobile.device.rules.service.MDRActionService;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceLocalService;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceService;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutPrototypeLocalService;
import com.liferay.portal.service.LayoutPrototypeService;
import com.liferay.portal.service.LayoutRevisionLocalService;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.LayoutSetLocalService;
import com.liferay.portal.service.LayoutSetService;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.RoleLocalService;
import com.liferay.portal.service.ThemeLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.sites.action.ActionUtil;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-layouts-admin",
		"com.liferay.portlet.icon=/icons/my_pages.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.single-page-application=false",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=My Pages",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LayoutAdminPortletKeys.MY_PAGES,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class}
)
public class MyPagesPortlet extends LayoutAdminPortlet {

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			User user = PortalUtil.getUser(renderRequest);

			RenderRequestImpl renderRequestImpl =
				(RenderRequestImpl)renderRequest;

			DynamicServletRequest dynamicRequest =
				(DynamicServletRequest)renderRequestImpl.
					getHttpServletRequest();

			dynamicRequest.setParameter(
				"p_u_i_d", String.valueOf(user.getUserId()));

			Group group = user.getGroup();

			dynamicRequest.setParameter(
				"groupId", String.valueOf(group.getGroupId()));

			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		super.doDispatch(renderRequest, renderResponse);
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		this.dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		this.layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutPrototypeLocalService(
		LayoutPrototypeLocalService layoutPrototypeLocalService) {

		this.layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutPrototypeService(
		LayoutPrototypeService layoutPrototypeService) {

		this.layoutPrototypeService = layoutPrototypeService;
	}

	@Reference(unbind = "-")
	protected void setLayoutRevisionLocalService(
		LayoutRevisionLocalService layoutRevisionLocalService) {

		this.layoutRevisionLocalService = layoutRevisionLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutService(LayoutService layoutService) {
		this.layoutService = layoutService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {

		this.layoutSetLocalService = layoutSetLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetService(LayoutSetService layoutSetService) {
		this.layoutSetService = layoutSetService;
	}

	@Reference(unbind = "-")
	protected void setMDRActionLocalService(
		MDRActionLocalService mdrActionLocalService) {

		this.mdrActionLocalService = mdrActionLocalService;
	}

	@Reference(unbind = "-")
	protected void setMDRActionService(MDRActionService mdrActionService) {
		this.mdrActionService = mdrActionService;
	}

	@Reference(unbind = "-")
	protected void setMDRRuleGroupInstanceLocalService(
		MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService) {

		this.mdrRuleGroupInstanceLocalService =
			mdrRuleGroupInstanceLocalService;
	}

	@Reference(unbind = "-")
	protected void setMDRRuleGroupInstanceService(
		MDRRuleGroupInstanceService mdrRuleGroupInstanceService) {

		this.mdrRuleGroupInstanceService = mdrRuleGroupInstanceService;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		this.portletLocalService = portletLocalService;
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	@Reference(unbind = "-")
	protected void setThemeLocalService(ThemeLocalService themeLocalService) {
		this.themeLocalService = themeLocalService;
	}

	private volatile RoleLocalService _roleLocalService;

}