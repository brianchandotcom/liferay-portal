AUI.add(
	'liferay-ddm-form-renderer-field-types',
	function(A) {
		var AArray = A.Array;

		var FieldTypes = {
			_fieldTypes: [],

			_getFieldType: function(config) {
				var instance = this;

				var defaultConfig = A.merge(
					config.settings,
					{
						type: config.name
					}
				);

				var fieldType = new A.FormBuilderFieldType(
					{
						defaultConfig: defaultConfig,
						fieldClass: Liferay.DDM.Renderer.Field,
						icon: config.icon,
						label: config.name
					}
				);

				fieldType.set('className', config.javaScriptClass);
				fieldType.set('name', config.name);
				fieldType.set('settings', config.settings);
				fieldType.set('templateNamespace', config.templateNamespace);

				return fieldType;
			},

			get: function(type) {
				var instance = this;

				return AArray.find(
					instance._fieldTypes,
					function(item, index) {
						return item.get('name') === type;
					}
				);
			},

			getAll: function() {
				var instance = this;

				return instance._fieldTypes;
			},

			register: function(fieldTypes) {
				var instance = this;

				instance._fieldTypes = AArray(fieldTypes).map(instance._getFieldType);
			}
		};

		Liferay.namespace('DDM.Renderer').FieldTypes = FieldTypes;
	},
	'',
	{
		requires: ['array-extras', 'aui-form-builder-field-type']
	}
);