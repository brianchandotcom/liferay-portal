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

SearchContainer<CPCatalogEntry> searchContainer = cpPublisherDisplayContext.getSearchContainer();
%>

<c:choose>
	<c:when test="<%= cpPublisherDisplayContext.isRenderSelectionADT() %>">
		<liferay-ddm:template-renderer
			className="<%= CPPublisherPortlet.class.getName() %>"
			contextObjects='<%=
				HashMapBuilder.<String, Object>put(
					"cpPublisherDisplayContext", cpPublisherDisplayContext
				).build()
			%>'
			displayStyle="<%= cpPublisherDisplayContext.getDisplayStyle() %>"
			displayStyleGroupId="<%= cpPublisherDisplayContext.getDisplayStyleGroupId() %>"
			entries="<%= searchContainer.getResults() %>"
		/>

		<c:if test="<%= cpPublisherDisplayContext.isPaginate() %>">
			<aui:form>
				<liferay-ui:search-paginator
					markupView="lexicon"
					searchContainer="<%= searchContainer %>"
				/>
			</aui:form>
		</c:if>
	</c:when>
	<c:when test="<%= cpPublisherDisplayContext.isRenderSelectionCustomRenderer() %>">
		<liferay-commerce-product:product-list-renderer
			CPDataSourceResult = "<%= new CPDataSourceResult(searchContainer.getResults(), searchContainer.getTotal()) %>"
			entryKeys = "<%= cpPublisherDisplayContext.getCPContentListEntryRendererKeys() %>"
			key = "<%= cpPublisherDisplayContext.getCPContentListRendererKey() %>"
		/>

		<c:if test="<%= cpPublisherDisplayContext.isPaginate() %>">
			<aui:form>
				<liferay-ui:search-paginator
					markupView="lexicon"
					searchContainer="<%= searchContainer %>"
				/>
			</aui:form>
		</c:if>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>