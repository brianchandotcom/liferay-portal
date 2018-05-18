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
CommerceDiscountDisplayContext commerceDiscountDisplayContext = (CommerceDiscountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceDiscount commerceDiscount = commerceDiscountDisplayContext.getCommerceDiscount();
PortletURL portletURL = commerceDiscountDisplayContext.getPortletURL();

portletURL.setParameter("mvcRenderCommandName", "editCommerceDiscount");

String title = LanguageUtil.get(request, "add-discount");

if (commerceDiscount != null) {
	title = LanguageUtil.format(request, "edit-x", commerceDiscount.getTitle(), false);
}

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "discounts"), String.valueOf(renderResponse.createRenderURL()), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);
%>

<%@ include file="/breadcrumb.jspf" %>

<liferay-frontend:screen-navigation
	containerCssClass="col-md-10"
	context="<%= commerceDiscount %>"
	fullContainerCssClass="col-md-8 offset-md-2"
	key="<%= CommerceDiscountScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_DISCOUNT_GENERAL %>"
	navCssClass="col-md-2"
	portletURL="<%= currentURLObj %>"
/>