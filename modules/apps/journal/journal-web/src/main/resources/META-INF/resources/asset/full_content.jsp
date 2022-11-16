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

String pageRedirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(pageRedirect)) {
	pageRedirect = currentURL;
}

int cur = ParamUtil.getInteger(request, "cur");
%>

<liferay-journal:journal-article-display
	articleDisplay="<%= articleDisplay %>"
	paginationURL='<%=
		PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(request, JournalPortletKeys.JOURNAL, PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/view_content.jsp"
		).setRedirect(
			pageRedirect
		).setParameter(
			"cur", cur
		).setParameter(
			"groupId", articleDisplay.getGroupId()
		).setParameter(
			"type", assetRendererFactory.getType()
		).setParameter(
			"urlTitle", articleDisplay.getUrlTitle()
		).buildPortletURL()
	%>'
/>

<liferay-util:dynamic-include key="com.liferay.journal.web#/asset/full_content.jsp#post" />