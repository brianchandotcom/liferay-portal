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
RelatedInfoItemCollectionProviderItemSelectorDisplayContext relatedInfoItemCollectionProviderItemSelectorDisplayContext = (RelatedInfoItemCollectionProviderItemSelectorDisplayContext)request.getAttribute(InfoCollectionProviderItemSelectorWebKeys.RELATED_INFO_ITEM_COLLECTION_PROVIDER_ITEM_SELECTOR_DISPLAY_CONTEXT);

SearchContainer<RelatedInfoItemCollectionProvider<?, ?>> searchContainer = relatedInfoItemCollectionProviderItemSelectorDisplayContext.getSearchContainer();
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new RelatedInfoItemCollectionProviderItemSelectorManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, relatedInfoItemCollectionProviderItemSelectorDisplayContext, searchContainer) %>"
/>

<clay:container-fluid
	cssClass="item-selector lfr-item-viewer"
	id='<%= liferayPortletResponse.getNamespace() + "entriesContainer" %>'
>
	<liferay-ui:search-container
		id="entries"
		searchContainer="<%= searchContainer %>"
		var="entriesSearch"
	>
		<liferay-ui:search-container-row
			className="com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider"
			modelVar="relatedInfoItemCollectionProvider"
		>

			<%
			row.setCssClass("selector-button");
			row.setData(
				HashMapBuilder.<String, Object>put(
					"return-type", relatedInfoItemCollectionProviderItemSelectorDisplayContext.getReturnType()
				).put(
					"value", relatedInfoItemCollectionProviderItemSelectorDisplayContext.getPayload(relatedInfoItemCollectionProvider)
				).build());
			%>

			<c:choose>
				<c:when test="<%= relatedInfoItemCollectionProviderItemSelectorDisplayContext.isIconDisplayStyle() %>">

					<%
					row.setCssClass("card-page-item card-page-item-asset entry " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							verticalCard="<%= new RelatedInfoItemCollectionProviderVerticalCard(renderRequest, relatedInfoItemCollectionProvider, searchContainer.getRowChecker()) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test="<%= relatedInfoItemCollectionProviderItemSelectorDisplayContext.isDescriptiveDisplayStyle() %>">

					<%
					row.setCssClass("item-selector-list-row " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
						cssClass="entry"
					>
						<p class="font-weight-bold h5">
							<%= relatedInfoItemCollectionProvider.getLabel(locale) %>
						</p>

						<p class="h6 text-default">
							<%= ResourceActionsUtil.getModelResource(locale, relatedInfoItemCollectionProvider.getCollectionItemClassName()) %>
						</p>

						<p class="h6 text-default">
							<c:choose>
								<c:when test="<%= relatedInfoItemCollectionProviderItemSelectorDisplayContext.supportsFilters(relatedInfoItemCollectionProvider) %>">
									<liferay-ui:message key="supports-filters" />
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="does-not-support-filters" />
								</c:otherwise>
							</c:choose>
						</p>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200"
						name="title"
					>
						<a class="entry" title="<%= relatedInfoItemCollectionProvider.getLabel(locale) %>">
							<%= relatedInfoItemCollectionProvider.getLabel(locale) %>
						</a>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200"
						name="type"
					>
						<a class="entry" title="<%= relatedInfoItemCollectionProvider.getLabel(locale) %>">
							<%= ResourceActionsUtil.getModelResource(locale, relatedInfoItemCollectionProvider.getCollectionItemClassName()) %>
						</a>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-smallest table-column-text-center"
						name="supports-filters"
					>
						<c:choose>
							<c:when test="<%= relatedInfoItemCollectionProviderItemSelectorDisplayContext.supportsFilters(relatedInfoItemCollectionProvider) %>">
								<liferay-ui:message key="yes" />
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="no" />
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= relatedInfoItemCollectionProviderItemSelectorDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
			searchContainer="<%= searchContainer %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>