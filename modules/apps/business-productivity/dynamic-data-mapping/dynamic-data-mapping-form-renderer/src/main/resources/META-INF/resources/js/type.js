AUI.add(
	'liferay-ddm-form-renderer-type',
	function(A) {

		var FormRendererFieldType = A.Component.create(
			{
				ATTRS: {

				},

				EXTENDS: A.FormBuilderFieldType,

				NAME: 'liferay-ddm-form-renderer-type',

				prototype: {
					TPL_FIELD_TYPE_CONTENT: '<div class="field-type-icon"><svg class="lexicon-icon"><use xlink:href="/o/frontend-theme-admin-web/admin/images/lexicon/icons.svg#{icon}" /></svg></div>' +
						'<div class="field-type-label">{label}</div></div>',
					initializer: function() {
						FormRendererFieldType.superclass.initializer.apply(this, arguments);
					}
				}
			}
		);

		Liferay.namespace('DDM').FormRendererFieldType = FormRendererFieldType;
	},
	'',
	{
		requires: ['aui-form-builder-field-type']
	}
);