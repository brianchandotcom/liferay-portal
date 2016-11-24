<script>
	var require = Liferay.Loader.require.bind(Liferay.Loader);

	require.apply(
		window,
		$MODULES.concat(
			[
				function(Component) {
					Liferay.component('$ID', new Component.default($CONTEXT, '#$ID'));

					Liferay.once(
						'beforeScreenFlip',
						function() {
							Liferay.component('$ID').dispose();
						}
					);
				}
			]
		)
	);
</script>