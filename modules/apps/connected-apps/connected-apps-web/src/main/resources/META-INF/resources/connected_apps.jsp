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

<div class="sheet sheet-lg">
	<div class="sheet-header">
		<h2 class="sheet-title">
			<liferay-ui:message key="apps" />
		</h2>

		<liferay-ui:message key="list-of-third-party-apps-connected-to-your-account" />
	</div>

	<div class="sheet-section">
		<liferay-portlet:actionURL name="/users_admin/revoke_connected_app" varImpl="actionCommandURL" />

		<aui:form action="<%= actionCommandURL.toString() %>" cssClass="portlet-users-admin-edit-user" data-senna-off="true" method="post" name="fm">

			<%
			User selUser = PortalUtil.getSelectedUser(request);
			%>

			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="p_u_i_d" type="hidden" value="<%= selUser.getUserId() %>" />
			<aui:input name="connectedAppKey" type="hidden" value="<%= StringPool.BLANK %>" />

			<%
			ConnectedAppsManager connectedAppsManager = (ConnectedAppsManager)request.getAttribute(ConnectedAppsManager.class.getName());

			List<ConnectedApp> connectedApps = connectedAppsManager.getConnectedApps(user);
			%>

			<%
			for (ConnectedApp connectedApp : connectedApps) {
			%>

				<div style="margin-bottom: 1em;">
					<span class="sticker">
						<img src="<%= connectedApp.getImageURL() %>" style="width: 1.25em;" />
					</span>

					<%= connectedApp.getName(locale) %>

					<input class="btn btn-secondary btn-sm pull-right" onclick="document.querySelector('[name=<portlet:namespace/>connectedAppKey]').setAttribute('value', '<%= connectedApp.getKey() %>')" type="submit" value="<%= LanguageUtil.get(resourceBundle, "revoke") %>" />
				</div>

			<%
			}
			%>

			<c:if test="<%= connectedApps.isEmpty() %>">
				<span class="text-muted">
					<liferay-ui:message key="no-applications-have-been-approved-yet" />
				</span>
			</c:if>
		</aui:form>
	</div>
</div>