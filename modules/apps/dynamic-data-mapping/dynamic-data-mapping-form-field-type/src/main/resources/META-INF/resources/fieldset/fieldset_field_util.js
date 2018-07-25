AUI.add(
	'liferay-ddm-form-field-fieldset-util',
	function(A) {
		var AObject = A.Object;

		var Renderer = Liferay.DDM.Renderer;

		var FieldTypes = Renderer.FieldTypes;

		var Util = Renderer.Util;

		var FieldSetFieldUtil = {
			createFieldSetNestedField: function(config) {
				var instance = this;

				var fieldType = FieldTypes.get(config.type);

				var fieldClassName = fieldType.get('className');

				var fieldClass = AObject.getValue(window, fieldClassName.split('.'));

				return A.Component.create(
					{
						EXTENDS: fieldClass,

						NAME: 'liferay-ddm-form-field-fieldset-nestedfield',

						prototype: {
							fetchContainer: function() {
								var instance = this;

								var instanceId = instance.get('instanceId');

								var container = instance._getContainerByInstanceId(instanceId);

								if (!container) {
									var name = instance.get('fieldName');

									var parent = instance.get('parent');

									container = parent.filterNodes(
										function(qualifiedName) {
											var nodeFieldName = Util.getFieldNameFromQualifiedName(qualifiedName);

											return name === nodeFieldName;
										}
									).item(0);
								}

								return container;
							},

							getQualifiedName: function() {
								var instance = this;

								var parent = instance.get('parent');

								return [
									instance.get('portletNamespace'),
									'ddm$$',
									parent.get('fieldName'),
									'$',
									parent.get('instanceId'),
									'$',
									parent.get('repeatedIndex'),
									'#',
									instance.get('fieldName'),
									'$',
									instance.get('instanceId'),
									'$',
									instance.get('repeatedIndex'),
									'$$',
									instance.get('locale')
								].join('');
							}
						}
					}
				);
			}
		};

		Liferay.namespace('DDM.Field').FieldSetUtil = FieldSetFieldUtil;
	},
	'',
	{
		requires: ['aui-component', 'liferay-ddm-form-renderer-util']
	}
);