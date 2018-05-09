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

<%@ include file="/dynamic_include/init.jsp" %>

<%
String articleId = (String)request.getAttribute(JournalWebKeys.JOURNAL_ARTICLE_ID);
%>

<aui:script use="aui-base">
	AUI().ready(
		function() {
			if (window.Analytics) {
				var applicationId = 'Journal';

				Analytics.send(
					'viewed',
					applicationId,
					{
						articleId: '<%= articleId %>'
					}
				);

				var contentElement = document.getElementById('<portlet:namespace/>journal_content_article_<%= articleId %>');

				var onClick = function(event) {
					var element = event.target;
					var tagName = element.tagName.toLowerCase();

					var payload = {
						articleId: '<%= articleId %>',
						tagName: tagName
					};

					if (tagName === 'a') {
						payload.href = element.href;
						payload.text = element.innerText;
					}
					else if (tagName === 'img') {
						payload.src = element.src;
					}

					Analytics.send('clicked', applicationId, payload);
				};

				contentElement.addEventListener('click', onClick);

				var onDestroyPortlet = function() {
					contentElement.removeEventListener('click', onClick);

					Liferay.detach('destroyPortlet', onDestroyPortlet);
				};

				Liferay.on('destroyPortlet', onDestroyPortlet);
			}
		}
	);
</aui:script>