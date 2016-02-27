AUI.add(
	'liferay-ddm-form-renderer-definition',
	function(A) {
		var AArray = A.Array;
		var Renderer = Liferay.DDM.Renderer;

		var Util = Renderer.Util;

		var FormDefinitionSupport = function() {
		};

		FormDefinitionSupport.ATTRS = {
			definition: {
				value: {}
			},

			fields: {
				valueFn: '_valueFields'
			},

			values: {
				value: {}
			}
		};

		FormDefinitionSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance.after('definitionChange', instance._afterDefinitionChange),
					instance.after('valuesChange', instance._afterValuesChange)
				);
			},

			_afterDefinitionChange: function(event) {
				var instance = this;

				AArray.invoke(instance.get('fields'), 'destroy');

				instance.set('fields', instance._createFieldsFromDefinition(event.newVal));
			},

			_afterValuesChange: function(event) {
				var instance = this;

				AArray.invoke(instance.get('fields'), 'destroy');

				instance.set('fields', instance._createFieldsFromValues(event.newVal));
			},

			_createFieldsFromDefinition: function(definition) {
				var instance = this;

				var portletNamespace = instance.get('portletNamespace');

				return A.map(
					definition.fields,
					function(item) {
						var fieldClass = Util.getFieldClass(item.type);

						return new fieldClass(
							A.merge(
								item,
								{
									parent: instance,
									portletNamespace: portletNamespace
								}
							)
						);
					}
				);
			},

			_createFieldsFromValues: function(values) {
				var instance = this;

				var definition = instance.get('definition');
				var portletNamespace = instance.get('portletNamespace');

				var fields = [];

				var getField = function(name) {
					return AArray.find(
						fields,
						function(item) {
							return item.get('name') === name;
						}
					);
				};

				values.fieldValues.forEach(
					function(item) {
						var name = item.name;

						var siblings = Util.searchFieldsByKey(values, name);

						var config = A.merge(
							Util.getFieldByKey(definition, name),
							{
								parent: instance,
								portletNamespace: portletNamespace,
								readOnly: instance.get('readOnly'),
								readOnlyFields: instance.get('readOnlyFields'),
								repeatedIndex: siblings.indexOf(item)
							}
						);

						var fieldClass = Util.getFieldClass(config.type);

						var fieldInstance = new fieldClass(A.merge(config, item));

						var createdField = getField(name);

						if (createdField) {
							var repetitions = createdField.get('repetitions');

							repetitions.push(fieldInstance);

							fieldInstance.set('repetitions', repetitions);
						}
						else {
							fields.push(fieldInstance);
						}
					}
				);

				return fields;
			},

			_valueFields: function(val) {
				var instance = this;

				var definition = instance.get('definition');
				var values = instance.get('values');

				if (values.fieldValues) {
					val = instance._createFieldsFromValues(values);
				}
				else if (definition.fields) {
					val = instance._createFieldsFromDefinition(definition);
				}

				return val;
			}
		};

		Liferay.namespace('DDM.Renderer').FormDefinitionSupport = FormDefinitionSupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-types', 'liferay-ddm-form-renderer-util']
	}
);