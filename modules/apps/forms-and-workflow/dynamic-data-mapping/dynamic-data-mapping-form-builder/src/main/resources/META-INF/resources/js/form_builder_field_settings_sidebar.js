AUI.add(
	'liferay-ddm-form-builder-field-settings-sidebar',
	function(A) {
		var CSS_PREFIX = A.getClassName('form', 'builder', 'field', 'settings', 'sidebar');

		var TPL_LOADING = '<div class="loading-icon loading-icon-lg"></div>';

		var TPL_NAVBAR_WRAPER = '<nav class="navbar navbar-default navbar-no-collapse"></nav>';

		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

		var Lang = A.Lang;

		var FormBuilderFieldsSettingsSidebar = A.Component.create(
			{
				ATTRS: {
					bodyContent: {
						setter: '_setBodyContent'
					},

					builder: {
						value: null
					},

					description: {
						value: ''
					},

					field: {
						value: null
					},

					title: {
						setter: '_setTitle',
						value: ''
					},

					toolbar: {
						valueFn: '_createToolbar'
					}
				},

				CSS_PREFIX: CSS_PREFIX,

				EXTENDS: Liferay.DDM.FormBuilderSidebar,

				NAME: 'liferay-ddm-form-builder-field-settings-sidebar',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers = [
							instance.after('open', instance._afterSidebarOpen),
							instance.before('open', instance._beforeSidebarOpen),
							instance.after('open:start', instance._afterOpenStart),
							instance.before('render', instance._addFieldTypesInToolbar),
							A.one('body').delegate('click', A.bind('changeFieldType', instance), '.' + CSS_PREFIX + ' .lfr-ddm-toolbar-field-type .dropdown-item')
						];
					},

					destructor: function() {
						var instance = this;

						var toolbar = instance.get('toolbar');

						toolbar.destroy();

						instance.destroyFieldSettingsForm();

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					changeFieldType: function(event) {
						var instance = this;

						var fieldNode = event.currentTarget;
						var type = fieldNode.getData('name');

						var fieldType = FieldTypes.get(type);
						var FormBuilder = instance.get('builder');

						var field = FormBuilder.createField(fieldType);
						var previousField = instance.get('field');

						var columns = FormBuilder.getFieldRow(previousField)._data['layout-row'].get('cols');
						var previousSettingsContext = previousField.get('context.settingsContext');
						var settingsRetriever = field.get('settingsRetriever');

						previousField.get('container').addClass(previousField._yuid);

						instance.settingsForm.destroy();

						instance._changeFieldTypeMenu(fieldType);

						settingsRetriever.getSettingsContext(field).then(
							function(settingsContext) {
								field.saveSettings();

								var newSettingsContext = instance._mergeFieldContext(settingsContext, previousSettingsContext);

								field.set('context.settingsContext', newSettingsContext);

								columns.forEach(
									function(column) {
										var node = column.get('node');

										if (node.one('.' + previousField._yuid)) {
											instance._updateField(previousField, field, column);
										}
									}
								);
							}
						);
					},

					destroyFieldSettingsForm: function() {
						var instance = this;

						if (instance.settingsForm) {
							instance.settingsForm.destroy();
						}
					},

					getFieldSettings: function() {
						var instance = this;

						var field = instance.get('field');

						return field.getSettings();
					},

					getPreviousContext: function() {
						var instance = this;

						return instance._previousContext;
					},

					hasChanges: function() {
						var instance = this;

						var previousContext = instance.getPreviousContext();

						var currentFieldSettings = instance.getFieldSettings();

						return JSON.stringify(previousContext) !== JSON.stringify(currentFieldSettings.context);
					},

					hasFocus: function(node) {
						var instance = this;

						var activeElement = A.one(node || document.activeElement);

						var settingsForm = instance.settingsForm;

						return (settingsForm && settingsForm.hasFocus()) || instance._containsNode(activeElement) || instance._isFieldNode(activeElement);
					},

					updateFieldName: function(field) {
						Liferay.DDM.FormBuilderUtil.visitLayout(
							field.get('context.settingsContext').pages,
							function(settingsFormFieldContext) {
								var fieldName = settingsFormFieldContext.fieldName;

								if (fieldName == 'name') {
									settingsFormFieldContext.value = field.get('context.fieldName');
								}
							}
						);

						field.set('context.name', field.get('context.fieldName'));
					},

					_addFieldTypesInToolbar: function() {
						var instance = this;

						var fieldTypes = FieldTypes.getAll().filter(
							function(fieldType) {
								return !fieldType.get('system');
							}
						);

						instance.set('fieldTypeOptions', instance._getFieldTypes(fieldTypes, true));
					},

					_afterOpenStart: function() {
						var instance = this;

						instance._showLoading();
					},

					_afterPressEscapeKey: function() {
						var instance = this;

						if (instance.isOpen()) {
							var field = instance.get('field');

							instance.get('builder').cancelFieldEdition(field);
						}
					},

					_afterSidebarOpen: function() {
						var instance = this;

						var field = instance.get('field');
						var fieldType = FieldTypes.get(field.get('type'));
						var toolbar = instance.get('toolbar');

						instance.set('description', fieldType.get('label'));
						instance.set('title', field.get('context.label'));

						instance._loadFieldSettingsForm(field);

						toolbar.set('field', field);
					},

					_beforeSidebarOpen: function() {
						var instance = this;

						var field = instance.get('field');
						var fieldType = FieldTypes.get(field.get('context.type'));

						instance._changeFieldTypeMenu(fieldType);
					},

					_bindSettingsFormEvents: function() {
						var instance = this;

						var settingsForm = instance.settingsForm;

						var labelField = settingsForm.getField('label');

						labelField.after(
							'valueChange',
							function() {
								instance.set('title', labelField.getValue());
							}
						);
					},

					_changeFieldTypeMenu: function(fieldType) {
						var instance = this;

						A.one('#field-type-menu-content').html(instance._getFieldTypeMenuLayout(fieldType));
					},

					_configureSideBar: function(field) {
						var instance = this;

						var settingsForm = instance.settingsForm;

						var settingsFormContainer = settingsForm.get('container');

						instance.set('bodyContent', settingsFormContainer);

						settingsForm.after(
							'render',
							function() {
								settingsFormContainer.one('.navbar-nav').wrap(TPL_NAVBAR_WRAPER);

								instance._bindSettingsFormEvents();
							}
						);

						settingsForm.render();

						instance._removeLoading();

						instance._setFocusToFirstPageField(settingsForm);

						delete field.newField;
					},

					_containsNode: function(node) {
						var instance = this;

						return instance.get('boundingBox').contains(node);
					},

					_createToolbar: function() {
						var instance = this;

						var toolbar = new Liferay.DDM.FormBuilderFieldOptionsToolbar(
							{
								element: instance.get('boundingBox'),
								formBuilder: instance.get('builder')
							}
						);

						return toolbar;
					},

					_getFieldTypeMenuLayout: function(fieldType) {
						var instance = this;

						return '<div>' + Liferay.Util.getLexiconIconTpl(fieldType.get('icon')) + '</div><span>' + fieldType.get('label') + '</span>' + Liferay.Util.getLexiconIconTpl('caret-bottom');
					},

					_isFieldNode: function(node) {
						var instance = this;

						return node.ancestorsByClassName('.ddm-form-field-container').size();
					},

					_isSameType: function(previousSettingsFormFieldContext, currentSettingsFormFieldContext) {
						return (typeof currentSettingsFormFieldContext.value === typeof previousSettingsFormFieldContext.value);
					},

					_isValueEmpty: function(settingsFormFieldContextValue) {

						if (Lang.isString(settingsFormFieldContextValue)) {
							return settingsFormFieldContextValue.trim() === '';
						}
						else if (Lang.isArray(settingsFormFieldContextValue)) {
							return settingsFormFieldContextValue.length === 0;
						}
						else if (Lang.isObject(settingsFormFieldContextValue)) {
							return A.Object.isEmpty(settingsFormFieldContextValue);
						}
						else if(Lang.isBoolean(settingsFormFieldContextValue)) {
							return false;
						}

						return true;
					},

					_loadFieldSettingsForm: function(field) {
						var instance = this;

						field.loadSettingsForm().then(
							function(settingsForm) {
								instance.settingsForm = settingsForm;

								instance._configureSideBar(field);

								settingsForm.evaluate();

								field.set('context.settingsContext', settingsForm.get('context'));

								instance.updateFieldName(field);

								field.saveSettings();

								instance._saveCurrentContext();

								instance.fire(
									'fieldSettingsFormLoaded',
									{
										field: field,
										settingsForm: settingsForm
									}
								);
							}
						);
					},

					_mergeFieldContext: function(newSettingsContext, previousSettingsContext) {
						var instance = this;

						var FormBuilderUtil = Liferay.DDM.FormBuilderUtil;

						FormBuilderUtil.visitLayout(
							newSettingsContext.pages,
							function(settingsFormFieldContext) {
								var fieldLocalizable = settingsFormFieldContext.localizable;
								var fieldName = settingsFormFieldContext.fieldName;

								FormBuilderUtil.visitLayout(
									previousSettingsContext.pages,
									function(previousSettingsFormFieldContext) {
										var previousFieldLocalizable = previousSettingsFormFieldContext.localizable;
										var previousFieldName = previousSettingsFormFieldContext.fieldName;

										if (!(fieldName === 'type') && fieldName === previousFieldName) {

											if ((fieldLocalizable == true) && (previousFieldLocalizable == true)) {
												settingsFormFieldContext.localizedValue = previousSettingsFormFieldContext.localizedValue;
											}

											if (instance._isSameType(previousSettingsFormFieldContext, settingsFormFieldContext)) {
												if (!instance._isValueEmpty(previousSettingsFormFieldContext.value)) {
													settingsFormFieldContext.value = previousSettingsFormFieldContext.value;
													settingsFormFieldContext.dataType = previousSettingsFormFieldContext.dataType;
												}

											}
											else if (settingsFormFieldContext.localizedValue) {
												var settingsFormFieldContextLocalizedValueKeys = Object.keys(settingsFormFieldContext.localizedValue);

												settingsFormFieldContextLocalizedValueKeys.forEach(
													function(key, index) {
														settingsFormFieldContext.localizedValue[key] = settingsFormFieldContext.value;
													}
												);
											}

										}

									}
								);
							}
						);

						return newSettingsContext;
					},

					_removeLoading: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						boundingBox.removeClass('loading-data');
					},

					_saveCurrentContext: function() {
						var instance = this;

						var field = instance.get('field');

						var fieldContext = field.get('context');

						instance._previousContext = fieldContext;

						instance._previousFormContext = instance.settingsForm.get('context');
					},

					_setBodyContent: function(content) {
						var instance = this;

						if (content) {
							instance.get('boundingBox').one('.sidebar-body').setHTML(content);
						}
					},

					_setFocusToFirstPageField: function(settingsForm) {
						var field = settingsForm.getFirstPageField();

						var container = field.get('container');

						var input = container.one('input');

						if (input) {
							input.focus();
						}
					},

					_setTitle: function(value) {
						return value || Liferay.Language.get('unlabeled');
					},

					_showLoading: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');
						var contentBox = instance.get('contentBox');

						if (!contentBox.one('.loading-icon')) {
							contentBox.append(TPL_LOADING);
						}

						boundingBox.addClass('loading-data');
					},

					_updateField: function(previousField, field, column) {
						var instance = this;

						var fieldset = column.get('value');
						var fieldsetFields = fieldset.get('fields');

						fieldsetFields.forEach(
							function(fieldsetField, index) {
								if (fieldsetField._yuid == previousField._yuid) {
									fieldsetFields[index] = field;
								}
							}
						);

						fieldset.set('fields', fieldsetFields);
						instance.set('field', field);

						instance._loadFieldSettingsForm(field);
						instance.get('builder')._renderField(field);
					}
				}
			}
		);

		Liferay.namespace('DDM').FormBuilderFieldsSettingsSidebar = FormBuilderFieldsSettingsSidebar;
	},
	'',
	{
		requires: ['aui-tabview', 'liferay-ddm-form-builder-sidebar', 'liferay-ddm-form-renderer-types']
	}
);