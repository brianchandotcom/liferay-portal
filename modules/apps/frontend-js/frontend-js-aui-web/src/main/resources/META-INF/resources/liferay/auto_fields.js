/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-auto-fields',
	A => {
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

		var TPL_ADD_BUTTON =
			'<button class="add-row btn btn-icon-only btn-monospaced btn-secondary toolbar-first toolbar-item" title="" type="button">' +
			Liferay.Util.getLexiconIconTpl('plus') +
			'</button>';

		var TPL_DELETE_BUTTON =
			'<button class="btn btn-icon-only btn-monospaced btn-secondary delete-row toolbar-item toolbar-last" title="" type="button">' +
			Liferay.Util.getLexiconIconTpl('hr') +
			'</button>';

		var TPL_AUTOROW_CONTROLS =
			'<span class="lfr-autorow-controls toolbar toolbar-horizontal">' +
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

		var AutoFields = A.Component.create({
			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'autofields',

			prototype: {
				_addHandleClass(node) {
					var instance = this;

					var sortableHandle = instance.config.sortableHandle;

					if (sortableHandle) {
						node.all(sortableHandle).addClass(
							'handle-sort-vertical'
						);
					}
				},

				_attachSubmitListener() {
					Liferay.on(
						'submitForm',
						A.bind('fire', Liferay, 'saveAutoFields')
					);

					AutoFields.prototype._attachSubmitListener = Lang.emptyFn;
				},

				_clearForm(node) {
					node.all('input, select, textarea').each(item => {
						var tag = item.get('nodeName').toLowerCase();

						var type = item.getAttribute('type');

						if (
							type == 'text' ||
							type == 'password' ||
							tag == 'textarea'
						) {
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
					});

					CSS_VALIDATION_HELPER_CLASSES.forEach(item => {
						node.all('.' + item).removeClass(item);
					});
				},

				_clearHiddenRows(item) {
					var instance = this;

					if (instance._isHiddenRow(item)) {
						item.remove(true);
					}
				},

				_clearInputsLocalized(node) {
					node.all('.language-value').attr('placeholder', '');
					node.all('.lfr-input-localized-state').removeClass(
						'lfr-input-localized-state-error'
					);
					node.all('.palette-item')
						.removeClass('palette-item-selected')
						.removeClass('lfr-input-localized');
					node.all('.lfr-input-localized-default').addClass(
						'palette-item-selected'
					);
				},

				_createClone(node) {
					var instance = this;

					var currentRow = node;

					var clone = currentRow.clone();

					var guid = instance._guid++;

					var formValidator = instance._getFormValidator(node);

					var inputsLocalized = node.all('.language-value');

					var clonedRow;

					if (instance.url) {
						clonedRow = instance._createCloneFromURL(clone, guid);
					}
					else {
						clonedRow = instance._createCloneFromMarkup(
							clone,
							guid,
							formValidator,
							inputsLocalized
						);
					}

					return clonedRow;
				},

				_createCloneFromMarkup(
					node,
					guid,
					formValidator,
					inputsLocalized
				) {
					var instance = this;

					var fieldStrings;

					var rules;

					if (formValidator) {
						fieldStrings = formValidator.get('fieldStrings');

						rules = formValidator.get('rules');
					}

					node.all('input, select, textarea, span, div').each(
						item => {
							var inputNodeName = item.attr('nodeName');
							var inputType = item.attr('type');

							var oldName = item.attr('name') || item.attr('id');

							var newName = oldName.replace(
								/([0-9]+)([_A-Za-z]*)$/,
								guid + '$2'
							);

							if (inputType == 'radio') {
								oldName = item.attr('id');

								item.attr('checked', '');
								item.attr('value', guid);
								item.attr('id', newName);
							}
							else if (
								inputNodeName == 'button' ||
								inputNodeName == 'div' ||
								inputNodeName == 'span'
							) {
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
								item.attr(
									'aria-describedby',
									newName + '_desc'
								);
							}

							node.all('label[for=' + oldName + ']').attr(
								'for',
								newName
							);
						}
					);

					instance._clearInputsLocalized(node);

					inputsLocalized.each(item => {
						var inputId = item.attr('id');

						var inputLocalized;

						if (inputId) {
							inputLocalized =
								Liferay.InputLocalized._registered[inputId];

							if (inputLocalized) {
								Liferay.component(inputId).render();
							}

							inputLocalized =
								Liferay.InputLocalized._instances[inputId];
						}

						instance._registerInputLocalized(inputLocalized, guid);
					});

					node.all('.form-validator-stack').remove();
					node.all('.help-inline').remove();

					instance._clearForm(node);

					node.all('input[type=hidden]').val('');

					return node;
				},

				_createCloneFromURL(node, guid) {
					var instance = this;

					var contentBox = node.one('> div');

					contentBox.html(TPL_LOADING);

					contentBox.plug(A.Plugin.ParseContent);

					var data = {
						index: guid
					};

					var namespace = instance.urlNamespace
						? instance.urlNamespace
						: instance.namespace;

					var namespacedData = Liferay.Util.ns(namespace, data);

					Liferay.Util.fetch(instance.url, {
						body: Liferay.Util.objectToFormData(namespacedData),
						method: 'POST'
					})
						.then(response => response.text())
						.then(response => contentBox.setContent(response));

					return node;
				},

				_getFormValidator(node) {
					var formValidator;

					var form = node.ancestor('form');

					if (form) {
						var formId = form.attr('id');

						formValidator = Liferay.Form.get(formId).formValidator;
					}

					return formValidator;
				},

				_guid: 0,

				_isHiddenRow(row) {
					return row.hasClass(row._hideClass || 'hide');
				},

				_makeSortable(sortableHandle) {
					var instance = this;

					var rows = instance._contentBox.all('.lfr-form-row');

					instance._addHandleClass(rows);

					instance._sortable = new A.Sortable({
						container: instance._contentBox,
						handles: [sortableHandle],
						nodes: '.lfr-form-row',
						opacity: 0
					});

					instance._undoManager.on('clearList', () => {
						rows.all('.lfr-form-row').each(item => {
							if (instance._isHiddenRow(item)) {
								A.DD.DDM.getDrag(item).destroy();
							}
						});
					});
				},

				_registerInputLocalized(inputLocalized, guid) {
					var inputLocalizedId = inputLocalized
						.get('id')
						.replace(/([0-9]+)$/, guid);

					var inputLocalizedNamespaceId =
						inputLocalized.get('namespace') + inputLocalizedId;

					Liferay.InputLocalized.register(inputLocalizedNamespaceId, {
						boundingBox:
							'#' + inputLocalizedNamespaceId + 'BoundingBox',
						columns: inputLocalized.get('columns'),
						contentBox:
							'#' + inputLocalizedNamespaceId + 'ContentBox',
						defaultLanguageId: inputLocalized.get(
							'defaultLanguageId'
						),
						fieldPrefix: inputLocalized.get('fieldPrefix'),
						fieldPrefixSeparator: inputLocalized.get(
							'fieldPrefixSeparator'
						),
						id: inputLocalizedId,
						inputPlaceholder: '#' + inputLocalizedNamespaceId,
						items: inputLocalized.get('items'),
						itemsError: inputLocalized.get('itemsError'),
						lazy: true,
						name: inputLocalizedId,
						namespace: inputLocalized.get('namespace'),
						toggleSelection: inputLocalized.get('toggleSelection'),
						translatedLanguages: inputLocalized.get(
							'translatedLanguages'
						)
					});
				},

				_updateContentButtons() {
					var instance = this;

					var minimumRows = instance.minimumRows;

					if (minimumRows) {
						var deleteRowButtons = instance._contentBox.all(
							'.lfr-form-row:not(.hide) .delete-row'
						);

						Liferay.Util.toggleDisabled(
							deleteRowButtons,
							deleteRowButtons.size() <= minimumRows
						);
					}
				},

				addRow(node) {
					var instance = this;

					var clone = instance._createClone(node);

					clone.resetId();

					node.placeAfter(clone);

					var input = clone.one(
						'input[type=text], input[type=password], textarea'
					);

					if (input) {
						Liferay.Util.focusFormField(input);
					}

					instance._updateContentButtons();

					instance.fire('clone', {
						guid: instance._guid,
						originalRow: node,
						row: clone
					});

					if (instance._sortable) {
						instance._addHandleClass(clone);
					}
				},

				deleteRow(node) {
					var instance = this;

					var contentBox = instance._contentBox;

					var visibleRows = contentBox.all('.lfr-form-row:visible');

					var visibleRowsSize = visibleRows.size();

					var deleteRow = visibleRowsSize > 1;

					if (visibleRowsSize === 1) {
						instance.addRow(node);

						deleteRow = true;
					}

					if (deleteRow) {
						var form = node.ancestor('form');

						node.hide();

						CSS_VALIDATION_HELPER_CLASSES.forEach(item => {
							var disabledClass = item + '-disabled';

							node.all('.' + item).replaceClass(
								item,
								disabledClass
							);
						});

						var rules;

						var deletedRules = {};

						var formValidator = instance._getFormValidator(node);

						if (formValidator) {
							var errors = formValidator.errors;

							rules = formValidator.get('rules');

							node.all('input, select, textarea').each(item => {
								var name = item.attr('name') || item.attr('id');

								if (rules && rules[name]) {
									deletedRules[name] = rules[name];

									delete rules[name];
								}

								if (errors && errors[name]) {
									delete errors[name];
								}
							});
						}

						instance._undoManager.add(() => {
							if (rules) {
								AObject.each(deletedRules, (item, index) => {
									rules[index] = item;
								});
							}

							CSS_VALIDATION_HELPER_CLASSES.forEach(item => {
								var disabledClass = item + '-disabled';

								node.all('.' + disabledClass).replaceClass(
									disabledClass,
									item
								);
							});

							node.show();

							instance._updateContentButtons();

							if (form) {
								form.fire('autofields:update');
							}
						});

						instance.fire('delete', {
							deletedRow: node,
							guid: instance._guid
						});

						if (form) {
							form.fire('autofields:update');
						}
					}

					instance._updateContentButtons();
				},

				initializer(config) {
					var instance = this;

					instance.config = config;
				},

				render() {
					var instance = this;

					var baseContainer = A.Node.create(
						'<div class="lfr-form-row"><div class="row-fields"></div></div>'
					);

					var config = instance.config;
					var contentBox = A.one(config.contentBox);

					var baseRows = contentBox.all(
						config.baseRows || '.lfr-form-row'
					);

					instance._contentBox = contentBox;
					instance._guid = baseRows.size();

					instance.minimumRows = config.minimumRows;
					instance.namespace = config.namespace;
					instance.url = config.url;
					instance.urlNamespace = config.urlNamespace;

					instance._undoManager = new Liferay.UndoManager().render(
						contentBox
					);

					if (config.fieldIndexes) {
						instance._fieldIndexes = A.all(
							'[name=' + config.fieldIndexes + ']'
						);

						if (!instance._fieldIndexes.size()) {
							instance._fieldIndexes = A.Node.create(
								'<input name="' +
									config.fieldIndexes +
									'" type="hidden" />'
							);

							contentBox.append(instance._fieldIndexes);
						}
					}
					else {
						instance._fieldIndexes = A.all([]);
					}

					contentBox.delegate(
						'click',
						event => {
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
						'.lfr-autorow-controls .btn:not(:disabled)'
					);

					baseRows.each((item, index) => {
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
					});

					instance._updateContentButtons();

					if (config.sortable) {
						instance._makeSortable(config.sortableHandle);
					}

					Liferay.on('saveAutoFields', event => {
						instance.save(event.form);
					});

					instance._undoManager.on('clearList', () => {
						contentBox
							.all('.lfr-form-row')
							.each(instance._clearHiddenRows, instance);
					});

					instance._attachSubmitListener();

					return instance;
				},

				reset() {
					var instance = this;

					var contentBox = instance._contentBox;

					contentBox.all('.lfr-form-row').each(item => {
						instance.deleteRow(item);
					});

					instance._undoManager.clear();
				},

				save(form) {
					var instance = this;

					var contentBox = form || instance._contentBox;

					contentBox
						.all('.lfr-form-row')
						.each(instance._clearHiddenRows, instance);

					var fieldOrder = instance.serialize();

					instance._fieldIndexes.val(fieldOrder);
				},

				serialize(filter) {
					var instance = this;

					var visibleRows = instance._contentBox
						.all('.lfr-form-row')
						.each(instance._clearHiddenRows, instance);

					var serializedData = [];

					if (filter) {
						serializedData =
							filter.call(instance, visibleRows) || [];
					}
					else {
						visibleRows.each(item => {
							var formField = item.one('input, textarea, select');

							var fieldId = formField.attr('id');

							if (!fieldId) {
								fieldId = formField.attr('name');
							}

							fieldId = (fieldId || '').match(/([0-9]+)$/);

							if (fieldId && fieldId[0]) {
								serializedData.push(fieldId[0]);
							}
						});
					}

					return serializedData.join();
				}
			}
		});

		Liferay.AutoFields = AutoFields;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-data-set-deprecated',
			'aui-parse-content',
			'base',
			'liferay-form',
			'liferay-portlet-base',
			'liferay-undo-manager',
			'sortable'
		]
	}
);
