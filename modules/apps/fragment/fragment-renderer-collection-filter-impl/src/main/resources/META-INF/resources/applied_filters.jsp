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
%>

<div class="d-flex py-1" id="<%= collectionAppliedFiltersFragmentRendererDisplayContext.getFragmentEntryLinkNamespace() %>">
	<div class="flex-grow-1">
		<c:choose>
			<c:when test="<%= collectionAppliedFiltersFragmentRendererDisplayContext.getAppliedFilters().isEmpty() %>">
				<span class="text-secondary">
					<liferay-ui:message key="no-active-filters" />
				</span>
			</c:when>
			<c:otherwise>

				<%
				for (Map<String, String> appliedFilter : collectionAppliedFiltersFragmentRendererDisplayContext.getAppliedFilters()) {
				%>

					<span class="label label-lg label-secondary">
						<span class="label-item label-item-expand">
							<%= appliedFilter.get("filterValue") %>
						</span>
						<span class="label-item label-item-after">
							<button aria-label="<liferay-ui:message key="remove-filter" />" class="close remove-collection-applied-filter-button" data-filter-fragment-entry-link-id="<%= appliedFilter.get("filterFragmentEntryLinkId") %>" data-filter-type="<%= appliedFilter.get("filterType") %>" data-filter-value="<%= appliedFilter.get("filterValue") %>" type="button">
								<span class="c-inner">
									<clay:icon
										symbol="times"
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

	<c:if test="<%= collectionAppliedFiltersFragmentRendererDisplayContext.showClearFiltersButton() %>">
		<button class="btn btn-link btn-sm flex-shrink-0 p-0 pl-2 remove-all-collection-filters-button text-secondary" type="button">
			<liferay-ui:message key="clear-filters" />
		</button>
	</c:if>
</div>

<liferay-frontend:component
	context="<%= collectionAppliedFiltersFragmentRendererDisplayContext.getCollectionAppliedFiltersProps() %>"
	module="js/CollectionAppliedFilters"
/>