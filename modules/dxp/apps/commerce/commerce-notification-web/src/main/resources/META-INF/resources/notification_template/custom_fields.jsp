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
CommerceNotificationsDisplayContext commerceNotificationsDisplayContext = (CommerceNotificationsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="custom-fields"
/>

<aui:model-context bean="<%= commerceNotificationsDisplayContext.getCommerceNotificationTemplate() %>" model="<%= CommerceNotificationTemplate.class %>" />

<liferay-expando:custom-attribute-list
	className="<%= CommerceNotificationTemplate.class.getName() %>"
	classPK="<%= commerceNotificationsDisplayContext.getCommerceNotificationTemplateId() %>"
	editable="<%= true %>"
	label="<%= true %>"
/>