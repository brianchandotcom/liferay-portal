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
CPSearchResultsDisplayContext cpSearchResultsDisplayContext = (CPSearchResultsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("cpSearchResultsDisplayContext", cpSearchResultsDisplayContext);

SearchContainer<CPCatalogEntry> cpCatalogEntrySearchContainer = cpSearchResultsDisplayContext.getSearchContainer();

List<CPCatalogEntry> results = cpCatalogEntrySearchContainer.getResults();

String orderByCol = cpSearchResultsDisplayContext.getOrderByCol();
%>

<c:choose>
	<c:when test="<%= !cpSearchResultsDisplayContext.hasCommerceChannel() %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:when test="<%= cpSearchResultsDisplayContext.isSelectionStyleADT() %>">
		<liferay-ddm:template-renderer
			className="<%= CPSearchResultsPortlet.class.getName() %>"
			contextObjects="<%= contextObjects %>"
			displayStyle="<%= cpSearchResultsDisplayContext.getDisplayStyle() %>"
			displayStyleGroupId="<%= cpSearchResultsDisplayContext.getDisplayStyleGroupId() %>"
			entries="<%= results %>"
		/>

		<c:if test="<%= cpSearchResultsDisplayContext.isPaginate() %>">
			<aui:form useNamespace="<%= false %>">
				<liferay-ui:search-paginator
					markupView="lexicon"
					searchContainer="<%= cpCatalogEntrySearchContainer %>"
				/>
			</aui:form>
		</c:if>
	</c:when>
	<c:when test="<%= cpSearchResultsDisplayContext.isSelectionStyleCustomRenderer() %>">
		<div class="m-0 mb-3 row">
			<div class="d-flex ml-auto pull-right">
				<p class="mb-auto mr-3 mt-auto">
					<liferay-ui:message arguments="<%= cpCatalogEntrySearchContainer.getTotal() %>" key="x-products-available" />
				</p>

				<button aria-expanded="false" aria-haspopup="true" class="btn btn-default commerce-order-by dropdown-toggle" data-toggle="dropdown" type="button">
					<c:set var="orderByColArgument">
						<span class="ml-1">
							<liferay-ui:message key="<%= orderByCol %>" />
						</span>
					</c:set>

					<liferay-ui:message arguments="${orderByColArgument}" key="sort-by-colon-x" />

					<aui:icon image="caret-double-l" markupView="lexicon" />
				</button>

				<div class="dropdown-menu dropdown-menu-right">

					<%
					String[] sortOptions = {"relevance", "price-low-to-high", "price-high-to-low", "new-items", "name-ascending", "name-descending"};

					for (String sortOption : sortOptions) {
					%>

						<clay:link
							elementClasses="dropdown-item transition-link"
							href="#"
							id="<%= renderResponse.getNamespace() + sortOption %>"
							label="<%= LanguageUtil.get(request, sortOption) %>"
							style="secondary"
						/>

						<aui:script>
							document
								.querySelector('#<%= renderResponse.getNamespace() + sortOption %>')
								.addEventListener('click', function(e) {
									e.preventDefault();
									<%= renderResponse.getNamespace() + "changeOrderBy('" + sortOption + "');" %>;
								});
						</aui:script>

					<%
					}
					%>

				</div>
			</div>
		</div>

		<liferay-commerce-product:product-list-renderer
			CPDataSourceResult = "<%= cpSearchResultsDisplayContext.getCPDataSourceResult() %>"
			entryKeys = "<%= cpSearchResultsDisplayContext.getCPContentListEntryRendererKeys() %>"
			key = "<%= cpSearchResultsDisplayContext.getCPContentListRendererKey() %>"
		/>

		<c:if test="<%= cpSearchResultsDisplayContext.isPaginate() %>">
			<aui:form useNamespace="<%= false %>">
				<liferay-ui:search-paginator
					markupView="lexicon"
					searchContainer="<%= cpCatalogEntrySearchContainer %>"
				/>
			</aui:form>
		</c:if>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />changeOrderBy',
		function(orderBy) {
			var portletURL = new Liferay.PortletURL.createURL(
				'<%= themeDisplay.getURLCurrent() %>'
			);

			portletURL.setParameter('orderByCol', orderBy);
			portletURL.setPortletId('<%= portletDisplay.getId() %>');

			window.location.replace(portletURL.toString());
		},
		['liferay-portlet-url']
	);
</aui:script>