Liferay.Loader.require.apply(
	Liferay.Loader,
	$MODULES.concat(
		[
			function(Component) {
				Liferay.component('$ID', new Component.default($CONTEXT, '#$ID'));

				Liferay.once(
					'beforeScreenFlip',
					function() {
						Liferay.component('$ID').dispose();
						Liferay.component('$ID', null);
					}
				);
			}
		]
	)
);