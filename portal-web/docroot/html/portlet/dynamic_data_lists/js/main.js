AUI().add(
	'liferay-portlet-dynamic-data-lists',
	function(A) {
		var AArray = A.Array;

		var Lang = A.Lang;

		var DDL = Liferay.Service.DDL;

		var DDLRecord = DDL.DDLRecord;

		var DDLRecordSet = DDL.DDLRecordSet;

		var DLApp = Liferay.Service.DL.DLApp;

		var getObjectKeys = A.Object.keys;

		var JSON = A.JSON;

		var EMPTY_FN = A.Lang.emptyFn;

		var STR_EMPTY = '';

		var SpreadSheet = A.Component.create(
			{
				ATTRS: {
					portletNamespace: {
						validator: Lang.isString,
						value: STR_EMPTY
					},

					recordsetId: {
						validator: Lang.isNumber,
						value: 0
					},

					structure: {
						validator: Lang.isArray,
						value: []
					}
				},

				DATATYPE_VALIDATOR: {
					'date': 'date',
					'double': 'number',
					'integer': 'digits',
					'long': 'digits'
				},

				EXTENDS: A.DataTable.Base,

				NAME: 'spreadsheet',

				TYPE_EDITOR: {
					'checkbox': A.CheckboxCellEditor,
					'ddm-date': A.DateCellEditor,
					'ddm-decimal': A.TextCellEditor,
					'ddm-integer': A.TextCellEditor,
					'ddm-number': A.TextCellEditor,
					'radio': A.RadioCellEditor,
					'select': A.DropDownCellEditor,
					'text': A.TextCellEditor,
					'textarea': A.TextAreaCellEditor
				},

				prototype: {
					initializer: function() {
						var instance = this;

						var recordset = instance.get('recordset');

						recordset.on('update', instance._onRecordUpdate, instance);
					},

					addEmptyRows: function(num) {
						var instance = this;

						var columnset = instance.get('columnset');
						var recordset = instance.get('recordset');

						var emptyRows = SpreadSheet.buildEmptyRecords(num, getObjectKeys(columnset.keyHash));

						recordset.add(emptyRows);

						instance._uiSetRecordset(recordset);

						instance._fixPluginsUI();
					},

					addRecord: function(displayIndex, fieldsMap, callback) {
						var instance = this;

						callback = (callback && A.bind(callback, instance)) || EMPTY_FN;

						var recordsetId = instance.get('recordsetId');

						var serviceParameterTypes = [
							'long',
							'long',
							'int',
							'java.util.Map<java.lang.String, java.io.Serializable>',
							'com.liferay.portal.service.ServiceContext'
						];

						DDLRecord.addRecord(
							{
								groupId: themeDisplay.getScopeGroupId(),
								recordsetId: recordsetId,
								displayIndex: displayIndex,
								fieldsMap: JSON.stringify(fieldsMap),
								serviceContext: JSON.stringify(
									{
										scopeGroupId: themeDisplay.getScopeGroupId(),
										userId: themeDisplay.getUserId(),
										workflowAction: Liferay.Workflow.ACTION_PUBLISH
									}
								),
								serviceParameterTypes: A.JSON.stringify(serviceParameterTypes)
							},
							callback
						);
					},

					updateMinDisplayRows: function(minDisplayRows, callback) {
						var instance = this;

						callback = (callback && A.bind(callback, instance)) || EMPTY_FN;

						var recordsetId = instance.get('recordsetId');

						DDLRecordSet.updateMinDisplayRows(
							{
								recordsetId: recordsetId,
								minDisplayRows: minDisplayRows,
								serviceContext: JSON.stringify(
									{
										scopeGroupId: themeDisplay.getScopeGroupId(),
										userId: themeDisplay.getUserId()
									}
								)
							},
							callback
						);
					},

					updateRecord: function(recordId, displayIndex, fieldsMap, merge, callback) {
						var instance = this;

						callback = (callback && A.bind(callback, instance)) || EMPTY_FN;

						var serviceParameterTypes = [
							'long',
							'int',
							'java.util.Map<java.lang.String, java.io.Serializable>',
							'boolean',
							'com.liferay.portal.service.ServiceContext'
						];

						DDLRecord.updateRecord(
							{
								recordId: recordId,
								displayIndex: displayIndex,
								fieldsMap: JSON.stringify(fieldsMap),
								merge: merge,
								serviceContext: JSON.stringify(
									{
										scopeGroupId: themeDisplay.getScopeGroupId(),
										userId: themeDisplay.getUserId(),
										workflowAction: Liferay.Workflow.ACTION_PUBLISH
									}
								),
								serviceParameterTypes: A.JSON.stringify(serviceParameterTypes)
							},
							callback
						);
					},

					_editCell: function(event) {
						var instance = this;

						SpreadSheet.superclass._editCell.apply(instance, arguments);

						var column = event.column;
						var record = event.record;

						var editor = instance.getCellEditor(record, column);

						if (editor) {
							editor.set('record', record);
						}
					},

					_normalizeRecordData: function(data) {
						var instance = this;

						var recordset = instance.get('recordset');
						var structure = instance.get('structure');

						var normalized = {};

						A.each(
							data,
							function(item, index, collection) {
								var field = SpreadSheet.findStructureFieldByAttribute(structure, 'name', index);

								if (field !== null) {
									var type = field.type;

									if ((type === 'radio') || (type === 'select')) {
										var option = SpreadSheet.findStructureFieldByAttribute(field.options, 'label', item);

										item = option.value;
									}
								}

								normalized[index] = instance._normalizeValue(item);
							}
						);

						delete normalized.displayIndex;
						delete normalized.recordId;

						return normalized;
					},

					_normalizeValue: function(value) {
						var instance = this;

						return String(value);
					},

					_onRecordUpdate: function(event) {
						var instance = this;

						var recordIndex = event.index;

						AArray.each(
							event.updated,
							function(item, index, collection) {
								var data = item.get('data');

								var fieldsMap = instance._normalizeRecordData(data);

								if (data.recordId > 0) {
									instance.updateRecord(data.recordId, recordIndex, fieldsMap, true);
								}
								else {
									instance.addRecord(
										recordIndex,
										fieldsMap,
										function(json) {
											if (json.recordId > 0) {
												data.recordId = json.recordId;
											}
										}
									);
								}
							}
						);
					}
				},

				buildDataTableColumnset: function(columnset, structure, editable) {
					var instance = this;

					AArray.each(
						columnset,
						function(item, index, collection) {
							var dataType = item.dataType;
							var label = item.label;
							var name = item.name;
							var type = item.type;

							item.key = name;

							var EditorClass = instance.TYPE_EDITOR[type] || A.TextCellEditor;

							var config = {
								elementName: name,
								validator: {
									rules: {}
								}
							};

							var required = item.required;

							var structureField;

							if (required) {
								item.label += ' (' + Liferay.Language.get('required') + ')';
							}

							if (type === 'checkbox') {
								config.options = {
									'true': Liferay.Language.get('true')
								};

								config.inputFormatter = function(value) {
									return String(value.length > 0);
								};

								item.formatter = function(obj) {
									var data = obj.record.get('data');

									var value = data[name];

									if (value !== STR_EMPTY) {
										value = Liferay.Language.get(value);
									}

									return value;
								};
							}
							else if (type === 'ddm-date') {
								config.inputFormatter = function(value) {
									return A.DataType.Date.parse(value).getTime();
								};

								item.formatter = function(obj) {
									var data = obj.record.get('data');

									var value = data[name];

									if (value !== STR_EMPTY) {
										value = parseInt(value, 10);

										value = A.DataType.Date.format(new Date(value));
									}

									return value;
								};
							}
							else if ((type === 'ddm-documentlibrary') || (type === 'ddm-fileupload')) {
								item.formatter = function(obj) {
									var data = obj.record.get('data');

									var label = STR_EMPTY;
									var value = data[name];

									if (value !== STR_EMPTY) {
										label = '(' + Liferay.Language.get('file') + ')';
									}

									return label;
								};

								structureField = instance.findStructureFieldByAttribute(structure, 'name', name);

								if (type === 'ddm-fileupload') {
									config.validator.rules[name] = {
										acceptFiles: structureField.acceptFiles
									};
								}
							}
							else if ((type === 'radio') || (type === 'select')) {
								structureField = instance.findStructureFieldByAttribute(structure, 'name', name);

								config.options = instance.getCellEditorOptions(structureField.options);
							}

							var validatorRuleName = instance.DATATYPE_VALIDATOR[dataType];

							var validatorRules = config.validator.rules;

							validatorRules[name] = A.mix(
								{
									required: required
								},
								validatorRules[name]
							);

							if (validatorRuleName) {
								validatorRules[name][validatorRuleName] = true;
							}

							if (editable && item.editable) {
								item.editor = new EditorClass(config);
							}
						}
					);

					return columnset;
				},

				buildEmptyRecords: function(num, keys) {
					var instance = this;

					var emptyRows = [];

					for (var i = 0; i < num; i++) {
						emptyRows.push(instance.getRecordModel(keys));
					}

					return emptyRows;
				},

				findStructureFieldByAttribute: function(structure, attributeName, attributeValue) {
					var found = null;

					AArray.some(
						structure,
						function(item, index, collection) {
							found = item;

							return (found[attributeName] === attributeValue);
						}
					);

					return found;
				},

				getCellEditorOptions: function(options) {
					var normalized = {};

					AArray.each(
						options,
						function(item, index, collection) {
							normalized[item.label] = item.label;
						}
					);

					return normalized;
				},

				getRecordModel: function(keys) {
					var instance = this;

					var recordModel = {};

					AArray.each(
						keys,
						function(item, index, collection) {
							recordModel[item] = STR_EMPTY;
						}
					);

					return recordModel;
				}
			}
		);

		SpreadSheet.Util = {
			getFileEntry: function(fileJSON, callback) {
				var instance = this;

				try {
					fileJSON = A.JSON.parse(fileJSON);

					DLApp.getFileEntryByUuidAndGroupId(
						{
							uuid: fileJSON.uuid,
							groupId: fileJSON.groupId
						},
						callback
					);
				}
				catch (e) {
				}
			},

			getFileEntryLinkNode: function(fileJSON, fileEntryLinkNode) {
				var instance = this;

				fileEntryLinkNode = fileEntryLinkNode || A.Node.create('<a href="javascript:;"></a>');

				if (fileJSON) {
					instance.getFileEntry(
						fileJSON,
						function(fileEntry) {
							var fileEntryURL = instance.getFileEntryURL(fileEntry);

							fileEntryLinkNode.setContent(fileEntry.title).attr('href', fileEntryURL);
						}
					);
				}
				else {
					fileEntryLinkNode.setContent(STR_EMPTY).attr('href', 'javascript:;');
				}

				return fileEntryLinkNode;
			},

			getFileEntryURL: function(fileEntry) {
				var instance = this;

				var buffer = [
					themeDisplay.getPathContext(),
					'documents',
					fileEntry.groupId,
					fileEntry.folderId,
					encodeURIComponent(fileEntry.title)
				];

				return buffer.join('/');
			}
		};

		Liferay.SpreadSheet = SpreadSheet;
	},
	'',
	{
		requires: ['aui-arraysort', 'aui-datatable', 'json']
	}
);