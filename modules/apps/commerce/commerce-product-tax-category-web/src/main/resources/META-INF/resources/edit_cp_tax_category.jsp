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
String redirect = ParamUtil.getString(request, "redirect");

CPTaxCategoryDisplayContext cpTaxCategoryDisplayContext = (CPTaxCategoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPTaxCategory cpTaxCategory = cpTaxCategoryDisplayContext.getCPTaxCategory();

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<portlet:actionURL name="/cp_tax_category/edit_cp_tax_category" var="editCPTaxCategoryActionURL" />

<liferay-portlet:renderURL var="editCPTaxCategoryExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/cp_tax_category/edit_cp_tax_category_external_reference_code" />
	<portlet:param name="cpTaxCategoryId" value="<%= String.valueOf(cpTaxCategory.getCPTaxCategoryId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= cpTaxCategoryDisplayContext.getHeaderActionModels() %>"
	bean="<%= cpTaxCategory %>"
	beanIdLabel="id"
	externalReferenceCode="<%= cpTaxCategory.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= editCPTaxCategoryExternalReferenceCodeURL %>"
	model="<%= CPTaxCategory.class %>"
	title="<%= cpTaxCategory.getName(locale) %>"
	wrapperCssClasses="side-panel-top-anchor"
/>

<aui:form action="<%= editCPTaxCategoryActionURL %>" cssClass="col pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpTaxCategory == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpTaxCategoryId" type="hidden" value="<%= (cpTaxCategory == null) ? 0 : cpTaxCategory.getCPTaxCategoryId() %>" />

	<div class="container">
		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "details") %>'
		>
			<liferay-ui:error exception="<%= CPTaxCategoryNameException.class %>" message="please-enter-a-valid-name" />

			<aui:model-context bean="<%= cpTaxCategory %>" model="<%= CPTaxCategory.class %>" />

			<aui:fieldset>
				<aui:input autoFocus="<%= true %>" name="name" />

				<aui:input name="description" />
			</aui:fieldset>
		</commerce-ui:panel>
	</div>
</aui:form>