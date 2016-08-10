AUI.add(
	'liferay-ddm-form-renderer-field-class-factory',
	function(A) {
		var AObject = A.Object;

		var Lang = A.Lang;

		var Renderer = Liferay.DDM.Renderer;

		var FieldTypes = Renderer.FieldTypes;
		var Util = Renderer.Util;

		var FieldClassFactory = {
			getFieldClass: function(type, context) {
				var instance = this;

				var fieldType = FieldTypes.get(type);

				var fieldClassName = fieldType.get('className');

				var fieldClass = AObject.getValue(window, fieldClassName.split('.'));

				var classAttributes = instance.getFieldClassAttributes(context, fieldClass);

				var unrepaintableAttributes = {};

				A.each(
					context,
					function(contextValue, name) {
						var attribute = classAttributes[name];

						if (attribute) {
							attribute.lazyAdd = false;

							if (attribute.repaint === false) {
								unrepaintableAttributes[name] = true;
							}

							instance._setAttributeSetter(attribute, name);

							if (name == 'valid') {
								contextValue = true;
							}

							if (contextValue) {
								attribute.value = contextValue;
							}
						}
					}
				);

				return A.Component.create(
					{
						ATTRS: classAttributes,

						EXTENDS: fieldClass,

						NAME: fieldClass.NAME,

						prototype: {
							isRepaintable: function(attributeName) {
								return context && context.hasOwnProperty(attributeName) && !unrepaintableAttributes[attributeName];
							}
						}
					}
				);
			},

			getFieldClassAttributes: function(context, fieldClass) {
				var EXTENDS = fieldClass;

				var classAttributes = {};

				var mergeAttributes = function(extendAttributes, attributeName) {
					extendAttributes[attributeName] = A.merge(EXTENDS.ATTRS[attributeName], {});
				};

				if (context) {
					while (EXTENDS) {
						var extendAttributes = {};

						AObject.keys(EXTENDS.ATTRS).forEach(A.bind(mergeAttributes, this, extendAttributes));

						classAttributes = A.mix(classAttributes, extendAttributes, false, null, 0, false);

						EXTENDS = EXTENDS.EXTENDS;
					}
				}

				return classAttributes;
			},

			_setAttributeSetter: function(attribute, name) {
				var defaultSetter = attribute.setter;

				attribute.setter = function(value) {
					var instance = this;

					if (Lang.isFunction(defaultSetter)) {
						value = defaultSetter.apply(instance, arguments);
					}
					else if (Lang.isFunction(instance[defaultSetter])) {
						value = instance[defaultSetter].apply(instance, arguments);
					}

					var context = instance.get('context');

					if (!Util.compare(value, context[name])) {
						instance.set('context.' + name, value);
					}

					return value;
				};
			}
		};

		Liferay.namespace('DDM.Renderer').FieldClassFactory = FieldClassFactory;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-types', 'liferay-ddm-form-renderer-util']
	}
);