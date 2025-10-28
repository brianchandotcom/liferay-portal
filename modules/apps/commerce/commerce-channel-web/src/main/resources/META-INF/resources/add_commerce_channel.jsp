<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceChannelDisplayContext commerceChannelDisplayContext = (CommerceChannelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CommerceChannelType> commerceChannelTypes = commerceChannelDisplayContext.getCommerceChannelTypes();
List<CommerceCurrency> commerceCurrencies = commerceChannelDisplayContext.getCommerceCurrencies();

String commerceCurrencyCode = ParamUtil.getString(request, "commerceCurrencyCode");
String type = ParamUtil.getString(request, "type");
%>

<commerce-ui:modal-content
	submitButtonLabel='<%= LanguageUtil.get(request, "add") %>'
	title='<%= LanguageUtil.get(request, "add-channel") %>'
	useNativeSubmit="<%= false %>"
>
	<portlet:actionURL name="/commerce_channels/edit_commerce_channel" var="editCommerceChannelActionURL" />

	<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>' useNamespace="<%= false %>">
		<div>
			<aui:model-context model="<%= CommerceChannel.class %>" />

			<aui:input name="name" value='<%= ParamUtil.getString(request, "name") %>' />

			<aui:select label="currency" name="currencyCode" required="<%= true %>" title="currency">

				<%
				for (CommerceCurrency commerceCurrency : commerceCurrencies) {
				%>

					<aui:option label="<%= commerceCurrency.getName(locale) %>" selected="<%= Validator.isNull(commerceCurrencyCode) ? commerceCurrency.isPrimary() : commerceCurrencyCode.equals(commerceCurrency.getCode()) %>" value="<%= commerceCurrency.getCode() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:select name="type" showEmptyOption="<%= true %>">

				<%
				for (CommerceChannelType commerceChannelType : commerceChannelTypes) {
					String commerceChannelTypeKey = commerceChannelType.getKey();
				%>

					<aui:option label="<%= commerceChannelType.getLabel(locale) %>" selected="<%= Validator.isNull(type) ? false : type.equals(commerceChannelTypeKey) %>" value="<%= commerceChannelTypeKey %>" />

				<%
				}
				%>

			</aui:select>
		</div>
	</aui:form>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"getEditCommerceChannelRenderURL", String.valueOf(commerceChannelDisplayContext.getEditCommerceChannelRenderURL())
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).build()
		%>'
		module="{addCommerceChannel} from commerce-channel-web"
	/>
</commerce-ui:modal-content>