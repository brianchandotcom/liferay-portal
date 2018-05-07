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

SearchContainer searchContainer = cpSearchResultsDisplayContext.getSearchContainer();

List<Document> results = searchContainer.getResults();
%>

<liferay-ddm:template-renderer
	className="<%= CPSearchResultsPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= cpSearchResultsDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= cpSearchResultsDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= results %>"
>
	<c:choose>
		<c:when test="<%= results.size() > 0 %>">
			<div class="row">

				<%
				for (Object object : results) {
					Document document = (Document)object;
				%>

				<div class="col-md-4">
					<div class="card">
						<a class="aspect-ratio" href="<%= cpSearchResultsDisplayContext.getProductFriendlyURL(document) %>">

							<%
							String img = cpSearchResultsDisplayContext.getProductDefaultImage(document, themeDisplay);
							%>

							<c:if test="<%= Validator.isNotNull(img) %>">
								<img class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="<%= img %>">
							</c:if>
						</a>

						<div class="card-row card-row-padded card-row-valign-top">
							<div class="card-col-content">
								<a class="truncate-text" href="<%= cpSearchResultsDisplayContext.getProductFriendlyURL(document) %>">
									<%= cpSearchResultsDisplayContext.getName(document) %>
								</a>
							</div>
						</div>
					</div>
				</div>

				<%
				}
				%>

			</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-info">
				<liferay-ui:message key="no-products-were-found" />
			</div>
		</c:otherwise>
	</c:choose>
</liferay-ddm:template-renderer>

<aui:form useNamespace="<%= false %>">
	<liferay-ui:search-paginator
		markupView="lexicon"
		searchContainer="<%= searchContainer %>"
		type="more"
	/>
</aui:form>