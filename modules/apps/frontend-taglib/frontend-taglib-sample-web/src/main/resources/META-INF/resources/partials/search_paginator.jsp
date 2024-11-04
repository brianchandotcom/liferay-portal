<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SearchPaginatorDisplayContext searchPaginatorDisplayContext = (SearchPaginatorDisplayContext)request.getAttribute(SamplePortletKeys.SEARCH_PAGINATOR_DISPLAY_CONTEXT);
%>

<clay:container-fluid>
	<liferay-ui:search-paginator
		markupView="lexicon"
		searchContainer="<%= searchPaginatorDisplayContext.getSearchContainer() %>"
	/>
</clay:container-fluid>