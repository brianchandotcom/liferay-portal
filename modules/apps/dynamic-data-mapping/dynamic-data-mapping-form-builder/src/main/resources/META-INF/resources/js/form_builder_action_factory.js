AUI.add(
	'liferay-ddm-form-builder-action-factory',
	function(A) {
		var FormBuilderActionFactory = A.Component.create(
			{
				ATTRS: {
					builder: {
						value: {}
					},

					dataProviders: {
						value: []
					},

					fields: {
						value: []
					},

					getDataProviders: {
						value: []
					},

					pages: {
						value: []
					}
				},

				AUGMENTS: [],

				NAME: 'liferay-ddm-form-builder-action-factory',

				prototype: {
					createAction: function(type, index, act, container) {
						var instance = this;

						var action;

						var builder = instance.get('builder');

						if (instance._isPropertyAction(type)) {
							action = new Liferay.DDM.FormBuilderActionProperty(
								{
									action: act,
									boundingBox: container,
									bubbleTargets: [instance],
									index: index,
									options: instance.get('fields'),
									type: type
								}
							);
						}
						else if (type === 'jump-to-page') {
							action = new Liferay.DDM.FormBuilderActionJumpToPage(
								{
									action: act,
									boundingBox: container,
									bubbleTargets: [instance],
									index: index,
									options: instance.get('pages')
								}
							);
						}
						else if (type === 'auto-fill') {
							action = new Liferay.DDM.FormBuilderActionAutofill(
								{
									action: act,
									boundingBox: container,
									bubbleTargets: [instance],
									fields: instance.get('fields'),
									getDataProviders: instance.get('getDataProviders'),
									index: index,
									options: instance.get('dataProviders')
								}
							);
						}
						else if (type === 'calculate') {
							action = new Liferay.DDM.FormBuilderActionCalculate(
								{
									action: act,
									boundingBox: container,
									bubbleTargets: [instance],
									builder: builder,
									index: index,
									options: instance.get('fields')
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

		Liferay.namespace('DDM').FormBuilderActionFactory = FormBuilderActionFactory;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);