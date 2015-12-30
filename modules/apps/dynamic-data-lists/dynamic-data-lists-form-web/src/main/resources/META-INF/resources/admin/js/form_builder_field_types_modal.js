AUI.add(
	'liferay-ddl-form-builder-field-types-modal',
	function(A) {
		var Lang = A.Lang;

		var FormBuilderUtil = Liferay.DDL.FormBuilderUtil;

		var TPL_COLUMN = '<div class="col col-md-{size}"></div>';

		var TPL_ROW = '<div class="row"></div>';

		var FormBuilderFieldTypesModal = A.Component.create(
			{
				ATTRS: {
					centered: {
						valueFn: '_valueCentered'
					},

					portletNamespace: {
					},

					zIndex: {
						value: Liferay.zIndex.OVERLAY
					}
				},

				CSS_PREFIX: 'lfr-ddl-form-builder-field-types-modal',

				EXTENDS: A.FormBuilderFieldTypesModal,

				NAME: 'form-builder-field-types-modal',

				prototype: {
					bindUI: function() {
						var instance = this;

						FormBuilderFieldTypesModal.superclass.bindUI.apply(instance, arguments);

						instance.on('xyChange', instance._onModalXYChange);
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

					_onModalXYChange: function(event) {
						event.newVal = FormBuilderUtil.normalizeModalXY(event.newVal);
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

								columnNode.append(fieldType.get('node'));

								rowNode.append(columnNode);
							}
						);

						instance.set('bodyContent', fieldTypesListNode);
					},

					_valueCentered: function() {
						var instance = this;

						return A.one('#p_p_id' + instance.get('portletNamespace'));
					},

					_valueToolbars: function() {
						return {
							header: [
								{
									cssClass: 'close',
									discardDefaultButtonCssClasses: true,
									labelHTML: '<svg class="lexicon-icon"><use xlink:href="/o/frontend-theme-admin-web/admin/images/lexicon/icons.svg#times" /></svg>',
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
		requires: ['aui-form-builder-field-types-modal', 'liferay-ddl-form-builder-util']
	}
);