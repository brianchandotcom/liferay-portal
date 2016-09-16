AUI.add(
	'liferay-ddl-form-builder-field-settings-sidebar',
	function(A) {
		var CSS_PREFIX = A.getClassName('form', 'builder', 'field', 'settings', 'sidebar');

		var TPL_LOADING = '<div class="loading-icon loading-icon-lg"></div>';

		var TPL_NAVBAR_WRAPER = '<nav class="navbar navbar-default navbar-no-collapse"></nav>';

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

						var eventHandlers;

						eventHandlers = [
							instance.after('open', instance._afterSidebarOpen),
							instance.after('open:start', instance._afterOpenStart)
						];

						instance._eventHandlers = eventHandlers;
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

						var settingsForm = instance.settingsForm;

						var settings = field.getSettings(settingsForm);

						return settings;
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

						if (instance.settingsForm) {
							instance._hideSettingsForm();
						}

						instance._showLoading();

						instance.set('description', field.get('type'));
						instance.set('title', field.get('context').label);

						instance._loadFieldSettingsForm(field);

						toolbar.set('field', field);
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
							}
						);

						var evaluator = settingsForm.get('evaluator');

						evaluator.after(
							'evaluationStarted',
							function() {
								instance.set('title', settingsForm.getField('label').getValue());
							}
						);

						settingsForm.render();

						settingsForm.getFirstPageField().focus();
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

					_hideSettingsForm: function() {
						var instance = this;

						var container = instance.settingsForm.get('container');

						container.addClass('invisible');
					},

					_loadFieldSettingsForm: function(field) {
						var instance = this;

						field.loadSettingsForm().then(
							function(settingsForm) {
								instance.settingsForm = settingsForm;

								instance._configureSideBar();

								settingsForm.evaluate(
									function() {
										instance._showSettingsForm();
										instance._removeLoading();
									}
								);

								field.setAttrs(field.getSettings(settingsForm));

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

					_removeLoading: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						boundingBox.one('.loading-icon').remove();
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

					_setTitle: function(value) {
						return value || Liferay.Language.get('unlabeled');
					},

					_showLoading: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						if (!contentBox.one('.loading-icon')) {
							contentBox.append(TPL_LOADING);
						}
					},

					_showSettingsForm: function() {
						var instance = this;

						var container = instance.settingsForm.get('container');

						container.removeClass('invisible');
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderFieldsSettingsSidebar = FormBuilderFieldsSettingsSidebar;
	},
	'',
	{
		requires: ['aui-tabview', 'liferay-ddl-form-builder-sidebar']
	}
);