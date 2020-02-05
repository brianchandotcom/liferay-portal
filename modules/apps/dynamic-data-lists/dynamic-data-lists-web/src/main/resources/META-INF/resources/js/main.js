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
	A => {
		var AArray = A.Array;

		var DateMath = A.DataType.DateMath;

		var FormBuilder = Liferay.FormBuilder;

		var Lang = A.Lang;

		var EMPTY_FN = A.Lang.emptyFn;

		var FIELDS_DISPLAY_INSTANCE_SEPARATOR = '_INSTANCE_';

		var FIELDS_DISPLAY_NAME = '_fieldsDisplay';

		var STR_EMPTY = '';

		var isArray = Array.isArray;
		var isNumber = Lang.isNumber;

		var SpreadSheet = A.Component.create({
			ATTRS: {
				portletNamespace: {
					validator: Lang.isString,
					value: STR_EMPTY
				},

				recordsetId: {
					validator: isNumber,
					value: 0
				},

				structure: {
					validator: isArray,
					value: []
				}
			},

			CSS_PREFIX: 'table',

			DATATYPE_VALIDATOR: {
				double: 'number',
				integer: 'digits',
				long: 'digits'
			},

			EXTENDS: A.DataTable,

			NAME: A.DataTable.Base.NAME,

			TYPE_EDITOR: {
				checkbox: A.CheckboxCellEditor,
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
				radio: A.RadioCellEditor,
				select: A.DropDownCellEditor,
				text: A.TextCellEditor,
				textarea: A.TextAreaCellEditor
			},

			addRecord(recordsetId, displayIndex, fieldsMap, callback) {
				var instance = this;

				callback = (callback && A.bind(callback, instance)) || EMPTY_FN;

				Liferay.Service(
					'/ddl.ddlrecord/add-record',
					{
						displayIndex,
						fieldsMap: JSON.stringify(fieldsMap),
						groupId: themeDisplay.getScopeGroupId(),
						recordSetId: recordsetId,
						serviceContext: JSON.stringify({
							scopeGroupId: themeDisplay.getScopeGroupId(),
							userId: themeDisplay.getUserId(),
							workflowAction: Liferay.Workflow.ACTION_PUBLISH
						})
					},
					callback
				);
			},

			buildDataTableColumns(columns, locale, structure, editable) {
				var instance = this;

				columns.forEach(item => {
					var dataType = item.dataType;
					var label = item.label;
					var name = item.name;
					var type = item.type;

					item.key = name;

					var EditorClass =
						instance.TYPE_EDITOR[type] || A.TextCellEditor;

					var config = {
						elementName: name,
						strings: {
							cancel: Liferay.Language.get('cancel'),
							edit: Liferay.Language.get('edit'),
							save: Liferay.Language.get('save')
						},
						validator: {
							rules: {}
						}
					};

					var required = item.required;

					var structureField;

					if (required) {
						label += ' (' + Liferay.Language.get('required') + ')';
					}

					label = A.Escape.html(label);

					item.label = label;

					if (type === 'checkbox') {
						config.options = {
							true: Liferay.Language.get('true')
						};

						config.inputFormatter = function(value) {
							if (Array.isArray(value) && value.length > 0) {
								value = value[0];
							}

							var checkedValue = 'false';

							if (value === 'true') {
								checkedValue = value;
							}

							return checkedValue;
						};

						item.formatter = function(obj) {
							var data = obj.data;

							var value = data[name];

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
						config.inputFormatter = function(val) {
							return val.map(item => {
								return A.DataType.Date.format(item);
							});
						};

						config.outputFormatter = function(val) {
							return val.map(item => {
								var date;

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

						item.formatter = function(obj) {
							var data = obj.data;

							var value = data[name];

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
						config.outputFormatter = function(value) {
							var number = A.DataType.Number.parse(value);

							var numberValue = STR_EMPTY;

							if (isNumber(number)) {
								numberValue = number;
							}

							return numberValue;
						};

						item.formatter = function(obj) {
							var data = obj.data;

							var value = A.DataType.Number.parse(data[name]);

							if (!isNumber(value)) {
								value = STR_EMPTY;
							}

							return value;
						};
					}
					else if (type === 'ddm-documentlibrary') {
						item.formatter = function(obj) {
							var data = obj.data;

							var label = STR_EMPTY;
							var value = data[name];

							if (value !== STR_EMPTY) {
								var fileData = FormBuilder.Util.parseJSON(
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
						item.formatter = function(obj) {
							var data = obj.data;

							var label = STR_EMPTY;
							var value = data[name];

							if (value !== STR_EMPTY) {
								var linkToPageData = FormBuilder.Util.parseJSON(
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

						var multiple = A.DataType.Boolean.parse(
							structureField.multiple
						);
						var options = instance.getCellEditorOptions(
							structureField.options,
							locale
						);

						item.formatter = function(obj) {
							var data = obj.data;

							var label = [];
							var value = data[name];

							if (isArray(value)) {
								value.forEach(item1 => {
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

						item.formatter = function(obj) {
							var data = obj.data;

							var value = data[name];

							if (!value) {
								return value;
							}

							return value.split('\n').join('<br>');
						};
					}

					var validatorRuleName =
						instance.DATATYPE_VALIDATOR[dataType];

					var validatorRules = config.validator.rules;

					validatorRules[name] = A.mix(
						{
							required
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
				var instance = this;

				var emptyRows = [];

				for (var i = 0; i < num; i++) {
					emptyRows.push(instance.getRecordModel(keys));
				}

				return emptyRows;
			},

			findStructureFieldByAttribute(
				fieldsArray,
				attributeName,
				attributeValue
			) {
				var instance = this;

				var structureField;

				AArray.some(fieldsArray, item => {
					var nestedFieldsArray = item.fields;

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
				var normalized = {};

				options.forEach(item => {
					normalized[item.value] = item.label;

					var localizationMap = item.localizationMap;

					if (localizationMap[locale]) {
						normalized[item.value] = localizationMap[locale].label;
					}
				});

				return normalized;
			},

			getRecordModel(keys) {
				var recordModel = {};

				keys.forEach(item => {
					recordModel[item] = STR_EMPTY;
				});

				return recordModel;
			},

			prototype: {
				_afterActiveCellIndexChange() {
					var instance = this;

					var activeCell = instance.get('activeCell');
					var boundingBox = instance.get('boundingBox');

					var scrollableElement = boundingBox.one(
						'.table-x-scroller'
					);

					var tableHighlightBorder = instance.highlight.get(
						'activeBorderWidth'
					)[0];

					var activeCellWidth =
						activeCell.outerWidth() + tableHighlightBorder;
					var scrollableWidth = scrollableElement.outerWidth();

					var activeCellOffsetLeft = activeCell.get('offsetLeft');
					var scrollLeft = scrollableElement.get('scrollLeft');

					var activeCellOffsetRight =
						activeCellOffsetLeft + activeCellWidth;

					var scrollTo = scrollLeft;

					if (scrollLeft + scrollableWidth < activeCellOffsetRight) {
						scrollTo = activeCellOffsetRight - scrollableWidth;
					}
					else if (activeCellOffsetLeft < scrollLeft) {
						scrollTo = activeCellOffsetLeft;
					}

					scrollableElement.set('scrollLeft', scrollTo);
				},

				_afterSelectionKey(event) {
					var instance = this;

					var activeCell = instance.get('activeCell');

					var alignNode = event.alignNode || activeCell;

					var column = instance.getColumn(alignNode);

					if (
						activeCell &&
						event.keyCode === 13 &&
						column.type !== 'textarea'
					) {
						instance._onEditCell(activeCell);
					}
				},

				_normalizeFieldData(
					item,
					record,
					fieldsDisplayValues,
					normalized
				) {
					var instance = this;

					var type = item.type;
					var value = record.get(item.name);

					if (!record.changed[item.id] && value && value.length > 0) {
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

					normalized[item.name] = instance._normalizeValue(value);

					fieldsDisplayValues.push(
						item.name +
							FIELDS_DISPLAY_INSTANCE_SEPARATOR +
							instance._randomString(8)
					);

					if (isArray(item.fields)) {
						item.fields.forEach(item => {
							instance._normalizeFieldData(
								item,
								record,
								fieldsDisplayValues,
								normalized
							);
						});
					}
				},

				_normalizeRecordData(record) {
					var instance = this;

					var structure = instance.get('structure');

					var fieldsDisplayValues = [];
					var normalized = {};

					structure.forEach(item => {
						instance._normalizeFieldData(
							item,
							record,
							fieldsDisplayValues,
							normalized
						);
					});

					normalized[FIELDS_DISPLAY_NAME] = fieldsDisplayValues.join(
						','
					);

					delete normalized.displayIndex;
					delete normalized.recordId;

					return normalized;
				},

				_normalizeValue(value) {
					return String(value);
				},

				_onDataChange(event) {
					var instance = this;

					instance._setDataStableSort(event.newVal);
				},

				_onEditCell(event) {
					var instance = this;

					SpreadSheet.superclass._onEditCell.apply(
						instance,
						arguments
					);

					var activeCell = instance.get('activeCell');

					var alignNode = event.alignNode || activeCell;

					var column = instance.getColumn(alignNode);
					var record = instance.getRecord(alignNode);

					var data = instance.get('data');
					var portletNamespace = instance.get('portletNamespace');
					var recordsetId = instance.get('recordsetId');
					var structure = instance.get('structure');

					var editor = instance.getEditor(record, column);

					if (editor) {
						editor.setAttrs({
							data,
							portletNamespace,
							record,
							recordsetId,
							structure,
							zIndex: Liferay.zIndex.OVERLAY
						});
					}
				},

				_onRecordUpdate(event) {
					var instance = this;

					if (
						!Object.prototype.hasOwnProperty.call(
							event.changed,
							'recordId'
						)
					) {
						var data = instance.get('data');
						var recordsetId = instance.get('recordsetId');

						var record = event.target;

						var recordId = record.get('recordId');

						var fieldsMap = instance._normalizeRecordData(record);

						var recordIndex = data.indexOf(record);

						if (recordId > 0) {
							SpreadSheet.updateRecord(
								recordId,
								recordIndex,
								fieldsMap,
								true
							);
						}
						else {
							SpreadSheet.addRecord(
								recordsetId,
								recordIndex,
								fieldsMap,
								json => {
									if (json.recordId > 0) {
										record.set('recordId', json.recordId, {
											silent: true
										});
									}
								}
							);
						}
					}
				},

				_randomString(length) {
					var random = Math.random();

					var randomString = random.toString(36);

					return randomString.substring(length);
				},

				_setDataStableSort(data) {
					data.sort = function(options) {
						if (this.comparator) {
							options = options || {};

							var models = this._items.concat();

							A.ArraySort.stableSort(
								models,
								A.bind(this._sort, this)
							);

							var facade = A.merge(options, {
								models,
								src: 'sort'
							});

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
					var instance = this;

					var columns = instance.get('columns');
					var data = instance.get('data');

					var keys = columns.map(item => {
						return item.key;
					});

					data.add(SpreadSheet.buildEmptyRecords(num, keys));
				},

				initializer() {
					var instance = this;

					instance._setDataStableSort(instance.get('data'));

					instance.set('scrollable', true);

					instance.on('dataChange', instance._onDataChange);
					instance.on('model:change', instance._onRecordUpdate);
				},

				updateMinDisplayRows(minDisplayRows, callback) {
					var instance = this;

					callback =
						(callback && A.bind(callback, instance)) || EMPTY_FN;

					var recordsetId = instance.get('recordsetId');

					Liferay.Service(
						'/ddl.ddlrecordset/update-min-display-rows',
						{
							minDisplayRows,
							recordSetId: recordsetId,
							serviceContext: JSON.stringify({
								scopeGroupId: themeDisplay.getScopeGroupId(),
								userId: themeDisplay.getUserId()
							})
						},
						callback
					);
				}
			},

			updateRecord(recordId, displayIndex, fieldsMap, merge, callback) {
				var instance = this;

				callback = (callback && A.bind(callback, instance)) || EMPTY_FN;

				Liferay.Service(
					'/ddl.ddlrecord/update-record',
					{
						displayIndex,
						fieldsMap: JSON.stringify(fieldsMap),
						mergeFields: merge,
						recordId,
						serviceContext: JSON.stringify({
							scopeGroupId: themeDisplay.getScopeGroupId(),
							userId: themeDisplay.getUserId(),
							workflowAction: Liferay.Workflow.ACTION_PUBLISH
						})
					},
					callback
				);
			}
		});

		Liferay.SpreadSheet = SpreadSheet;

		var DDLUtil = {
			openPreviewDialog(content) {
				var instance = this;

				var previewDialog = instance.previewDialog;

				if (!previewDialog) {
					previewDialog = Liferay.Util.Window.getWindow({
						dialog: {
							bodyContent: content
						},
						title: Liferay.Language.get('preview')
					});

					instance.previewDialog = previewDialog;
				}
				else {
					previewDialog.show();

					previewDialog.set('bodyContent', content);
				}
			},

			previewDialog: null
		};

		Liferay.DDLUtil = DDLUtil;
	},
	'',
	{
		requires: [
			'aui-arraysort',
			'aui-datatable',
			'datatable-sort',
			'json',
			'liferay-portlet-dynamic-data-mapping-custom-fields',
			'liferay-portlet-url',
			'liferay-util-window'
		]
	}
);
