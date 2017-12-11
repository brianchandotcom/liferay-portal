Liferay.provide(
	Liferay.Util,
	'openSimpleInputModal',
	function(config) {
		Liferay.Loader.require(
			'frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es',
			function(openSimpleInputModal) {
				openSimpleInputModal.default(config);
			},
			function(error) {
				console.error(error);
			}
		);
	}
);