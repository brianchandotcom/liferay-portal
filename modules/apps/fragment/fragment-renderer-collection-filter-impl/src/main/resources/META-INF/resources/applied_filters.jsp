<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
							<%= HtmlUtil.escape(appliedFilter.get("filterLabel")) %>
						</span>
						<span class="label-item label-item-after">
							<button aria-label="<liferay-ui:message key="remove-filter" />" class="close remove-filter-button" data-filter-fragment-entry-link-id="<%= HtmlUtil.escapeAttribute(appliedFilter.get("filterFragmentEntryLinkId")) %>" data-filter-type="<%= appliedFilter.get("filterType") %>" data-filter-value="<%= HtmlUtil.escapeAttribute(appliedFilter.get("filterValue")) %>" type="button">
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
		<clay:button
			cssClass="border-0 btn btn-link btn-sm d-none flex-shrink-0 mt-0 mt-sm-2 p-0 text-right text-secondary"
			data-show-less-label='<%= LanguageUtil.get(request, "show-less") %>'
			data-show-more-label='<%= LanguageUtil.get(request, "show-more") %>'
			displayType="secondary"
			id='<%= collectionAppliedFiltersFragmentRendererDisplayContext.getFragmentEntryLinkNamespace() + "_toggleExpand" %>'
			style="line-height: 1.3125;"
		>
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
		</clay:button>

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