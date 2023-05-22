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
	'liferay-portlet-dynamic-data-lists',
	(A) => {
		const AArray = A.Array;

		const DateMath = A.DataType.DateMath;

		const FormBuilder = Liferay.FormBuilder;

		const Lang = A.Lang;

		const EMPTY_FN = A.Lang.emptyFn;

		const STR_EMPTY = '';

		const isArray = Array.isArray;
		const isNumber = Lang.isNumber;

		const SpreadSheet = A.Component.create({
			ATTRS: {
				addRecordURL: {
					validator: Lang.isString,
					value: STR_EMPTY,
				},

				portletNamespace: {
					validator: Lang.isString,
					value: STR_EMPTY,
				},

				recordsetId: {
					validator: isNumber,
					value: 0,
				},

				structure: {
					validator: isArray,
					value: [],
				},

				updateRecordURL: {
					validator: Lang.isString,
					value: STR_EMPTY,
				},
			},

			CSS_PREFIX: 'table',

			DATATYPE_VALIDATOR: {
				double: 'number',
				integer: 'digits',
				long: 'digits',
			},

			EXTENDS: A.DataTable,

			NAME: A.DataTable.Base.NAME,

			TYPE_EDITOR: {
				'checkbox': A.CheckboxCellEditor,
				'ddm-color':
					FormBuilder.CUSTOM_CELL_EDITORS['color-cell-editor'],
				'ddm-date': A.DateCellEditor,
				'ddm-decimal': A.TextCellEditor,
				'ddm-documentlibrary':
					FormBuilder.CUSTOM_CELL_EDITORS[
						'document-library-file-entry-cell-editor'
					],
				'ddm-integer': A.TextCellEditor,
				'ddm-link-to-page':
					FormBuilder.CUSTOM_CELL_EDITORS['link-to-page-cell-editor'],
				'ddm-number': A.TextCellEditor,
				'radio': A.RadioCellEditor,
				'select': A.DropDownCellEditor,
				'text': A.TextCellEditor,
				'textarea': A.TextAreaCellEditor,
			},

			addRecord(
				addRecordURL,
				callback,
				ddmFormValues,
				displayIndex,
				portletNamespace,
				recordsetId
			) {
				const instance = this;

				callback = (callback && A.bind(callback, instance)) || EMPTY_FN;

				// eslint-disable-next-line @liferay/aui/no-io
				A.io.request(addRecordURL, {
					data: Liferay.Util.ns(portletNamespace, {
						ddmFormValues: JSON.stringify(ddmFormValues),
						displayIndex,
						groupId: themeDisplay.getScopeGroupId(),
						recordSetId: recordsetId,
						serviceContext: JSON.stringify({
							scopeGroupId: themeDisplay.getScopeGroupId(),
							userId: themeDisplay.getUserId(),
							workflowAction: Liferay.Workflow.ACTION_PUBLISH,
						}),
					}),
					dataType: 'JSON',
					method: 'POST',
					on: {
						success() {
							callback();
						},
					},
				});
			},

			buildDataTableColumns(columns, locale, structure, editable) {
				const instance = this;

				columns.forEach((item) => {
					const dataType = item.dataType;
					let label = item.label;
					const name = item.name;
					const type = item.type;

					item.key = name;

					const EditorClass =
						instance.TYPE_EDITOR[type] || A.TextCellEditor;

					const config = {
						elementName: name,
						strings: {
							cancel: Liferay.Language.get('cancel'),
							edit: Liferay.Language.get('edit'),
							save: Liferay.Language.get('save'),
						},
						validator: {
							rules: {},
						},
					};

					const required = item.required;

					let structureField;

					if (required) {
						label += ' (' + Liferay.Language.get('required') + ')';
					}

					label = A.Escape.html(label);

					item.label = label;

					if (type === 'checkbox') {
						config.options = {
							true: Liferay.Language.get('true'),
						};

						config.inputFormatter = function (value) {
							if (Array.isArray(value) && !!value.length) {
								value = value[0];
							}

							let checkedValue = 'false';

							if (value === 'true') {
								checkedValue = value;
							}

							return checkedValue;
						};

						item.formatter = function (object) {
							const data = object.data;

							let value = data[name];

							if (value === 'true') {
								value = Liferay.Language.get('true');
							}
							else if (value === 'false') {
								value = Liferay.Language.get('false');
							}

							return value;
						};
					}
					else if (type === 'ddm-date') {
						config.inputFormatter = function (val) {
							return val.map((item) => {
								return A.DataType.Date.format(item);
							});
						};

						config.outputFormatter = function (val) {
							return val.map((item) => {
								let date;

								if (item !== STR_EMPTY) {
									date = A.DataType.Date.parse(item);
								}
								else {
									date = new Date();
								}

								date = DateMath.add(
									date,
									DateMath.MINUTES,
									date.getTimezoneOffset()
								);

								return date;
							});
						};

						item.formatter = function (object) {
							const data = object.data;

							let value = data[name];

							if (isArray(value)) {
								value = value[0];
							}

							return value;
						};
					}
					else if (
						type === 'ddm-decimal' ||
						type === 'ddm-integer' ||
						type === 'ddm-number'
					) {
						config.outputFormatter = function (value) {
							const number = A.DataType.Number.parse(value);

							let numberValue = STR_EMPTY;

							if (isNumber(number)) {
								numberValue = number;
							}

							return numberValue;
						};

						item.formatter = function (object) {
							const data = object.data;

							let value = A.DataType.Number.parse(data[name]);

							if (!isNumber(value)) {
								value = STR_EMPTY;
							}

							return value;
						};
					}
					else if (type === 'ddm-documentlibrary') {
						item.formatter = function (object) {
							const data = object.data;

							let label = STR_EMPTY;
							const value = data[name];

							if (value !== STR_EMPTY) {
								const fileData = FormBuilder.Util.parseJSON(
									value
								);

								if (fileData.title) {
									label = fileData.title;
								}
							}

							return label;
						};
					}
					else if (type === 'ddm-link-to-page') {
						item.formatter = function (object) {
							const data = object.data;

							let label = STR_EMPTY;
							const value = data[name];

							if (value !== STR_EMPTY) {
								const linkToPageData = FormBuilder.Util.parseJSON(
									value
								);

								if (linkToPageData.name) {
									label = linkToPageData.name;
								}
							}

							return label;
						};
					}
					else if (type === 'radio') {
						structureField = instance.findStructureFieldByAttribute(
							structure,
							'name',
							name
						);

						config.multiple = false;
						config.options = instance.getCellEditorOptions(
							structureField.options,
							locale
						);
					}
					else if (type === 'select') {
						structureField = instance.findStructureFieldByAttribute(
							structure,
							'name',
							name
						);

						const multiple = A.DataType.Boolean.parse(
							structureField.multiple
						);
						const options = instance.getCellEditorOptions(
							structureField.options,
							locale
						);

						item.formatter = function (object) {
							const data = object.data;

							const label = [];
							const value = data[name];

							if (isArray(value)) {
								value.forEach((item1) => {
									label.push(options[item1]);
								});
							}

							return label.join(', ');
						};

						config.inputFormatter = AArray;
						config.multiple = multiple;
						config.options = options;
					}
					else if (type === 'textarea') {
						item.allowHTML = true;

						item.formatter = function (object) {
							const data = object.data;

							const value = data[name];

							if (!value) {
								return value;
							}

							return value.split('\n').join('<br>');
						};
					}

					const validatorRuleName =
						instance.DATATYPE_VALIDATOR[dataType];

					const validatorRules = config.validator.rules;

					validatorRules[name] = A.mix(
						{
							required,
						},
						validatorRules[name]
					);

					if (validatorRuleName) {
						validatorRules[name][validatorRuleName] = true;
					}

					if (editable && item.editable) {
						item.editor = new EditorClass(config);
					}
				});

				return columns;
			},

			buildEmptyRecords(num, keys) {
				const instance = this;

				const emptyRows = [];

				for (let i = 0; i < num; i++) {
					emptyRows.push(instance.getRecordModel(keys));
				}

				return emptyRows;
			},

			findStructureFieldByAttribute(
				fieldsArray,
				attributeName,
				attributeValue
			) {
				const instance = this;

				let structureField;

				AArray.some(fieldsArray, (item) => {
					const nestedFieldsArray = item.fields;

					if (item[attributeName] === attributeValue) {
						structureField = item;
					}
					else if (nestedFieldsArray) {
						structureField = instance.findStructureFieldByAttribute(
							nestedFieldsArray,
							attributeName,
							attributeValue
						);
					}

					return structureField !== undefined;
				});

				return structureField;
			},

			getCellEditorOptions(options, locale) {
				const normalized = {};

				options.forEach((item) => {
					normalized[item.value] = item.label;

					const localizationMap = item.localizationMap;

					if (localizationMap[locale]) {
						normalized[item.value] = localizationMap[locale].label;
					}
				});

				return normalized;
			},

			getRecordModel(keys) {
				const recordModel = {};

				keys.forEach((item) => {
					recordModel[item] = STR_EMPTY;
				});

				return recordModel;
			},

			prototype: {
				_afterActiveCellIndexChange() {
					const instance = this;

					const activeCell = instance.get('activeCell');
					const boundingBox = instance.get('boundingBox');

					const scrollableElement = boundingBox.one(
						'.table-x-scroller'
					);

					const tableHighlightBorder = instance.highlight.get(
						'activeBorderWidth'
					)[0];

					const activeCellWidth =
						activeCell.outerWidth() + tableHighlightBorder;
					const scrollableWidth = scrollableElement.outerWidth();

					const activeCellOffsetLeft = activeCell.get('offsetLeft');
					const scrollLeft = scrollableElement.get('scrollLeft');

					const activeCellOffsetRight =
						activeCellOffsetLeft + activeCellWidth;

					let scrollTo = scrollLeft;

					if (scrollLeft + scrollableWidth < activeCellOffsetRight) {
						scrollTo = activeCellOffsetRight - scrollableWidth;
					}
					else if (activeCellOffsetLeft < scrollLeft) {
						scrollTo = activeCellOffsetLeft;
					}

					scrollableElement.set('scrollLeft', scrollTo);
				},

				_afterSelectionKey(event) {
					const instance = this;

					const activeCell = instance.get('activeCell');

					const alignNode = event.alignNode || activeCell;

					const column = instance.getColumn(alignNode);

					if (
						activeCell &&
						event.keyCode === 13 &&
						column.type !== 'textarea'
					) {
						instance._onEditCell(activeCell);
					}
				},

				_normalizeFieldData(item, record, normalized) {
					const instance = this;

					const type = item.type;
					let value = record.get(item.name);

					if (!record.changed[item.id] && value && !!value.length) {
						return;
					}

					if (type === 'ddm-link-to-page') {
						value = FormBuilder.Util.parseJSON(value);

						delete value.name;

						value = JSON.stringify(value);
					}
					else if (type === 'select') {
						if (!isArray(value)) {
							value = AArray(value);
						}

						value = JSON.stringify(value);
					}

					const fieldValue = {
						instanceId: instance._randomString(8),
						name: item.name,
					};

					if (item.localizable) {
						fieldValue['value'] = {
							[themeDisplay.getLanguageId()]: value.toString(),
						};
					}
					else {
						fieldValue['value'] = value;
					}

					normalized['fieldValues'].push(fieldValue);

					if (isArray(item.fields)) {
						item.fields.forEach((item) => {
							instance._normalizeFieldData(
								item,
								record,
								normalized
							);
						});
					}
				},

				_normalizeRecordData(record) {
					const instance = this;

					const structure = instance.get('structure');

					const normalized = {
						availableLanguageIds: [themeDisplay.getLanguageId()],
						defaultLanguageId: themeDisplay.getLanguageId(),
						fieldValues: [],
					};

					structure.forEach((item) => {
						instance._normalizeFieldData(item, record, normalized);

						if (item.fields) {
							item.fields.forEach((nestedField) =>
								instance._normalizeFieldData(
									nestedField,
									record,
									normalized
								)
							);
						}
					});

					delete normalized.displayIndex;
					delete normalized.recordId;

					return normalized;
				},

				_onDataChange(event) {
					const instance = this;

					instance._setDataStableSort(event.newVal);
				},

				_onEditCell(event) {
					const instance = this;

					SpreadSheet.superclass._onEditCell.apply(
						instance,
						arguments
					);

					const activeCell = instance.get('activeCell');

					const alignNode = event.alignNode || activeCell;

					const column = instance.getColumn(alignNode);
					const record = instance.getRecord(alignNode);

					const data = instance.get('data');
					const portletNamespace = instance.get('portletNamespace');
					const recordsetId = instance.get('recordsetId');
					const structure = instance.get('structure');

					const editor = instance.getEditor(record, column);

					if (editor) {
						editor.setAttrs({
							data,
							portletNamespace,
							record,
							recordsetId,
							structure,
							zIndex: Liferay.zIndex.OVERLAY,
						});
					}
				},

				_onRecordUpdate(event) {
					const instance = this;

					if (
						!Object.prototype.hasOwnProperty.call(
							event.changed,
							'recordId'
						)
					) {
						const data = instance.get('data');
						const recordsetId = instance.get('recordsetId');

						const record = event.target;

						const recordId = record.get('recordId');

						const fieldsMap = instance._normalizeRecordData(record);

						const recordIndex = data.indexOf(record);

						if (recordId > 0) {
							SpreadSheet.updateRecord(
								recordId,
								recordIndex,
								fieldsMap,
								false,
								instance.get('portletNamespace'),
								instance.get('updateRecordURL')
							);
						}
						else {
							SpreadSheet.addRecord(
								instance.get('addRecordURL'),
								(json) => {
									if (json.recordId > 0) {
										record.set('recordId', json.recordId, {
											silent: true,
										});
									}
								},
								fieldsMap,
								recordIndex,
								instance.get('portletNamespace'),
								recordsetId
							);
						}
					}
				},

				_randomString(length) {
					const random = Math.random();

					const randomString = random.toString(36);

					return randomString.substring(length);
				},

				_setDataStableSort(data) {
					data.sort = function (options) {
						if (this.comparator) {
							options = options || {};

							const models = this._items.concat();

							A.ArraySort.stableSort(
								models,
								A.bind(this._sort, this)
							);

							const facade = {
								...options,
								models,
								src: 'sort',
							};

							if (options.silent) {
								this._defResetFn(facade);
							}
							else {
								this.fire('reset', facade);
							}
						}

						return this;
					};
				},

				addEmptyRows(num) {
					const instance = this;

					const columns = instance.get('columns');
					const data = instance.get('data');

					const keys = columns.map((item) => {
						return item.key;
					});

					data.add(SpreadSheet.buildEmptyRecords(num, keys));
				},

				initializer() {
					const instance = this;

					instance._setDataStableSort(instance.get('data'));

					instance.set('scrollable', true);

					instance.on('dataChange', instance._onDataChange);
					instance.on('model:change', instance._onRecordUpdate);
				},

				updateMinDisplayRows(minDisplayRows, callback) {
					const instance = this;

					callback =
						(callback && A.bind(callback, instance)) || EMPTY_FN;

					const recordsetId = instance.get('recordsetId');

					Liferay.Service(
						'/ddl.ddlrecordset/update-min-display-rows',
						{
							minDisplayRows,
							recordSetId: recordsetId,
							serviceContext: JSON.stringify({
								scopeGroupId: themeDisplay.getScopeGroupId(),
								userId: themeDisplay.getUserId(),
							}),
						},
						callback
					);
				},
			},

			updateRecord(
				recordId,
				displayIndex,
				ddmFormValues,
				majorVersion,
				portletNamespace,
				updateRecordURL,
				callback
			) {
				const instance = this;

				callback = (callback && A.bind(callback, instance)) || EMPTY_FN;

				// eslint-disable-next-line @liferay/aui/no-io
				A.io.request(updateRecordURL, {
					data: Liferay.Util.ns(portletNamespace, {
						ddmFormValues: JSON.stringify(ddmFormValues),
						displayIndex,
						majorVersion,
						recordId,
					}),
					dataType: 'JSON',
					method: 'POST',
					on: {
						success() {
							callback();
						},
					},
				});
			},
		});

		Liferay.SpreadSheet = SpreadSheet;

		const DDLUtil = {
			openPreviewDialog(content) {
				const instance = this;

				let previewDialog = instance.previewDialog;

				if (!previewDialog) {
					previewDialog = Liferay.Util.Window.getWindow({
						dialog: {
							bodyContent: content,
						},
						title: Liferay.Language.get('preview'),
					});

					instance.previewDialog = previewDialog;
				}
				else {
					previewDialog.show();

					previewDialog.set('bodyContent', content);
				}
			},

			previewDialog: null,
		};

		Liferay.DDLUtil = DDLUtil;
	},
	'',
	{
		requires: [
			'aui-arraysort',
			'aui-datatable',
			'aui-io-deprecated',
			'datatable-sort',
			'json',
			'liferay-portlet-dynamic-data-mapping-custom-fields',
			'liferay-portlet-url',
			'liferay-util-window',
		],
	}
);
