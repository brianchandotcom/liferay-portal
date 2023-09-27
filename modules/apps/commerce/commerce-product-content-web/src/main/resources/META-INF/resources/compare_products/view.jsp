<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPCompareContentDisplayContext cpCompareContentDisplayContext = (CPCompareContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDataSourceResult cpDataSourceResult = cpCompareContentDisplayContext.getCPDataSourceResult();
%>

<c:choose>
	<c:when test="<%= !cpCompareContentDisplayContext.hasCommerceChannel() %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:when test="<%= cpCompareContentDisplayContext.isSelectionStyleADT() %>">
		<liferay-ddm:template-renderer
			className="<%= CPCompareContentPortlet.class.getName() %>"
			contextObjects='<%=
				HashMapBuilder.<String, Object>put(
					"cpCompareContentDisplayContext", cpCompareContentDisplayContext
				).build()
			%>'
			displayStyle="<%= cpCompareContentDisplayContext.getDisplayStyle() %>"
			displayStyleGroupId="<%= cpCompareContentDisplayContext.getDisplayStyleGroupId() %>"
			entries="<%= cpDataSourceResult.getCPCatalogEntries() %>"
		/>
	</c:when>
	<c:when test="<%= cpCompareContentDisplayContext.isSelectionStyleCustomRenderer() %>">
		<liferay-commerce-product:product-list-renderer
			CPDataSourceResult="<%= cpCompareContentDisplayContext.getCPDataSourceResult() %>"
			entryKeys="<%= cpCompareContentDisplayContext.getCPContentListEntryRendererKeys() %>"
			key="<%= cpCompareContentDisplayContext.getCPContentListRendererKey() %>"
		/>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>