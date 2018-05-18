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
AssetCategory assetCategory = (AssetCategory)request.getAttribute(WebKeys.ASSET_CATEGORY);

CPAssetCategoryWebPortletUtil cpAssetCategoryWebPortletUtil = (CPAssetCategoryWebPortletUtil)request.getAttribute("cpAssetCategoryWebPortletUtil");

CPDefinitionService cpDefinitionService = (CPDefinitionService)request.getAttribute("cpDefinitionService");

PortletURL portletURL = currentURLObj;

portletURL.setParameter("historyKey", renderResponse.getNamespace() + "products");

SearchContainer<CPDefinition> cpDefinitionSearchContainer = new SearchContainer<>(liferayPortletRequest, portletURL, null, null);

int cpDefinitionsCount = cpDefinitionService.getCPDefinitionsCountByCategoryId(assetCategory.getCategoryId());

List<CPDefinition> cpDefinitions = cpDefinitionService.getCPDefinitionsByCategoryId(assetCategory.getCategoryId(), cpDefinitionSearchContainer.getStart(), cpDefinitionSearchContainer.getEnd());

cpDefinitionSearchContainer.setTotal(cpDefinitionsCount);
cpDefinitionSearchContainer.setResults(cpDefinitions);
%>

<div id="<portlet:namespace />productsContainer">
	<div class="products-container" id="<portlet:namespace />entriesContainer">
		<liferay-ui:search-container
			emptyResultsMessage="no-products-were-found"
			id="cpDefinitions"
			searchContainer="<%= cpDefinitionSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.product.model.CPDefinition"
				cssClass="entry-display-style"
				keyProperty="CPDefinitionId"
				modelVar="cpDefinition"
			>

				<%
				String thumbnailSrc = cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);

				CPType cpType = cpAssetCategoryWebPortletUtil.getCPType(cpDefinition.getProductTypeName());
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="id"
					value="<%= String.valueOf(cpDefinition.getCPDefinitionId()) %>"
				/>

				<c:choose>
					<c:when test="<%= Validator.isNotNull(thumbnailSrc) %>">
						<liferay-ui:search-container-column-image
							cssClass="table-cell-content"
							name="image"
							src="<%= thumbnailSrc %>"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-icon
							icon="documents-and-media"
							name="image"
						/>
					</c:otherwise>
				</c:choose>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="name"
					value="<%= HtmlUtil.escape(cpDefinition.getName(languageId)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="type"
					value="<%= cpType.getLabel(locale) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="author"
					property="userName"
				/>

				<liferay-ui:search-container-column-status
					cssClass="table-cell-content"
					name="status"
					status="<%= cpDefinition.getStatus() %>"
				/>

				<liferay-ui:search-container-column-date
					cssClass="table-cell-content"
					name="modified-date"
					property="modifiedDate"
				/>

				<liferay-ui:search-container-column-date
					cssClass="table-cell-content"
					name="display-date"
					property="displayDate"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/product_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
				searchContainer="<%= cpDefinitionSearchContainer %>"
			/>
		</liferay-ui:search-container>
	</div>
</div>