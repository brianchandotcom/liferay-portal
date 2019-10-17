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

(function() {
	var NAMESPACE = '_com_liferay_reading_time_web_portlet_ReadingTimePortlet_';

	CKEDITOR.plugins.add('readingtime', {
		init(editor) {
			AUI().use('aui-debounce', A => {
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
							method: 'POST'
						})
							.then(response => {
								return response.json();
							})
							.then(message => {
								var readingTimeElement = A.one(
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
		}
	});
})();
