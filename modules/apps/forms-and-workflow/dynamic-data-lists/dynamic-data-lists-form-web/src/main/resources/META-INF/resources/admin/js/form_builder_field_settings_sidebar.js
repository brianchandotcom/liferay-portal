AUI.add(
	'liferay-ddl-form-builder-field-settings-sidebar',
	function(A) {
		var CSS_PREFIX = A.getClassName('form', 'builder', 'field', 'settings', 'sidebar');

		var TPL_LOADING = '<div class="loading-icon loading-icon-lg"></div>';

		var TPL_NAVBAR_WRAPER = '<nav class="navbar navbar-default navbar-no-collapse"></nav>';

		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

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

				EXTENDS: Liferay.DDL.FormBuilderSidebar,

				NAME: 'liferay-ddl-form-builder-field-settings-sidebar',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers = [
							A.getDoc().on('click', A.bind(instance._onClickDocument, instance)),
							instance.after('open', instance._afterSidebarOpen),
							instance.after('open:start', instance._afterOpenStart)
						];
					},

					destructor: function() {
						var instance = this;

						var toolbar = instance.get('toolbar');

						toolbar.destroy();

						instance.destroyFieldSettingsForm();

						(new A.EventHandle(instance._eventHandlers)).detach();
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

						var toolbar = instance.get('toolbar');

						var fieldType = FieldTypes.get(field.get('type'));

						instance.set('description', fieldType.get('label'));
						instance.set('title', field.get('context.label'));

						instance._loadFieldSettingsForm(field);

						toolbar.set('field', field);
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

					_configureSideBar: function() {
						var instance = this;

						var settingsForm = instance.settingsForm;

						var settingsFormContainer = settingsForm.get('container');

						instance.set('bodyContent', settingsFormContainer);

						settingsForm.after(
							'render',
							function() {
								settingsFormContainer.one('.navbar-nav').wrap(TPL_NAVBAR_WRAPER);

								settingsForm.getFirstPageField().focus();

								instance._bindSettingsFormEvents();
							}
						);

						settingsForm.render();
					},

					_containsNode: function(node) {
						var instance = this;

						return instance.get('boundingBox').contains(node);
					},

					_createToolbar: function() {
						var instance = this;

						var toolbar = new Liferay.DDL.FormBuilderFieldOptionsToolbar(
							{
								formBuilder: instance.get('builder')
							}
						);

						return toolbar;
					},

					_isFieldNode: function(node) {
						var instance = this;

						return node.ancestorsByClassName('.ddm-form-field-container').size();
					},

					_loadFieldSettingsForm: function(field) {
						var instance = this;

						field.loadSettingsForm().then(
							function(settingsForm) {
								instance.settingsForm = settingsForm;

								instance._configureSideBar();

								settingsForm.evaluate(
									function() {
										instance._removeLoading();

										instance._setFocusToFirstPageField(settingsForm);
									}
								);

								var settingsFormContext = settingsForm.get('context');

								field.set('context.settingsContext', settingsFormContext);

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

					_onClickDocument: function(event) {
						var instance = this;

						if (instance.get('open') && !instance.hasFocus(event.target)) {
							instance.close();
						}
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
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderFieldsSettingsSidebar = FormBuilderFieldsSettingsSidebar;
	},
	'',
	{
		requires: ['aui-tabview', 'liferay-ddl-form-builder-sidebar', 'liferay-ddm-form-renderer-types']
	}
);