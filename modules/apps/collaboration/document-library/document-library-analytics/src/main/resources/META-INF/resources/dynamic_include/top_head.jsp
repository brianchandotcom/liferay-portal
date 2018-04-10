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

<script data-senna-track="temporary" type="text/javascript">
	if (window.Analytics) {
		window.<%= DocumentLibraryAnalyticsConstants.JS_PREFIX %>isViewFileEntry = false;
	}
</script>

<aui:script require="metal-dom/src/all/dom as dom,metal-uri/src/Uri" sandbox="<%= true %>">
	var Uri = metalUriSrcUri.default;
	var pathnameRegexp = /\/documents\/(\d+)\/(\d+)\/(.+)\/(.+)/;

	var downloadClickHandler = dom.delegate(
		document.body,
		'click',
		'a',
		function(event) {
			if (window.Analytics) {
				var anchor = event.delegateTarget;
				var uri = new Uri(anchor.href);

				var match = pathnameRegexp.exec(uri.getPathname());

				if (match) {
					Analytics.send(
						'DOWNLOAD',
						'DocumentLibrary',
						{
							groupId: match[1],
							fileEntryUUID: match[4],
							preview: !!window.<%= DocumentLibraryAnalyticsConstants.JS_PREFIX %>isViewFileEntry,
							version: uri.getParameterValue('version')
						}
					);
				}
			}
		}
	);

	var onDestroyPortlet = function() {
		downloadClickHandler.removeListener()
		Liferay.detach('destroyPortlet', onDestroyPortlet);
	}

	Liferay.on('destroyPortlet', onDestroyPortlet);
</aui:script>