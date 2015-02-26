function ${namespace}openDocument(webDavURL) {
	Liferay.Util.openDocument(
		webDavURL,
		null,
		function(exception) {
			var errorMessage = AUI._.sub(
				'${errorMessage}',
				[exception.message]
			);

			var openMSOfficeError = AUI.$('#${namespace}openMSOfficeError');

			openMSOfficeError.html(errorMessage);

			openMSOfficeError.removeClass('hide');
		}
	);
}