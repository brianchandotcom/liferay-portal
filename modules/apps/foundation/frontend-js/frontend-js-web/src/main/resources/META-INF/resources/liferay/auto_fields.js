AUI.add(
	'liferay-auto-fields',
	function(A) {
		var AObject = A.Object;
		var Lang = A.Lang;

		var CSS_ICON_LOADING = 'loading-animation';

		var CSS_VALIDATION_HELPER_CLASSES = [
			'error',
			'error-field',
			'has-error',
			'success',
			'success-field'
		];

		var TPL_ADD_BUTTON = '<button class="add-row btn btn-default btn-icon-only toolbar-first toolbar-item" title="" type="button">' +
				'<span class="btn-icon icon icon-plus"></span>' +
			'</button>';

		var TPL_DELETE_BUTTON = '<button class="btn btn-default btn-icon-only delete-row toolbar-item toolbar-last" title="" type="button">' +
				'<span class="btn-icon icon icon-minus"></span>' +
			'</button>';

		var TPL_AUTOROW_CONTROLS = '<span class="lfr-autorow-controls toolbar toolbar-horizontal">' +
				'<span class="toolbar-content">' +
					TPL_ADD_BUTTON +
					TPL_DELETE_BUTTON +
				'</span>' +
			'</span>';

		var TPL_LOADING = '<div class="' + CSS_ICON_LOADING + '"></div>';

		/**
		 * OPTIONS
		 *
		 * Required
		 * container {string|object}: A selector that contains the rows you wish to duplicate.
		 * baseRows {string|object}: A selector that defines which fields are duplicated.
		 *
		 * Optional
		 * fieldIndexes {string}: The name of the POST parameter that will contain a list of the order for the fields.
		 * sortable{boolean}: Whether or not the rows should be sortable
		 * sortableHandle{string}: A selector that defines a handle for the sortables
		 *
		 */

		var AutoFields = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'autofields',

				prototype: {

					initializer: function(config) {
						var instance = this;

						instance.config = config;
					},

					addRow: function(node) {
						var instance = this;

						var clone = instance._createClone(node);

						clone.resetId();

						node.placeAfter(clone);

						var input = clone.one('input[type=text], input[type=password], textarea');

						if (input) {
							Liferay.Util.focusFormField(input);
						}

						instance.fire(
							'clone',
							{
								guid: instance._guid,
								originalRow: node,
								row: clone
							}
						);

						if (instance._sortable) {
							instance._addHandleClass(clone);
						}
					},

					deleteRow: function(node) {
						var instance = this;

						var visibleRows = instance._contentBox.all('.lfr-form-row:visible').size();

						var deleteRow = visibleRows > 1;

						if (visibleRows == 1) {
							instance.addRow(node);

							deleteRow = true;
						}

						if (deleteRow) {
							var form = node.ancestor('form');

							node.hide();

							CSS_VALIDATION_HELPER_CLASSES.forEach(
								function(item, index) {
									var disabledClass = item + '-disabled';

									node.all('.' + item).replaceClass(item, disabledClass);
								}
							);

							var rules;

							var deletedRules = {};

							var formValidator = instance._getFormValidator(node);

							if (formValidator) {
								var errors = formValidator.errors;

								rules = formValidator.get('rules');

								node.all('input, select, textarea').each(
									function(item, index) {
										var name = item.attr('name') || item.attr('id');

										if (rules && rules[name]) {
											deletedRules[name] = rules[name];

											delete rules[name];
										}

										if (errors && errors[name]) {
											delete errors[name];
										}
									}
								);
							}

							instance._undoManager.add(
								function(stateData) {
									if (rules) {
										AObject.each(
											deletedRules,
											function(item, index) {
												rules[index] = item;
											}
										);
									}

									CSS_VALIDATION_HELPER_CLASSES.forEach(
										function(item, index) {
											var disabledClass = item + '-disabled';

											node.all('.' + disabledClass).replaceClass(disabledClass, item);
										}
									);

									node.show();

									if (form) {
										form.fire('autofields:update');
									}
								}
							);

							instance.fire(
								'delete',
								{
									deletedRow: node,
									guid: instance._guid
								}
							);

							if (form) {
								form.fire('autofields:update');
							}
						}
					},

					render: function() {
						var instance = this;

						var baseContainer = A.Node.create('<div class="lfr-form-row"><div class="row-fields"></div></div>');

						var config = instance.config;
						var contentBox = A.one(config.contentBox);

						var baseRows = contentBox.all(config.baseRows || '.lfr-form-row');

						instance._contentBox = contentBox;
						instance._guid = baseRows.size();

						instance.url = config.url;

						instance._undoManager = new Liferay.UndoManager().render(contentBox);

						if (config.fieldIndexes) {
							instance._fieldIndexes = A.all('[name=' + config.fieldIndexes + ']');

							if (!instance._fieldIndexes.size()) {
								instance._fieldIndexes = A.Node.create('<input name="' + config.fieldIndexes + '" type="hidden" />');

								contentBox.append(instance._fieldIndexes);
							}
						}
						else {
							instance._fieldIndexes = A.all([]);
						}

						contentBox.delegate(
							'click',
							function(event) {
								var link = event.currentTarget;

								var currentRow = link.ancestor('.lfr-form-row');

								if (link.hasClass('add-row')) {
									instance.addRow(currentRow);
								}
								else if (link.hasClass('delete-row')) {
									link.fire('change');

									instance.deleteRow(currentRow);
								}
							},
							'.lfr-autorow-controls .btn'
						);

						baseRows.each(
							function(item, index) {
								var firstChild;
								var formRow;

								if (item.hasClass('lfr-form-row')) {
									formRow = item;
								}
								else {
									formRow = baseContainer.clone();
									firstChild = formRow.one('> div');
									firstChild.append(item);
								}

								formRow.append(TPL_AUTOROW_CONTROLS);

								if (!contentBox.contains(formRow)) {
									contentBox.append(formRow);
								}

								if (index === 0) {
									instance._rowTemplate = formRow.clone();
									instance._clearForm(instance._rowTemplate);
								}
							}
						);

						if (config.sortable) {
							instance._makeSortable(config.sortableHandle);
						}

						Liferay.on(
							'saveAutoFields',
							function(event) {
								instance.save(event.form);
							}
						);

						instance._undoManager.on(
							'clearList',
							function(event) {
								contentBox.all('.lfr-form-row').each(instance._clearHiddenRows, instance);
							}
						);

						instance._attachSubmitListener();

						return instance;
					},

					reset: function() {
						var instance = this;

						var contentBox = instance._contentBox;

						contentBox.all('.lfr-form-row').each(
							function(item, index) {
								instance.deleteRow(item);
							}
						);

						instance._undoManager.clear();
					},

					save: function(form) {
						var instance = this;

						var contentBox = form || instance._contentBox;

						contentBox.all('.lfr-form-row').each(instance._clearHiddenRows, instance);

						var fieldOrder = instance.serialize();

						instance._fieldIndexes.val(fieldOrder);
					},

					serialize: function(filter) {
						var instance = this;

						var visibleRows = instance._contentBox.all('.lfr-form-row').each(instance._clearHiddenRows, instance);

						var serializedData = [];

						if (filter) {
							serializedData = filter.call(instance, visibleRows) || [];
						}
						else {
							visibleRows.each(
								function(item, index) {
									var formField = item.one('input, textarea, select');

									var fieldId = formField.attr('id');

									if (!fieldId) {
										fieldId = formField.attr('name');
									}

									fieldId = (fieldId || '').match(/([0-9]+)$/);

									if (fieldId && fieldId[0]) {
										serializedData.push(fieldId[0]);
									}
								}
							);
						}

						return serializedData.join();
					},

					_addHandleClass: function(node) {
						var instance = this;

						var sortableHandle = instance.config.sortableHandle;

						if (sortableHandle) {
							node.all(sortableHandle).addClass('handle-sort-vertical');
						}
					},

					_attachSubmitListener: function() {
						var instance = this;

						Liferay.on('submitForm', A.bind('fire', Liferay, 'saveAutoFields'));

						AutoFields.prototype._attachSubmitListener = Lang.emptyFn;
					},

					_clearForm: function(node) {
						node.all('input, select, textarea').each(
							function(item, index) {
								var tag = item.get('nodeName').toLowerCase();

								var type = item.getAttribute('type');

								if (type == 'text' || type == 'password' || tag == 'textarea') {
									item.val('');
								}
								else if (type == 'checkbox' || type == 'radio') {
									item.attr('checked', false);
								}
								else if (tag == 'select') {
									var selectedIndex = 0;

									if (item.getAttribute('showEmptyOption')) {
										selectedIndex = -1;
									}

									item.attr('selectedIndex', selectedIndex);
								}
							}
						);

						CSS_VALIDATION_HELPER_CLASSES.forEach(
							function(item, index) {
								node.all('.' + item).removeClass(item);
							}
						);
					},

					_clearHiddenRows: function(item, index) {
						var instance = this;

						if (instance._isHiddenRow(item)) {
							item.remove(true);
						}
					},

					_clearInputsLocalized: function(node) {
						node.all('.language-value').attr('placeholder', '');
						node.all('.lfr-input-localized-state').removeClass('lfr-input-localized-state-error');
						node.all('.palette-item').removeClass('palette-item-selected');
						node.all('.lfr-input-localized-default').addClass('palette-item-selected');
					},

					_createClone: function(node) {
						var instance = this;

						var currentRow = node;

						var clone = currentRow.clone();

						var guid = ++instance._guid;

						var formValidator = instance._getFormValidator(node);

						var inputsLocalized = node.all('.language-value');

						var clonedRow;

						if (instance.url) {
							clonedRow = instance._createCloneFromURL(clone, guid);
						}
						else {
							clonedRow = instance._createCloneFromMarkup(clone, guid, formValidator, inputsLocalized);
						}

						return clonedRow;
					},

					_createCloneFromMarkup: function(node, guid, formValidator, inputsLocalized) {
						var instance = this;

						var fieldStrings;

						var rules;

						if (formValidator) {
							fieldStrings = formValidator.get('fieldStrings');

							rules = formValidator.get('rules');
						}

						node.all('input, select, textarea, span, div').each(
							function(item, index) {
								var inputNodeName = item.attr('nodeName');
								var inputType = item.attr('type');

								var oldName = item.attr('name') || item.attr('id');

								var newName = oldName.replace(/([0-9]+)([_A-Za-z]*)$/, guid + '$2');

								if (inputType == 'radio') {
									oldName = item.attr('id');

									item.attr('checked', '');
									item.attr('value', guid);
									item.attr('id', newName);
								}
								else if (inputNodeName == 'button' || inputNodeName == 'div' || inputNodeName == 'span') {
									if (oldName) {
										item.attr('id', newName);
									}
								}
								else {
									item.attr('name', newName);
									item.attr('id', newName);
								}

								if (fieldStrings && fieldStrings[oldName]) {
									fieldStrings[newName] = fieldStrings[oldName];
								}

								if (rules && rules[oldName]) {
									rules[newName] = rules[oldName];
								}

								if (item.attr('aria-describedby')) {
									item.attr('aria-describedby', newName + '_desc');
								}

								node.all('label[for=' + oldName + ']').attr('for', newName);
							}
						);

						instance._clearInputsLocalized(node);

						inputsLocalized.each(
							function(item, index) {
								var inputId = item.attr('id');

								var inputLocalized;

								if (inputId) {
									inputLocalized = Liferay.InputLocalized._registered[inputId];

									if (inputLocalized) {
										Liferay.component(inputId).render();
									}

									inputLocalized = Liferay.InputLocalized._instances[inputId];
								}

								instance._registerInputLocalized(inputLocalized, guid);
							}
						);

						node.all('.form-validator-stack').remove();
						node.all('.help-inline').remove();

						instance._clearForm(node);

						node.all('input[type=hidden]').val('');

						return node;
					},

					_createCloneFromURL: function(node, guid) {
						var instance = this;

						var contentBox = node.one('> div');

						contentBox.html(TPL_LOADING);

						contentBox.plug(A.Plugin.ParseContent);

						A.io.request(
							instance.url,
							{
								data: instance.ns(
									{
										index: guid
									}
								),
								on: {
									success: function(event, id, obj) {
										var responseData = this.get('responseData');

										contentBox.setContent(responseData);
									}
								}
							}
						);

						return node;
					},

					_getFormValidator: function(node) {
						var instance = this;

						var formValidator;

						var form = node.ancestor('form');

						if (form) {
							var formId = form.attr('id');

							formValidator = Liferay.Form.get(formId).formValidator;
						}

						return formValidator;
					},

					_isHiddenRow: function(row) {
						var instance = this;

						return row.hasClass(row._hideClass || 'hide');
					},

					_makeSortable: function(sortableHandle) {
						var instance = this;

						var rows = instance._contentBox.all('.lfr-form-row');

						instance._addHandleClass(rows);

						instance._sortable = new A.Sortable(
							{
								container: instance._contentBox,
								handles: [sortableHandle],
								nodes: '.lfr-form-row',
								opacity: 0
							}
						);

						instance._undoManager.on(
							'clearList',
							function(event) {
								rows.all('.lfr-form-row').each(
									function(item, index) {
										if (instance._isHiddenRow(item)) {
											A.DD.DDM.getDrag(item).destroy();
										}
									}
								);
							}
						);
					},

					_registerInputLocalized: function(inputLocalized, guid) {
						var inputLocalizedId = inputLocalized.get('id').replace(/([0-9]+)$/, guid);

						var inputLocalizedNamespaceId = inputLocalized.get('namespace') + inputLocalizedId;

						Liferay.InputLocalized.register(
							inputLocalizedNamespaceId,
							{
								boundingBox: '#' + inputLocalizedNamespaceId + 'BoundingBox',
								columns: inputLocalized.get('columns'),
								contentBox: '#' + inputLocalizedNamespaceId + 'ContentBox',
								defaultLanguageId: inputLocalized.get('defaultLanguageId'),
								fieldPrefix: inputLocalized.get('fieldPrefix'),
								fieldPrefixSeparator: inputLocalized.get('fieldPrefixSeparator'),
								id: inputLocalizedId,
								inputPlaceholder: '#' + inputLocalizedNamespaceId,
								items: inputLocalized.get('items'),
								itemsError: inputLocalized.get('itemsError'),
								lazy: true,
								name: inputLocalizedId,
								namespace: inputLocalized.get('namespace'),
								toggleSelection: inputLocalized.get('toggleSelection'),
								translatedLanguages: inputLocalized.get('translatedLanguages')
							}
						);
					},

					_guid: 0
				}
			}
		);

		Liferay.AutoFields = AutoFields;
	},
	'',
	{
		requires: ['aui-base', 'aui-data-set-deprecated', 'aui-io-request', 'aui-parse-content', 'base', 'liferay-form', 'liferay-portlet-base', 'liferay-undo-manager', 'sortable']
	}
);