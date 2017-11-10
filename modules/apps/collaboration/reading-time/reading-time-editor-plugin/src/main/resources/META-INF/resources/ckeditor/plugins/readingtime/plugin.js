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
								fetch(
									editor.config.readingTime.url,
									{
										body: new URLSearchParams(Liferay.Util.ns(
											NAMESPACE,
											{
												content: editor.getData(),
												contentType: 'text/html'
											}
										)),
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