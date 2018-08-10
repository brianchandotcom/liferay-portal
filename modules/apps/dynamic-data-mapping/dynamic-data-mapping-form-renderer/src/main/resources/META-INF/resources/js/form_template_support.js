AUI.add(
	'liferay-ddm-form-renderer-template',
	function(A) {
		var AObject = A.Object;

		var FormTemplateSupport = function() {
		};

		FormTemplateSupport.ATTRS = {
			templateNamespace: {
				getter: '_getTemplateNamespace'
			}
		};

		FormTemplateSupport.prototype = {
			getTemplate: function() {
				var instance = this;

				var renderer = instance.getTemplateRenderer();

				var context = instance.getTemplateContext();

				return MetalComponent.renderToString(renderer, context);
			},

			getTemplateContext: function() {
				var instance = this;

				return instance.get('context');
			},

			getTemplateRenderer: function() {
				var instance = this;

				var templateNamespace = instance.get('templateNamespace');

				var renderer = AObject.getValue(window, templateNamespace.split('.'));

				if (!renderer) {
					throw new Error('Form template renderer is not defined: "' + templateNamespace);
				}

				return renderer;
			},

			render: function() {
				var instance = this;

				var container = instance.get('container');

				container.html(instance.getTemplate());

				instance.eachNestedField(
					function(field) {
						field.updateContainer();
					}
				);

				instance.fire('render');

				return instance;
			},

			_getTemplateNamespace: function() {
				var instance = this;

				return instance.get('context.templateNamespace');
			},

			_valueContext: function() {
				var instance = this;

				return {
					pages: [
						{
							rows: [
								{
									columns: []
								}
							],
							title: Liferay.Language.get('title')
						}
					]
				};
			}
		};

		Liferay.namespace('DDM.Renderer').FormTemplateSupport = FormTemplateSupport;
	},
	'',
	{
		requires: ['aui-base']
	}
);