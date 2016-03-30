<script>
	require(
		$REQUIRED_MODULES,
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