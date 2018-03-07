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
boolean editable = (boolean)request.getAttribute(UsersAdminWebKeys.EDITABLE);
String formLabel = (String)request.getAttribute(UsersAdminWebKeys.FORM_LABEL);
String jspPath = (String)request.getAttribute(UsersAdminWebKeys.JSP_PATH);

User selUser = PortalUtil.getSelectedUser(request);

request.setAttribute(UsersAdminWebKeys.SELECTED_USER, selUser);

if (selUser != null) {
	PortalUtil.setPageSubtitle(selUser.getFullName(), request);
}

long selUserId = (selUser != null) ? selUser.getUserId() : 0;

String screenNavigationCategoryKey = ParamUtil.getString(request, "screenNavigationCategoryKey", UserFormConstants.CATEGORY_KEY_GENERAL);
String screenNavigationEntryKey = ParamUtil.getString(request, "screenNavigationEntryKey", UserFormConstants.ENTRY_KEY_INFORMATION);
%>

<portlet:actionURL name="<%= actionCommandName %>" var="actionCommandURL" />

<portlet:renderURL var="redirect">
	<portlet:param name="mvcRenderCommandName" value="/users_admin/edit_user" />
	<portlet:param name="p_u_i_d" value="<%= String.valueOf(selUserId) %>" />
	<portlet:param name="screenNavigationCategoryKey" value="<%= screenNavigationCategoryKey %>" />
	<portlet:param name="screenNavigationEntryKey" value="<%= screenNavigationEntryKey %>" />
</portlet:renderURL>

<portlet:renderURL var="viewUsersRenderURL" />

<%
if (!portletName.equals(UsersAdminPortletKeys.MY_ACCOUNT)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(viewUsersRenderURL.toString());

	renderResponse.setTitle((selUser == null) ? LanguageUtil.get(request, "add-user") : LanguageUtil.format(request, "edit-user-x", selUser.getFullName(), false));
}
%>

<liferay-ui:success key="userAdded" message="the-user-was-created-successfully" />

<aui:form action="<%= actionCommandURL %>" cssClass="container-fluid-1280 portlet-users-admin-edit-user" data-senna-off="true" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect.toString() %>" />
	<aui:input name="p_u_i_d" type="hidden" value="<%= selUserId %>" />
	<aui:input name="screenNavigationCategoryKey" type="hidden" value="<%= screenNavigationCategoryKey %>" />
	<aui:input name="screenNavigationEntryKey" type="hidden" value="<%= screenNavigationEntryKey %>" />

	<aui:fieldset-group markupView="lexicon">
		<div class="sheet">
			<h2 class="sheet-title"><%= formLabel %></h2>

			<div class="sheet-section">
				<liferay-util:include page="<%= jspPath %>" servletContext="<%= application %>" />
			</div>

			<c:if test="<%= editable %>">
				<div class="sheet-footer">
					<aui:button primary="<%= true %>" type="submit" />

					<c:if test="<%= !portletName.equals(UsersAdminPortletKeys.MY_ACCOUNT) %>">
						<aui:button href="<%= viewUsersRenderURL.toString() %>" type="cancel" />
					</c:if>
				</div>
			</c:if>
		</div>
	</aui:fieldset-group>
</aui:form>