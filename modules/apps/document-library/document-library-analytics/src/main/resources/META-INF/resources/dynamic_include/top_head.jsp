<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<script data-senna-track="temporary" type="text/javascript">
	if (window.Analytics) {
		window.<%= DocumentLibraryAnalyticsConstants.JS_PREFIX %>isViewFileEntry = false;
	}
</script>

<aui:script>
	function getValueByAttribute(node, attr) {
		return (
			node.dataset[attr] ||
			(node.parentElement && node.parentElement.dataset[attr])
		);
	}

	function sendAnalyticsEvent(anchor) {
		var fileEntryId = getValueByAttribute(anchor, 'analyticsFileEntryId');
		var title = getValueByAttribute(anchor, 'analyticsFileEntryTitle');
		var version = getValueByAttribute(anchor, 'analyticsFileEntryVersion');

		if (fileEntryId) {
			Analytics.send('documentDownloaded', 'Document', {
				groupId: themeDisplay.getScopeGroupId(),
				fileEntryId,
				preview: !!window.<%= DocumentLibraryAnalyticsConstants.JS_PREFIX %>isViewFileEntry,
				title,
				version,
			});
		}
	}

	function handleDownloadClick(event) {
		if (window.Analytics) {
			if (event.target.nodeName.toLowerCase() === 'a') {
				sendAnalyticsEvent(event.target);
			}
			else if (
				event.target.parentNode &&
				event.target.parentNode.nodeName.toLowerCase() === 'a'
			) {
				sendAnalyticsEvent(event.target.parentNode);
			}
			else {
				var target = event.target;
				var matchTitle =
					target.title && target.title.toLowerCase() === 'download';
				var matchAction = target.action === 'download';
				var matchLexiconIcon = !!target.querySelector(
					'.lexicon-icon-download'
				);
				var matchLexiconClassName = target.classList.contains(
					'lexicon-icon-download'
				);
				var matchParentTitle =
					target.parentNode &&
					target.parentNode.title &&
					target.parentNode.title.toLowerCase() === 'download';
				var matchParentLexiconClassName =
					target.parentNode &&
					target.parentNode.classList.contains('lexicon-icon-download');

				if (
					matchTitle ||
					matchParentTitle ||
					matchAction ||
					matchLexiconIcon ||
					matchLexiconClassName ||
					matchParentLexiconClassName
				) {
					var selectedFiles = document.querySelectorAll(
						'.form .custom-control-input:checked'
					);

					selectedFiles.forEach(({value}) => {
						var selectedFile = document.querySelector(
							'[data-analytics-file-entry-id="' + value + '"]'
						);

						sendAnalyticsEvent(selectedFile);
					});
				}
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