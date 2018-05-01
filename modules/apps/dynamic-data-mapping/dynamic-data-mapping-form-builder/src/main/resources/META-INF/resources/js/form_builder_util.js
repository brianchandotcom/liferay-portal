AUI.add(
	'liferay-ddm-form-builder-util',
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

						AUGMENTS: [Liferay.DDM.FormBuilderSettingsSupport],

						EXTENDS: fieldClass,

						NAME: fieldClass.NAME
					}
				);
			},

			visitLayout: function(pages, fieldHandler) {
				var visitor = new Liferay.DDM.LayoutVisitor();

				visitor.setAttrs(
					{
						fieldHandler: fieldHandler,
						pages: pages
					}
				);

				visitor.visit();
			}
		};

		Liferay.namespace('DDM').FormBuilderUtil = FormBuilderUtil;
	},
	'',
	{
		requires: ['liferay-ddm-form-builder-field-support', 'liferay-ddm-form-renderer-layout-visitor', 'liferay-ddm-form-renderer-util']
	}
);