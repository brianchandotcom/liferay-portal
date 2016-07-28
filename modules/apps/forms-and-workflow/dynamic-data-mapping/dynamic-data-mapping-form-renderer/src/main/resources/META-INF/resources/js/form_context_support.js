AUI.add(
	'liferay-ddm-form-renderer-context',
	function(A) {
		var AArray = A.Array;
		var Renderer = Liferay.DDM.Renderer;

		var FieldClassFactory = Renderer.FieldClassFactory;
		var Util = Renderer.Util;

		var FormContextSupport = function() {
		};

		FormContextSupport.ATTRS = {
			fields: {
				valueFn: '_valueFields'
			},

			visitor: {
				valueFn: '_valueVisitor'
			}
		};

		FormContextSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance.on('contextChange', instance._onContextChange)
				);
			},

			_createField: function(context, fieldsMap) {
				var instance = this;

				var name = Util.getFieldNameFromQualifiedName(context.name);

				var alreadyAdded = fieldsMap[name];

				var repeatedIndex = 0;

				if (alreadyAdded) {
					var repetitions = alreadyAdded[0].get('repetitions');

					repeatedIndex = repetitions.length;
				}
				else {
					fieldsMap[name] = [];
				}

				context.errorMessage = '';
				context.valid = true;

				var config = A.merge(
					context,
					{
						context: A.clone(context),
						fieldName: name,
						parent: instance,
						portletNamespace: instance.get('portletNamespace'),
						repeatedIndex: repeatedIndex
					}
				);

				var fieldClass = FieldClassFactory.getFieldClass(config.type, config.context);

				var field = new fieldClass(config);

				fieldsMap[name].push(field);

				fieldsMap[name].forEach(
					function(repetition, index, repetitions) {
						repetition.set('repetitions', repetitions);
					}
				);

				return alreadyAdded ? null : field;
			},

			_createFieldsFromContext: function(context) {
				var instance = this;

				var fields = [];

				var fieldsMap = {};

				var visitor = instance.get('visitor');

				visitor.set(
					'fieldHandler',
					function(fieldContext) {
						var field = instance._createField(fieldContext, fieldsMap);

						if (field) {
							fields.push(field);
						}
					}
				);

				visitor.visit();

				return fields;
			},

			_onContextChange: function(event) {
				var instance = this;

				var context = event.newVal;

				var visitor = instance.get('visitor');

				AArray.invoke(instance.get('fields'), 'destroy');

				visitor.set('pages', context.pages);

				instance.set('fields', instance._createFieldsFromContext(context));
			},

			_valueFields: function(val) {
				var instance = this;

				var context = instance.get('context');

				return instance._createFieldsFromContext(context);
			},

			_valueVisitor: function() {
				var instance = this;

				var context = instance.get('context');

				return new Liferay.DDM.LayoutVisitor(
					{
						pages: context.pages
					}
				);
			}
		};

		Liferay.namespace('DDM.Renderer').FormContextSupport = FormContextSupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field-class-factory', 'liferay-ddm-form-renderer-layout-visitor', 'liferay-ddm-form-renderer-types', 'liferay-ddm-form-renderer-util']
	}
);