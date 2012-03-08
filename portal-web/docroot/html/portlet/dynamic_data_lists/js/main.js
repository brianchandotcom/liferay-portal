AUI.add(
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

		var STR_COMMA = ',';

		var STR_EMPTY = '';

		var DLFileEntryCellEditor = A.Component.create(
			{
				NAME: 'document-library-file-entry-cell-editor',

				EXTENDS: A.BaseCellEditor,

				prototype: {
					ELEMENT_TEMPLATE: '<input type="hidden" />',

					initializer: function() {
						var instance = this;

						window[Liferay.Util.getPortletNamespace('15') + 'selectDocumentLibrary'] = A.bind(instance._selectFileEntry, instance);
					},

					getElementsValue: function() {
						var instance = this;

						return instance.get('value');
					},

					_defInitToolbarFn: function() {
						var instance = this;

						DLFileEntryCellEditor.superclass._defInitToolbarFn.apply(instance, arguments);

						instance.toolbar.add(
							{
								handler: A.bind(instance._handleChooseEvent, instance),
								label: Liferay.Language.get('choose')
							},
							1
						);
					},

					_handleChooseEvent: function() {
						var instance = this;

						var uri = Liferay.Util.addParams(
							{
								groupId: themeDisplay.getScopeGroupId(),
								p_p_id: '15',
								p_p_state: 'pop_up',
								struts_action: '/journal/select_document_library'
							},
							themeDisplay.getURLControlPanel()
						);

						Liferay.Util.openWindow(
							{
								id: 'selectDocumentLibrary',
								title: Liferay.Language.get('javax.portlet.title.20'),
								uri: uri
							}
						);
					},

					_selectFileEntry: function(url, uuid, title, version) {
						var instance = this;

						instance.selectedTitle = title;
						instance.selectedURL = url;

						instance.set(
							'value',
							JSON.stringify(
								{
									groupId: themeDisplay.getScopeGroupId(),
									uuid: uuid,
									title: title,
									version: version
								}
							)
						);
					},

					_syncFileLabel: function(title, url) {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var linkNode = contentBox.one('a');

						if (!linkNode) {
							linkNode = A.Node.create('<a></a>');

							contentBox.prepend(linkNode);
						}

						linkNode.setAttribute('href', url);
						linkNode.setContent(title);
					},

					_uiSetValue: function(val) {
						var instance = this;

						if (val) {
							var selectedTitle = instance.selectedTitle;
							var selectedURL = instance.selectedURL;

							if (selectedTitle && selectedURL) {
								instance._syncFileLabel(selectedTitle, selectedURL);
							}
							else {
								SpreadSheet.Util.getFileEntry(
									val,
									function(fileEntry) {
										var url = SpreadSheet.Util.getFileEntryURL(fileEntry);

										instance._syncFileLabel(fileEntry.title, url);
									}
								);
							}
						}
						else {
							instance._syncFileLabel(STR_EMPTY, STR_EMPTY);

							val = STR_EMPTY;
						}

						instance.elements.val(val);
					}
				}
			}
		);

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

				CSS_PREFIX: '',

				DATATYPE_VALIDATOR: {
					'date': 'date',
					'double': 'number',
					'integer': 'digits',
					'long': 'digits'
				},

				EXTENDS: A.DataTable.Base,

				NAME: A.DataTable.Base.NAME,

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

					_editCell: function(event) {
						var instance = this;

						SpreadSheet.superclass._editCell.apply(instance, arguments);

						var column = event.column;
						var record = event.record;

						var recordset = instance.get('recordset');
						var recordsetId = instance.get('recordsetId');
						var structure = instance.get('structure');

						var editor = instance.getCellEditor(record, column);

						if (editor) {
							editor.set('record', record);
							editor.set('recordset', recordset);
							editor.set('recordsetId', recordsetId);
							editor.set('structure', structure);
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
										if (!Lang.isArray(item)) {
											item = AArray(item);
										}

										item = JSON.stringify(item);
									}
								}

								normalized[index] = instance._normalizeValue(item);
							}
						);

						delete normalized.classPK;
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

						var recordsetId = instance.get('recordsetId');

						var recordIndex = event.index;

						AArray.each(
							event.updated,
							function(item, index, collection) {
								var data = item.get('data');

								var fieldsMap = instance._normalizeRecordData(data);

								if (data.classPK > 0) {
									SpreadSheet.updateRecord(data.classPK, recordIndex, fieldsMap, true);
								}
								else {
									SpreadSheet.addRecord(
										recordsetId,
										recordIndex,
										fieldsMap,
										function(json) {
											if (json.recordId > 0) {
												data.classPK = json.recordId;
											}
										}
									);
								}
							}
						);
					}
				},

				addRecord: function(recordsetId, displayIndex, fieldsMap, callback) {
					var instance = this;

					callback = (callback && A.bind(callback, instance)) || EMPTY_FN;

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
							serviceParameterTypes: JSON.stringify(serviceParameterTypes)
						},
						callback
					);
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
								config.inputFormatter = function(value) {
									var date = A.DataType.Date.parse(value);

									var dateValue = STR_EMPTY;

									if (date) {
										dateValue = date.getTime();
									}

									return dateValue;
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
							else if (type === 'ddm-documentlibrary') {
								item.formatter = function(obj) {
									var data = obj.record.get('data');

									var label = STR_EMPTY;
									var value = data[name];

									if (value !== STR_EMPTY) {
										var fileData = SpreadSheet.Util.parseJSON(value);

										if (fileData.title) {
											label = fileData.title;
										}
									}

									return label;
								};
							}
							else if (type === 'ddm-fileupload') {
								item.formatter = function(obj) {
									var data = obj.record.get('data');

									var label = STR_EMPTY;
									var value = data[name];

									if (value !== STR_EMPTY) {
										var fileData = SpreadSheet.Util.parseJSON(value);

										if (fileData.classPK) {
											label = fileData.name;
										}
									}

									return label;
								};

								structureField = instance.findStructureFieldByAttribute(structure, 'name', name);

								config.validator.rules[name] = {
									acceptFiles: structureField.acceptFiles,
									requiredFields: true
								};
							}
							else if ((type === 'radio') || (type === 'select')) {
								structureField = instance.findStructureFieldByAttribute(structure, 'name', name);

								var multiple = A.DataType.Boolean.parse(structureField.multiple);
								var options = instance.getCellEditorOptions(structureField.options);

								item.formatter = function(obj) {
									var data = obj.record.get('data');

									var label = [];
									var value = data[name];

									AArray.each(
										value,
										function(item1, index1, collection1) {
											label.push(options[item1]);
										}
									);

									return label.join(', ');
								};

								config.inputFormatter = AArray;
								config.multiple = multiple;
								config.options = options;
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
							normalized[item.value] = item.label;
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
							serviceParameterTypes: JSON.stringify(serviceParameterTypes)
						},
						callback
					);
				}
			}
		);

		SpreadSheet.Util = {
			getFileEntry: function(fileJSON, callback) {
				var instance = this;

				fileJSON = instance.parseJSON(fileJSON);

				DLApp.getFileEntryByUuidAndGroupId(
					{
						uuid: fileJSON.uuid,
						groupId: fileJSON.groupId
					},
					callback
				);
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
			},

			parseJSON: function(value) {
				var instance = this;

				var data = {};

				try {
					data = JSON.parse(value);
				}
				catch (e) {
				}

				return data;
			}
		};

		SpreadSheet.TYPE_EDITOR['ddm-documentlibrary'] = DLFileEntryCellEditor;

		Liferay.SpreadSheet = SpreadSheet;
	},
	'',
	{
		requires: ['aui-arraysort', 'aui-datatable', 'json', 'liferay-portlet-url']
	}
);