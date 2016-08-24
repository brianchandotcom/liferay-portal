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
						value: 'No description'
					},

					field: {
						value: null
					},

					title: {
						value: 'Untitle'
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

					getFieldSettings: function() {
						var instance = this;

						var field = instance.get('field');

						var settingsForm = instance.settingsForm;

						var settings = field.getSettings(settingsForm);

						return settings;
					},

					_afterOpenStart: function() {
						var instance = this;

						instance._showLoading();
					},

					_afterSidebarOpen: function() {
						var instance = this;

						var field = instance.get('field');

						var toolbar = instance.get('toolbar');

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

						var evaluator = settingsForm.get('evaluator');

						instance.set('bodyContent', settingsFormContainer);

						settingsForm.after(
							'render',
							function() {
								settingsFormContainer.one('.navbar-nav').wrap(TPL_NAVBAR_WRAPER);
							}
						);

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

					_loadFieldSettingsForm: function(field) {
						var instance = this;

						field.loadSettingsForm().then(
							function(settingsForm) {
								instance.settingsForm = settingsForm;

								instance._configureSideBar();

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

					_showLoading: function() {
						var instance = this;

						var bodyContent = instance.get('bodyContent');

						instance.set('description', '');
						instance.set('title', '');

						if (bodyContent !== TPL_LOADING) {
							instance.set('bodyContent', TPL_LOADING);
						}
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