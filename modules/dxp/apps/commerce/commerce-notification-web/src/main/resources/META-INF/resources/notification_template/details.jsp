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

CommerceNotificationTemplate commerceNotificationTemplate = commerceNotificationsDisplayContext.getCommerceNotificationTemplate();

String name = BeanParamUtil.getString(commerceNotificationTemplate, renderRequest, "name");
String description = BeanParamUtil.getString(commerceNotificationTemplate, renderRequest, "description");
String cc = BeanParamUtil.getString(commerceNotificationTemplate, renderRequest, "cc");
String ccn = BeanParamUtil.getString(commerceNotificationTemplate, renderRequest, "ccn");
String type = BeanParamUtil.getString(commerceNotificationTemplate, renderRequest, "type");

CommerceNotificationType commerceNotificationType = commerceNotificationsDisplayContext.getCommerceNotificationType(type);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<liferay-ui:error exception="<%= CommerceNotificationTemplateNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= CommerceNotificationTemplateTypeException.class %>" message="please-select-a-valid-type" />

<aui:model-context bean="<%= commerceNotificationTemplate %>" model="<%= CommerceNotificationTemplate.class %>" />

<aui:fieldset>
	<aui:input name="name" value="<%= name %>" />

	<aui:input name="description" value="<%= description %>" />

	<%@ include file="/notification_template/user_segments.jspf" %>

	<aui:input name="cc" value="<%= cc %>" />

	<aui:input name="ccn" value="<%= ccn %>" />

	<aui:select name="type" onChange='<%= renderResponse.getNamespace() + "selectType();" %>' showEmptyOption="<%= true %>">

		<%
		List<CommerceNotificationType> commerceNotificationTypes = commerceNotificationsDisplayContext.getCommerceNotificationTypes();

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