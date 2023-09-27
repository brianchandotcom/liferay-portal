<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DispatchTriggerDisplayContext dispatchTriggerDisplayContext = (DispatchTriggerDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

DispatchTrigger dispatchTrigger = dispatchTriggerDisplayContext.getDispatchTrigger();

String title = LanguageUtil.get(request, "add-trigger");

if (dispatchTrigger != null) {
	title = dispatchTrigger.getName();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
portletDisplay.setTitle(title);
%>

<div id="<portlet:namespace />editDispatchTriggerContainer">
	<liferay-frontend:screen-navigation
		containerCssClass="col-md-10"
		key="<%= DispatchScreenNavigationConstants.SCREEN_NAVIGATION_KEY_DISPATCH_GENERAL %>"
		modelBean="<%= dispatchTrigger %>"
		navCssClass="col-md-2"
		portletURL="<%= currentURLObj %>"
	/>
</div>