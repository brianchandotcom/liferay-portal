AUI.add(
	'liferay-ddl-form-builder-field-support',
	function(A) {
		var CSS_FIELD = A.getClassName('form', 'builder', 'field');

		var CSS_FIELD_CONTENT_TOOLBAR = A.getClassName('form', 'builder', 'field', 'content', 'toolbar');

		var CSS_FIELD_REPEATABLE_TOOLBAR = A.getClassName('lfr', 'ddm', 'form', 'field', 'repeatable', 'toolbar');

		var CSS_FIELD_TOOLBAR_CONTAINER = A.getClassName('form', 'builder', 'field', 'toolbar', 'container');

		var CSS_FORM_GROUP = A.getClassName('form', 'group');

		var RendererUtil = Liferay.DDM.Renderer.Util;

		var FormBuilderSettingsSupport = function() {
		};

		FormBuilderSettingsSupport.ATTRS = {
			builder: {
				value: null
			},

			evaluatorURL: {
			},

			content: {
				getter: function() {
					var instance = this;

					return instance.get('container');
				}
			},

			getFieldTypeSettingFormContextURL: {
				value: ''
			},

			settingsRetriever: {
				valueFn: '_valueSettingsRetriever'
			}
		};

		FormBuilderSettingsSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance.after(instance._renderFormBuilderField, instance, 'render')
				);
			},

			getSettings: function(settingsForm) {
				var instance = this;

				var settings = {};

				var fieldSettingsJSON = settingsForm.toJSON();

				fieldSettingsJSON.fieldValues.forEach(
					function(item) {
						var name = item.name;

						if (name === 'name') {
							name = 'fieldName';
						}

						settings[name] = item.value;
					}
				);

				settings.dataType = instance.get('dataType');
				settings.readOnly = true;
				settings.type = instance.get('type');
				settings.value = '';
				settings.visible = true;

				settings.context = A.clone(settings);

				return settings;
			},

			getSettingsModal: function() {
				var instance = this;

				var builder = instance.get('builder');

				var settingsModal = builder._fieldSettingsModal;

				return settingsModal;
			},

			isAdding: function() {
				var instance = this;

				var builder = instance.get('builder');

				return !builder.contains(instance);
			},

			isPersisted: function() {
				var instance = this;

				var builder = instance.get('builder');

				var definition = builder.get('definition');

				var searchResults = RendererUtil.searchFieldsByKey(definition, instance.get('fieldName'), 'fieldName');

				return searchResults.length === 0;
			},

			loadSettingsForm: function() {
				var instance = this;

				var settingsRetriever = instance.get('settingsRetriever');

				return settingsRetriever
					.getSettingsContext(instance.get('type'))
					.then(
						function(context) {
							var settingsForm = instance._createSettingsForm(context);

							instance._updateSettingsFormValues(settingsForm);

							return settingsForm;
						}
					);
			},

			saveSettings: function(settingsForm) {
				var instance = this;

				instance.setAttrs(instance.getSettings(settingsForm));

				instance.render();

				instance.fire(
					'field:saveSettings',
					{
						field: instance
					}
				);
			},

			_createSettingsForm: function(context) {
				var instance = this;

				var builder = instance.get('builder');

				return new Liferay.DDL.FormBuilderSettingsForm(
					{
						context: context,
						definition: JSON.parse(context.definition),
						editMode: builder.get('recordSetId') === 0 || instance.isPersisted(),
						evaluatorURL: instance.get('evaluatorURL'),
						field: instance,
						layout: JSON.parse(context.layout),
						portletNamespace: instance.get('portletNamespace'),
						templateNamespace: 'ddm.settings_form'
					}
				);
			},

			_renderFormBuilderField: function() {
				var instance = this;

				var container = instance.get('container');

				container.addClass(CSS_FIELD);

				container.setData('field-instance', instance);

				var wrapper = container.one('.' + CSS_FORM_GROUP);

				wrapper.append('<div class="' + CSS_FIELD_TOOLBAR_CONTAINER + '"></div>');

				wrapper.addClass(CSS_FIELD_CONTENT_TOOLBAR);

				if (instance.get('repeatable')) {
					var toolbar = container.one('.' + CSS_FIELD_REPEATABLE_TOOLBAR);

					if (toolbar) {
						toolbar.hide();
					}
				}
			},

			_updateSettingsFormValues: function(settingsForm) {
				var instance = this;

				var context = instance.get('context');

				settingsForm.get('fields').forEach(
					function(item, index) {
						var name = item.get('fieldName');

						if (name === 'name') {
							name = 'fieldName';
						}

						if (context.hasOwnProperty(name)) {
							item.set('errorMessage', '');
							item.set('valid', true);
							item.set('value', context[name]);
						}
					}
				);
			},

			_valueSettingsRetriever: function() {
				var instance = this;

				return new Liferay.DDL.FormBuilderSettingsRetriever(
					{
						getFieldTypeSettingFormContextURL: instance.get('getFieldTypeSettingFormContextURL'),
						portletNamespace: instance.get('portletNamespace')
					}
				);
			}
		};

		Liferay.namespace('DDL').FormBuilderSettingsSupport = FormBuilderSettingsSupport;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-settings-form', 'liferay-ddl-form-builder-settings-retriever', 'liferay-ddl-form-builder-util', 'liferay-ddm-form-renderer-util']
	}
);