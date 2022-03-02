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

<aui:script>
	var pathnameRegexp = /\/documents\/(\d+)\/(\d+)\/(.+?)\/([^&]+)/;

	function sendAnalyticsEvent(anchor) {
		var fileEntryId =
			anchor.dataset.analyticsFileEntryId ||
			(anchor.parentElement &&
				anchor.parentElement.dataset.analyticsFileEntryId);

		var getParameterValue = (parameterName) => {
			var result = null;

			anchor.search
				.substr(1)
				.split('&')
				.forEach((item) => {
					var tmp = item.split('=');

					if (tmp[0] === parameterName) {
						result = decodeURIComponent(tmp[1]);
					}
				});

			return result;
		};

		var match = pathnameRegexp.exec(anchor.pathname);

		if (fileEntryId && match) {
			Analytics.send('documentDownloaded', 'Document', {
				groupId: match[1],
				fileEntryId,
				preview: !!window.<%= DocumentLibraryAnalyticsConstants.JS_PREFIX %>isViewFileEntry,
				title: decodeURIComponent(match[3].replace(/\+/gi, ' ')),
				version: getParameterValue('version'),
			});
		}
	}

	function handleDownloadClick(event) {
		if (window.Analytics) {
			if (
				event.target.dataset.action === 'download' ||
				event.target.querySelector('.lexicon-icon-download') ||
				event.target.classList.contains('lexicon-icon-download') ||
				(event.target.parentNode &&
					event.target.parentNode.classList.contains(
						'lexicon-icon-download'
					))
			) {
				var selectedFiles = document.querySelectorAll(
					'.portlet-document-library .entry-selector:checked'
				);

				selectedFiles.forEach(({value}) => {
					var selectedFile = document.querySelector(
						'[data-analytics-file-entry-id="' + value + '"]'
					);

					sendAnalyticsEvent(selectedFile);
				});
			}
			else if (event.target.nodeName.toLowerCase() === 'a') {
				sendAnalyticsEvent(event.target);
			}
			else if (
				event.target.parentNode &&
				event.target.parentNode.nodeName.toLowerCase() === 'a'
			) {
				sendAnalyticsEvent(event.target.parentNode);
			}
		}
	}

	Liferay.once('destroyPortlet', () => {
		document.body.removeEventListener('click', handleDownloadClick);
	});

	Liferay.once('portletReady', () => {
		document.body.addEventListener('click', handleDownloadClick);
	});
</aui:script>