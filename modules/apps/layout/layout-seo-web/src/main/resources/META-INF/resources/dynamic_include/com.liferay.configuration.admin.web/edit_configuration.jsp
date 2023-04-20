<%--
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
--%>

<%@ include file="/init.jsp" %>

<clay:alert
	defaultTitleDisabled="<%= true %>"
	dismissible="<%= true %>"
	displayType="warning"
	message='<%=
		LanguageUtil.format(
			request, "to-set-up-the-user-agents-go-to-crawler-user-agents",
			new String[] {
				"<a href=" +
					PortletURLBuilder.create(
						PortalUtil.getControlPanelPortletURL(request, ConfigurationAdminPortletKeys.SYSTEM_SETTINGS, PortletRequest.RENDER_PHASE)
					).setMVCRenderCommandName(
						"/configuration_admin/edit_configuration"
					).setRedirect(
						PortalUtil.getCurrentCompleteURL(request)
					).setParameter(
						"factoryPid", CrawlerUserAgentsConfiguration.class.getName()
					).setParameter(
						"pid", CrawlerUserAgentsConfiguration.class.getName()
					).buildString() + ">",
				"</a>"
			})
	%>'
	symbol="warning-full"
/>