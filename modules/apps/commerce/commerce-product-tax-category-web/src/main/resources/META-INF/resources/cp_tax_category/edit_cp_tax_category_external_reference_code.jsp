<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPTaxCategoryDisplayContext cpTaxCategoryDisplayContext = (CPTaxCategoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPTaxCategory cpTaxCategory = cpTaxCategoryDisplayContext.getCPTaxCategory();
%>

<portlet:actionURL name="/cp_tax_category/edit_cp_tax_category_external_reference_code" var="editCPTaxCategoryExternalReferenceCodeURL" />

<commerce-ui:modal-content>
	<liferay-ui:error exception="<%= DuplicateCPTaxCategoryException.class %>" message="please-enter-a-unique-external-reference-code" />

	<aui:form action="<%= editCPTaxCategoryExternalReferenceCodeURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="cpTaxCategoryId" type="hidden" value="<%= cpTaxCategory.getCPTaxCategoryId() %>" />

		<aui:model-context bean="<%= cpTaxCategory %>" model="<%= CPTaxCategory.class %>" />

		<aui:input name="externalReferenceCode" type="text" value="<%= cpTaxCategory.getExternalReferenceCode() %>" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>