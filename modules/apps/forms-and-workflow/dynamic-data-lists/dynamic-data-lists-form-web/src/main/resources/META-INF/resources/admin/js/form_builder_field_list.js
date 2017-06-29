AUI.add(
	'liferay-ddl-form-builder-field-list',
	function(A) {
		var CSS_BETWEEN_FIELDS = A.getClassName('between', 'fields');

		var CSS_FIELD_LIST_ADD_BUTTON = A.getClassName('form', 'builder', 'field', 'list', 'add', 'button');

		var CSS_FIELD_LIST_ADD_BUTTON_ICON = A.getClassName('form', 'builder', 'field', 'list', 'add', 'button', 'icon');

		var CSS_FIELD_LIST_ADD_BUTTON_LABEL = A.getClassName('form', 'builder', 'field', 'list', 'add', 'button', 'label');

		var CSS_FIELD_LIST_ADD_BUTTON_PLUS_ICON = A.getClassName('form', 'builder', 'field', 'list', 'add', 'button', 'plus', 'icon');

		var CSS_FIELD_LIST_ADD_BUTTON_VISIBLE = A.getClassName('form', 'builder', 'field', 'list', 'add', 'button', 'visible');

		var CSS_FIELD_LIST_ADD_CONTAINER = A.getClassName('form', 'builder', 'field', 'list', 'add', 'container');

		var CSS_FIELD_MOVE_TARGET = A.getClassName('form', 'builder', 'field', 'move', 'target');

		var FormBuilderFieldList = A.Component.create(
			{
				ATTRS: {
					strings: {
						value: {
							addField: Liferay.Language.get('add-field'),
							pasteHere: Liferay.Language.get('paste-here')
						}
					}
				},

				EXTENDS: A.FormBuilderFieldList,

				NAME: 'liferay-ddl-form-builder-field-list',

				prototype: {
					TPL_ADD_FIELD: '<div class="' + CSS_FIELD_LIST_ADD_CONTAINER + '">' +
							'<button class="btn ' + CSS_FIELD_LIST_ADD_BUTTON + ' ' + CSS_FIELD_LIST_ADD_BUTTON_VISIBLE + '" type="button">' +
								'<span class="' + CSS_FIELD_LIST_ADD_BUTTON_ICON + ' ' + CSS_FIELD_LIST_ADD_BUTTON_PLUS_ICON + '">' +
									Liferay.Util.getLexiconIconTpl('plus') +
								'</span>' +
								'<label class="' + CSS_FIELD_LIST_ADD_BUTTON_LABEL + '"> {addField} </label>' +
							'</button>' +
						'</div>',

					initializer: function() {
						var instance = this;

						instance.after(instance._afterUiToggleDisableAddField, instance, '_uiToggleDisableAddField');
					},

					_afterUiToggleDisableAddField: function() {
						if (!this.get('enableAddFields')) {
							this.get('content').one('.' + CSS_FIELD_LIST_ADD_BUTTON).setAttribute('disabled', '');
						}
						else {
							this.get('content').one('.' + CSS_FIELD_LIST_ADD_BUTTON).removeAttribute('disabled');
						}
					},

					_onClickAddField: function(event) {
						var instance = this;

						if (!this.get('enableAddFields')) {
							event.stopPropagation();
						}

						FormBuilderFieldList.superclass._onClickAddField.apply(instance, arguments);
					},

					_uiSetField: function(container, field, index) {
						var instance = this;

						var fields = instance.get('fields');

						FormBuilderFieldList.superclass._uiSetField.apply(instance, arguments);

						if (index && index < fields.length) {
							var content = field.get('content');

							content.previous('.' + CSS_FIELD_MOVE_TARGET).addClass(CSS_BETWEEN_FIELDS);
							content.previous('.' + CSS_FIELD_LIST_ADD_CONTAINER).addClass(CSS_BETWEEN_FIELDS);
						}
					}
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