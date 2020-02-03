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

<%@ include file="/layout/init.jsp" %>

<%
String appName = ParamUtil.getString(request, "appName");

String portletName = ParamUtil.getString(request, "portletName");

String editEntryCssClass = "";

String mvcPath = ParamUtil.getString(request, PortalUtil.getPortletNamespace(portletName) + "mvcPath");

if (mvcPath.startsWith("/edit_entry.jsp")) {
	editEntryCssClass = "edit-entry";
}
%>

<div class="app-builder-standalone">
	<header class="app-builder-standalone-header">
		<div class="container p-0">
			<div class="app-builder-standalone-menu autofit-row">
				<div class="autofit-col autofit-col-expand">
					<a class="company-link" href="<%= PortalUtil.addPreservedParameters(themeDisplay, themeDisplay.getURLPortal(), false, true) %>">
						<span class="company-details text-truncate">
							<img alt="" class="company-logo" src="<%= themeDisplay.getPathImage() + "/company_logo?img_id=" + company.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(company.getLogoId()) %>" />

							<span class="company-name"><%= HtmlUtil.escape(company.getName()) %></span>
						</span>
					</a>
				</div>

				<div style="display: none;">
					<liferay-portlet:runtime
						portletName="<%= PortletKeys.LOGIN %>"
					/>
				</div>

				<div class="autofit-col text-right">
					<liferay-portlet:runtime
						portletProviderAction="<%= PortletProvider.Action.VIEW %>"
						portletProviderClassName="com.liferay.admin.kernel.util.PortalUserPersonalBarApplicationType$UserPersonalBar"
					/>
				</div>
			</div>

			<h1 class="app-builder-standalone-name <%= editEntryCssClass %>"><%= HtmlUtil.escape(appName) %></h1>
		</div>
	</header>

	<div class="app-builder-standalone-content container <%= editEntryCssClass %> sheet">
		<liferay-portlet:runtime
			portletName="<%= portletName %>"
		/>
	</div>
</div>