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
String actionCommandName = (String)request.getAttribute(UsersAdminWebKeys.ACTION_COMMAND_NAME);
String jspPath = (String)request.getAttribute(UsersAdminWebKeys.JSP_PATH);

User selUser = PortalUtil.getSelectedUser(request);

request.setAttribute(UsersAdminWebKeys.SELECTED_USER, selUser);
%>

<portlet:actionURL name="<%= actionCommandName %>" var="actionCommandURL" />

<portlet:renderURL var="viewUsersRenderURL" />

<%
if (!portletName.equals(UsersAdminPortletKeys.MY_ACCOUNT)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(viewUsersRenderURL.toString());

	renderResponse.setTitle((selUser == null) ? LanguageUtil.get(request, "add-user") : LanguageUtil.format(request, "edit-user-x", selUser.getFullName(), false));
}
%>

<aui:form action="<%= actionCommandURL %>" cssClass="container-fluid-1280 portlet-users-admin-edit-user" data-senna-off="true" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="p_u_i_d" type="hidden" value="<%= (selUser != null) ? selUser.getUserId() : 0 %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<liferay-util:include page="<%= jspPath %>" servletContext="<%= application %>" />

			<aui:button-row>
				<aui:button primary="<%= true %>" type="submit" />

				<c:if test="<%= !portletName.equals(UsersAdminPortletKeys.MY_ACCOUNT) %>">
					<aui:button href="<%= viewUsersRenderURL.toString() %>" type="cancel" />
				</c:if>
			</aui:button-row>
		</aui:fieldset>
	</aui:fieldset-group>
</aui:form>