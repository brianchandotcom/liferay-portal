Liferay.Loader.require(
	'$MODULE',
	function(Component) {
		var context = Object.assign(
			$CONTEXT,
			Liferay.getComponentCache('$ID')
		);

		var componentConfig = {
			cacheState: context.cacheState,
			destroyOnNavigate: true,
			portletId: context.portletId
		};

		var componentArgs = [context];

		if ($WRAPPER) {
			componentArgs.push('#$ID');
		}

		if (context.defaultEventHandler) {
			Liferay.componentReady(
				context.defaultEventHandler
			).then(
				function(defaultEventHandler) {
					context.defaultEventHandler = defaultEventHandler;

					Liferay.component(
						'$ID',
						new Component.default(...componentArgs),
						componentConfig
					);
				}
			);
		}
		else {
			Liferay.component(
				'$ID',
				new Component.default(...componentArgs),
				componentConfig
			);
		}
	},
	function(error) {
		console.error('Unable to load $MODULE');

		Liferay.fire(
			'soyComponentLoadingError',
			{
				error: error,
				modules: ['$MODULE']
			}
		);
	}
);