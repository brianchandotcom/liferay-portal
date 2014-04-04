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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
UserSearch searchContainer = (UserSearch)request.getAttribute("liferay-ui:search:searchContainer");

UserDisplayTerms displayTerms = (UserDisplayTerms)searchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_users_admin_user_search"
>
	<%@ include file="/html/portlet/directory/user_search_user_name.jspf" %>

	<aui:fieldset>
		<aui:input inlineField="<%= true %>" name="<%= UserDisplayTerms.SCREEN_NAME %>" size="20" value="<%= displayTerms.getScreenName() %>" />

		<aui:input inlineField="<%= true %>" name="<%= UserDisplayTerms.EMAIL_ADDRESS %>" size="20" value="<%= displayTerms.getEmailAddress() %>" />

		<c:if test="<%= showActiveUserSelect %>">
			<aui:select inlineField="<%= true %>" name="<%= UserDisplayTerms.STATUS %>" value="<%= displayTerms.getStatus() %>">
				<aui:option label="any-status" value="<%= WorkflowConstants.STATUS_ANY %>" />
				<aui:option label="active" value="<%= WorkflowConstants.STATUS_APPROVED %>" />
				<aui:option label="inactive" value="<%= WorkflowConstants.STATUS_INACTIVE %>" />
			</aui:select>
		</c:if>
	</aui:fieldset>
</liferay-ui:search-toggle>