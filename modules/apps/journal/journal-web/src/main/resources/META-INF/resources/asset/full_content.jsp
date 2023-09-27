<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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