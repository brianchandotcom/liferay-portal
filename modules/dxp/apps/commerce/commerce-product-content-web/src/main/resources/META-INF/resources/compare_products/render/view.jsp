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

Set<String> cpDefinitionOptionRelTitles = cpCompareContentHelper.getCPDefinitionOptionRelNames(cpDataSourceResult, locale);
Set<CPSpecificationOption> cpSpecificationOptions = cpCompareContentHelper.getCPSpecificationOptions(cpDataSourceResult);
Set<CPSpecificationOption> categorizedCPSpecificationOptions = cpCompareContentHelper.getCategorizedCPSpecificationOptions(cpDataSourceResult);
List<CPOptionCategory> cpOptionCategories = cpCompareContentHelper.getCPOptionCategories(scopeGroupId);
%>

<c:if test="<%= !cpCatalogEntries.isEmpty() %>">
	<div class="commerce-compare-table">
		<table class="table table-sm">
			<tr class="product-table-row">
				<td></td>

				<%
				for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
				%>

					<td>
						<liferay-commerce-product:product-list-entry-renderer
							CPCatalogEntry = "<%= cpCatalogEntry %>"
						/>
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

							<td><%= HtmlUtil.escape(cpCompareContentHelper.getCPDefinitionOptionValueRels(cpCatalogEntry, cpDefinitionOptionRelTitle, locale)) %></td>

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
			String dimensionCPMeasurementUnitName = cpCompareContentHelper.getDimensionCPMeasurementUnitName(scopeGroupId, locale);

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

							<td><%= HtmlUtil.escape(cpCompareContentHelper.getCPDefinitionSpecificationOptionValue(cpCatalogEntry.getCPDefinitionId(), cpSpecificationOption.getCPSpecificationOptionId(), locale)) %></td>

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

				<c:if test="<%= cpCompareContentHelper.hasCategorizedCPDefinitionSpecificationOptionValues(cpDataSourceResult, cpOptionCategory.getCPOptionCategoryId()) %>">
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

								<td><%= HtmlUtil.escape(cpCompareContentHelper.getCPDefinitionSpecificationOptionValue(cpCatalogEntry.getCPDefinitionId(), cpSpecificationOption.getCPSpecificationOptionId(), locale)) %></td>

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