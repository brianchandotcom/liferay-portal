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
CommerceTaxMethodsDisplayContext commerceTaxMethodsDisplayContext = (CommerceTaxMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTaxMethod commerceTaxMethod = commerceTaxMethodsDisplayContext.getCommerceTaxMethod();

long commerceTaxMethodId = 0;

if (commerceTaxMethod != null) {
	commerceTaxMethodId = commerceTaxMethod.getCommerceTaxMethodId();
}
%>

<portlet:actionURL name="/commerce_tax_methods/edit_commerce_tax_method" var="editCommerceTaxMethodActionURL" />

<aui:form action="<%= editCommerceTaxMethodActionURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceTaxMethod == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceTaxMethodsDisplayContext.getCommerceChannelId() %>" />
	<aui:input name="commerceTaxMethodId" type="hidden" value="<%= commerceTaxMethodId %>" />
	<aui:input name="commerceTaxMethodEngineKey" type="hidden" value="<%= commerceTaxMethodsDisplayContext.getCommerceTaxMethodEngineKey() %>" />

	<liferay-ui:error exception="<%= CommerceTaxMethodNameException.class %>" message="please-enter-a-valid-name" />

	<commerce-ui:panel>
		<aui:input autoFocus="<%= true %>" label="name" localized="<%= true %>" name="nameMapAsXML" type="text" value='<%= BeanParamUtil.getString(commerceTaxMethod, request, "name", commerceTaxMethodsDisplayContext.getCommerceTaxMethodEngineName(locale)) %>'>
			<aui:validator name="required" />
		</aui:input>

		<aui:input label="description" localized="<%= true %>" name="descriptionMapAsXML" type="text" value='<%= BeanParamUtil.getString(commerceTaxMethod, request, "description", commerceTaxMethodsDisplayContext.getCommerceTaxMethodEngineDescription(locale)) %>' />

		<aui:model-context bean="<%= commerceTaxMethod %>" model="<%= CommerceTaxMethod.class %>" />

		<aui:input checked="<%= (commerceTaxMethod == null) ? false : commerceTaxMethod.getPercentage() %>" name="percentage" type="toggle-switch" />

		<aui:input checked="<%= (commerceTaxMethod == null) ? false : commerceTaxMethod.isActive() %>" name="active" type="toggle-switch" />
	</commerce-ui:panel>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>