AUI.add(
	'liferay-ddl-form-builder-field-support',
	function(A) {
		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

		var CSS_FIELD = A.getClassName('form', 'builder', 'field');

		var CSS_FIELD_CONTENT_TOOLBAR = A.getClassName('form', 'builder', 'field', 'content', 'toolbar');

		var CSS_FIELD_REPEATABLE_TOOLBAR = A.getClassName('lfr', 'ddm', 'form', 'field', 'repeatable', 'toolbar');

		var CSS_FIELD_TOOLBAR_CONTAINER = A.getClassName('form', 'builder', 'field', 'toolbar', 'container');

		var CSS_FIELD_WRAPPER = A.getClassName('field', 'wrapper');

		var FormBuilderFieldSupport = function() {};

		FormBuilderFieldSupport.ATTRS = {
			builder: {
				value: null
			},

			content: {
				getter: function() {
					var instance = this;

					return instance.get('container');
				}
			},

			settingsForm: {
				valueFn: '_valueSettingsForm'
			}
		};

		FormBuilderFieldSupport.prototype = {
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

				fieldSettingsJSON.fields.forEach(
					function(item) {
						settings[item.name] = item.value;
					}
				);

				settings.type = instance.get('type');

				settings.visibilityExpression = 'true';

				return settings;
			},

			renderSettingsPanel: function(bodyNode) {
				var instance = this;

				var settingsForm = instance.get('settingsForm');

				settingsForm.set('container', bodyNode);

				instance._updateSettingsFormValues();
			},

			saveSettings: function() {
				var instance = this;

				instance.setAttrs(instance.getSettings());

				instance.render();

				instance.fire(
					'field:saveSettings',
					{
						field: instance
					}
				);
			},

			validateSettings: function() {
				var instance = this;

				var builder = instance.get('builder');

				var settingsForm = instance.get('settingsForm');

				var nameSettingsField = settingsForm.getField('name');

				var field = builder.getField(nameSettingsField.getValue());

				var hasField = !!field && field !== instance;

				nameSettingsField.get('container').toggleClass('has-error', hasField);

				return !hasField;
			},

			_renderFormBuilderField: function() {
				var instance = this;

				var container = instance.get('container');

				container.addClass(CSS_FIELD);

				container.setData('field-instance', instance);

				var wrapper = container.one('.' + CSS_FIELD_WRAPPER);

				wrapper.append('<div class="' + CSS_FIELD_TOOLBAR_CONTAINER + '"></div>');

				wrapper.addClass(CSS_FIELD_CONTENT_TOOLBAR);

				if (instance.get('repeatable')) {
					var toolbar = container.one('.' + CSS_FIELD_REPEATABLE_TOOLBAR);

					toolbar.hide();
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

				return new Liferay.DDM.Renderer.Form(
					{
						definition: fieldType.get('settings'),
						portletNamespace: instance.get('portletNamespace')
					}
				);
			}
		};

		Liferay.namespace('DDL').FormBuilderFieldSupport = FormBuilderFieldSupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-field-checkbox-template', 'liferay-ddm-form-field-options-template', 'liferay-ddm-form-field-radio-template', 'liferay-ddm-form-field-select-template', 'liferay-ddm-form-field-text-template', 'liferay-ddm-form-field-types', 'liferay-ddm-form-renderer', 'liferay-ddm-form-renderer-field']
	}
);