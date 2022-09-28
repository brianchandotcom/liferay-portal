var context = Object.assign(
	$CONTEXT,
	Liferay.getComponentCache('$ID')
);

var componentConfig = {
	cacheState: context.cacheState,
	destroyOnNavigate: true,
	portletId: context.portletId
};

if (context.defaultEventHandler) {
	Liferay.componentReady(
		context.defaultEventHandler
	).then(
		function(defaultEventHandler) {
			context.defaultEventHandler = defaultEventHandler;

			if ($WRAPPER) {
				Liferay.component(
					'$ID',
					new $MODULE.default(context, '#$ID'),
					componentConfig
				);
			}
			else {
				Liferay.component(
					'$ID',
					new $MODULE.default(context),
					componentConfig
				);
			}
		}
	);
}
else {
	if ($WRAPPER) {
		Liferay.component(
			'$ID',
			new $MODULE.default(context, '#$ID'),
			componentConfig
		);
	}
	else {
		Liferay.component(
			'$ID',
			new $MODULE.default(context),
			componentConfig
		);
	}
}