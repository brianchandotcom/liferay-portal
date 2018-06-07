Liferay.Loader.require.apply(
	Liferay.Loader,
	$MODULES.concat(
		[
			function(Component) {
				var context = $CONTEXT;

				var destroyConfig = {
					destroyOnNavigate: true,
					portletId: context.portletId
				};

				Liferay.component(
					'$ID',
					new Component.default(context, '#$ID'),
					destroyConfig
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