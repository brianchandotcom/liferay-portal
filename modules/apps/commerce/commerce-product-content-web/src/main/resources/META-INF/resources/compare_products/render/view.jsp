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
List<CPOptionCategory> cpOptionCategories = cpCompareContentHelper.getCPOptionCategories(company.getCompanyId());
%>

<c:if test="<%= !cpCatalogEntries.isEmpty() %>">
	<table class="commerce-compare-table entry--<%= cpCatalogEntries.size() %>">
		<thead>
			<tr class="commerce-compare-table__card-row">
				<td></td>

				<%
				for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
				%>

					<td class="commerce-compare-table__card">
						<liferay-commerce-product:product-list-entry-renderer
							CPCatalogEntry = "<%= cpCatalogEntry %>"
						/>
					</td>

				<%
				}
				%>

			</tr>
		</thead>

		<tbody>
			<c:if test="<%= !cpDefinitionOptionRelTitles.isEmpty() %>">
				<tr class="commerce-compare-table__separator">
					<td colspan="<%= cpCatalogEntries.size() + 1 %>">
						<span class="commerce-compare-table__title"><liferay-ui:message key="options" /></span>
					</td>
				</tr>

				<%
				for (String cpDefinitionOptionRelTitle : cpDefinitionOptionRelTitles) {
				%>

					<tr class="commerce-compare-table__common-row">
						<td class="commerce-compare-table__title">
							<%= HtmlUtil.escape(cpDefinitionOptionRelTitle) %>
						</td>

						<%
						for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
						%>

							<td class="commerce-compare-table__value">
								<%= HtmlUtil.escape(cpCompareContentHelper.getCPDefinitionOptionValueRels(cpCatalogEntry, cpDefinitionOptionRelTitle, locale)) %>
							</td>

						<%
						}
						%>

					</tr>

				<%
				}
				%>

			</c:if>

			<c:if test="<%= !cpSpecificationOptions.isEmpty() %>">
				<tr class="commerce-compare-table__separator">
					<td colspan="<%= cpCatalogEntries.size() + 1 %>">
						<span class="commerce-compare-table__title">
							<liferay-ui:message key="specifications" />
						</span>
					</td>
				</tr>

				<%
				for (CPSpecificationOption cpSpecificationOption : cpSpecificationOptions) {
				%>

					<tr class="commerce-compare-table__common-row">
						<td class="commerce-compare-table__title">
							<%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %>
						</td>

						<%
						for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
						%>

							<td class="commerce-compare-table__value">
								<%= HtmlUtil.escape(cpCompareContentHelper.getCPDefinitionSpecificationOptionValue(cpCatalogEntry.getCPDefinitionId(), cpSpecificationOption.getCPSpecificationOptionId(), locale)) %>
							</td>

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
					<tr class="commerce-compare-table__separator">
						<td colspan="<%= cpCatalogEntries.size() + 1 %>">
							<span class="commerce-compare-table__title">
								<%= HtmlUtil.escape(cpOptionCategory.getTitle(languageId)) %>
							</span>
						</td>
					</tr>

					<%
					for (CPSpecificationOption cpSpecificationOption : categorizedCPSpecificationOptions) {
						if (cpSpecificationOption.getCPOptionCategoryId() != cpOptionCategory.getCPOptionCategoryId()) {
							continue;
						}
					%>

						<tr class="commerce-compare-table__common-row">
							<td class="commerce-compare-table__title">
								<%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %>
							</td>

							<%
							for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
							%>

								<td class="commerce-compare-table__value">
									<%= HtmlUtil.escape(cpCompareContentHelper.getCPDefinitionSpecificationOptionValue(cpCatalogEntry.getCPDefinitionId(), cpSpecificationOption.getCPSpecificationOptionId(), locale)) %>
								</td>

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

		</tbody>
	</table>
</c:if>