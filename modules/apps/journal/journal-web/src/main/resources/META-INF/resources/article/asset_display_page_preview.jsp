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
JournalEditArticleDisplayContext journalEditArticleDisplayContext = new JournalEditArticleDisplayContext(request, liferayPortletResponse, journalDisplayContext.getArticle());
%>

<p class="text-secondary">
	<liferay-ui:message key="preview-this-web-content-through-the-display-page-templates-of-a-specific-site" />
</p>

<div>
	<span aria-hidden="true" class="loading-animation loading-animation-sm mt-4"></span>

	<react:component
		data="<%= journalEditArticleDisplayContext.getAssetDisplayPagePreviewContext() %>"
		module="js/article/AssetDisplayPagePreview"
	/>
</div>