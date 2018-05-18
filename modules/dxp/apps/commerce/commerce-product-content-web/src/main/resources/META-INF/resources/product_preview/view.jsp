<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
String languageId = LocaleUtil.toLanguageId(locale);

CPPreviewContentDisplayContext cpPreviewContentDisplayContext = (CPPreviewContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpPreviewContentDisplayContext.getCPDefinition();

String cpDefinitionName = StringPool.BLANK;
String defaultImageThumbnailSrc = StringPool.BLANK;

if (cpDefinition != null) {
	cpDefinitionName = cpDefinition.getName(languageId);
	defaultImageThumbnailSrc = cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);
}

List<CPDefinitionOptionRel> cpDefinitionOptionRels = cpPreviewContentDisplayContext.getCPDefinitionOptionRels();

SearchContainer<CPInstance> cpInstanceSearchContainer = cpPreviewContentDisplayContext.getSearchContainer();

PortletURL portletURL = cpPreviewContentDisplayContext.getPortletURL();
%>

<div class="container-fluid-1280">
	<div class="product-preview-header">
		<img class="d-inline" src="<%= defaultImageThumbnailSrc %>" />

		<h2 class="d-inline"><%= HtmlUtil.escape(cpDefinitionName) %></h2>
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
							name="<%= cpDefinitionOptionRel.getName(languageId) %>"
						>

							<%
							StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

							if (cpDefinitionOptionRelListMap.containsKey(cpDefinitionOptionRel)) {
								List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels = cpDefinitionOptionRelListMap.get(cpDefinitionOptionRel);

								for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : cpDefinitionOptionValueRels) {
									stringJoiner.add(cpDefinitionOptionValueRel.getName(languageId));
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

				<liferay-ui:search-iterator
					displayStyle="list"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</div>
	</div>
</div>