AUI.add(
	'liferay-ddl-form-builder-definition-serializer',
	function(A) {
		var AArray = A.Array;

		var DefinitionSerializer = A.Component.create(
			{
				ATTRS: {
					fieldHandler: {
						valueFn: '_valueFieldHandler'
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

						instance.get('fields').push(
							A.merge(
								config,
								{
									dataType: 'string',
									type: field.get('fieldType')
								}
							)
						);
					},

					_valueFieldHandler: function() {
						var instance = this;

						return instance.serializeField;
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