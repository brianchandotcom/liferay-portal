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

<aui:script sandbox="<%= true %>">
	var onDestroyPortlet = function () {
		Liferay.detach('messagePosted', onMessagePosted);
		Liferay.detach('destroyPortlet', onDestroyPortlet);
	};

	Liferay.on('destroyPortlet', onDestroyPortlet);

	var onMessagePosted = function (event) {
		if (window.Analytics) {
			const eventProperties = {
				className: event.className,
				classPK: event.classPK,
				commentId: event.commentId,
				text: event.text,
			};

			const blogNode = document.querySelector(
				'[data-analytics-asset-id="' + event.classPK + '"]'
			);

			const dmNode = document.querySelector(
				'[data-analytics-file-entry-id="' + event.classPK + '"]'
			);

			if (blogNode) {
				eventProperties.title = blogNode.dataset.analyticsAssetTitle;
			}
			else if (dmNode) {
				eventProperties.title = dmNode.dataset.analyticsFileEntryTitle;
			}

			Analytics.send('posted', 'Comment', eventProperties);
		}
	};

	Liferay.on('messagePosted', onMessagePosted);
</aui:script>