AUI.add(
	'liferay-ddl-form-builder-util',
	function(A) {
		var FieldClassFactory = Liferay.DDM.Renderer.FieldClassFactory;

		var FormBuilderUtil = {
			getFieldClass: function(type, context) {
				var fieldClass = FieldClassFactory.getFieldClass(type, context);

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
		requires: ['liferay-ddl-form-builder-settings-support', 'liferay-ddm-form-renderer-util']
	}
);