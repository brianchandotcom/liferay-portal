AUI.add(
	'liferay-ddl-form-builder-field-types-modal',
	function(A) {
		var Lang = A.Lang;

		var TPL_COLUMN = '<div class="col col-md-{size}"></div>';

		var TPL_ROW = '<div class="row"></div>';

		var FormBuilderFieldTypesModal = A.Component.create(
			{
				CSS_PREFIX: 'lfr-ddl-form-builder-field-types-modal',

				AUGMENTS: [Liferay.DDL.FormBuilderModalSupport],

				EXTENDS: A.FormBuilderFieldTypesModal,

				NAME: 'form-builder-field-types-modal',

				prototype: {
					bindUI: function() {
						var instance = this;

						FormBuilderFieldTypesModal.superclass.bindUI.apply(instance, arguments);
					},

					_createColumn: function(size) {
						var instance = this;

						return A.Node.create(
							Lang.sub(
								TPL_COLUMN,
								{
									size: size
								}
							)
						);
					},

					_createRow: function() {
						var instance = this;

						return A.Node.create(TPL_ROW);
					},

					_onClickFieldType: function(event) {
						var instance = this;

						event.preventDefault();

						FormBuilderFieldTypesModal.superclass._onClickFieldType.apply(instance, arguments);
					},

					_uiSetFieldTypes: function(fieldTypes) {
						var instance = this;

						var fieldTypesListNode = A.Node.create(instance.TPL_TYPES_LIST);

						fieldTypesListNode.empty();

						var length = fieldTypes.length;

						var rowNode;

						fieldTypes.forEach(
							function(fieldType, index) {
								var size = 4;

								if (index % 3 === 0) {
									rowNode = instance._createRow();

									fieldTypesListNode.append(rowNode);

									if (index === length - 1) {
										size = 12;
									}
								}

								if (length % 3 === 2 && index > length - 3) {
									size = 6;
								}

								var columnNode = instance._createColumn(size);

								fieldType.set('label', instance.get('strings').fieldTypes[fieldType.get('label')]);

								columnNode.append(fieldType.get('node'));

								rowNode.append(columnNode);
							}
						);

						instance.set('bodyContent', fieldTypesListNode);
					},

					_valueToolbars: function() {
						return {
							header: [
								{
									cssClass: 'close',
									discardDefaultButtonCssClasses: true,
									labelHTML: Liferay.Util.getLexiconIconTpl('times'),
									on: {
										click: A.bind(this._onFieldTypesModalCloseClick, this)
									}
								}
							]
						};
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderFieldTypesModal = FormBuilderFieldTypesModal;
	},
	'',
	{
		requires: ['aui-form-builder-field-types-modal', 'liferay-ddl-form-builder-modal-support']
	}
);