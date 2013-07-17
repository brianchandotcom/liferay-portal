<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<c:if test="<%= Validator.isNotNull(footerArticleId) %>">
	<p>
		<liferay-ui:journal-article articleId="<%= footerArticleId %>" groupId="<%= footerArticleGroupId %>" />
	</p>
</c:if>

<aui:script use="aui-base">
	var minusAlt = '<%= UnicodeLanguageUtil.get(pageContext, "collapse") %>';
	var minusImage = '01_minus.png';
	var plusAlt = '<%= UnicodeLanguageUtil.get(pageContext, "expand") %>';
	var plusImage = '01_plus.png';

	A.all('.<portlet:namespace />entry-expander').on(
		'click',
		function(event) {
			var expander = event.currentTarget;
			var feedContent = expander.get('parentNode').one('.feed-entry-content');

			if (feedContent) {
				var altText = expander.attr('alt');
				var src = expander.attr('src');

				if (src.indexOf('minus.png') > -1) {
					altText = altText.replace(minusAlt, plusAlt);
					src = src.replace(minusImage, plusImage);
				}
				else {
					altText = altText.replace(plusAlt, minusAlt);
					src = src.replace(plusImage, minusImage);
				}

				feedContent.toggle();

				expander.attr('alt', altText);
				expander.attr('src', src);
			}
		}
	);
</aui:script>