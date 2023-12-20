<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:actionURL name="/commerce_payment/edit_function_commerce_shipping_method_configuration" var="editFunctionCommerceShippingMethodActionURL" />

<aui:form action="<%= editFunctionCommerceShippingMethodActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value='<%= ParamUtil.getLong(request, "commerceChannelId") %>' />
	<aui:input name="commerceShippingMethodEngineKey" type="hidden" value='<%= ParamUtil.getString(request, "commerceShippingMethodEngineKey") %>' />
	<aui:input name="commerceShippingMethodId" type="hidden" value='<%= ParamUtil.getLong(request, "commerceShippingMethodId") %>' />

	<c:if test="<%= (boolean)request.getAttribute(FunctionCommerceShippingEngineWebKeys.IS_DEFAULT_VALUE) %>">
		<div class="alert alert-info">
			<liferay-ui:message key="use-default-values" />
		</div>
	</c:if>

	<commerce-ui:panel>
		<aui:input autoSize="<%= true %>" id="shipping-method-type-settings" label="type-settings" name="settings--shippingMethodTypeSettings--" style="min-height: 600px;" type="textarea" value="<%= (UnicodeProperties)request.getAttribute(FunctionCommerceShippingEngineWebKeys.SHIPPING_METHOD_TYPE_SETTINGS) %>" />
	</commerce-ui:panel>

	<commerce-ui:panel
		bodyClasses="p-0"
	>
		<frontend-data-set:classic-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"commerceChannelId", ParamUtil.getString(request, "commerceChannelId")
				).put(
					"commerceShippingMethodEngineKey", ParamUtil.getString(request, "commerceShippingMethodEngineKey")
				).build()
			%>'
			dataProviderKey="<%= FunctionCommerceShippingEngineFDSNames.FUNCTION_COMMERCE_SHIPPING_ENGINE_OPTIONS %>"
			id="<%= FunctionCommerceShippingEngineFDSNames.FUNCTION_COMMERCE_SHIPPING_ENGINE_OPTIONS %>"
			itemsPerPage="<%= 10 %>"
			showManagementBar="<%= false %>"
			style="fluid"
		/>
	</commerce-ui:panel>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>