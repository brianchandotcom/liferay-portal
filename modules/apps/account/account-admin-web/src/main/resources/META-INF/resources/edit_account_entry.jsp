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
AccountDisplay accountDisplay = (AccountDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_DISPLAY);

PortletURL portletURL = renderResponse.createRenderURL();

if (accountDisplay != null) {
	portletURL.setParameter("mvcRenderCommandName", "/account_admin/edit_account_entry");
	portletURL.setParameter("accountEntryId", String.valueOf(accountDisplay.getAccountId()));
}
%>

<liferay-frontend:screen-navigation
	containerWrapperCssClass=""
	context="<%= accountDisplay %>"
	headerContainerCssClass=""
	inverted="<%= true %>"
	key="<%= AccountScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_ACCOUNT %>"
	portletURL="<%= portletURL %>"
/>