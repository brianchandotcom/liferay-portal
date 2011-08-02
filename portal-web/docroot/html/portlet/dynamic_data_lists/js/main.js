AUI().add(
	'liferay-portlet-dynamic-data-lists',
	function(A) {
		var AArray = A.Array;

		var Lang = A.Lang;

		var DDL = Liferay.Service.DDL;

		var DDLRecord = DDL.DDLRecord;

		var DDLRecordSet = DDL.DDLRecordSet;

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

					recordSetId: {
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

						var recordSet = instance.get('recordSet');

						recordSet.on('update', instance._onRecordUpdate, instance);
					},

					addEmptyRows: function(num) {
						var instance = this;

						var columnset = instance.get('columnset');
						var recordSet = instance.get('recordSet');

						var emptyRows = SpreadSheet.buildEmptyRecords(num, getObjectKeys(columnset.keyHash));

						recordSet.add(emptyRows);

						instance._uiSetRecordset(recordSet);

						instance._fixPluginsUI();
					},

					addRecord: function(displayIndex, fieldsMap, callback) {
						var instance = this;

						callback = (callback && A.bind(callback, instance)) || EMPTY_FN;

						var recordSetId = instance.get('recordSetId');

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
								recordSetId: recordSetId,
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

						var recordSetId = instance.get('recordSetId');

						DDLRecordSet.updateMinDisplayRows(
							{
								recordSetId: recordSetId,
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

					_normalizeRecordData: function(data) {
						var instance = this;

						var recordSet = instance.get('recordSet');
						var structure = instance.get('structure');

						var normalized = {};

						A.each(
							data,
							function(item, index, collection) {
								var field = SpreadSheet.findStructureFieldByAttribute(structure, 'key', index);

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
							item.key = item.name;

							if (editable && item.editable) {
								var dataType = item.dataType;
								var label = item.label;
								var required = item.required;
								var type = item.type;

								var EditorClass = instance.TYPE_EDITOR[type] || A.TextCellEditor;

								var config = {
									validator: {
										rules: {}
									}
								};

								var elementName = 'value';

								if (type === 'checkbox') {
									elementName = label;

									config.options = [label];
								}
								else if ((type === 'radio') || (type === 'select')) {
									var structureField = instance.findStructureFieldByAttribute(structure, 'key', item.key);

									config.options = instance.getCellEditorOptions(structureField.options);
								}

								var validatorRules = config.validator.rules;
								var validatorRuleName = instance.DATATYPE_VALIDATOR[dataType];

								validatorRules[elementName] = {
									required: required
								};

								if (validatorRuleName) {
									validatorRules[elementName][validatorRuleName] = true;
								}

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

		Liferay.SpreadSheet = SpreadSheet;
	},
	'',
	{
		requires: ['aui-datatable']
	}
);