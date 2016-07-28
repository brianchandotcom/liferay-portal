AUI.add(
	'liferay-ddm-form-renderer-types',
	function(A) {
		var AArray = A.Array;

		var _fieldTypes = [];

		var FieldTypes = {
			get: function(type) {
				var instance = this;

				return AArray.find(
					_fieldTypes,
					function(item, index) {
						return item.get('name') === type;
					}
				);
			},

			getAll: function(includeSystem) {
				var instance = this;

				return _fieldTypes;
			},

			register: function(fieldTypes) {
				var instance = this;

				_fieldTypes = AArray(fieldTypes).map(instance._getFieldType);
			},

			_getFieldType: function(config) {
				var instance = this;

				var fieldType = new Liferay.DDM.FormRendererFieldType(
					{
						defaultConfig: {
							type: config.name
						},
						fieldClass: Liferay.DDM.Renderer.Field,
						icon: config.icon,
						label: config.label
					}
				);

				fieldType.set('className', config.javaScriptClass);
				fieldType.set('name', config.name);
				fieldType.set('system', config.system);

				if (config.templateNamespace) {
					fieldType.set('templateNamespace', config.templateNamespace);
				}

				return fieldType;
			}
		};

		Liferay.namespace('DDM.Renderer').FieldTypes = FieldTypes;
	},
	'',
	{
		requires: ['array-extras', 'liferay-ddm-form-renderer-type']
	}
);