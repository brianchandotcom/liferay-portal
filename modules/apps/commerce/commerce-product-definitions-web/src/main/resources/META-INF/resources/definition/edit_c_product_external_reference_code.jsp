<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CProduct cProduct = cpDefinitionsDisplayContext.getCProduct();
%>

<portlet:actionURL name="/cp_definitions/edit_c_product_external_reference_code" var="editCProductExternalReferenceCodeURL" />

<commerce-ui:modal-content>
	<aui:form action="<%= editCProductExternalReferenceCodeURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="cProductId" type="hidden" value="<%= cProduct.getCProductId() %>" />

		<aui:model-context bean="<%= cProduct %>" model="<%= CProduct.class %>" />

		<aui:input name="externalReferenceCode" type="text" value="<%= cProduct.getExternalReferenceCode() %>" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>