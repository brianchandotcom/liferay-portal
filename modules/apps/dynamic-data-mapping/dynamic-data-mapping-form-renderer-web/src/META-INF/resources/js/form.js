AUI.add(
	'liferay-ddm-form-renderer',
	function(A) {
		var AArray = A.Array;
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
						).render();

						AArray.invoke(instance.get('fields'), 'render');

						instance.after('liferay-ddm-form-renderer-field:remove', A.bind(instance, instance._afterFieldRemove));
					},

					getFieldNodes: function() {
						var instance = this;

						return instance.get('container').all('.lfr-ddm-form-field-container').filter(
							function(item) {
								return item.ancestors('.field-wrapper', false).size() === 0;
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

						var FieldClass = Util.getFieldClass(fieldDefinition);

						var field = new FieldClass(
							{
								container: node,
								definition: fieldDefinition,
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

						var fields = [];

						instance.getFieldNodes().each(
							function(item) {
								fields.push(instance._getField(item));
							}
						);

						return fields;
					}
				}
			}
		);

		Liferay.namespace('DDM.Renderer').Form = Form;
	},
	'',
	{
		requires: ['array-extras', 'aui-tabview', 'liferay-ddm-form-renderer-field', 'liferay-ddm-form-renderer-field-types', 'liferay-ddm-form-renderer-util']
	}
);