AUI.add(
	'liferay-ddl-form-builder-util',
	function(A) {
		var AObject = A.Object;

		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

		var FormBuilderUtil = {
			getFieldClass: function(type) {
				var fieldType = FieldTypes.get(type);

				var fieldClassName = fieldType.get('className');

				var fieldClass = AObject.getValue(window, fieldClassName.split('.'));

				return A.Component.create(
					{
						ATTRS: {
							enableEvaluations: {
								value: false
							}
						},

						AUGMENTS: [Liferay.DDL.FormBuilderSettingsSupport],

						EXTENDS: fieldClass,

						NAME: fieldClass.NAME
					}
				);
			}
		};

		Liferay.namespace('DDL').FormBuilderUtil = FormBuilderUtil;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-field-support', 'liferay-ddm-form-renderer-util']
	}
);