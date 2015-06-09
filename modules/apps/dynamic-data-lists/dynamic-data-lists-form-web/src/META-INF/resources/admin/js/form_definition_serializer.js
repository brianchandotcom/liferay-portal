AUI.add(
	'liferay-ddl-form-builder-definition-serializer',
	function(A) {
		var AArray = A.Array;

		var DefinitionSerializer = A.Component.create(
			{
				ATTRS: {
					columnHandler: {
						valueFn: '_valueColumnHandler'
					},

					fields: {
						value: []
					}
				},

				EXTENDS: Liferay.DDL.LayoutVisitor,

				NAME: 'liferay-ddl-form-builder-definition-serializer',

				prototype: {
					serialize: function() {
						var instance = this;

						instance.visit();

						var layouts = instance.get('layouts');

						var definition = A.JSON.stringify(
							{
								availableLanguageIds: ['en_US'],
								defaultLanguageId: 'en_US',
								fields: instance.get('fields')
							}
						);

						instance.set('fields', []);

						return definition;
					},

					serializeColumn: function(column) {
						var instance = this;

						var value = column.get('value');

						if (A.instanceOf(value, Liferay.DDM.Renderer.Field)) {
							var field = instance.serializeField(value);

							instance.get('fields').push(field);
						}
					},

					serializeField: function(field) {
						var instance = this;

						var config = {};

						var settingsForm = field.get('settingsForm');

						var settingsJSON = settingsForm.toJSON();

						AArray.each(
							settingsJSON.fields,
							function(item, index) {
								config[item.name] = item.value;
							}
						);

						return A.merge(
							config,
							{
								dataType: 'string',
								type: field.get('fieldType')
							}
						);
					},

					_valueColumnHandler: function() {
						var instance = this;

						return instance.serializeColumn;
					}
				}
			}
		);

		Liferay.namespace('DDL').DefinitionSerializer = DefinitionSerializer;
	},
	'',
	{
		requires: ['json', 'liferay-ddl-form-builder-layout-visitor']
	}
);