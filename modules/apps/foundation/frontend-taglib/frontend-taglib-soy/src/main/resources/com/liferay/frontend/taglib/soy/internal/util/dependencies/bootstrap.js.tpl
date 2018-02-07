Liferay.Loader.require.apply(
	Liferay.Loader,
	$MODULES.concat(
		[
			function(Component) {
				var context = $CONTEXT;

				var portletRefreshedHandler;

				var screenFlipHandler;

				Liferay.component('$ID', new Component.default(context, '#$ID'));

				if (context.portletId) {
					portletRefreshedHandler = Liferay.once(
						context.portletId + ':portletRefreshed',
						function() {
							if (screenFlipHandler) {
								screenFlipHandler.detach();
							}
						}
					);
				}

				screenFlipHandler = Liferay.once(
					'beforeScreenFlip',
					function() {
						Liferay.component('$ID').dispose();
						Liferay.component('$ID', null);

						if (portletRefreshedHandler) {
							portletRefreshedHandler.detach();
						}
					}
				);
			},
			function(error) {
				console.error('Unable to load ' + $MODULES);

				Liferay.fire(
					'soyComponentLoadingError',
					{
						error: error,
						modules: $MODULES
					}
				);
			}
		]
	)
);