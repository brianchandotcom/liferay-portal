AUI.add(
	'liferay-ddm-form-renderer-util',
	function(A) {
		var AArray = A.Array;

		var Util = {
			generateInstanceId: function(number) {
				var instance = this;

				var text = '';

				var possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

				for (var i = 0; i < number; i++) {
					text += possible.charAt(Math.floor(Math.random() * possible.length));
				}

				return text;
			},

			getFieldClass: function(definition) {
				var instance = this;

				var attributes = {
					definition: {
						value: definition
					}
				};

				return A.Component.create(
					{
						ATTRS: attributes,

						EXTENDS: Liferay.DDM.Renderer.Field,

						NAME: 'liferay-form-field'
					}
				);
			},

			getFieldNameFromQualifiedName: function(qualifiedName) {
				var instance = this;

				var name = qualifiedName.split('$$')[1];

				return name.split('$')[0];
			},

			getInstanceIdFromQualifiedName: function(qualifiedName) {
				var instance = this;

				var name = qualifiedName.split('$$')[1];

				return name.split('$')[1];
			},

			searchFieldData: function(parent, key, value) {
				var queue = new A.Queue(parent);

				var addToQueue = function(item) {
					if (AArray.indexOf(queue._q, item) === -1) {
						queue.add(item);
					}
				};

				var fieldInfo = {};

				while (queue.size() > 0) {
					var next = queue.next();

					if (next[key] === value) {
						fieldInfo = next;
					}
					else {
						var children = next.fields || next.nestedFields || next.fieldValues || next.nestedFieldValues;

						if (children) {
							children.forEach(addToQueue);
						}
					}
				}

				return fieldInfo;
			}
		};

		Liferay.namespace('DDM.Renderer').Util = Util;
	},
	'',
	{
		requires: ['array-extras']
	}
);