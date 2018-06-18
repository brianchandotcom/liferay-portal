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
CPCompareContentDisplayContext cpCompareContentDisplayContext = (CPCompareContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CPCatalogEntry> cpCatalogEntries = cpCompareContentDisplayContext.getCPCatalogEntries();
Set<String> cpDefinitionOptionRelTitles = cpCompareContentDisplayContext.getCPDefinitionOptionRelNames(cpCatalogEntries);
Set<CPSpecificationOption> cpSpecificationOptions = cpCompareContentDisplayContext.getCPSpecificationOptions(cpCatalogEntries);
Set<CPSpecificationOption> categorizedCPSpecificationOptions = cpCompareContentDisplayContext.getCategorizedCPSpecificationOptions(cpCatalogEntries);
List<CPOptionCategory> cpOptionCategories = cpCompareContentDisplayContext.getCPOptionCategories();
%>

<c:if test="<%= cpCatalogEntries.size() > 0 %>">
	<div class="commerce-compare-table">
		<table class="table table-sm">
			<tr class="product-table-row">
				<td></td>

				<%
				for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
					CPInstance cpInstance = cpCompareContentDisplayContext.getDefaultCPInstance(cpCatalogEntry);

					request.setAttribute("compare_product.jsp-cpContentListRenderer-cpCatalogEntry", cpCatalogEntry);
					request.setAttribute("compare_product.jsp-cpContentListRenderer-cpInstance", cpInstance);
					request.setAttribute("compare_product.jsp-cpContentListRenderer-deleteCompareProductURL", cpCompareContentDisplayContext.getDeleteCompareProductURL(cpCatalogEntry.getCPDefinitionId()));
				%>

					<td>

						<%
						cpCompareContentDisplayContext.renderCPContentListEntry(cpCatalogEntry);
						%>

					</td>

				<%
				}
				%>

			</tr>

			<c:if test="<%= !cpDefinitionOptionRelTitles.isEmpty() %>">
				<tr class="table-divider">
					<td colspan="<%= cpCatalogEntries.size() + 1 %>"><h4 class="table-title"><liferay-ui:message key="options" /></h4></td>
				</tr>

				<%
				for (String cpDefinitionOptionRelTitle : cpDefinitionOptionRelTitles) {
				%>

					<tr>
						<td class="title-column"><div class="table-title"><%= HtmlUtil.escape(cpDefinitionOptionRelTitle) %></div></td>

						<%
						for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
						%>

							<td><%= HtmlUtil.escape(cpCompareContentDisplayContext.getCPDefinitionOptionValueRels(cpCatalogEntry, cpDefinitionOptionRelTitle)) %></td>

						<%
						}
						%>

					</tr>

				<%
				}
				%>

			</c:if>

			<tr class="table-divider">
				<td colspan="<%= cpCatalogEntries.size() + 1 %>"><h4 class="table-title"><liferay-ui:message key="dimensions" /></h4></td>
			</tr>

			<%
			String dimensionCPMeasurementUnitName = cpCompareContentDisplayContext.getDimensionCPMeasurementUnitName();

			if (Validator.isNotNull(dimensionCPMeasurementUnitName)) {
				dimensionCPMeasurementUnitName = StringPool.OPEN_PARENTHESIS + dimensionCPMeasurementUnitName + StringPool.CLOSE_PARENTHESIS;
			}
			%>

			<tr>
				<td class="title-column"><div class="table-title"><%= LanguageUtil.get(request, "depth").concat(StringPool.SPACE).concat(HtmlUtil.escape(dimensionCPMeasurementUnitName)) %></div></td>

				<%
				for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
				%>

					<td><%= cpCatalogEntry.getDepth() %></td>

				<%
				}
				%>

			</tr>
			<tr>
				<td class="title-column"><div class="table-title"><%= LanguageUtil.get(request, "height").concat(StringPool.SPACE).concat(HtmlUtil.escape(dimensionCPMeasurementUnitName)) %></div></td>

				<%
				for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
				%>

					<td><%= cpCatalogEntry.getHeight() %></td>

				<%
				}
				%>

			</tr>

			<c:if test="<%= !cpSpecificationOptions.isEmpty() %>">
				<tr class="table-divider">
					<td colspan="<%= cpCatalogEntries.size() + 1 %>"><h4 class="table-title"><liferay-ui:message key="specifications" /></h4></td>
				</tr>

				<%
				for (CPSpecificationOption cpSpecificationOption : cpSpecificationOptions) {
				%>

					<tr>
						<td class="title-column"><div class="table-title"><%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %></div></td>

						<%
						for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
						%>

							<td><%= HtmlUtil.escape(cpCompareContentDisplayContext.getCPDefinitionSpecificationOptionValue(cpCatalogEntry.getCPDefinitionId(), cpSpecificationOption.getCPSpecificationOptionId())) %></td>

						<%
						}
						%>

					</tr>

				<%
				}
				%>

			</c:if>

			<%
			for (CPOptionCategory cpOptionCategory : cpOptionCategories) {
			%>

				<c:if test="<%= cpCompareContentDisplayContext.hasCategorizedCPDefinitionSpecificationOptionValues(cpOptionCategory.getCPOptionCategoryId()) %>">
					<tr class="table-divider">
						<td colspan="<%= cpCatalogEntries.size() + 1 %>"><h4 class="table-title"><%= HtmlUtil.escape(cpOptionCategory.getTitle(languageId)) %></h4></td>
					</tr>

					<%
					for (CPSpecificationOption cpSpecificationOption : categorizedCPSpecificationOptions) {
						if (cpSpecificationOption.getCPOptionCategoryId() != cpOptionCategory.getCPOptionCategoryId()) {
							continue;
						}
					%>

						<tr>
							<td class="title-column"><div class="table-title"><%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %></div></td>

							<%
							for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
							%>

								<td><%= HtmlUtil.escape(cpCompareContentDisplayContext.getCPDefinitionSpecificationOptionValue(cpCatalogEntry.getCPDefinitionId(), cpSpecificationOption.getCPSpecificationOptionId())) %></td>

							<%
							}
							%>

						</tr>

					<%
					}
					%>

				</c:if>

			<%
			}
			%>

		</table>
	</div>
</c:if>