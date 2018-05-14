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

<%@ include file="/asset/init.jsp" %>

<liferay-util:dynamic-include key="com.liferay.journal.web#/asset/full_content.jsp#pre" />

<%
AssetRendererFactory<?> assetRendererFactory = (AssetRendererFactory<?>)request.getAttribute(WebKeys.ASSET_RENDERER_FACTORY);

JournalArticleDisplay articleDisplay = (JournalArticleDisplay)request.getAttribute(WebKeys.JOURNAL_ARTICLE_DISPLAY);
%>

<liferay-journal:journal-article-display
	articleDisplay="<%= articleDisplay %>"
/>

<c:if test="<%= articleDisplay.isPaginate() %>">

	<%
	String pageRedirect = ParamUtil.getString(request, "redirect");

	if (Validator.isNull(pageRedirect)) {
		pageRedirect = currentURL;
	}

	int cur = ParamUtil.getInteger(request, "cur");

	PortletURL articlePageURL = renderResponse.createRenderURL();

	articlePageURL.setParameter("mvcPath", "/view_content.jsp");
	articlePageURL.setParameter("cur", String.valueOf(cur));
	articlePageURL.setParameter("redirect", pageRedirect);
	articlePageURL.setParameter("type", assetRendererFactory.getType());
	articlePageURL.setParameter("groupId", String.valueOf(articleDisplay.getGroupId()));
	articlePageURL.setParameter("urlTitle", articleDisplay.getUrlTitle());
	%>

	<br />

	<liferay-ui:page-iterator
		cur="<%= articleDisplay.getCurrentPage() %>"
		curParam="page"
		delta="<%= 1 %>"
		id="articleDisplayPages"
		maxPages="<%= 25 %>"
		portletURL="<%= articlePageURL %>"
		total="<%= articleDisplay.getNumberOfPages() %>"
		type="article"
	/>

	<br />
</c:if>

<liferay-util:dynamic-include key="com.liferay.journal.web#/asset/full_content.jsp#post" />