<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceSubscriptionEntryDisplayContext commerceSubscriptionEntryDisplayContext = (CommerceSubscriptionEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceSubscriptionEntry commerceSubscriptionEntry = commerceSubscriptionEntryDisplayContext.getCommerceSubscriptionEntry();
%>

<commerce-ui:header
	actions="<%= commerceSubscriptionEntryDisplayContext.getHeaderActionModels() %>"
	bean="<%= commerceSubscriptionEntry %>"
	beanIdLabel="id"
	model="<%= CommerceSubscriptionEntry.class %>"
	thumbnailUrl="<%= commerceSubscriptionEntryDisplayContext.getAccountEntryThumbnailURL() %>"
	title="<%= String.valueOf(commerceSubscriptionEntryDisplayContext.getCommerceSubscriptionEntryId()) %>"
/>

<div id="<portlet:namespace />editSubscriptionEntryContainer">
	<liferay-frontend:screen-navigation
		containerWrapperCssClass="container"
		key="<%= CommerceSubscriptionEntryScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_SUBSCRIPTION_ENTRY %>"
		modelBean="<%= commerceSubscriptionEntry %>"
		portletURL="<%= currentURLObj %>"
	/>
</div>