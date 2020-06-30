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
CommerceSubscriptionEntryDisplayContext commerceSubscriptionEntryDisplayContext = (CommerceSubscriptionEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceSubscriptionEntry commerceSubscriptionEntry = commerceSubscriptionEntryDisplayContext.getCommerceSubscriptionEntry();
%>

<commerce-ui:header
	actions="<%= commerceSubscriptionEntryDisplayContext.getHeaderActionModels() %>"
	bean="<%= commerceSubscriptionEntry %>"
	beanIdLabel="id"
	model="<%= CommerceSubscriptionEntry.class %>"
	thumbnailUrl="<%= commerceSubscriptionEntryDisplayContext.getCommerceAccountThumbnailURL() %>"
	title="<%= String.valueOf(commerceSubscriptionEntryDisplayContext.getCommerceSubscriptionEntryId()) %>"
/>

<div id="<portlet:namespace />editSubscriptionEntryContainer">
	<liferay-frontend:screen-navigation
		fullContainerCssClass="col-12 pt-4"
		key="<%= CommerceSubscriptionEntryScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_SUBSCRIPTION_ENTRY %>"
		modelBean="<%= commerceSubscriptionEntry %>"
		portletURL="<%= currentURLObj %>"
	/>
</div>