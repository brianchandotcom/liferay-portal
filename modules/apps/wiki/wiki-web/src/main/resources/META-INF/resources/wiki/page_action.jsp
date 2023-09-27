<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WikiPage wikiPage = null;

if (row != null) {
	wikiPage = (WikiPage)row.getObject();
}
else {
	wikiPage = (WikiPage)request.getAttribute("page_info_panel.jsp-wikiPage");
}

WikiListPagesDisplayContext wikiListPagesDisplayContext = new WikiListPagesDisplayContext(request, (TrashHelper)request.getAttribute(TrashWebKeys.TRASH_HELPER), wikiPage.getNode());
%>

<clay:dropdown-actions
	aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
	dropdownItems="<%= wikiListPagesDisplayContext.getActionDropdownItems(wikiPage) %>"
	propsTransformer="wiki/js/WikiPageDropdownPropsTransformer"
/>