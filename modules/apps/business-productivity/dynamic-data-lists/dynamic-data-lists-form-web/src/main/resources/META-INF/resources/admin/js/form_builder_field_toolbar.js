AUI.add(
	'liferay-ddl-form-builder-field-toolbar',
	function(A) {
		var CSS_TOOLBAR = A.getClassName('form', 'builder', 'field', 'toolbar');

		var CSS_TOOLBAR_ITEM = A.getClassName('form', 'builder', 'field', 'toolbar', 'item');

		var CSS_TOOLBAR_ITEMS = A.getClassName('form', 'builder', 'field', 'toolbar', 'items');

		var CSS_TOOLBAR_TOGGLE = A.getClassName('form', 'builder', 'field', 'toolbar', 'toggle');

		var FormBuilderFieldToolbar = A.Component.create(
			{
				EXTENDS: A.FormBuilderFieldToolbar,

				NAME: 'liferay-ddl-form-builder-field-toolbar',

				prototype: {
					TPL_TOOLBAR: '<div class="' + CSS_TOOLBAR + '">' +
							'<button type="button" class="btn btn-default ' + CSS_TOOLBAR_TOGGLE + '">' +
								Liferay.Util.getLexiconIconTpl('ellipsis-v') +
							'</button>' +
							'<div class="btn-group ' + CSS_TOOLBAR_ITEMS + '"></div>' +
						'</div>',
					TPL_TOOLBAR_ITEM: '<button type="button" class="btn btn-default ' + CSS_TOOLBAR_ITEM + ' {buttonClass}" tabindex="9">{icon}</button>',

					_uiSetItems: function(items) {
						var instance = this;

						var itemsNode = instance._toolbar.one('.' + CSS_TOOLBAR_ITEMS);

						itemsNode.html(
							items.map(
								function(item) {
									return A.Lang.sub(
										instance.TPL_TOOLBAR_ITEM,
										{
											buttonClass: item.buttonClass || '',
											icon: Liferay.Util.getLexiconIconTpl(item.iconClass)
										}
									);
								}
							).join('')
						);
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderFieldToolbar = FormBuilderFieldToolbar;

		Liferay.DDL.FormBuilderFieldToolbar.ITEM_ADD_NESTED = {
			handler: 'addNestedField',
			iconClass: 'glyphicon glyphicon-plus'
		};

		Liferay.DDL.FormBuilderFieldToolbar.ITEM_EDIT = {
			handler: 'editField',
			iconClass: 'pencil'
		};

		Liferay.DDL.FormBuilderFieldToolbar.ITEM_MOVE = {
			buttonClass: 'layout-builder-move-cut-button layout-builder-move-cut-col-button',
			iconClass: 'move'
		};

		Liferay.DDL.FormBuilderFieldToolbar.ITEM_REMOVE = {
			handler: 'removeField',
			iconClass: 'trash'
		};

		Liferay.DDL.FormBuilderFieldToolbar.ITEM_CLOSE = {
			iconClass: 'times'
		};
	},
	'',
	{
		requires: ['aui-form-builder-field-toolbar']
	}
);