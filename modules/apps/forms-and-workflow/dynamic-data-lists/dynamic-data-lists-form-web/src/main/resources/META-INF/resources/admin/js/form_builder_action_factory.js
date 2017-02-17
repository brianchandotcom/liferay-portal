AUI.add(
	'liferay-ddl-form-builder-action-factory',
	function(A) {
		var FormBuilderActionFactory = A.Component.create(
			{
				ATTRS: {
					dataProviders: {
						value: []
					},

					fields: {
						value: []
					},

					getDataProviderParametersSettingsURL: {
						value: ''
					},

					getDataProviders: {
						value: []
					},

					pages: {
						value: []
					},

					portletNamespace: {
						value: ''
					}
				},

				AUGMENTS: [],

				NAME: 'liferay-ddl-form-builder-action-factory',

				prototype: {
					createAction: function(type, index, act, container) {
						var instance = this;

						var action;

						if (instance._isPropertyAction(type)) {
							action = new Liferay.DDL.FormBuilderActionProperty(
								{
									action: act,
									boundingBox: container,
									index: index,
									options: instance.get('fields'),
									type: type
								}
							);
						}
						else if (type === 'jump-to-page') {
							action = new Liferay.DDL.FormBuilderActionJumpToPage(
								{
									action: act,
									boundingBox: container,
									index: index,
									options: instance.get('pages')
								}
							);
						}
						else if (type === 'auto-fill') {
							action = new Liferay.DDL.FormBuilderActionAutofill(
								{
									action: act,
									boundingBox: container,
									fields: instance.get('fields'),
									getDataProviderParametersSettingsURL: instance.get('getDataProviderParametersSettingsURL'),
									getDataProviders: instance.get('getDataProviders'),
									index: index,
									options: instance.get('dataProviders'),
									portletNamespace: instance.get('portletNamespace')
								}
							);
						}

						return action;
					},

					_isPropertyAction: function(actionType) {
						var instance = this;

						var prototypesAction = ['show', 'enable', 'require'];

						return prototypesAction.indexOf(actionType) !== -1;
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderActionFactory = FormBuilderActionFactory;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-render-template', 'liferay-ddm-form-renderer-field']
	}
);