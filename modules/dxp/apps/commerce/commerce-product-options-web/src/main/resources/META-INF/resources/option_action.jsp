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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CPOption cpOption = null;

if (row != null) {
	cpOption = (CPOption)row.getObject();
}
else {
	cpOption = (CPOption)request.getAttribute("info_panel.jsp-entry");
}
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="editProductOption" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backURL" value="<%= backURL %>" />
		<portlet:param name="cpOptionId" value="<%= String.valueOf(cpOption.getCPOptionId()) %>" />
		<portlet:param name="toolbarItem" value="view-product-option-details" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="editProductOption" var="setFacetableURL">
		<portlet:param name="<%= Constants.CMD %>" value="setFacetable" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backURL" value="<%= backURL %>" />
		<portlet:param name="cpOptionId" value="<%= String.valueOf(cpOption.getCPOptionId()) %>" />
		<portlet:param name="facetable" value="<%= String.valueOf(!cpOption.getFacetable()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message='<%= (cpOption.getFacetable()) ? LanguageUtil.get(request, "unset-as-facetable") : LanguageUtil.get(request, "set-as-facetable") %>'
		url="<%= setFacetableURL %>"
	/>

	<portlet:actionURL name="editProductOption" var="setRequiredURL">
		<portlet:param name="<%= Constants.CMD %>" value="setRequired" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backURL" value="<%= backURL %>" />
		<portlet:param name="cpOptionId" value="<%= String.valueOf(cpOption.getCPOptionId()) %>" />
		<portlet:param name="required" value="<%= String.valueOf(!cpOption.getRequired()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message='<%= (cpOption.getRequired()) ? LanguageUtil.get(request, "unset-as-required") : LanguageUtil.get(request, "set-as-required") %>'
		url="<%= setRequiredURL %>"
	/>

	<portlet:actionURL name="editProductOption" var="setSkuContributorURL">
		<portlet:param name="<%= Constants.CMD %>" value="setSkuContributor" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backURL" value="<%= backURL %>" />
		<portlet:param name="cpOptionId" value="<%= String.valueOf(cpOption.getCPOptionId()) %>" />
		<portlet:param name="skuContributor" value="<%= String.valueOf(!cpOption.getSkuContributor()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message='<%= (cpOption.getSkuContributor()) ? LanguageUtil.get(request, "unset-as-sku-contributor") : LanguageUtil.get(request, "set-as-sku-contributor") %>'
		url="<%= setSkuContributorURL %>"
	/>

	<portlet:actionURL name="editProductOption" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backURL" value="<%= backURL %>" />
		<portlet:param name="cpOptionId" value="<%= String.valueOf(cpOption.getCPOptionId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>