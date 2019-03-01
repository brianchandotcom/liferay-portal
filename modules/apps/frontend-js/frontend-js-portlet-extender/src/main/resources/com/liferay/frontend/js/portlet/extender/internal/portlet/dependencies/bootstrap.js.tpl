<script type="text/javascript">
	Liferay.Loader.require(
		"[$PACKAGE_NAME$]@[$PACKAGE_VERSION$]",
		function(module) {
			var initializer;

			if (typeof module.default === 'function') {
				initializer = module.default;
			}
			else if (typeof module === 'function') {
				initializer = module;
			}

			if (initializer) {
				initializer(
					{
						configuration: {
							portletInstance: JSON.parse('[$PORTLET_INSTANCE_CONFIGURATION$]'),
							system: JSON.parse('[$SYSTEM_CONFIGURATION$]')
						},
						contextPath: '[$CONTEXT_PATH$]',
						portletElementId: '[$PORTLET_ELEMENT_ID$]',
						portletNamespace: '[$PORTLET_NAMESPACE$]'
					});
			}
			else {
				console.error(
					'Module', '[$PACKAGE_NAME$]@[$PACKAGE_VERSION$]',
					'is not exporting a function: cannot initialize it.');
			}

		});
</script>