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
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

User selUser = PortalUtil.getSelectedUser(request, false);

renderResponse.setTitle(LanguageUtil.format(request, "edit-user-x", HtmlUtil.escape(selUser.getFullName()), false));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<portlet:actionURL var="actionURL" />

<aui:form action="<%= actionURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="p_u_i_d" type="hidden" value="<%= selUser.getUserId() %>" />

	<clay:sheet>
		<h2 class="sheet-title">
			<liferay-ui:message key="accounts" />
		</h2>

		<liferay-util:include page="/common/common_user_account_entries.jsp" servletContext="<%= application %>" />

		<clay:sheet-footer>
			<aui:button type="submit" />

			<aui:button href="<%= backURL %>" type="cancel" />
		</clay:sheet-footer>
	</clay:sheet>
</aui:form>