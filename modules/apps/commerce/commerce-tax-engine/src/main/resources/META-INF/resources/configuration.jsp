<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:actionURL name="/commerce_payment/edit_function_commerce_tax_method_configuration" var="editFunctionCommerceTaxMethodActionURL" />

<aui:form action="<%= editFunctionCommerceTaxMethodActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value='<%= ParamUtil.getLong(request, "commerceChannelId") %>' />
	<aui:input name="commerceTaxMethodEngineKey" type="hidden" value='<%= ParamUtil.getString(request, "commerceTaxMethodEngineKey") %>' />
	<aui:input name="commerceTaxMethodId" type="hidden" value='<%= ParamUtil.getLong(request, "commerceTaxMethodId") %>' />

	<c:if test="<%= (boolean)request.getAttribute(FunctionCommerceTaxEngineWebKeys.IS_DEFAULT_VALUE) %>">
		<div class="alert alert-info">
			<liferay-ui:message key="use-default-values" />
		</div>
	</c:if>

	<commerce-ui:panel>
		<aui:input autoSize="<%= true %>" id="tax-method-type-settings" label="type-settings" name="settings--taxMethodTypeSettings--" type="textarea" value="<%= (UnicodeProperties)request.getAttribute(FunctionCommerceTaxEngineWebKeys.TAX_METHOD_TYPE_SETTINGS) %>" />
	</commerce-ui:panel>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>