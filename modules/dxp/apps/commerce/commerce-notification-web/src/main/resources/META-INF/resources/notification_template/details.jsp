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
CommerceNotificationTemplatesDisplayContext commerceNotificationTemplatesDisplayContext = (CommerceNotificationTemplatesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceNotificationTemplate commerceNotificationTemplate = commerceNotificationTemplatesDisplayContext.getCommerceNotificationTemplate();

String name = BeanParamUtil.getString(commerceNotificationTemplate, renderRequest, "name");
String description = BeanParamUtil.getString(commerceNotificationTemplate, renderRequest, "description");
String from = BeanParamUtil.getString(commerceNotificationTemplate, renderRequest, "from");
String fromName = BeanParamUtil.getString(commerceNotificationTemplate, renderRequest, "fromName");
String cc = BeanParamUtil.getString(commerceNotificationTemplate, renderRequest, "cc");
String bcc = BeanParamUtil.getString(commerceNotificationTemplate, renderRequest, "bcc");
String type = BeanParamUtil.getString(commerceNotificationTemplate, renderRequest, "type");

CommerceNotificationType commerceNotificationType = commerceNotificationTemplatesDisplayContext.getCommerceNotificationType(type);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<liferay-ui:error exception="<%= CommerceNotificationTemplateFromException.class %>" message="please-enter-a-valid-email-address" />
<liferay-ui:error exception="<%= CommerceNotificationTemplateNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= CommerceNotificationTemplateTypeException.class %>" message="please-select-a-valid-type" />

<aui:model-context bean="<%= commerceNotificationTemplate %>" model="<%= CommerceNotificationTemplate.class %>" />

<aui:fieldset>
	<aui:input name="name" value="<%= name %>" />

	<aui:input name="description" value="<%= description %>" />

	<%@ include file="/notification_template/user_segments.jspf" %>

	<aui:input label="from-address" name="from" value="<%= from %>" />

	<aui:input name="fromName" value="<%= fromName %>" />

	<aui:input name="cc" value="<%= cc %>" />

	<aui:input name="bcc" value="<%= bcc %>" />

	<aui:select name="type" onChange='<%= renderResponse.getNamespace() + "selectType();" %>' showEmptyOption="<%= true %>">

		<%
		List<CommerceNotificationType> commerceNotificationTypes = commerceNotificationTemplatesDisplayContext.getCommerceNotificationTypes();

		for (CommerceNotificationType curCommerceNotificationType : commerceNotificationTypes) {
			String commerceNotificationTypeKey = curCommerceNotificationType.getKey();
		%>

			<aui:option label="<%= curCommerceNotificationType.getLabel(locale) %>" selected="<%= (commerceNotificationType != null) && commerceNotificationTypeKey.equals(type) %>" value="<%= commerceNotificationTypeKey %>" />

		<%
		}
		%>

	</aui:select>

	<c:if test="<%= commerceNotificationType != null %>">

		<%
		Map<String, String> definitionTerms = commerceNotificationType.getDefinitionTerms(locale);
		%>

		<%@ include file="/notification_template/notification_editor.jspf" %>
	</c:if>
</aui:fieldset>