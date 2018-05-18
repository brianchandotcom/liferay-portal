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
CommerceUserSegmentDisplayContext commerceUserSegmentDisplayContext = (CommerceUserSegmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceUserSegmentEntry commerceUserSegmentEntry = commerceUserSegmentDisplayContext.getCommerceUserSegmentEntry();
PortletURL portletURL = commerceUserSegmentDisplayContext.getPortletURL();

portletURL.setParameter("mvcRenderCommandName", "editCommerceUserSegmentEntry");

String title = LanguageUtil.format(request, "edit-x", commerceUserSegmentEntry.getName(locale), false);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "user-segments"), String.valueOf(renderResponse.createRenderURL()), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);
%>

<%@ include file="/breadcrumb.jspf" %>

<liferay-frontend:screen-navigation
	containerCssClass="col-md-10"
	context="<%= commerceUserSegmentEntry %>"
	key="<%= CommerceUserSegmentScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_USER_SEGMENT_ENTRY_GENERAL %>"
	navCssClass="col-md-2"
	portletURL="<%= currentURLObj %>"
/>