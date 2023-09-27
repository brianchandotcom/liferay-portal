<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String specificationNavbarItemKey = ParamUtil.getString(request, "specificationNavbarItemKey", "specification-labels");

CPSpecificationOption cpSpecificationOption = (CPSpecificationOption)request.getAttribute(CPWebKeys.CP_SPECIFICATION_OPTION);

long cpSpecificationOptionId = BeanParamUtil.getLong(cpSpecificationOption, request, "CPSpecificationOptionId");

renderResponse.setTitle(LanguageUtil.get(request, "specifications"));

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<%@ include file="/navbar_specifications.jspf" %>

<portlet:actionURL name="/cp_specification_options/edit_cp_specification_option" var="editProductSpecificationOptionActionURL" />

<aui:form action="<%= editProductSpecificationOptionActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpSpecificationOption == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="cpSpecificationOptionId" type="hidden" value="<%= String.valueOf(cpSpecificationOptionId) %>" />

	<div class="lfr-form-content">
		<liferay-frontend:form-navigator
			backURL="<%= redirect %>"
			formModelBean="<%= cpSpecificationOption %>"
			id="<%= CPSpecificationOptionFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_SPECIFICATION_OPTION %>"
		/>
	</div>
</aui:form>

<liferay-frontend:component
	module="js/EditCPSpecificationOption"
/>