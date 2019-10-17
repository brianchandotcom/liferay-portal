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

<div class="sheet sheet-lg" id="<portlet:namespace/>connectedApp">
	<div class="sheet-header">
		<h2 class="sheet-title">
			<liferay-ui:message key="apps" />
		</h2>

		<liferay-ui:message key="your-account-is-connected-to-the-following-third-party-apps" />
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
			ConnectedAppManager connectedAppManager = (ConnectedAppManager)request.getAttribute(ConnectedAppManager.class.getName());

			List<ConnectedApp> connectedApps = connectedAppManager.getConnectedApps(user);

			for (ConnectedApp connectedApp : connectedApps) {
			%>

				<div class="autofit-padded-no-gutters-x autofit-row autofit-row-center mb-3">
					<div class="autofit-col">
						<img class="icon-monospaced" src="<%= HtmlUtil.escapeAttribute(connectedApp.getImageURL()) %>" />
					</div>

					<div class="autofit-col autofit-col-expand">
						<%= HtmlUtil.escape(connectedApp.getName(locale)) %>
					</div>

					<div class="autofit-col">

						<%
						Map<String, String> data = new HashMap<>();

						data.put("key", connectedApp.getKey());
						%>

						<clay:button
							data="<%= data %>"
							elementClasses="btn-secondary"
							label='<%= LanguageUtil.get(resourceBundle, "revoke") %>'
							size="sm"
							type="submit"
						/>
					</div>
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

<aui:script require="metal-dom/src/dom as dom">
	var connectedAppKeyInput = document.querySelector(
		'[name=<portlet:namespace/>connectedAppKey]'
	);

	dom.delegate(
		document.getElementById('<portlet:namespace/>connectedApp'),
		'click',
		'[data-key]',
		function(event) {
			connectedAppKeyInput.setAttribute('value', event.target.dataset.key);
		}
	);
</aui:script>