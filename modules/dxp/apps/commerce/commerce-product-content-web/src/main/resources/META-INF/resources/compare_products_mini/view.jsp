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
CPCompareContentMiniDisplayContext cpCompareContentMiniDisplayContext = (CPCompareContentMiniDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDataSourceResult cpDataSourceResult = cpCompareContentMiniDisplayContext.getCPDataSourceResult();
%>

<c:choose>
	<c:when test="<%= cpCompareContentMiniDisplayContext.isSelectionStyleADT() %>">

		<%
		Map<String, Object> contextObjects = new HashMap<>();

		contextObjects.put("cpCompareContentMiniDisplayContext", cpCompareContentMiniDisplayContext);

		List<CPCatalogEntry> cpCatalogEntries = cpDataSourceResult.getCPCatalogEntries();
		%>

		<liferay-ddm:template-renderer
			className="<%= CPCompareContentMiniPortlet.class.getName() %>"
			contextObjects="<%= contextObjects %>"
			displayStyle="<%= cpCompareContentMiniDisplayContext.getDisplayStyle() %>"
			displayStyleGroupId="<%= cpCompareContentMiniDisplayContext.getDisplayStyleGroupId() %>"
			entries="<%= cpCatalogEntries %>"
		/>
	</c:when>
	<c:when test="<%= cpCompareContentMiniDisplayContext.isSelectionStyleCustomRenderer() %>">
		<liferay-commerce-product:product-list-renderer
			CPDataSourceResult = "<%= cpCompareContentMiniDisplayContext.getCPDataSourceResult() %>"
			entryKeys = "<%= cpCompareContentMiniDisplayContext.getCPContentListEntryRendererKeys() %>"
			key = "<%= cpCompareContentMiniDisplayContext.getCPContentListRendererKey() %>"
		/>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>