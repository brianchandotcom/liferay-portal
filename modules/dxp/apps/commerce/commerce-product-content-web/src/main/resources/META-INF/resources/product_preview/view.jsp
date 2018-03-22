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
String languageId = LocaleUtil.toLanguageId(locale);

CPPreviewContentDisplayContext cpPreviewContentDisplayContext = (CPPreviewContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpPreviewContentDisplayContext.getCPDefinition();

String cpDefinitionTitle = StringPool.BLANK;
String defaultImageThumbnailSrc = StringPool.BLANK;

if (cpDefinition != null) {
	cpDefinitionTitle = cpDefinition.getTitle(languageId);
	defaultImageThumbnailSrc = cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);
}

List<CPDefinitionOptionRel> cpDefinitionOptionRels = cpPreviewContentDisplayContext.getCPDefinitionOptionRels();

SearchContainer<CPInstance> cpInstanceSearchContainer = cpPreviewContentDisplayContext.getSearchContainer();

PortletURL portletURL = cpPreviewContentDisplayContext.getPortletURL();
%>

<div class="container-fluid-1280">
	<div class="product-preview-header">
		<img class="d-inline" src="<%= defaultImageThumbnailSrc %>" />

		<h2 class="d-inline"><%= HtmlUtil.escape(cpDefinitionTitle) %></h2>
	</div>

	<div id="<portlet:namespace />productInstancesContainer">
		<div class="product-skus-container" id="<portlet:namespace />entriesContainer">
			<liferay-ui:search-container
				id="cpInstances"
				iteratorURL="<%= portletURL %>"
				searchContainer="<%= cpInstanceSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.product.model.CPInstance"
					cssClass="entry-display-style"
					keyProperty="CPInstanceId"
					modelVar="cpInstance"
				>

					<%
					String thumbnailSrc = cpPreviewContentDisplayContext.getCPInstanceThumbnailSrc(cpInstance, themeDisplay);
					%>

					<liferay-ui:search-container-column-image
						name="product"
						src="<%= thumbnailSrc %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						property="sku"
					/>

					<%
					Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>> cpDefinitionOptionRelListMap = cpPreviewContentDisplayContext.parseCPInstanceDDMContent(cpInstance.getCPInstanceId());

					for (CPDefinitionOptionRel cpDefinitionOptionRel : cpDefinitionOptionRels) {
					%>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							name="<%= cpDefinitionOptionRel.getTitle(languageId) %>"
						>

							<%
							StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

							if (cpDefinitionOptionRelListMap.containsKey(cpDefinitionOptionRel)) {
								List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels = cpDefinitionOptionRelListMap.get(cpDefinitionOptionRel);

								for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : cpDefinitionOptionValueRels) {
									stringJoiner.add(cpDefinitionOptionValueRel.getTitle(languageId));
								}
							}
							%>

							<%= HtmlUtil.escape(stringJoiner.toString()) %>
						</liferay-ui:search-container-column-text>

					<%
					}
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="price"
						value="<%= HtmlUtil.escape(cpPreviewContentDisplayContext.getFormattedPrice(cpInstance.getPrice())) %>"
					/>

					<%
					String quantityInputId = cpInstance.getCPInstanceId() + "_quantity";
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="quantity"
					>
						<liferay-commerce:quantity-input CPDefinitionId="<%= cpInstance.getCPDefinitionId() %>" name="<%= quantityInputId %>" useSelect="<%= false %>" />
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
					>
						<liferay-commerce-cart:add-to-cart
							CPDefinitionId="<%= cpInstance.getCPDefinitionId() %>"
							CPInstanceId="<%= cpInstance.getCPInstanceId() %>"
							taglibQuantityInputId='<%= renderResponse.getNamespace() + quantityInputId %>'
						/>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
			</liferay-ui:search-container>
		</div>
	</div>
</div>