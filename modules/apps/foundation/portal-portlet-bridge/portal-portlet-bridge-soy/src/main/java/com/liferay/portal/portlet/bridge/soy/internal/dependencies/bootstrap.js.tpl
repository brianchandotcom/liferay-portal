<script>
	require(
		'$MODULE_NAME/$CONTROLLER_NAME',
		function(Portlet) {
			Liferay.component('$PORTLET_NAMESPACE', new Portlet.default($CONTEXT).render());

			Liferay.once(
				'beforeScreenFlip',
				function() {
					Liferay.component('$PORTLET_NAMESPACE').dispose();
				}
			);
		}
	);
</script>