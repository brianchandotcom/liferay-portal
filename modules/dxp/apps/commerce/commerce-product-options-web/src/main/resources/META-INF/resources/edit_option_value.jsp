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
CPOptionValue cpOptionValue = (CPOptionValue)request.getAttribute(CPWebKeys.COMMERCE_PRODUCT_OPTION_VALUE);

long cpOptionValueId = BeanParamUtil.getLong(cpOptionValue, request, "cpOptionValueId");

long cpOptionId = ParamUtil.getLong(request, "cpOptionId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((cpOptionValue == null) ? LanguageUtil.get(request, "add-option-value") : cpOptionValue.getTitle(locale));
%>

<portlet:actionURL name="editProductOptionValue" var="editProductOptionValueActionURL" />

<aui:form action="<%= editProductOptionValueActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpOptionValue == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="cpOptionId" type="hidden" value="<%= cpOptionId %>" />
	<aui:input name="cpOptionValueId" type="hidden" value="<%= cpOptionValueId %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= backURL %>"
			formModelBean="<%= cpOptionValue %>"
			id="<%= CPOptionValueFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_OPTION_VALUE %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>