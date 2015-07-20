AUI.add(
	'liferay-ddm-form-renderer',
	function(A) {
		var AArray = A.Array;
		var Renderer = Liferay.DDM.Renderer;

		var Form = A.Component.create(
			{
				ATTRS: {
					container: {
						setter: A.one,
						valueFn: '_valueContainer'
					},

					portletNamespace: {
						value: ''
					}
				},

				AUGMENTS: [Renderer.DefinitionSupport, Renderer.FormValidationSupport, Renderer.NestedFieldsSupport],

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-form-renderer',

				prototype: {
					initializer: function() {
						var instance = this;

						var container = instance.get('container');

						instance.tabView = new A.TabView(
							{
								boundingBox: container,
								srcNode: container.one('.lfr-ddm-form-pages'),
								type: 'pills'
							}
						);
					},

					getTabView: function() {
						var instance = this;

						return instance.tabView;
					},

					render: function() {
						var instance = this;

						instance.tabView.render();

						return instance;
					},

					toJSON: function() {
						var instance = this;

						var definition = instance.get('definition');

						return {
							availableLanguageIds: definition.availableLanguageIds,
							defaultLanguageId: definition.defaultLanguageId,
							fieldValues: AArray.invoke(instance.get('fields'), 'toJSON')
						};
					},

					_valueContainer: function() {
						var instance = this;

						return A.Node.create('<div class="lfr-ddm-form-container"></div>');
					}
				}
			}
		);

		Liferay.namespace('DDM.Renderer').Form = Form;
	},
	'',
	{
		requires: ['aui-component', 'aui-tabview', 'liferay-ddm-form-renderer-definition', 'liferay-ddm-form-renderer-nested-fields', 'liferay-ddm-form-renderer-validation']
	}
);