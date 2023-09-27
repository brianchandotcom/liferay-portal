<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	<table class="products-comparison-table table table-autofit table-bordered table-list table-nowrap entries-<%= cpCatalogEntries.size() %>">
		<thead>
			<tr>
				<td></td>

				<%
				for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
				%>

					<td class="table-cell-expand">
						<liferay-commerce-product:product-list-entry-renderer
							CPCatalogEntry="<%= cpCatalogEntry %>"
						/>
					</td>

				<%
				}
				%>

			</tr>
		</thead>

		<tbody>
			<c:if test="<%= !cpDefinitionOptionRelTitles.isEmpty() %>">
				<tr>
					<td colspan="<%= cpCatalogEntries.size() + 1 %>">
						<liferay-ui:message key="options" />
					</td>
				</tr>

				<%
				for (String cpDefinitionOptionRelTitle : cpDefinitionOptionRelTitles) {
				%>

					<tr>
						<td>
							<%= HtmlUtil.escape(cpDefinitionOptionRelTitle) %>
						</td>

						<%
						for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
						%>

							<td class="table-cell-expand">
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
				<tr class="table-divider">
					<td colspan="<%= cpCatalogEntries.size() + 1 %>">
						<liferay-ui:message key="specifications" />
					</td>
				</tr>

				<%
				for (CPSpecificationOption cpSpecificationOption : cpSpecificationOptions) {
				%>

					<tr>
						<td>
							<%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %>
						</td>

						<%
						for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
						%>

							<td class="table-cell-expand">
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
					<tr class="table-divider">
						<td colspan="<%= cpCatalogEntries.size() + 1 %>">
							<%= HtmlUtil.escape(cpOptionCategory.getTitle(languageId)) %>
						</td>
					</tr>

					<%
					for (CPSpecificationOption cpSpecificationOption : categorizedCPSpecificationOptions) {
						if (cpSpecificationOption.getCPOptionCategoryId() != cpOptionCategory.getCPOptionCategoryId()) {
							continue;
						}
					%>

						<tr>
							<td>
								<%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %>
							</td>

							<%
							for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
							%>

								<td class="table-cell-expand">
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