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
CommerceProductOption commerceProductOption = (CommerceProductOption)request.getAttribute(CommerceProductWebKeys.COMMERCE_PRODUCT_OPTION);

long commerceProductOptionId = BeanParamUtil.getLong(commerceProductOption, request, "commerceProductOptionId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((commerceProductOption == null) ? LanguageUtil.get(request, "add-product-option") : commerceProductOption.getName(locale));
%>

<portlet:actionURL name="editProductOption" var="editProductOptionActionURL" />

<aui:form action="<%= editProductOptionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceProductOption == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="commerceProductOptionId" type="hidden" value="<%= commerceProductOptionId %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= backURL %>"
			formModelBean="<%= commerceProductOption %>"
			id="<%= CommerceProductOptionFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_OPTION %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>