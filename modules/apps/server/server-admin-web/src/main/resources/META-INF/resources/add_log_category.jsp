<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "add-category"));
%>

<portlet:actionURL name="/server_admin/edit_server" var="addLogCategoryURL">
	<portlet:param name="cmd" value="addLogLevel" />
	<portlet:param name="redirect" value="<%= String.valueOf(redirect) %>" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= addLogCategoryURL %>"
	method="post"
	name="fm"
>
	<liferay-frontend:edit-form-body>
		<aui:input cssClass="lfr-input-text-container" label="logger-name" name="loggerName" type="text" />

		<aui:select label="log-level" name="priority">

			<%
			for (int i = 0; i < _ALL_PRIORITIES.length; i++) {
			%>

				<aui:option label="<%= _ALL_PRIORITIES[i] %>" selected="<%= Objects.equals(String.valueOf(Level.INFO), _ALL_PRIORITIES[i]) %>" />

			<%
			}
			%>

		</aui:select>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= redirect %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>