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

package com.liferay.account.admin.web.internal.portlet.filter;

import com.liferay.account.admin.web.internal.constants.AccountScreenNavigationEntryConstants;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.roles.admin.constants.RolesAdminPortletKeys;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
	service = PortletFilter.class
)
public class AccountEntriesAdminPortletFilter
	implements ActionFilter, RenderFilter, ResourceFilter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			ActionRequest actionRequest, ActionResponse actionResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

		if (Validator.isNotNull(actionName) &&
			(actionName.equals("deletePermission") ||
			 actionName.equals("updateActions"))) {

			_portlet.processAction(actionRequest, actionResponse);

			return;
		}

		filterChain.doFilter(actionRequest, actionResponse);
	}

	@Override
	public void doFilter(
			RenderRequest renderRequest, RenderResponse renderResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		String mvcPath = ParamUtil.getString(renderRequest, "mvcPath");

		if (Validator.isNotNull(mvcPath) &&
			(mvcPath.startsWith("/edit_role") ||
			 mvcPath.equals("/view_resources.jsp"))) {

			if (mvcPath.equals("/edit_role_permissions.jsp")) {
				renderRequest.removeAttribute("mvcPath");

				PortletURL portletURL = PortletURLBuilder.create(
					_portal.getControlPanelPortletURL(
						renderRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/account_admin/edit_account_role"
				).setBackURL(
					ParamUtil.getString(renderRequest, "backURL")
				).setParameter(
					"cur", ParamUtil.getString(renderRequest, "cur")
				).setParameter(
					"delta", ParamUtil.getString(renderRequest, "delta")
				).setParameter(
					"resetCur", ParamUtil.getString(renderRequest, "resetCur")
				).setParameter(
					"screenNavigationCategoryKey",
					AccountScreenNavigationEntryConstants.
						CATEGORY_KEY_DEFINE_PERMISSIONS
				).buildPortletURL();

				long roleId = ParamUtil.getLong(renderRequest, "roleId");

				AccountRole accountRole =
					_accountRoleLocalService.fetchAccountRoleByRoleId(roleId);

				if (accountRole != null) {
					portletURL.setParameter(
						"accountEntryId",
						String.valueOf(accountRole.getAccountEntryId()));
					portletURL.setParameter(
						"accountRoleId",
						String.valueOf(accountRole.getAccountRoleId()));
				}

				HttpServletResponse httpServletResponse =
					_portal.getHttpServletResponse(renderResponse);

				httpServletResponse.sendRedirect(portletURL.toString());

				return;
			}

			_jspRenderer.renderJSP(
				_servletContext, _portal.getHttpServletRequest(renderRequest),
				_portal.getHttpServletResponse(renderResponse), mvcPath);

			return;
		}

		filterChain.doFilter(renderRequest, renderResponse);
	}

	@Override
	public void doFilter(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		String mvcPath = ParamUtil.getString(resourceRequest, "mvcPath");

		if (Validator.isNotNull(mvcPath) &&
			mvcPath.equals("/view_resources.jsp")) {

			MVCPortlet mvcPortlet = (MVCPortlet)_portlet;

			mvcPortlet.serveResource(resourceRequest, resourceResponse);

			return;
		}

		filterChain.doFilter(resourceRequest, resourceResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(component.name=com.liferay.account.admin.web.internal.portlet.action.EditAccountRoleMVCRenderCommand)"
	)
	private MVCRenderCommand _mvcRenderCommand;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(javax.portlet.name=" + RolesAdminPortletKeys.ROLES_ADMIN + ")",
		unbind = "-"
	)
	private Portlet _portlet;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.roles.admin.web)")
	private ServletContext _servletContext;

}