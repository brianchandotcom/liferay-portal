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

				AUGMENTS: [
					Renderer.FormDefinitionSupport,
					Renderer.FormFeedbackSupport,
					Renderer.FormPaginationSupport,
					Renderer.FormTabsSupport,
					Renderer.FormValidationSupport,
					Renderer.NestedFieldsSupport
				],

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-form-renderer',

				prototype: {
					_eventHandlers: [],

					initializer: function() {
						var instance = this;

						var formNode = instance.getFormNode();

						if (formNode) {
							instance._eventHandlers.push(
								formNode.on('submit', A.bind('_onDOMSubmitForm', instance)),
								Liferay.on('submitForm', instance._onLiferaySubmitForm, instance)
							);
						}
					},

					destructor: function() {
						var instance = this;

						instance.get('container').remove();

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					getFormNode: function() {
						var instance = this;

						var container = instance.get('container');

						return container.ancestor('form', true);
					},

					render: function() {
						var instance = this;

						instance.fire('render');

						return instance;
					},

					submit: function() {
						var instance = this;

						instance.validate(
							function(hasErrors) {
								if (!hasErrors) {
									var formNode = instance.getFormNode();

									formNode.submit();
								}
							}
						);
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

					_onDOMSubmitForm: function(event) {
						var instance = this;

						event.preventDefault();

						var currentPage = instance.getCurrentPage();
						var pagesTotal = instance.getPagesTotal();

						if (pagesTotal > 1 && currentPage < pagesTotal) {
							instance.nextPage();
						}
						else {
							instance.submit();
						}
					},

					_onLiferaySubmitForm: function(event) {
						var instance = this;

						if (event.form === instance.getFormNode()) {
							event.preventDefault();
						}
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
		requires: ['aui-component', 'liferay-ddm-form-renderer-definition', 'liferay-ddm-form-renderer-feedback', 'liferay-ddm-form-renderer-nested-fields', 'liferay-ddm-form-renderer-pagination', 'liferay-ddm-form-renderer-tabs', 'liferay-ddm-form-renderer-validation']
	}
);