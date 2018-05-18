<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNotNull(redirect)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);
}

CommerceUserDetailDisplayContext commerceUserDetailDisplayContext = (CommerceUserDetailDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

User curUser = commerceUserDetailDisplayContext.getSelectedUser();
%>

<h1><%= HtmlUtil.escape(LanguageUtil.get(request, "manage-account")) %></h1>

<liferay-frontend:screen-navigation
	containerCssClass="col-md-10"
	context="<%= curUser %>"
	key="<%= CommerceUserScreenNavigationConstants.SCREEN_NAVIGATION_KEY %>"
	navCssClass="col-md-2"
	portletURL="<%= currentURLObj %>"
/>