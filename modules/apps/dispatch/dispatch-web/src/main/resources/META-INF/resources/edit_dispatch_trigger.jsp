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
		key="<%= DispatchConstants.SCREEN_NAVIGATION_KEY_DISPATCH_GENERAL %>"
		modelBean="<%= dispatchTrigger %>"
		navCssClass="col-md-2"
		portletURL="<%= currentURLObj %>"
	/>
</div>