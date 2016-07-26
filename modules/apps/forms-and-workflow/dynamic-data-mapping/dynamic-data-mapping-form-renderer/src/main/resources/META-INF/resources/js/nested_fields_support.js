AUI.add(
	'liferay-ddm-form-renderer-nested-fields',
	function(A) {
		var AArray = A.Array;

		var NestedFieldsSupport = function() {
		};

		NestedFieldsSupport.ATTRS = {
			fields: {
				setter: '_setFields',
				validator: Array.isArray,
				value: []
			}
		};

		NestedFieldsSupport.prototype = {
			destructor: function() {
				var instance = this;

				AArray.invoke(instance.get('fields'), 'destroy');
			},

			appendChild: function(field) {
				var instance = this;

				instance.insert(0, field);
			},

			eachField: function(fn, flat) {
				var instance = this;

				var queue = new A.Queue();

				var addToQueue = A.fn(1, 'add', queue);

				var addSiblingsToQueue = function(item) {
					item.getRepeatedSiblings().forEach(addToQueue);
				};

				instance.get('fields').forEach(addSiblingsToQueue);

				while (queue.size() > 0) {
					var field = queue.next();

					var stop = fn.call(instance, field, queue);

					if (stop === true) {
						break;
					}

					if (!flat) {
						field.get('fields').forEach(addSiblingsToQueue);
					}
				}
			},

			eachParent: function(fn) {
				var instance = this;

				var parent = instance.get('parent');

				while (parent !== undefined) {
					fn.call(instance, parent);

					parent = parent.get('parent');
				}
			},

			filterNodes: function(fn) {
				var instance = this;

				var nodes = instance.get('container').all('.lfr-ddm-form-field-container');

				return nodes.filter(
					function(item) {
						var qualifiedName = item.one('.form-group').getData('fieldname');

						return fn.call(instance, qualifiedName, item);
					}
				);
			},

			getField: function(name, instanceId) {
				var instance = this;

				var field;

				instance.eachField(
					function(item) {
						if (item.get('fieldName') === name) {
							field = item;
						}

						if (field && instanceId &&
							instanceId !== field.get('instanceId')) {

							field = undefined;
						}

						return field !== undefined;
					}
				);

				return field;
			},

			getImmediateFields: function() {
				var instance = this;

				var fields = [];

				instance.eachField(
					function(field) {
						fields.push(field);
					},
					true
				);

				return fields;
			},

			getRoot: function() {
				var instance = this;

				var root;

				instance.eachParent(
					function(parent) {
						root = parent;
					}
				);

				return root;
			},

			indexOf: function(field) {
				var instance = this;

				return instance.get('fields').indexOf(field);
			},

			insert: function(index, field) {
				var instance = this;

				instance.removeChild(field);

				var fields = instance.get('fields');

				fields.splice(index, 0, field);

				instance.set('fields', fields);
			},

			removeChild: function(field) {
				var instance = this;

				var fields = instance.get('fields');

				var index = instance.indexOf(field);

				if (index > -1) {
					fields.splice(index, 1);

					instance.set('fields', fields);
				}
			},

			_afterNestedFieldsChange: function(event) {
				var instance = this;

				instance.eachField(
					function(field) {
						field.render();
					}
				);
			},

			_setFields: function(fields) {
				var instance = this;

				fields.forEach(
					function(field) {
						field.set('parent', instance);
					}
				);
			}
		};

		Liferay.namespace('DDM.Renderer').NestedFieldsSupport = NestedFieldsSupport;
	},
	'',
	{
		requires: ['array-invoke', 'liferay-ddm-form-renderer-types', 'liferay-ddm-form-renderer-util']
	}
);