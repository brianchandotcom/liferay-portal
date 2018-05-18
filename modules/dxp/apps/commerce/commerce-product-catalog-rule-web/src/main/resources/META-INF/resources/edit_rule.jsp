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
CPCatalogRuleDisplayContext cpCatalogRuleDisplayContext = (CPCatalogRuleDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
%>

<liferay-frontend:screen-navigation
	containerCssClass="col-md-10"
	context="<%= cpCatalogRuleDisplayContext.getCPRule() %>"
	key="<%= CPRuleScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_PRODUCT_RULE_GENERAL %>"
	navCssClass="col-md-2"
	portletURL="<%= currentURLObj %>"
/>