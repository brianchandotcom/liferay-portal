AUI.add(
	'liferay-ddm-form-renderer-util',
	function(A) {
		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

		var Util = {
			generateInstanceId: function(length) {
				var instance = this;

				var text = '';

				var possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

				for (var i = 0; i < length; i++) {
					text += possible.charAt(Math.floor(Math.random() * possible.length));
				}

				return text;
			},

			getFieldByKey: function(haystack, needle, searchKey) {
				var instance = this;

				return instance.searchFieldsByKey(haystack, needle, searchKey)[0];
			},

			getFieldClass: function(type) {
				var instance = this;

				var fieldType = FieldTypes.get(type);

				var fieldClassName = fieldType.get('className');

				return A.Object.getValue(window, fieldClassName.split('.'));
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

			searchFieldsByKey: function(haystack, needle, searchKey) {
				var queue = new A.Queue(haystack);

				var results = [];

				var addToQueue = function(item) {
					queue.add(item);
				};

				searchKey = searchKey || 'name';

				while (queue.size() > 0) {
					var next = queue.next();

					if (next[searchKey] === needle) {
						results.push(next);
					}
					else {
						var children = next.fields || next.nestedFields || next.fieldValues || next.nestedFieldValues;

						if (children) {
							children.forEach(addToQueue);
						}
					}
				}

				return results;
			}
		};

		Liferay.namespace('DDM.Renderer').Util = Util;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-types', 'queue']
	}
);