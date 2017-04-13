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
CommerceProductOptionValue commerceProductOptionValue = (CommerceProductOptionValue)request.getAttribute(CommerceProductWebKeys.COMMERCE_PRODUCT_OPTION_VALUE);

long commerceProductOptionValueId = BeanParamUtil.getLong(commerceProductOptionValue, request, "commerceProductOptionValueId");

long commerceProductOptionId = ParamUtil.getLong(request, "commerceProductOptionId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((commerceProductOptionValue == null) ? LanguageUtil.get(request, "add-product-option-value") : commerceProductOptionValue.getTitle(locale));
%>

<portlet:actionURL name="editProductOptionValue" var="editProductOptionValueActionURL" />

<aui:form action="<%= editProductOptionValueActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceProductOptionValue == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="commerceProductOptionId" type="hidden" value="<%= commerceProductOptionId %>" />
	<aui:input name="commerceProductOptionValueId" type="hidden" value="<%= commerceProductOptionValueId %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= backURL %>"
			formModelBean="<%= commerceProductOptionValue %>"
			id="<%= CommerceProductOptionValueFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_OPTION_VALUE %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>