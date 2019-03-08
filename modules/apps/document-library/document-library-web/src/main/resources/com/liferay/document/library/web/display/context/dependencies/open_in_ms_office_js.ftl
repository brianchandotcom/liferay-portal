function ${namespace}openDocument(webDavURL) {
	Liferay.Util.openDocument(
		webDavURL,
		null,
		function(exception) {
			var errorMessage = Liferay.Util.sub('${errorMessage}', exception.message);

			var openMSOfficeErrorElement = document.getElementById('${namespace}openMSOfficeError');

			if (openMSOfficeErrorElement) {
				openMSOfficeErrorElement.innerHTML = errorMessage;

				openMSOfficeErrorElement.classList.remove('hide');
			}
		}
	);
}