AUI.add(
	'liferay-ddm-form-builder-fieldset',
	function(A) {
		var AArray = A.Array;

		var _fieldSets = [];

		var formBuilderFieldSetDefinitionRetriever = new Liferay.DDM.FormBuilderFieldSetDefinitionRetriever();

		var FieldSets = {
			get: function(id) {
				var instance = this;

				return AArray.find(
					_fieldSets,
					function(item, index) {
						return item.get('id') === id;
					}
				);
			},

			getAll: function() {
				var instance = this;

				return _fieldSets;
			},

			getDefinitionRetriever: function() {
				var instance = this;

				return formBuilderFieldSetDefinitionRetriever;
			},

			register: function(fieldSets) {
				var instance = this;

				_fieldSets = AArray(fieldSets).map(instance._getFieldSet);
			},

			_getFieldSet: function(config) {
				var instance = this;

				var fieldSet = new Liferay.DDM.FormRendererFieldType(
					{
						defaultConfig: {
							type: config.id
						},
						fieldClass: Liferay.DDM.Renderer.Field,
						icon: config.icon,
						label: A.Lang.String.escapeHTML(config.name)
					}
				);

				fieldSet.set('id', config.id);
				fieldSet.set('name', config.name);
				fieldSet.set('description', config.description);

				return fieldSet;
			}
		};

		Liferay.namespace('DDM').FieldSets = FieldSets;
	},
	'',
	{
		requires: ['array-extras', 'liferay-ddm-form-builder-fieldset-definition-retriever', 'liferay-ddm-form-renderer-type']
	}
);