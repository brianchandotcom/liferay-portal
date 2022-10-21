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
EditAccountEntryAccountUserDisplayContext editAccountEntryAccountUserDisplayContext = (EditAccountEntryAccountUserDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<aui:model-context bean="<%= editAccountEntryAccountUserDisplayContext.getSelectedAccountUser() %>" model="<%= User.class %>" />

<liferay-frontend:edit-form
	action="<%= editAccountEntryAccountUserDisplayContext.getEditAccountUserActionURL() %>"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= editAccountEntryAccountUserDisplayContext.getBackURL() %>" />
	<aui:input name="accountEntryId" type="hidden" value="<%= editAccountEntryAccountUserDisplayContext.getAccountEntryId() %>" />
	<aui:input name="accountUserId" type="hidden" value="<%= editAccountEntryAccountUserDisplayContext.getAccountUserId() %>" />

	<clay:sheet-header>
		<h2 class="sheet-title"><%= editAccountEntryAccountUserDisplayContext.getTitle() %></h2>
	</clay:sheet-header>

	<liferay-frontend:edit-form-body>
		<liferay-ui:user-name-fields
			contact="<%= editAccountEntryAccountUserDisplayContext.getSelectedAccountUserContact() %>"
			user="<%= editAccountEntryAccountUserDisplayContext.getSelectedAccountUser() %>"
		/>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= editAccountEntryAccountUserDisplayContext.getBackURL() %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>