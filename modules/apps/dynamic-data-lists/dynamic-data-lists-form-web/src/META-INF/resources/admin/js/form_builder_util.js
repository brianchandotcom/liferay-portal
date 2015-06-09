AUI.add(
	'liferay-ddl-form-builder-util',
	function(A) {
		var AArray = A.Array;
		var RendererUtil = Liferay.DDM.Renderer.Util;

		var FormBuilderUtil = {
			getFieldClass: function(fieldClassName) {
				var fieldClass = RendererUtil.getFieldClass(fieldClassName);

				return A.Component.create(
					{
						AUGMENTS: [Liferay.DDL.FormBuilderFieldSupport],

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