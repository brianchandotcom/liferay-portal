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

package com.liferay.portlet.tck.bridge;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTCKStrutsAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			User user = _getUser(request);

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			String[] portletIds = request.getParameterValues("portletId");

			if (portletIds == null) {
				portletIds = request.getParameterValues("portletName");
			}

			for (int i = 0; i < portletIds.length; i++) {
				String[] nameAndWar = StringUtil.split(portletIds[i], '/');

				portletIds[i] = PortalUtil.getJsSafePortletId(
					nameAndWar[1] + PortletConstants.WAR_SEPARATOR +
						nameAndWar[0]);
			}

			long userId = user.getUserId();
			long groupId = user.getGroupId();

			ServiceContext serviceContext = new ServiceContext();

			Layout layout = LayoutLocalServiceUtil.addLayout(
				userId, groupId, false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "TCKAction",
				StringPool.BLANK, StringPool.BLANK,
				LayoutConstants.TYPE_PORTLET, false, StringPool.BLANK,
				serviceContext);

			LayoutTypePortlet layoutType =
				(LayoutTypePortlet)layout.getLayoutType();

			for (int i = 0; i < portletIds.length; i++) {
				String portletId = portletIds[i];

				// Update the render weight for portlets in both the company and
				// global pools since the company pool holds clones from the
				// global pool

				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					layout.getCompanyId(), portletId);

				portlet.setRenderWeight(portletIds.length - i);

				portlet = PortletLocalServiceUtil.getPortletById(portletId);

				portlet.setRenderWeight(portletIds.length - i);

				layoutType.addPortletId(userId, portletId, false);

				String rootPortletId = PortletConstants.getRootPortletId(
					portletId);

				String portletPrimaryKey = PortletPermissionUtil.getPrimaryKey(
					layout.getPlid(), portletId);

				ResourceLocalServiceUtil.addResources(
					user.getCompanyId(), groupId, 0, rootPortletId,
					portletPrimaryKey, true, true, true);
			}

			LayoutLocalServiceUtil.updateLayout(
				layout.getGroupId(), layout.getLayoutId(),
				layout.getTypeSettings());

			request.setAttribute(
				WebKeys.FORWARD_URL,
				themeDisplay.getPathMain() + "/portal/layout?p_l_id=" +
					layout.getPlid());

			return ActionConstants.COMMON_FORWARD_JSP;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	private User _getUser(HttpServletRequest request) throws Exception {
		long companyId = PortalUtil.getCompanyId(request);

		try {
			return UserLocalServiceUtil.getUserByScreenName(companyId, "tck");
		}
		catch (Exception e) {
			long creatorUserId = 0;
			boolean autoPassword = false;
			String password1 = "password";
			String password2 = password1;
			boolean autoScreenName = false;
			String screenName = "tck";
			String emailAddress = "tck@liferay.com";
			long facebookId = 0;
			String openId = StringPool.BLANK;
			Locale locale = LocaleUtil.US;
			String firstName = "TCK";
			String middleName = StringPool.BLANK;
			String lastName = "User";
			long prefixId = 0;
			long suffixId = 0;
			boolean male = true;
			int birthdayMonth = Calendar.JANUARY;
			int birthdayDay = 1;
			int birthdayYear = 1970;
			String jobTitle = StringPool.BLANK;
			long[] groupIds = null;
			long[] organizationIds = null;
			long[] roleIds = null;
			long[] userGroupIds = null;
			boolean sendEmail = false;

			ServiceContext serviceContext = new ServiceContext();

			User user = UserLocalServiceUtil.addUser(
				creatorUserId, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, facebookId, openId,
				locale, firstName, middleName, lastName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
				serviceContext);

			long userId = user.getUserId();

			UserLocalServiceUtil.updateAgreedToTermsOfUse(userId, true);
			UserLocalServiceUtil.updatePasswordReset(userId, false);
			UserLocalServiceUtil.updateReminderQuery(userId, "TCK", "TCK");

			return user;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletTCKStrutsAction.class);

}