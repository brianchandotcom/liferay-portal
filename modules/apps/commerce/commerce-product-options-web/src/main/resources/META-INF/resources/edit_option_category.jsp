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

String specificationNavbarItemKey = ParamUtil.getString(request, "specificationNavbarItemKey", "specification-groups");

CPOptionCategory cpOptionCategory = (CPOptionCategory)request.getAttribute(CPWebKeys.CP_OPTION_CATEGORY);

long cpOptionCategoryId = BeanParamUtil.getLong(cpOptionCategory, request, "CPOptionCategoryId");

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

<portlet:actionURL name="editProductOptionCategory" var="editProductOptionCategoryActionURL" />

<aui:form action="<%= editProductOptionCategoryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpOptionCategory == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="cpOptionCategoryId" type="hidden" value="<%= String.valueOf(cpOptionCategoryId) %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= redirect %>"
			formModelBean="<%= cpOptionCategory %>"
			id="<%= CPOptionCategoryFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_OPTION_CATEGORY %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>