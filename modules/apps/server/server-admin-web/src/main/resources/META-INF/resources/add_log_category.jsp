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