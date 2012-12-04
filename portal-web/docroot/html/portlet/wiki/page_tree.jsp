<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
WikiPage parentPage = (WikiPage)request.getAttribute(WebKeys.WIKI_TREE_WALKER_PARENT);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_TREE_WALKER_PAGE);
int depth = (Integer)request.getAttribute(WebKeys.WIKI_TREE_WALKER_DEPTH);

String preface = StringPool.BLANK;

for (int i = 0; i < depth; i++) {
	preface += "- ";
}

List<WikiPage> childPages = ListUtil.copy(parentPage.getViewableChildPages());

childPages.remove(wikiPage);

childPages = ListUtil.sort(childPages);
%>

<aui:option label="<%= preface + parentPage.getTitle() %>" selected="<%= parentPage.getTitle().equals(wikiPage.getParentTitle()) %>" value="<%= parentPage.getTitle() %>" />

<%
for (WikiPage childPage : childPages) {
	if (Validator.isNull(childPage.getRedirectTitle())) {
		request.setAttribute(WebKeys.WIKI_TREE_WALKER_PARENT, childPage);
		request.setAttribute(WebKeys.WIKI_TREE_WALKER_PAGE, wikiPage);
		request.setAttribute(WebKeys.WIKI_TREE_WALKER_DEPTH, depth + 1);
%>

		<liferay-util:include page="/html/portlet/wiki/page_tree.jsp" />

<%
	}
}
%>