AUI.add(
	'liferay-ddm-form-renderer',
	function(A) {
		var AArray = A.Array;
		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;
		var Util = Liferay.DDM.Renderer.Util;

		var Form = A.Component.create(
			{
				ATTRS: {
					container: {
						setter: A.one
					},

					definition: {
						value: []
					},

					fields: {
						valueFn: '_valueFields'
					},

					portletNamespace: {
						value: ''
					},

					values: {
					}
				},

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-form-renderer',

				prototype: {
					initializer: function() {
						var instance = this;

						var container = instance.get('container');

						instance.tabView = new A.TabView(
							{
								boundingBox: container,
								srcNode: container.one('.lfr-ddm-form-pages'),
								type: 'pills'
							}
						);

						instance.renderUI();
						instance.bindUI();
					},

					renderUI: function() {
						var instance = this;

						var container = instance.get('container');

						var fields = instance.get('fields');

						AArray.invoke(fields, 'render');

						instance.tabView.render();
					},

					bindUI: function() {
						var instance = this;

						instance.after('definitionChange', instance._afterDefinitionChange);
						instance.after('liferay-ddm-form-renderer-field:remove', A.bind('_afterFieldRemove', instance));
					},

					destructor: function() {
						var instance = this;

						AArray.invoke(instance.get('fields'), 'destroy');
					},

					getFieldNodes: function() {
						var instance = this;

						return instance.get('container').all('.lfr-ddm-form-field-container').filter(
							function(item) {
								return item.ancestors('.field-wrapper', false).size() === 0;
							}
						);
					},

					toJSON: function() {
						var instance = this;

						return {
							fields: AArray.invoke(instance.get('fields'), 'toJSON')
						};
					},

					_afterDefinitionChange: function(event) {
						var instance = this;

						AArray.invoke(instance.get('fields'), 'destroy');

						instance.set('fields', instance._getDefinitionFields(event.newVal));

						AArray.invoke(instance.get('fields'), 'render');
					},

					_getDefinitionFields: function(definition) {
						var instance = this;

						var portletNamespace = instance.get('portletNamespace');

						return AArray.map(
							definition.fields,
							function(item) {
								var fieldValue = Util.searchFieldData(instance.get('values'), 'name', item.name);

								var fieldType = FieldTypes.get(item.type);

								var fieldClassName = fieldType.get('className');

								var fieldClass = Util.getFieldClass(fieldClassName);

								return new fieldClass(
									{
										definition: item,
										fieldType: item.type,
										form: instance,
										parent: instance,
										portletNamespace: portletNamespace,
										value: fieldValue.value
									}
								);
							}
						);
					},

					_getField: function(node) {
						var instance = this;

						var fieldNode = node.one('.field-wrapper');

						var qualifiedName = fieldNode.getData('fieldname');

						var instanceId = Util.getInstanceIdFromQualifiedName(qualifiedName);
						var name = Util.getFieldNameFromQualifiedName(qualifiedName);

						var fieldDefinition = Util.searchFieldData(instance.get('definition'), 'name', name);

						var fieldType = FieldTypes.get(fieldDefinition.type);

						var fieldClassName = fieldType.get('className');

						var fieldClass = Util.getFieldClass(fieldClassName);

						var field = new fieldClass(
							{
								container: node,
								definition: fieldDefinition,
								fieldType: fieldDefinition.type,
								form: instance,
								instanceId: instanceId,
								parent: instance,
								portletNamespace: instance.get('portletNamespace')
							}
						);

						field.addTarget(instance);

						return field;
					},

					_valueFields: function() {
						var instance = this;

						var fieldNodes = instance.getFieldNodes();

						var fields = [];

						if (fieldNodes.size() > 0) {
							fieldNodes.each(
								function(item) {
									fields.push(instance._getField(item));
								}
							);
						}
						else {
							var definition = instance.get('definition');

							fields = instance._getDefinitionFields(definition);
						}

						return fields;
					}
				}
			}
		);

		Liferay.namespace('DDM.Renderer').Form = Form;
	},
	'',
	{
		requires: ['array-extras', 'aui-tabview', 'liferay-ddm-form-field', 'liferay-ddm-form-field-checkbox', 'liferay-ddm-form-field-checkbox-template', 'liferay-ddm-form-field-options', 'liferay-ddm-form-field-options-template', 'liferay-ddm-form-field-radio', 'liferay-ddm-form-field-radio-template', 'liferay-ddm-form-field-select', 'liferay-ddm-form-field-select-template', 'liferay-ddm-form-field-text-template', 'liferay-ddm-form-renderer-field-types', 'liferay-ddm-form-renderer-util']
	}
);