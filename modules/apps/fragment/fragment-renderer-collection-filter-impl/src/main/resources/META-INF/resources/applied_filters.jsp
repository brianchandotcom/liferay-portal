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
CollectionAppliedFiltersFragmentRendererDisplayContext collectionAppliedFiltersFragmentRendererDisplayContext = (CollectionAppliedFiltersFragmentRendererDisplayContext)request.getAttribute(CollectionAppliedFiltersFragmentRendererDisplayContext.class.getName());

List<Map<String, String>> appliedFilters = collectionAppliedFiltersFragmentRendererDisplayContext.getAppliedFilters();
%>

<div class="align-items-sm-start align-items-stretch d-flex flex-column flex-sm-row py-1" id="<%= collectionAppliedFiltersFragmentRendererDisplayContext.getFragmentEntryLinkNamespace() %>">
	<div class="flex-grow-1 overflow-hidden" id="<%= collectionAppliedFiltersFragmentRendererDisplayContext.getFragmentEntryLinkNamespace() %>_filterList" style="max-height: 4em;">
		<c:choose>
			<c:when test="<%= appliedFilters.isEmpty() && collectionAppliedFiltersFragmentRendererDisplayContext.isEditMode() %>">
				<span class="text-secondary">
					<liferay-ui:message key="no-active-filters" />
				</span>
			</c:when>
			<c:otherwise>

				<%
				for (Map<String, String> appliedFilter : appliedFilters) {
				%>

					<span class="label label-lg label-secondary">
						<span class="label-item label-item-expand">
							<%= appliedFilter.get("filterLabel") %>
						</span>
						<span class="label-item label-item-after">
							<button aria-label="<liferay-ui:message key="remove-filter" />" class="close remove-filter-button" data-filter-fragment-entry-link-id="<%= appliedFilter.get("filterFragmentEntryLinkId") %>" data-filter-type="<%= appliedFilter.get("filterType") %>" data-filter-value="<%= appliedFilter.get("filterValue") %>" type="button">
								<span class="c-inner">
									<clay:icon
										symbol="times-small"
									/>
								</span>
							</button>
						</span>
					</span>

				<%
				}
				%>

			</c:otherwise>
		</c:choose>
	</div>

	<div class="d-flex flex-grow-1 flex-shrink-0 flex-sm-column-reverse flex-sm-grow-0 justify-content-between justify-content-sm-start ml-sm-2 mt-2 mt-sm-0">
		<button class="btn btn-link btn-sm d-none flex-shrink-0 mt-0 mt-sm-2 p-0 text-right text-secondary" data-show-less-label="<liferay-ui:message key="show-less" />" data-show-more-label="<liferay-ui:message key="show-more" />" id="<%= collectionAppliedFiltersFragmentRendererDisplayContext.getFragmentEntryLinkNamespace() %>_toggleExpand" style="line-height: 1.3125;" type="button">
			<span class="inline-item-expand">
				<liferay-ui:message key="show-more" />
			</span>
			<span class="inline-item inline-item-after ml-0">
				<clay:icon
					symbol="angle-down-small"
				/>

				<clay:icon
					class="d-none"
					symbol="angle-up-small"
				/>
			</span>
		</button>

		<c:if test="<%= (!appliedFilters.isEmpty() || collectionAppliedFiltersFragmentRendererDisplayContext.isEditMode()) && collectionAppliedFiltersFragmentRendererDisplayContext.showClearFiltersButton() %>">
			<button class="btn btn-link btn-sm flex-shrink-0 ml-2 mt-0 mt-sm-1 p-0 text-right text-secondary" id="<%= collectionAppliedFiltersFragmentRendererDisplayContext.getFragmentEntryLinkNamespace() %>_removeAllFilters" type="button">
				<liferay-ui:message key="clear-filters" />
			</button>
		</c:if>
	</div>
</div>

<liferay-frontend:component
	context="<%= collectionAppliedFiltersFragmentRendererDisplayContext.getCollectionAppliedFiltersProps() %>"
	module="js/CollectionAppliedFilters"
/>