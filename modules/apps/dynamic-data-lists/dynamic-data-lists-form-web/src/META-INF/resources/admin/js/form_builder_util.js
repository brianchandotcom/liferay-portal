AUI.add(
	'liferay-ddl-form-builder-util',
	function(A) {
		var RendererUtil = Liferay.DDM.Renderer.Util;

		var FormBuilderUtil = {
			getFieldClass: function(type) {
				var fieldClass = RendererUtil.getFieldClass(type);

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