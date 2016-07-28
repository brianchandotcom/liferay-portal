AUI.add(
	'liferay-ddm-form-renderer-field',
	function(A) {
		var AArray = A.Array;
		var AObject = A.Object;
		var Lang = A.Lang;
		var Renderer = Liferay.DDM.Renderer;

		var FieldTypes = Renderer.FieldTypes;
		var Util = Renderer.Util;

		var TPL_DIV = '<div></div>';

		var TPL_FORM_FIELD_CONTAINER = '<div class="clearfix {hide} lfr-ddm-form-field-container"></div>';

		var Field = A.Component.create(
			{
				ATTRS: {
					container: {
						setter: A.one,
						valueFn: '_valueContainer'
					},

					context: {
						validator: Lang.isObject,
						value: {}
					},

					dataType: {
						getter: '_getDataType',
						value: 'string'
					},

					fieldName: {
						value: ''
					},

					instanceId: {
						repaint: false,
						valueFn: '_valueInstanceId'
					},

					locale: {
						value: themeDisplay.getLanguageId()
					},

					name: {
						repaint: false,
						value: ''
					},

					parent: {
						setter: '_setParent'
					},

					portletNamespace: {
						value: ''
					},

					predefinedValue: {
						value: ''
					},

					readOnly: {
						value: false
					},

					rendered: {
						value: false
					},

					showLabel: {
						value: true
					},

					type: {
						value: ''
					},

					value: {
						repaint: false,
						value: ''
					},

					visible: {
						value: true
					}
				},

				AUGMENTS: [
					Renderer.FieldEvaluationSupport,
					Renderer.FieldEventsSupport,
					Renderer.FieldFeedbackSupport,
					Renderer.FieldRepetitionSupport,
					Renderer.FieldValidationSupport,
					Renderer.NestedFieldsSupport
				],

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-form-renderer-field',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers = [
							instance.after('contextChange', instance._afterContextChange)
						];
					},

					destructor: function() {
						var instance = this;

						var container = instance.get('container');

						if (container && container.inDoc()) {
							container.remove(true);
						}

						var parent = instance.get('parent');

						if (parent) {
							parent.removeChild(instance);
						}

						(new A.EventHandle(instance._eventHandlers)).detach();

						instance.set('rendered', false);
					},

					fetchContainer: function() {
						var instance = this;

						var instanceId = instance.get('instanceId');

						var container = instance._getContainerByInstanceId(instanceId);

						if (!container) {
							var name = instance.get('fieldName');

							var repeatedIndex = instance.get('repeatedIndex');

							container = instance._getContainerByNameAndIndex(name, repeatedIndex);
						}

						return container;
					},

					focus: function() {
						var instance = this;

						instance.scrollIntoView();

						instance.getInputNode().focus();
					},

					getChildElementsHTML: function() {
						var instance = this;

						return instance.get('fields').map(
							function(field) {
								var fragment = A.Node.create(TPL_DIV);

								var container = field._createContainer();

								container.html(field.getTemplate());

								container.appendTo(fragment);

								return fragment.html();
							}
						).join('');
					},

					getInputNode: function() {
						var instance = this;

						return instance.get('container').one(instance.getInputSelector());
					},

					getInputSelector: function() {
						var instance = this;

						var qualifiedName = instance.getQualifiedName().replace(/\$/ig, '\\$');

						return '[name="' + qualifiedName + '"]';
					},

					getQualifiedName: function() {
						var instance = this;

						return [
							instance.get('portletNamespace'),
							'ddm$$',
							instance.get('fieldName'),
							'$',
							instance.get('instanceId'),
							'$',
							instance.get('repeatedIndex'),
							'$$',
							instance.get('locale')
						].join('');
					},

					getTemplate: function() {
						var instance = this;

						var renderer = instance.getTemplateRenderer();

						return renderer(instance.getTemplateContext());
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							instance.get('context'),
							{
								name: instance.getQualifiedName(),
								value: instance.get('value')
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

					hasFocus: function() {
						var instance = this;

						var container = instance.get('container');

						return container.contains(document.activeElement);
					},

					render: function(target) {
						var instance = this;

						var container = instance.get('container');

						var parent = instance.get('parent');

						if (target && !parent) {
							container.appendTo(target);
						}

						container.html(instance.getTemplate());

						instance.eachField(
							function(field) {
								field.updateContainer();
							}
						);

						instance.fire('render');

						instance.set('rendered', true);

						return instance;
					},

					scrollIntoView: function() {
						var instance = this;

						instance.get('container').scrollIntoView(false);
					},

					setValue: function(value) {
						var instance = this;

						instance.getInputNode().val(value);
					},

					toJSON: function() {
						var instance = this;

						var fieldJSON = {
							instanceId: instance.get('instanceId'),
							name: instance.get('fieldName'),
							value: instance.getValue()
						};

						var fields = instance.getImmediateFields();

						if (fields.length > 0) {
							fieldJSON.nestedFieldValues = AArray.invoke(fields, 'toJSON');
						}

						return fieldJSON;
					},

					updateContainer: function() {
						var instance = this;

						instance.set('container', instance._valueContainer());
					},

					_afterContextChange: function(event) {
						var instance = this;

						if (instance.get('rendered')) {
							var repaint = false;

							var newContext = event.newVal;
							var oldContext = event.prevVal;

							for (var name in newContext) {
								if (!Util.compare(newContext[name], oldContext[name])) {
									if (instance.isRepaintable(name)) {
										repaint = true;
									}

									if (instance.attrAdded(name)) {
										instance.set(name, newContext[name]);
									}
								}
							}

							if (repaint) {
								instance.render();
							}
						}
					},

					_createContainer: function() {
						var instance = this;

						var visible = instance.get('visible');

						return A.Node.create(
							Lang.sub(
								TPL_FORM_FIELD_CONTAINER,
								{
									hide: visible ? '' : 'hide'
								}
							)
						);
					},

					_getContainerByInstanceId: function(instanceId) {
						var instance = this;

						var container;

						var root = instance.getRoot();

						if (root) {
							container = root.filterNodes(
								function(qualifiedName) {
									var nodeInstanceId = Util.getInstanceIdFromQualifiedName(qualifiedName);

									return instanceId === nodeInstanceId;
								}
							).item(0);
						}

						return container;
					},

					_getContainerByNameAndIndex: function(name, repeatedIndex) {
						var instance = this;

						var container;

						var root = instance.getRoot();

						if (root) {
							container = instance.getRoot().filterNodes(
								function(qualifiedName) {
									var nodeFieldName = Util.getFieldNameFromQualifiedName(qualifiedName);

									return name === nodeFieldName;
								}
							).item(repeatedIndex);
						}

						return container;
					},

					_getDataType: function(dataType) {
						var instance = this;

						var validation = instance.get('validation');

						if (validation) {
							dataType = Util.getDataTypeFromValidation(dataType, validation);
						}

						return dataType;
					},

					_setParent: function(val) {
						var instance = this;

						instance.addTarget(val);
					},

					_valueContainer: function() {
						var instance = this;

						var container = instance.fetchContainer();

						if (!container) {
							container = instance._createContainer();
						}

						return container;
					},

					_valueInstanceId: function() {
						var instance = this;

						var instanceId;

						var name = instance.get('name');

						if (name) {
							instanceId = Util.getInstanceIdFromQualifiedName(name);
						}
						else {
							instanceId = Util.generateInstanceId(8);
						}

						return instanceId;
					}
				}
			}
		);

		Liferay.namespace('DDM.Renderer').Field = Field;
	},
	'',
	{
		requires: ['aui-datatype', 'aui-node', 'liferay-ddm-form-renderer', 'liferay-ddm-form-renderer-field-evaluation', 'liferay-ddm-form-renderer-field-events', 'liferay-ddm-form-renderer-field-feedback', 'liferay-ddm-form-renderer-field-repetition', 'liferay-ddm-form-renderer-field-validation', 'liferay-ddm-form-renderer-nested-fields', 'liferay-ddm-form-renderer-types', 'liferay-ddm-form-renderer-util']
	}
);