/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const NAMESPACE =
		'_com_liferay_reading_time_web_portlet_ReadingTimePortlet_';

	CKEDITOR.plugins.add('readingtime', {
		init(editor) {
			AUI().use('aui-debounce', (A) => {
				editor.on(
					'change',
					A.debounce(() => {
						const formData = new FormData();

						formData.append(
							Liferay.Util.ns(NAMESPACE, 'content'),
							editor.getData()
						);

						formData.append(
							Liferay.Util.ns(NAMESPACE, 'contentType'),
							'text/html'
						);

						Liferay.Util.fetch(editor.config.readingTime.url, {
							body: formData,
							credentials: 'same-origin',
							method: 'POST',
						})
							.then((response) => {
								return response.json();
							})
							.then((message) => {
								const readingTimeElement = A.one(
									'#' + editor.config.readingTime.elementId
								);

								if (readingTimeElement) {
									readingTimeElement.html(
										message.readingTimeMessage
									);
									readingTimeElement.setAttribute(
										'datetime',
										message.readingTimeInSeconds + 's'
									);
								}

								editor.fire('readingTime', message);
							});
					}, 500)
				);
			});
		},
	});
})();
