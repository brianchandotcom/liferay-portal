AUI.add(
	'liferay-ddm-form-renderer-definition',
	function(A) {
		var _ = AUI._;
		var Renderer = Liferay.DDM.Renderer;

		var FieldTypes = Renderer.FieldTypes;
		var Util = Renderer.Util;

		var DefinitionSupport = function() {
		};

		DefinitionSupport.ATTRS = {
			definition: {
				value: {}
			},

			values: {
				value: {}
			}
		};

		DefinitionSupport.prototype = {
			initializer: function() {
				var instance = this;

				var definition = instance.get('definition');

				var values = instance.get('values');

				var fields = instance.get('fields');

				if (values.fieldValues) {
					fields = instance._createFieldsFromValues(values);
				}
				else if (definition.fields) {
					fields = instance._createFieldsFromDefinition(definition);
				}

				instance.set('fields', fields);

				instance.after('definitionChange', instance._afterDefinitionChange);
				instance.after('valuesChange', instance._afterValuesChange);
			},

			_afterDefinitionChange: function(event) {
				var instance = this;

				_.invoke(instance.get('fields'), 'destroy');

				instance.set('fields', instance._createFieldsFromDefinition(event.newVal));
			},

			_afterValuesChange: function(event) {
				var instance = this;

				_.invoke(instance.get('fields'), 'destroy');

				instance.set('fields', instance._createFieldsFromValues(event.newVal));
			},

			_createFieldsFromDefinition: function(definition) {
				var instance = this;

				var portletNamespace = instance.get('portletNamespace');

				return _.map(
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

				var portletNamespace = instance.get('portletNamespace');

				var definition = instance.get('definition');

				return _.map(
					values.fieldValues,
					function(item) {
						var siblings = Util.searchFieldsByKey(values, item.name);

						var config = A.merge(
							Util.getFieldByKey(definition, item.name),
							{
								parent: instance,
								portletNamespace: portletNamespace,
								repeatedIndex: _.indexOf(siblings, item)
							}
						);

						var fieldClass = Util.getFieldClass(config.type);

						return new fieldClass(A.merge(config, item));
					}
				);
			}
		};

		Liferay.namespace('DDM.Renderer').DefinitionSupport = DefinitionSupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field-types', 'liferay-ddm-form-renderer-util']
	}
);