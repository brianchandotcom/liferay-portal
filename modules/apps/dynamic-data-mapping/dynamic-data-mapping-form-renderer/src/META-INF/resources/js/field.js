AUI.add(
	'liferay-ddm-form-renderer-field',
	function(A) {
		var AArray = A.Array;
		var AObject = A.Object;
		var Lang = A.Lang;
		var Renderer = Liferay.DDM.Renderer;

		var FieldTypes = Renderer.FieldTypes;
		var Util = Renderer.Util;

		var Field = A.Component.create(
			{
				ATTRS: {
					container: {
						setter: A.one,
						valueFn: '_valueContainer'
					},

					fieldNamespace: {
						value: ''
					},

					instanceId: {
						valueFn: '_valueInstanceId'
					},

					label: {
						getter: '_getLabel',
						value: ''
					},

					locale: {
						value: themeDisplay.getLanguageId()
					},

					localizable: {
						setter: A.DataType.Boolean.parse,
						value: true
					},

					name: {
						value: ''
					},

					parent: {
						valueFn: '_valueParent'
					},

					portletNamespace: {
						value: ''
					},

					predefinedValue: {
						valueFn: '_getDefaultValue'
					},

					readOnly: {
						value: false
					},

					required: {
						setter: A.DataType.Boolean.parse,
						value: false
					},

					showLabel: {
						setter: A.DataType.Boolean.parse,
						value: true
					},

					tip: {
						value: {}
					},

					type: {
						value: ''
					},

					value: {
						valueFn: '_getDefaultValue'
					},

					visibilityExpression: {
						value: 'true'
					}
				},

				AUGMENTS: [Renderer.FieldEventsSupport, Renderer.FieldRepetitionSupport, Renderer.FieldValidationSupport, Renderer.NestedFieldsSupport],

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-form-renderer-field',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers = [
							instance.after('localizableChange', instance._afterLocalizableChange),
							instance.after('parentChange', instance._afterParentChange),
							instance.after('valueChange', instance._afterValueChange)
						];

						var parent = instance.get('parent');

						instance.addTarget(parent);

						var container = instance.get('container');

						if (!container.inDoc()) {
							instance._uiSetParent(parent);
						}

						instance.render();
					},

					destructor: function() {
						var instance = this;

						var parent = instance.get('parent');

						parent.removeChild(instance);

						instance.get('container').remove(true);

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					focus: function() {
						var instance = this;

						instance.get('container').scrollIntoView();

						instance.getInputNode().focus();
					},

					getInputNode: function() {
						var instance = this;

						var qualifiedName = instance.getQualifiedName().replace(/\$/ig, '\\$');

						return instance.get('container').one('[name=' + qualifiedName + ']');
					},

					getLabel: function() {
						var instance = this;

						var label = instance.get('label');

						var locale = instance.get('locale');

						if (Lang.isObject(label) && locale in label) {
							label = label[locale];
						}

						return label || instance.get('name');
					},

					getLabelNode: function() {
						var instance = this;

						return instance.get('container').one('label');
					},

					getQualifiedName: function() {
						var instance = this;

						return [
							instance.get('portletNamespace'),
							'ddm$$',
							instance.get('name'),
							'$',
							instance.get('instanceId'),
							'$',
							instance.get('repeatedIndex'),
							'$$',
							instance.get('locale')
						].join('');
					},

					getSerializedValue: function() {
						var instance = this;

						var serializedValue;

						if (instance.get('localizable')) {
							serializedValue = {};

							serializedValue[instance.get('locale')] = instance.getValue();
						}
						else {
							serializedValue = instance.getValue();
						}

						return serializedValue;
					},

					getTemplate: function() {
						var instance = this;

						var renderer = instance.getTemplateRenderer();

						return renderer(instance.getTemplateContext());
					},

					getTemplateContext: function() {
						var instance = this;

						var context = {};

						var fieldType = FieldTypes.get(instance.get('type'));

						A.each(
							fieldType.get('settings').fields,
							function(item, index) {
								context[item.name] = instance.get(item.name);
							}
						);

						var value = instance.get('value');

						if (instance.get('localizable') && Lang.isObject(value)) {
							value = value[instance.get('locale')];
						}

						return A.merge(
							context,
							{
								childElementsHTML: '',
								label: instance.getLabel(),
								name: instance.getQualifiedName(),
								value: value || '',
								visible: A.DataType.Boolean.parse(context.visibilityExpression)
							}
						);
					},

					getTemplateRenderer: function() {
						var instance = this;

						var type = instance.get('type');

						var fieldType = FieldTypes.get(type);

						if (!fieldType) {
							throw new Error('Unknown field type "' + type + '".');
						}

						var templateNamespace = fieldType.get('templateNamespace');

						return AObject.getValue(window, templateNamespace.split('.'));
					},

					getValue: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						return Lang.String.unescapeHTML(inputNode.val());
					},

					render: function() {
						var instance = this;

						var container = instance.get('container');

						container.html(instance.getTemplate());

						return instance;
					},

					setValue: function(value) {
						var instance = this;

						instance.getInputNode().val(value);
					},

					toJSON: function() {
						var instance = this;

						var fieldJSON = {
							instanceId: instance.get('instanceId'),
							name: instance.get('name'),
							value: instance.getSerializedValue()
						};

						var fields = instance.get('fields');

						if (fields.length > 0) {
							fieldJSON.nestedFieldValues = AArray.invoke(fields, 'toJSON');
						}

						return fieldJSON;
					},

					_afterLocalizableChange: function() {
						var instance = this;

						instance.set('value', instance._getDefaultValue());
					},

					_afterParentChange: function(event) {
						var instance = this;

						instance.addTarget(event.newVal);

						var prevParent = event.prevVal;

						prevParent.removeChild(instance);

						instance.removeTarget(prevParent);

						instance._uiSetParent(event.newVal);
					},

					_afterValueChange: function() {
						var instance = this;

						instance.render();
					},

					_createContainer: function() {
						var instance = this;

						return A.Node.create('<div class="lfr-ddm-form-field-container"></div>');
					},

					_getContainerByInstanceId: function(instanceId) {
						var instance = this;

						return instance.getRoot().filterNodes(
							function(qualifiedName) {
								var nodeInstanceId = Util.getInstanceIdFromQualifiedName(qualifiedName);

								return instanceId === nodeInstanceId;
							}
						).item(0);
					},

					_getContainerByNameAndIndex: function(name, repeatedIndex) {
						var instance = this;

						return instance.getRoot().filterNodes(
							function(qualifiedName) {
								var nodeFieldName = Util.getFieldNameFromQualifiedName(qualifiedName);

								return name === nodeFieldName;
							}
						).item(repeatedIndex);
					},

					_getDefaultValue: function() {
						var instance = this;

						var value = '';

						if (instance.get('localizable')) {
							value = {};

							value[instance.get('locale')] = '';
						}

						return value;
					},

					_uiSetParent: function(parent) {
						var instance = this;

						var container = instance.get('container');

						container.appendTo(parent.get('container'));
					},

					_valueContainer: function() {
						var instance = this;

						var instanceId = instance.get('instanceId');

						var container = instance._getContainerByInstanceId(instanceId);

						if (!container) {
							var name = instance.get('name');

							var repeatedIndex = instance.get('repeatedIndex');

							container = instance._getContainerByNameAndIndex(name, repeatedIndex);
						}

						if (!container) {
							container = instance._createContainer();
						}

						return container;
					},

					_valueInstanceId: function() {
						var instance = this;

						return Util.generateInstanceId(8);
					},

					_valueParent: function() {
						var instance = this;

						return new Renderer.Form(
							{
								fields: [instance]
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDM.Renderer').Field = Field;
	},
	'',
	{
		requires: ['aui-datatype', 'aui-node', 'liferay-ddm-form-renderer', 'liferay-ddm-form-renderer-field-events', 'liferay-ddm-form-renderer-field-repetition', 'liferay-ddm-form-renderer-field-types', 'liferay-ddm-form-renderer-field-validation', 'liferay-ddm-form-renderer-nested-fields', 'liferay-ddm-form-renderer-util']
	}
);