AUI.add(
	'liferay-ddl-form-builder-field-list',
	function(A) {
		var CSS_FIELD_LIST_ADD_BUTTON = A.getClassName('form', 'builder', 'field', 'list', 'add', 'button');

		var CSS_FIELD_LIST_ADD_BUTTON_ICON = A.getClassName('form', 'builder', 'field', 'list', 'add', 'button', 'icon');

		var CSS_FIELD_LIST_ADD_BUTTON_LABEL = A.getClassName('form', 'builder', 'field', 'list', 'add', 'button', 'label');

		var CSS_FIELD_LIST_ADD_BUTTON_PLUS_ICON = A.getClassName('form', 'builder', 'field', 'list', 'add', 'button', 'plus', 'icon');

		var CSS_FIELD_LIST_ADD_BUTTON_VISIBLE = A.getClassName('form', 'builder', 'field', 'list', 'add', 'button', 'visible');

		var CSS_FIELD_LIST_ADD_CONTAINER = A.getClassName('form', 'builder', 'field', 'list', 'add', 'container');

		var FormBuilderFieldList = A.Component.create(
			{
				EXTENDS: A.FormBuilderFieldList,

				NAME: 'liferay-ddl-form-builder-field-list',

				prototype: {
					TPL_ADD_FIELD: '<div class="' + CSS_FIELD_LIST_ADD_CONTAINER + '">' +
							'<button class="' + CSS_FIELD_LIST_ADD_BUTTON + ' ' + CSS_FIELD_LIST_ADD_BUTTON_VISIBLE + '" type="button">' +
								'<span class="' + CSS_FIELD_LIST_ADD_BUTTON_ICON + ' ' + CSS_FIELD_LIST_ADD_BUTTON_PLUS_ICON + '">' +
									Liferay.Util.getLexiconIconTpl('plus') +
								'</span>' +
								'<label class="' + CSS_FIELD_LIST_ADD_BUTTON_LABEL + '"> {addField} </label>' +
							'</button>' +
						'</div>'
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderFieldList = FormBuilderFieldList;
	},
	'',
	{
		requires: ['aui-form-builder-field-list']
	}
);