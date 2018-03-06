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

String languageId = LocaleUtil.toLanguageId(locale);

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("cpCompareContentDisplayContext", cpCompareContentDisplayContext);

List<CPDefinition> cpDefinitions = cpCompareContentDisplayContext.getCPDefinitions();
Set<String> cpDefinitionOptionRelTitles = cpCompareContentDisplayContext.getCPDefinitionOptionRelTitles(cpDefinitions);
Set<CPSpecificationOption> cpSpecificationOptions = cpCompareContentDisplayContext.getCPSpecificationOptions(cpDefinitions);
Set<CPSpecificationOption> categorizedCPSpecificationOptions = cpCompareContentDisplayContext.getCategorizedCPSpecificationOptions(cpDefinitions);
List<CPOptionCategory> cpOptionCategories = cpCompareContentDisplayContext.getCPOptionCategories();
%>

<liferay-ddm:template-renderer
	className="<%= CPCompareContentPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= cpCompareContentDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= cpCompareContentDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= cpDefinitions %>"
>
	<c:if test="<%= cpDefinitions.size() > 0 %>">
		<div class="table-responsive">
			<table class="table table-sm">
				<tr>
					<td></td>

					<%
					for (CPDefinition cpDefinition : cpDefinitions) {
					%>

						<td>
							<div class="card">
								<a href="<%= HtmlUtil.escape(cpCompareContentDisplayContext.getProductFriendlyURL(cpDefinition.getCPDefinitionId())) %>">

									<%
									String img = cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);
									%>

									<c:if test="<%= Validator.isNotNull(img) %>">
										<img src="<%= img %>">
									</c:if>
								</a>

								<div class="card-row card-row-padded card-row-valign-top">
									<div class="card-col-content">
										<a class="align-middle d-inline-block truncate-text" href="<%= HtmlUtil.escape(cpCompareContentDisplayContext.getProductFriendlyURL(cpDefinition.getCPDefinitionId())) %>">
											<%= HtmlUtil.escape(cpDefinition.getTitle(languageId)) %>
										</a>

										<div class="align-middle d-inline-block px-3">
											<liferay-ui:icon
												icon="times"
												markupView="lexicon"
												message="remove"
												url="<%= cpCompareContentDisplayContext.getDeleteCompareProductURL(cpDefinition.getCPDefinitionId()) %>"
											/>
										</div>
									</div>
								</div>
							</div>
						</td>

					<%
					}
					%>

				</tr>

				<c:if test="<%= !cpDefinitionOptionRelTitles.isEmpty() %>">
					<tr class="table-active">
						<td colspan="<%= (cpDefinitions.size() + 1) %>"><h4><liferay-ui:message key="options" /></h4></td>
					</tr>

					<%
					for (String cpDefinitionOptionRelTitle : cpDefinitionOptionRelTitles) {
					%>

						<tr>
							<td><%= HtmlUtil.escape(cpDefinitionOptionRelTitle) %></td>

								<%
								for (CPDefinition cpDefinition : cpDefinitions) {
								%>

									<td><%= HtmlUtil.escape(cpCompareContentDisplayContext.getCPDefinitionOptionValueRels(cpDefinition, cpDefinitionOptionRelTitle)) %></td>

								<%
								}
								%>

						</tr>

					<%
					}
					%>

				</c:if>

				<tr class="table-active">
					<td colspan="<%= (cpDefinitions.size() + 1) %>"><h4><liferay-ui:message key="dimensions" /></h4></td>
				</tr>

				<%
				String dimensionCPMeasurementUnitName = cpCompareContentDisplayContext.getDimensionCPMeasurementUnitName();

				if (Validator.isNotNull(dimensionCPMeasurementUnitName)) {
					dimensionCPMeasurementUnitName = StringPool.OPEN_PARENTHESIS + dimensionCPMeasurementUnitName + StringPool.CLOSE_PARENTHESIS;
				}
				%>

				<tr>
					<td><%= LanguageUtil.get(request, "depth").concat(StringPool.SPACE).concat(HtmlUtil.escape(dimensionCPMeasurementUnitName)) %></td>

					<%
					for (CPDefinition cpDefinition : cpDefinitions) {
					%>

						<td><%= cpDefinition.getDepth() %></td>

					<%
					}
					%>

				</tr>
				<tr>
					<td><%= LanguageUtil.get(request, "height").concat(StringPool.SPACE).concat(HtmlUtil.escape(dimensionCPMeasurementUnitName)) %></td>

					<%
					for (CPDefinition cpDefinition : cpDefinitions) {
					%>

						<td><%= cpDefinition.getHeight() %></td>

					<%
					}
					%>

				</tr>

				<c:if test="<%= !cpSpecificationOptions.isEmpty() %>">
					<tr class="table-active">
						<td colspan="<%= (cpDefinitions.size() + 1) %>"><h4><liferay-ui:message key="specifications" /></h4></td>
					</tr>

					<%
					for (CPSpecificationOption cpSpecificationOption : cpSpecificationOptions) {
					%>

						<tr>
							<td><%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %></td>

							<%
							for (CPDefinition cpDefinition : cpDefinitions) {
							%>

								<td><%= HtmlUtil.escape(cpCompareContentDisplayContext.getCPDefinitionSpecificationOptionValue(cpDefinition.getCPDefinitionId(), cpSpecificationOption.getCPSpecificationOptionId())) %></td>

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
						<tr class="table-active">
							<td colspan="<%= (cpDefinitions.size() + 1) %>"><h4><%= HtmlUtil.escape(cpOptionCategory.getTitle(languageId)) %></h4></td>
						</tr>

						<%
						for (CPSpecificationOption cpSpecificationOption : categorizedCPSpecificationOptions) {
							if (cpSpecificationOption.getCPOptionCategoryId() != cpOptionCategory.getCPOptionCategoryId()) {
								continue;
							}
						%>

							<tr>
								<td><%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %></td>

								<%
								for (CPDefinition cpDefinition : cpDefinitions) {
								%>

									<td><%= HtmlUtil.escape(cpCompareContentDisplayContext.getCPDefinitionSpecificationOptionValue(cpDefinition.getCPDefinitionId(), cpSpecificationOption.getCPSpecificationOptionId())) %></td>

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
</liferay-ddm:template-renderer>