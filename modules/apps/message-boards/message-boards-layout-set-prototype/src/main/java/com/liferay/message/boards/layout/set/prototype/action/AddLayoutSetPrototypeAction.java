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

package com.liferay.message.boards.layout.set.prototype.action;

import com.liferay.layout.set.prototype.web.constants.LayoutSetPrototypePortletKeys;
import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.polls.constants.PollsPortletKeys;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.DefaultLayoutPrototypesUtil;
import com.liferay.portal.util.DefaultLayoutSetPrototypesUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.social.user.statistics.web.constants.SocialUserStatisticsPortletKeys;

import java.util.List;

import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = AddLayoutSetPrototypeAction.class)
public class AddLayoutSetPrototypeAction {

	@Activate
	protected void activate() throws Exception {
		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			long defaultUserId = _userLocalService.getDefaultUserId(
				company.getCompanyId());

			List<LayoutSetPrototype> layoutSetPrototypes =
				_layoutSetPrototypeLocalService.search(
					company.getCompanyId(), null, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			addPublicSite(
				company.getCompanyId(), defaultUserId, layoutSetPrototypes);
		}
	}

	protected void addPublicSite(
			long companyId, long defaultUserId,
			List<LayoutSetPrototype> layoutSetPrototypes)
		throws Exception {

		LayoutSet layoutSet =
			DefaultLayoutSetPrototypesUtil.addLayoutSetPrototype(
				companyId, defaultUserId,
				"layout-set-prototype-community-site-title",
				"layout-set-prototype-community-site-description",
				layoutSetPrototypes,
				AddLayoutSetPrototypeAction.class.getClassLoader());

		if (layoutSet == null) {
			return;
		}

		// Home layout

		Layout layout = DefaultLayoutPrototypesUtil.addLayout(
			layoutSet, "home", "/home", "2_columns_iii");
		String portletId = PortletProviderUtil.getPortletId(
			MBMessage.class.getName(), PortletProvider.Action.EDIT);

		DefaultLayoutPrototypesUtil.addPortletId(layout, portletId, "column-1");

		DefaultLayoutPrototypesUtil.addPortletId(
			layout, PollsPortletKeys.POLLS_DISPLAY, "column-2");

		DefaultLayoutPrototypesUtil.addPortletId(
			layout, SocialUserStatisticsPortletKeys.SOCIAL_USER_STATISTICS,
			"column-2");

		// Wiki layout

		DefaultLayoutPrototypesUtil.addLayout(
			layoutSet, "wiki", "/wiki", "2_columns_iii");
	}

	protected void doRun(long companyId) throws Exception {
		long defaultUserId = _userLocalService.getDefaultUserId(companyId);

		List<LayoutSetPrototype> layoutSetPrototypes =
			_layoutSetPrototypeLocalService.search(
				companyId, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		addPublicSite(companyId, defaultUserId, layoutSetPrototypes);
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetPrototypeLocalService(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {

		_layoutSetPrototypeLocalService = layoutSetPrototypeLocalService;
	}

	@Reference(
		target = "(javax.portlet.name=" + LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE + ")",
		unbind = "-"
	)
	protected void setLayoutSetPrototypePortlet(Portlet portlet) {
	}

	@Reference(
		target = "(javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS + ")",
		unbind = "-"
	)
	protected void setMessageBoardsPortlet(Portlet portlet) {
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(
		target = "(&(objectClass=javax.servlet.Servlet)(osgi.http.whiteboard.servlet.name=59 Servlet))",
		unbind = "-"
		)
	protected void setPollsServlet(Servlet servlet) {
	}

	@Reference(
		target = "(javax.portlet.name=" + SocialUserStatisticsPortletKeys.SOCIAL_USER_STATISTICS + ")",
		unbind = "-"
	)
	protected void setSocialUserStatisticsPortletKeys(Portlet portlet) {
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private volatile CompanyLocalService _companyLocalService;
	private volatile LayoutSetPrototypeLocalService
		_layoutSetPrototypeLocalService;
	private volatile UserLocalService _userLocalService;

}