AUI.add(
	'liferay-ddl-form-builder-field-support',
	function(A) {
		var Renderer = Liferay.DDM.Renderer;

		var FieldTypes = Renderer.FieldTypes;

		var Settings = Liferay.DDL.Settings;

		var FormBuilderUtil = Liferay.DDL.FormBuilderUtil;

		var CSS_FIELD = A.getClassName('form', 'builder', 'field');

		var CSS_FIELD_CONTENT_TARGET = A.getClassName('form', 'builder', 'field', 'content', 'target');

		var CSS_FORM_GROUP = A.getClassName('form', 'group');

		var FormBuilderSettingsSupport = function() {
		};

		FormBuilderSettingsSupport.ATTRS = {
			builder: {
				value: null
			},

			content: {
				getter: function() {
					var instance = this;

					return instance.get('container');
				}
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

			copy: function() {
				var instance = this;

				var builder = instance.get('builder');

				var config = instance.copyConfiguration();

				var type = instance.get('type');

				var fieldType = FieldTypes.get(type);

				var copy = builder.createField(fieldType, config);

				FormBuilderUtil.visitLayout(
					copy.get('settingsContext').pages,
					function(settingsFormFieldContext) {
						var fieldName = settingsFormFieldContext.fieldName;

						if (fieldName === 'name') {
							settingsFormFieldContext.value = copy.generateFieldName(instance.get('fieldName'));
						}
					}
				);

				return copy;
			},

			createSettingsForm: function(context) {
				var instance = this;

				var builder = instance.get('builder');

				var settingsForm = new Liferay.DDL.FormBuilderSettingsForm(
					{
						context: context,
						editMode: builder.isEditMode() || instance.isPersisted(),
						evaluatorURL: Settings.evaluatorURL,
						field: instance,
						portletNamespace: Settings.portletNamespace,
						templateNamespace: 'ddm.settings_form'
					}
				);

				var dataTypeField = settingsForm.getField('dataType');

				dataTypeField.set('value', 'string');

				return settingsForm;
			},

			generateFieldName: function(key) {
				var instance = this;

				var counter = 0;

				var builder = instance.get('builder');

				var existingField;

				if (!key) {
					key = instance.get('context').type;
				}

				var name = key;

				if (name) {
					do {
						if (counter > 0) {
							name = key + counter;
						}

						existingField = builder.findField(name, true);

						counter++;
					}
					while (existingField !== undefined && existingField !== instance);
				}

				return name;
			},

			getSettings: function() {
				var instance = this;

				var settings = {};

				var context = instance.get('context.settingsContext');

				var defaultLocale = themeDisplay.getDefaultLanguageId();

				var locale = instance.get('locale');

				FormBuilderUtil.visitLayout(
					context.pages,
					function(settingsFormFieldContext) {
						var fieldName = settingsFormFieldContext.fieldName;

						if (settingsFormFieldContext.localizable) {
							var localizedValue = settingsFormFieldContext.localizedValue[locale] || settingsFormFieldContext.localizedValue[defaultLocale] || '';

							settings[fieldName] = localizedValue;

							settingsFormFieldContext.value = localizedValue;
						}
						else if (settingsFormFieldContext.type === 'options') {
							settings[fieldName] = settingsFormFieldContext.value[locale] || settingsFormFieldContext.value[defaultLocale];
						}
						else {
							settings[fieldName] = settingsFormFieldContext.value;
						}
					}
				);

				settings.readOnly = true;
				settings.type = instance.get('type');
				settings.value = '';
				settings.visible = true;

				return settings;
			},

			isAdding: function() {
				var instance = this;

				var builder = instance.get('builder');

				return !builder.contains(instance);
			},

			isPersisted: function() {
				var instance = this;

				var builder = instance.get('builder');

				var context = builder.get('context');

				var persisted = false;

				FormBuilderUtil.visitLayout(
					context.pages,
					function(formFieldContext) {
						if (instance.get('fieldName') === formFieldContext.fieldName) {
							persisted = true;
						}
					}
				);

				return persisted;
			},

			loadSettingsForm: function() {
				var instance = this;

				var settingsRetriever = instance.get('settingsRetriever');

				return settingsRetriever.getSettingsContext(instance)
					.then(
						function(settingsContext) {
							return instance.createSettingsForm(settingsContext);
						}
					);
			},

			saveSettings: function() {
				var instance = this;

				var settings = instance.getSettings();

				instance.setAttrs(settings);
				instance.set('context', settings);

				instance.render();

				instance.fire(
					'field:saveSettings',
					{
						field: instance
					}
				);
			},

			_renderFormBuilderField: function() {
				var instance = this;

				var container = instance.get('container');

				container.addClass(CSS_FIELD);

				container.setData('field-instance', instance);

				var wrapper = container.one('.' + CSS_FORM_GROUP);

				wrapper.append('<div class="' + CSS_FIELD_CONTENT_TARGET + '"></div>');
			},

			_valueSettingsRetriever: function() {
				var instance = this;

				return new Liferay.DDL.FormBuilderSettingsRetriever();
			}
		};

		Liferay.namespace('DDL').FormBuilderSettingsSupport = FormBuilderSettingsSupport;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-field-settings-form', 'liferay-ddl-form-builder-settings-retriever', 'liferay-ddl-form-builder-util', 'liferay-ddm-form-renderer-util']
	}
);