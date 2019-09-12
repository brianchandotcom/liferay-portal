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

<liferay-ui:icon
	id="viewResourcesButton"
	message="view-source"
	url="javascript:;"
/>

<%
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);
%>

<script>
	var viewResourcesButton = document.getElementById(
		'<portlet:namespace />viewResourcesButton'
	);

	if (viewResourcesButton) {
		viewResourcesButton.addEventListener('click', function(event) {
			Liferay.Util.openWindow({
				dialog: {
					destroyOnHide: true,
					modal: true
				},
				dialogIframe: {
					bodyCssClass: 'dialog-with-footer'
				},
				title:
					'<%= HtmlUtil.escapeJS(HtmlUtil.escape(article.getTitle(themeDisplay.getLocale()))) %>',
				uri:
					'<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/configuration/icon/view_source.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" /><portlet:param name="articleId" value="<%= article.getArticleId() %>" /><portlet:param name="status" value="<%= String.valueOf(article.getStatus()) %>" /></portlet:renderURL>'
			});
		});
	}
</script>