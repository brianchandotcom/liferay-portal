Liferay.provide(
	window,
	'${namespace}openDocument',
	function(webDavURL) {
		var A = AUI();

		Liferay.Util.openDocument(
			webDavURL,
			null,
			function(exception) {
				var errorMessage = A.Lang.sub(
					'${errorMessage}',
					[exception.message]
				);

				var openMSOfficeError = A.one('#${namespace}openMSOfficeError');

				openMSOfficeError.html(errorMessage);

				openMSOfficeError.show();
			}
		);
	},
	['aui-base']
);