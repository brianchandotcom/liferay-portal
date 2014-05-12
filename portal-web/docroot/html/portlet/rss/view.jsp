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

<%@ include file="/html/portlet/rss/init.jsp" %>

<%
String url = ParamUtil.getString(request, "url");
String title = StringPool.BLANK;
%>

<c:if test="<%= Validator.isNotNull(headerArticleId) %>">
	<p>
		<liferay-ui:journal-article articleId="<%= headerArticleId %>" groupId="<%= headerArticleGroupId %>" />
	</p>
</c:if>

<div id="<portlet:namespace />feedsContainer">

	<%
	for (int i = 0; i < urls.length; i++) {
		url = urls[i];

		if (i < titles.length) {
			title = titles[i];
		}
		else {
			title = StringPool.BLANK;
		}

		boolean last = false;

		if (i == (urls.length - 1)) {
			last = true;
		}
	%>

		<%@ include file="/html/portlet/rss/feed.jspf" %>

	<%
	}
	%>

</div>

<c:if test="<%= Validator.isNotNull(footerArticleId) %>">
	<p>
		<liferay-ui:journal-article articleId="<%= footerArticleId %>" groupId="<%= footerArticleGroupId %>" />
	</p>
</c:if>

<aui:script use="aui-base">
	var feedsContainer = A.one('#<portlet:namespace />feedsContainer');

	feedsContainer.delegate(
		'click',
		function(event) {
			var expander = event.currentTarget;
			var feedContent = expander.get('parentNode').one('.feed-entry-content');

			if (feedContent) {
				if (expander.hasClass('icon-collapse-alt')) {
					expander.addClass('icon-expand-alt');
					expander.removeClass('icon-collapse-alt');
				}
				else {
					expander.addClass('icon-collapse-alt');
					expander.removeClass('icon-expand-alt');
				}

				feedContent.toggle();
			}
		},
		'.entry-expander'
	);
</aui:script>