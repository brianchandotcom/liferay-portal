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

String screenNavigationCategoryKey = ParamUtil.getString(request, "screenNavigationCategoryKey", UserScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL);
String screenNavigationEntryKey = ParamUtil.getString(request, "screenNavigationEntryKey");

PortletURL viewURL = renderResponse.createRenderURL();

String backURL = ParamUtil.getString(request, "backURL", viewURL.toString());

if (!portletName.equals(UsersAdminPortletKeys.MY_ACCOUNT)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);

	renderResponse.setTitle((selUser == null) ? LanguageUtil.get(request, "add-user") : LanguageUtil.format(request, "edit-user-x", selUser.getFullName(), false));
}

String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	redirect = PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCRenderCommandName(
		"/users_admin/edit_user"
	).setBackURL(
		backURL
	).setParameter(
		"p_u_i_d", selUserId
	).buildString();
}

redirect = HttpComponentsUtil.addParameter(redirect, liferayPortletResponse.getNamespace() + "screenNavigationCategoryKey", screenNavigationCategoryKey);
redirect = HttpComponentsUtil.addParameter(redirect, liferayPortletResponse.getNamespace() + "screenNavigationEntryKey", screenNavigationEntryKey);
%>

<liferay-ui:success key="userAdded" message="the-user-was-created-successfully" />

<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-151671")) %>'>
	<liferay-ui:error exception="<%= ObjectValidationRuleEngineException.class %>">

		<%
		ModelListenerException mle = (ModelListenerException)errorException;

		ObjectValidationRuleEngineException ovree = (ObjectValidationRuleEngineException)mle.getCause();
		%>

		<liferay-ui:message key="<%= ovree.getMessage() %>" />
	</liferay-ui:error>
</c:if>

<portlet:actionURL name="<%= actionCommandName %>" var="actionCommandURL" />

<aui:form action="<%= actionCommandURL %>" cssClass="portlet-users-admin-edit-user" data-senna-off="true" method="post" name="fm">
	<aui:input name="p_u_i_d" type="hidden" value="<%= selUserId %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="screenNavigationCategoryKey" type="hidden" value="<%= screenNavigationCategoryKey %>" />
	<aui:input name="screenNavigationEntryKey" type="hidden" value="<%= screenNavigationEntryKey %>" />

	<clay:sheet>
		<c:if test="<%= (boolean)request.getAttribute(UsersAdminWebKeys.SHOW_TITLE) %>">
			<clay:sheet-header>
				<h2 class="sheet-title"><%= formLabel %></h2>
			</clay:sheet-header>
		</c:if>

		<clay:sheet-section>
			<liferay-util:include page="<%= jspPath %>" servletContext="<%= application %>" />
		</clay:sheet-section>

		<c:if test="<%= editable && (boolean)request.getAttribute(UsersAdminWebKeys.SHOW_CONTROLS) %>">
			<clay:sheet-footer>
				<aui:button primary="<%= true %>" type="submit" />

				<c:if test="<%= !portletName.equals(UsersAdminPortletKeys.MY_ACCOUNT) %>">
					<aui:button href="<%= backURL %>" type="cancel" />
				</c:if>
			</clay:sheet-footer>
		</c:if>
	</clay:sheet>
</aui:form>