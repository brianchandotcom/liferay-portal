<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				<aui:input name="name" />

				<aui:input name="description" />
			</aui:fieldset>
		</commerce-ui:panel>
	</div>
</aui:form>