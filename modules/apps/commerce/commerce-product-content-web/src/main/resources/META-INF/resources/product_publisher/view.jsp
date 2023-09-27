<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			CPDataSourceResult="<%= new CPDataSourceResult(searchContainer.getResults(), searchContainer.getTotal()) %>"
			entryKeys="<%= cpPublisherDisplayContext.getCPContentListEntryRendererKeys() %>"
			key="<%= cpPublisherDisplayContext.getCPContentListRendererKey() %>"
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