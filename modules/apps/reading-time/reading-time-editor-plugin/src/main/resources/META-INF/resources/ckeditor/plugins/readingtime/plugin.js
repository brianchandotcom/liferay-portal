(function() {
	var NAMESPACE = '_com_liferay_reading_time_web_portlet_ReadingTimePortlet_';

	CKEDITOR.plugins.add(
		'readingtime',
		{
			init: function(editor) {
				AUI().use(
					'aui-debounce',
					function(A) {
						editor.on(
							'change',
							A.debounce(function() {
								const formData = new FormData();

								formData.append(
									Liferay.Util.ns(NAMESPACE, 'content'),
									editor.getData()
								);

								formData.append(
									Liferay.Util.ns(NAMESPACE, 'contentType'),
									'text/html'
								);

								fetch(
									editor.config.readingTime.url,
									{
										body: formData,
										credentials: 'same-origin',
										method: 'POST'
									}
								).then(function(response) {
									return response.json();
								}).then(function(message) {
									var readingTimeElement = A.one('#' + editor.config.readingTime.elementId);

									if (readingTimeElement) {
										readingTimeElement.html(message.readingTimeMessage);
										readingTimeElement.setAttribute('datetime', message.readingTimeInSeconds + 's');
									}

									editor.fire('readingTime', message);
								});
							}, 500)
						);
					}
				);
			}
		}
	);
})();