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
CommerceDataIntegrationProcessDisplayContext commerceDataIntegrationProcessDisplayContext = (CommerceDataIntegrationProcessDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceDataIntegrationProcess commerceDataIntegrationProcess = commerceDataIntegrationProcessDisplayContext.getCommerceDataIntegrationProcess();

String title = LanguageUtil.get(request, "add-process");

if (commerceDataIntegrationProcess != null) {
	title = commerceDataIntegrationProcess.getName();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
portletDisplay.setTitle(title);
%>

<div id="<portlet:namespace />editProcessContainer">
	<liferay-frontend:screen-navigation
		containerCssClass="col-md-10"
		key="<%= CommerceDataIntegrationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_DATA_INTEGRATION_GENERAL %>"
		modelBean="<%= commerceDataIntegrationProcess %>"
		navCssClass="col-md-2"
		portletURL="<%= currentURLObj %>"
	/>
</div>