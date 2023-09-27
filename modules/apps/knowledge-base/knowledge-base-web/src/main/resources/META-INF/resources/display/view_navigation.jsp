<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/display/init.jsp" %>

<%
KBNavigationDisplayContext kbNavigationDisplayContext = (KBNavigationDisplayContext)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_NAVIGATION_DISPLAY_CONTEXT);

long parentResourcePrimKey = kbNavigationDisplayContext.getRootResourcePrimKey();

String pageTitle = kbNavigationDisplayContext.getPageTitle();

if (Validator.isNotNull(pageTitle)) {
	PortalUtil.setPageTitle(pageTitle, request);
}

KBArticleURLHelper kbArticleURLHelper = new KBArticleURLHelper(renderRequest, renderResponse);
%>

<div class="kbarticle-navigation">
	<c:if test="<%= resourceClassNameId == kbFolderClassNameId %>">
		<liferay-util:include page="/display/content_root_selector.jsp" servletContext="<%= application %>" />
	</c:if>

	<%
	request.setAttribute("view_navigation_kb_articles.jsp-kbArticleURLHelper", kbArticleURLHelper);
	request.setAttribute("view_navigation_kb_articles.jsp-level", 0);
	request.setAttribute("view_navigation_kb_articles.jsp-parentResourcePrimKey", parentResourcePrimKey);
	%>

	<liferay-util:include page="/display/view_navigation_kb_articles.jsp" servletContext="<%= application %>" />
</div>