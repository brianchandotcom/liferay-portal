AUI.add(
	'liferay-ddm-form-renderer-field-types',
	function(A) {
		var AArray = A.Array;
		var AObject = A.Object;
		var Util = Liferay.DDM.Renderer.Util;

		var FieldTypes = {
			_fieldTypes: [],

			_getFieldType: function(config) {
				var instance = this;

				var defaultConfig = {
					fieldType: config.name
				};

				var fieldClass = Util.getFieldClass(defaultConfig);

				var fieldType = new A.FormBuilderFieldType(
					{
						defaultConfig: defaultConfig,
						fieldClass: fieldClass,
						icon: config.icon,
						label: config.label,
					}
				);

				fieldType.set('name', config.name);
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

			getFieldTypeTemplate: function(type, context) {
				var instance = this;

				var fieldType = instance.get(type);

				var templateNamespace = fieldType.get('templateNamespace');

				var renderer = AObject.getValue(window, templateNamespace.split('.'));

				return renderer(context);
			},

			register: function(fieldTypes) {
				var instance = this;

				instance._fieldTypes = AArray.map(AArray(fieldTypes), instance._getFieldType);
			}
		};

		Liferay.namespace('DDM.Renderer').FieldTypes = FieldTypes;
	},
	'',
	{
		requires: ['array-extras', 'aui-form-builder-field-type', 'liferay-ddm-form-renderer-util']
	}
);