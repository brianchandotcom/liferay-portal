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

<%@ include file="/admin/common/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

KBArticleURLHelper kbArticleURLHelper = new KBArticleURLHelper(renderRequest, renderResponse, templatePath);

PortletURL viewKBArticleURL = kbArticleURLHelper.createViewURL(kbArticle);
%>

<liferay-social-bookmarks:bookmarks
	className="<%= KBArticle.class.getName() %>"
	classPK="<%= kbArticle.getKbArticleId() %>"
	displayStyle="<%= socialBookmarksDisplayStyle %>"
	target="_blank"
	title="<%= kbArticle.getTitle() %>"
	types="<%= SocialBookmarksUtil.getSocialBookmarksTypes(socialBookmarksTypes) %>"
	urlImpl="<%= viewKBArticleURL %>"
/>