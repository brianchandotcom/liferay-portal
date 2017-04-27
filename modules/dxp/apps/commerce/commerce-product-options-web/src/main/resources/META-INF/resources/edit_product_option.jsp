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
CPOption cpOption = (CPOption)request.getAttribute(CPWebKeys.COMMERCE_PRODUCT_OPTION);

long cpOptionId = BeanParamUtil.getLong(cpOption, request, "cpOptionId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((cpOption == null) ? LanguageUtil.get(request, "add-product-option") : cpOption.getName(locale));
%>

<portlet:actionURL name="editProductOption" var="editProductOptionActionURL" />

<aui:form action="<%= editProductOptionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpOption == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="cpOptionId" type="hidden" value="<%= cpOptionId %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= backURL %>"
			formModelBean="<%= cpOption %>"
			id="<%= CPOptionFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_OPTION %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>