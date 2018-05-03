AUI.add(
	'liferay-ddm-soy-template-util',
	function(A) {
		var AObject = A.Object;

		var SoyTemplateUtil = {
			getTemplateRenderer: function(templateNamespace) {
				var instance = this;

				var renderer = AObject.getValue(window, templateNamespace.split('.'));

				if (!renderer) {
					throw new Error('Form template renderer is not defined: "' + templateNamespace);
				}

				return renderer;
			},

			loadModules: function(callback) {
				var modules = AObject.keys(Liferay.MODULES);

				var dependencies = modules.filter(
					function(item) {
						return /dynamic-data-.*\.es/.test(item);
					}
				);

				Liferay.Loader.require.apply(
					Liferay.Loader,
					dependencies.concat(callback)
				);
			}
		};

		Liferay.namespace('DDM').SoyTemplateUtil = SoyTemplateUtil;
	},
	'',
	{
		requires: []
	}
);