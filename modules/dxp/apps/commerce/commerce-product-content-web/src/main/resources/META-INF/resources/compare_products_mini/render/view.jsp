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
CPCompareContentHelper cpCompareContentHelper = (CPCompareContentHelper)request.getAttribute(CPContentWebKeys.CP_COMPARE_CONTENT_HELPER);
CPDataSourceResult cpDataSourceResult = (CPDataSourceResult)request.getAttribute(CPWebKeys.CP_DATA_SOURCE_RESULT);

List<CPCatalogEntry> cpCatalogEntries = cpDataSourceResult.getCPCatalogEntries();
%>

<c:if test="<%= !cpCatalogEntries.isEmpty() %>">
	<div id="<portlet:namespace />compareProductsMiniContainer">
		<div class="compare-products-mini-header">
			<a class="collapse-icon lfr-compare-products-mini-header" href="javascript:;">
				<span class="component-title"><liferay-ui:message arguments="<%= new Object[] {cpCatalogEntries.size(), cpCompareContentHelper.getProductsLimit(portletDisplay)} %>" key="x-of-x-products-selected" translateArguments="<%= false %>" /></span>

				<span class="collapse-icon-open">
					<liferay-ui:icon
						icon="angle-down"
						markupView="lexicon"
					/>
				</span>
				<span class="collapse-icon-closed">
					<liferay-ui:icon
						icon="angle-up"
						markupView="lexicon"
					/>
				</span>
			</a>
		</div>

		<div class="lfr-compare-products-mini-content">
			<ul class="card-page">

				<%
				for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
					request.setAttribute("cpContentListRenderer-deleteCompareProductURL", cpCompareContentHelper.getDeleteCompareProductURL(cpCatalogEntry.getCPDefinitionId(), renderRequest, renderResponse));
				%>

					<li class="card-page-item">
						<liferay-commerce-product:product-list-entry-renderer
							CPCatalogEntry = "<%= cpCatalogEntry %>"
						/>
					</li>

				<%
				}
				%>

				<li class="card-page-item card-page-item-shrink">
					<a class="btn btn-link" href="<%= HtmlUtil.escape(cpCompareContentHelper.getClearCompareProductsURL(renderRequest, renderResponse)) %>"><liferay-ui:message key="clear-all" /></a>
				</li>
				<li class="card-page-item card-page-item-shrink">
					<aui:button cssClass="btn-primary" href="<%= HtmlUtil.escape(cpCompareContentHelper.getCompareProductsURL(themeDisplay)) %>" value="compare" />
				</li>
			</ul>
		</div>
	</div>

	<aui:script use="aui-toggler">
		new A.Toggler(
			{
				animated: true,
				content: '#<portlet:namespace />compareProductsMiniContainer .lfr-compare-products-mini-content',
				expanded: true,
				header: '#<portlet:namespace />compareProductsMiniContainer .lfr-compare-products-mini-header'
			}
		);
	</aui:script>
</c:if>