AUI.add(
	'liferay-ddm-form-renderer-layout-visitor',
	function(A) {
		var EMPTY_FN = A.Lang.emptyFn;

		var LayoutVisitor = A.Component.create(
			{
				ATTRS: {
					columnHandler: {
						value: EMPTY_FN
					},

					fieldHandler: {
						value: EMPTY_FN
					},

					pageHandler: {
						value: EMPTY_FN
					},

					pages: {
						validator: Array.isArray,
						value: []
					},

					rowHandler: {
						value: EMPTY_FN
					}
				},

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-form-renderer-layout-visitor',

				prototype: {
					visit: function() {
						var instance = this;

						return instance._visitPages(instance.get('pages'));
					},

					_visitColumn: function(column) {
						var instance = this;

						var columnHandler = instance.get('columnHandler');

						instance._visitFields(column.fields);

						return columnHandler.apply(instance, arguments);
					},

					_visitColumns: function(columns) {
						var instance = this;

						return columns.map(A.bind('_visitColumn', instance));
					},

					_visitField: function(field) {
						var instance = this;

						var fieldHandler = instance.get('fieldHandler');

						return fieldHandler.apply(instance, arguments);
					},

					_visitFields: function(fields) {
						var instance = this;

						return fields.map(A.bind('_visitField', instance));
					},

					_visitPage: function(page) {
						var instance = this;

						var pageHandler = instance.get('pageHandler');

						instance._visitRows(page.rows);

						return pageHandler.apply(instance, arguments);
					},

					_visitPages: function(pages) {
						var instance = this;

						return pages.map(A.bind('_visitPage', instance));
					},

					_visitRow: function(row) {
						var instance = this;

						var rowHandler = instance.get('rowHandler');

						instance._visitColumns(row.columns);

						return rowHandler.apply(instance, arguments);
					},

					_visitRows: function(rows) {
						var instance = this;

						return rows.map(A.bind('_visitRow', instance));
					}
				}
			}
		);

		Liferay.namespace('DDM').LayoutVisitor = LayoutVisitor;
	},
	'',
	{
		requires: ['aui-base']
	}
);