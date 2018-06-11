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
CPPublisherDisplayContext cpPublisherDisplayContext = (CPPublisherDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("cpPublisherDisplayContext", cpPublisherDisplayContext);

SearchContainer searchContainer = cpPublisherDisplayContext.getSearchContainer();

List<CPCatalogEntry> results = searchContainer.getResults();
%>

<div class="row">

	<%
	for (Object object : results) {
		CPCatalogEntry cpCatalogEntry = (CPCatalogEntry)object;
	%>

	<div class="col-md-4">
		<div class="card">
			<a class="aspect-ratio" href="<%= cpPublisherDisplayContext.getProductFriendlyURL(cpCatalogEntry) %>">

				<%
				String img = cpCatalogEntry.getDefaultImageFileUrl();
				%>

				<c:if test="<%= Validator.isNotNull(img) %>">
					<img class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="<%= img %>">
				</c:if>
			</a>

			<div class="card-row card-row-padded card-row-valign-top">
				<div class="card-col-content">
					<a class="truncate-text" href="<%= cpPublisherDisplayContext.getProductFriendlyURL(cpCatalogEntry) %>">
						<%= cpCatalogEntry.getName() %>
					</a>
				</div>
			</div>
		</div>
	</div>

	<%
	}
	%>

<aui:form useNamespace="<%= false %>">
	<liferay-ui:search-paginator
		markupView="lexicon"
		searchContainer="<%= searchContainer %>"
		type="more"
	/>
</aui:form>