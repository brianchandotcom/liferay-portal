AUI.add(
	'liferay-ddl-form-builder-field-support',
	function(A) {
		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

		var CSS_FIELD = A.getClassName('form', 'builder', 'field');

		var CSS_FIELD_CONTENT_TOOLBAR = A.getClassName('form', 'builder', 'field', 'content', 'toolbar');

		var CSS_FIELD_REPEATABLE_TOOLBAR = A.getClassName('lfr', 'ddm', 'form', 'field', 'repeatable', 'toolbar');

		var CSS_FIELD_TOOLBAR_CONTAINER = A.getClassName('form', 'builder', 'field', 'toolbar', 'container');

		var CSS_FORM_GROUP = A.getClassName('form', 'group');

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

			dataProviders: {
			},

			settingsForm: {
				valueFn: '_valueSettingsForm'
			}
		};

		FormBuilderSettingsSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance.after(instance._renderFormBuilderField, instance, 'render')
				);
			},

			destructor: function() {
				var instance = this;

				instance.get('settingsForm').destroy();
			},

			getSettings: function() {
				var instance = this;

				var settings = {};

				var settingsForm = instance.get('settingsForm');

				var fieldSettingsJSON = settingsForm.toJSON();

				fieldSettingsJSON.fieldValues.forEach(
					function(item) {
						settings[item.name] = item.value;
					}
				);

				settings.type = instance.get('type');

				return settings;
			},

			getSettingsModal: function() {
				var instance = this;

				var builder = instance.get('builder');

				var settingsModal = builder._fieldSettingsModal;

				return settingsModal;
			},

			hideSettingsModal: function() {
				var instance = this;

				var settingsModal = instance.getSettingsModal();

				settingsModal.hide();
			},

			isNew: function() {
				var instance = this;

				var builder = instance.get('builder');

				return !builder.contains(instance);
			},

			renderSettingsPanel: function() {
				var instance = this;

				instance._updateSettingsFormValues();
			},

			saveSettings: function() {
				var instance = this;

				instance.setAttrs(instance.getSettings());

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

				wrapper.append('<div class="' + CSS_FIELD_TOOLBAR_CONTAINER + '"></div>');

				wrapper.addClass(CSS_FIELD_CONTENT_TOOLBAR);

				if (instance.get('repeatable')) {
					var toolbar = container.one('.' + CSS_FIELD_REPEATABLE_TOOLBAR);

					if (toolbar) {
						toolbar.hide();
					}
				}
			},

			_updateSettingsFormValues: function() {
				var instance = this;

				var settingsForm = instance.get('settingsForm');

				settingsForm.get('fields').forEach(
					function(item, index) {
						item.set('value', instance.get(item.get('name')));
					}
				);
			},

			_valueSettingsForm: function() {
				var instance = this;

				var fieldType = FieldTypes.get(instance.get('type'));

				return new Liferay.DDL.FormBuilderSettingsForm(
					{
						dataProviders: instance.get('dataProviders'),
						definition: fieldType.get('settings'),
						evaluatorURL: instance.get('evaluatorURL'),
						field: instance,
						layout: fieldType.get('settingsLayout'),
						portletNamespace: instance.get('portletNamespace'),
						templateNamespace: 'ddm.settings_form'
					}
				);
			}
		};

		Liferay.namespace('DDL').FormBuilderSettingsSupport = FormBuilderSettingsSupport;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-settings-form', 'liferay-ddl-form-builder-util']
	}
);