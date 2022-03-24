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

<div class="sheet sheet-lg" id="<portlet:namespace />connectedApp">
	<div class="sheet-header">
		<h2 class="sheet-title">
			<liferay-ui:message key="apps" />
		</h2>

		<liferay-ui:message key="your-account-is-connected-to-the-following-third-party-apps" />
	</div>

	<div class="sheet-section">
		<liferay-portlet:actionURL name="/connected_app/revoke_connected_app" varImpl="actionCommandURL" />

		<aui:form action="<%= actionCommandURL %>" cssClass="portlet-users-admin-edit-user" data-senna-off="true" method="post" name="fm">

			<%
			User selUser = PortalUtil.getSelectedUser(request);
			%>

			<aui:input name="p_u_i_d" type="hidden" value="<%= selUser.getUserId() %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="connectedAppKey" type="hidden" value="<%= StringPool.BLANK %>" />

			<%
			ConnectedAppManager connectedAppManager = (ConnectedAppManager)request.getAttribute(ConnectedAppManager.class.getName());

			List<ConnectedApp> connectedApps = connectedAppManager.getConnectedApps(user);

			for (ConnectedApp connectedApp : connectedApps) {
			%>

				<clay:content-row
					cssClass="mb-3"
					noGutters="x"
					verticalAlign="center"
				>
					<clay:content-col>
						<img class="icon-monospaced" src="<%= HtmlUtil.escapeAttribute(connectedApp.getImageURL()) %>" />
					</clay:content-col>

					<clay:content-col
						expand="<%= true %>"
					>
						<%= HtmlUtil.escape(connectedApp.getName(locale)) %>
					</clay:content-col>

					<clay:content-col>
						<clay:button
							data-key="<%= connectedApp.getKey() %>"
							displayType="secondary"
							label="revoke"
							small="<%= true %>"
							type="submit"
						/>
					</clay:content-col>
				</clay:content-row>

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

<aui:script require="frontend-js-web/index as frontendJsWeb">
	var {delegate} = frontendJsWeb;

	var connectedAppKeyInput = document.querySelector(
		'[name=<portlet:namespace />connectedAppKey]'
	);

	delegate(
		document.getElementById('<portlet:namespace />connectedApp'),
		'click',
		'[data-key]',
		(event) => {
			connectedAppKeyInput.setAttribute('value', event.target.dataset.key);
		}
	);
</aui:script>