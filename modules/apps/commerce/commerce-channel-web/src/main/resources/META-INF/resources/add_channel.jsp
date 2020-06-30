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
CommerceChannelDisplayContext commerceChannelDisplayContext = (CommerceChannelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceChannel commerceChannel = commerceChannelDisplayContext.getCommerceChannel();
long commerceChannelId = commerceChannelDisplayContext.getCommerceChannelId();
List<CommerceChannelType> commerceChannelTypes = commerceChannelDisplayContext.getCommerceChannelTypes();
List<CommerceCurrency> commerceCurrencies = commerceChannelDisplayContext.getCommerceCurrencies();

String name = BeanParamUtil.getString(commerceChannel, request, "name");
String commerceCurrencyCode = BeanParamUtil.getString(commerceChannel, request, "commerceCurrencyCode");
String type = BeanParamUtil.getString(commerceChannel, request, "type");

boolean isViewOnly = false;

if (commerceChannel != null) {
	isViewOnly = !commerceChannelDisplayContext.hasPermission(commerceChannelId, ActionKeys.UPDATE);
}
%>

<commerce-ui:modal-content
	submitButtonLabel='<%= LanguageUtil.get(request, "add") %>'
	title='<%= LanguageUtil.get(request, "add-channel") %>'
>
	<portlet:actionURL name="editCommerceChannel" var="editCommerceChannelActionURL" />

	<aui:form action="<%= editCommerceChannelActionURL %>" method="post" name="channelFm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceChannel == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURLObj %>" />
		<aui:input name="addTypeSettings" type="hidden" />
		<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelId %>" />
		<aui:input name="deleteTypeSettings" type="hidden" />

		<div class="lfr-form-content">
			<aui:model-context bean="<%= commerceChannel %>" model="<%= CommerceChannel.class %>" />

			<aui:input autoFocus="<%= true %>" disabled="<%= isViewOnly %>" name="name" value="<%= name %>" />

			<aui:select label="currency" name="commerceCurrencyCode" required="<%= true %>" title="currency">

				<%
				for (CommerceCurrency commerceCurrency : commerceCurrencies) {
				%>

					<aui:option label="<%= commerceCurrency.getName(locale) %>" selected="<%= (commerceChannel == null) ? commerceCurrency.isPrimary() : commerceCurrencyCode.equals(commerceCurrency.getCode()) %>" value="<%= commerceCurrency.getCode() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:select disabled="<%= isViewOnly %>" name="type" showEmptyOption="<%= true %>">

				<%
				for (CommerceChannelType commerceChannelType : commerceChannelTypes) {
					String commerceChannelTypeKey = commerceChannelType.getKey();
				%>

					<aui:option label="<%= commerceChannelType.getLabel(locale) %>" selected="<%= (commerceChannel != null) && commerceChannelTypeKey.equals(type) %>" value="<%= commerceChannelTypeKey %>" />

				<%
				}
				%>

			</aui:select>
		</div>
	</aui:form>
</commerce-ui:modal-content>