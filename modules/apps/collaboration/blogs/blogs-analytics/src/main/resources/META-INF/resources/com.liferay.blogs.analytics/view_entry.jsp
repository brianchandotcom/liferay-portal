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

<%@ include file="/com.liferay.blogs.analytics/init.jsp" %>

<%
BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

long entryId = ParamUtil.getLong(request, "entryId", entry.getEntryId());
%>

<aui:script require="metal-dom/src/all/dom as dom,metal-debounce/src/debounce">
	if (window.Analytics) {
		var applicationId = 'Blogs';

		Analytics.registerMiddleware(
			function(request, analytics) {
				request.context['referrer'] = document.referrer;

				return request;
			}
		);

		Analytics.send(
			'VISITS',
			applicationId,
			{
				entryId: '<%= entryId %>'
			}
		);

		var handleSocialShare = function(event) {
			Analytics.send(
				'SOCIAL',
				applicationId,
				{
					entryId: '<%= entryId %>',
					network: event.delegateTarget.id.substr(event.delegateTarget.id.lastIndexOf('_') + 1)
				}
			);
		};

		dom.delegate(
			document.body,
			'click',
			'.social-bookmark[data-contentid="<%= entry.getEntryId() %>"]',
			handleSocialShare
		);

		dom.delegate(
			document.body,
			'click',
			'.social-bookmark-link[data-contentid="<%= entry.getEntryId() %>"]',
			handleSocialShare
		);

		var scrollSessionId = new Date().toISOString();

		var entry = document.querySelector('#<portlet:namespace /><%= entry.getEntryId() %>');

		var debounce = metalDebounceSrcDebounce.default;

		var throttle = function(fn, wait) {
			var time = Date.now();

			return function() {
				if ((time + wait - Date.now()) < 0) {
					fn();
					time = Date.now();
				}
			}
		};

		var sendEvent = function() {
			var entryBoundingClientRect = entry.getBoundingClientRect();

			var depth = Math.trunc(100 * (-entryBoundingClientRect.top) / entryBoundingClientRect.height);

			if (depth >= 0 && depth <= 100) {
				Analytics.send(
					'DEPTH',
					applicationId,
					{
						depth: depth,
						entryId: '<%= entryId %>',
						sessionId: scrollSessionId
					}
				);
			}
		};

		Analytics.registerPlugin(
			function(analytics) {
				document.addEventListener('scroll', throttle(sendEvent, 500));
				document.addEventListener('scroll', debounce(sendEvent, 1500));
			}
		);
	}
</aui:script>