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
CommerceApplicationAdminDisplayContext commerceApplicationAdminDisplayContext = (CommerceApplicationAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceApplicationBrand commerceApplicationBrand = commerceApplicationAdminDisplayContext.getCommerceApplicationBrand();

renderResponse.setTitle(LanguageUtil.get(request, "applications"));
%>

<liferay-frontend:screen-navigation
	containerCssClass="col-md-10"
	key="<%= CommerceApplicationBrandScreenNavigationConstants.SCREEN_NAVIGATION_KEY %>"
	modelBean="<%= commerceApplicationBrand %>"
	navCssClass="col-md-2"
	portletURL="<%= currentURLObj %>"
/>