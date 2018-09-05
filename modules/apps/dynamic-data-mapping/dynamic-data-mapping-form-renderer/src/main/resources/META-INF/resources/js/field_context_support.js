AUI.add(
	'liferay-ddm-form-renderer-field-context-support',
	function(A) {
		var AObject = A.Object;

		var Renderer = Liferay.DDM.Renderer;

		var FieldTypes = Renderer.FieldTypes;
		var Util = Renderer.Util;

		var FieldContextSupport = function(config) {};

		FieldContextSupport.ATTRS = {
			context: {
				setter: '_setContext',
				value: {}
			}
		};

		FieldContextSupport.prototype = {
			initializer: function() {
				var instance = this;

				var type = instance.get('type');

				var fieldType = FieldTypes.get(type);

				var fieldClassName = fieldType.get('className');

				var fieldClass = A.Object.getValue(window, fieldClassName.split('.'));

				instance._eventHandlers = [];

				instance._stateRepaintableAttributes = {};

				instance.bindFieldClassAttributesStatus(fieldClass);
			},

			bindFieldClassAttributesStatus: function(fieldClass) {
				var instance = this;

				var context = instance.get('context');

				var setAttributeChangeEvent = function(attributes, attributeName) {
					if (context.hasOwnProperty(attributeName)) {
						var attributeValue = instance.get('value');

						if (!Util.compare(attributeValue, context[attributeName])) {
							instance.set(attributeName, context[attributeName]);
						}

						instance._eventHandlers.push(instance.after(attributeName + 'Change', A.bind(instance._afterAttributeChange, instance, attributeName)));
					}

					instance._setStateRepaintableAttributeValue(attributeName, !!attributes[attributeName].state);
				};

				var classAttrs = instance.getAttrs();

				AObject.keys(context).forEach(
					function(attr) {
						if (!classAttrs.hasOwnProperty(attr)) {
							var config = {
								state: true,
								value: context[attr]
							};

							instance.addAttr(attr, config);
							instance._eventHandlers.push(instance.after(attr + 'Change', A.bind(instance._afterAttributeChange, instance, attr)));
						}
					}
				);

				var parentClass = fieldClass;

				while (parentClass) {
					var attrs = parentClass.ATTRS;

					AObject.keys(attrs).forEach(A.bind(setAttributeChangeEvent, instance, attrs));

					parentClass = parentClass.EXTENDS;
				}

				instance._eventHandlers.push(instance.after('contextChange', instance._afterContextChange));
			},

			isRepaintable: function(attributeName) {
				var instance = this;

				var context = instance.get('context');

				return context && context.hasOwnProperty(attributeName) && instance._stateRepaintableAttributes[attributeName];
			},

			_afterAttributeChange: function(name) {
				var instance = this;

				var value = instance.get(name);

				var context = instance.get('context');

				if (!Util.compare(value, context[name])) {
					var localizable = context.localizable;

					instance.set('context.' + name, value);

					if (name === 'value' && localizable === false) {
						var localizedValue = {};

						var locale = context.locale;

						localizedValue[locale] = value;

						instance.set('context.localizedValue', localizedValue);
					}
				}
			},

			_afterContextChange: function(event) {
				var instance = this;

				var repaint = false;

				var newContext = event.newVal;

				var oldContext = event.prevVal;

				for (var name in newContext) {
					if (!Util.compare(newContext[name], oldContext[name])) {
						if (instance.isRepaintable(name)) {
							repaint = true;
						}
					}

					if (!Util.compare(newContext[name], instance.get(name))) {
						instance.set(name, newContext[name]);
					}
				}

				if (repaint && instance.get('rendered')) {
					instance.render();
				}
			},

			_isStateRepaintableAttributeDefined: function(attributeName) {
				var instance = this;

				return instance._stateRepaintableAttributes.hasOwnProperty(attributeName);
			},

			_setContext: function(val) {
				var instance = this;

				return A.merge(instance.get('context'), val);
			},

			_setStateRepaintableAttributeValue: function(attributeName, value) {
				var instance = this;

				if (!instance._isStateRepaintableAttributeDefined(attributeName)) {
					instance._stateRepaintableAttributes[attributeName] = value;
				}
			}
		};

		Liferay.namespace('DDM.Renderer').FieldContextSupport = FieldContextSupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-types', 'liferay-ddm-form-renderer-util']
	}
);