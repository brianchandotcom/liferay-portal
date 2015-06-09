AUI.add(
	'liferay-ddl-form-builder-layout-visitor',
	function(A) {
		var AArray = A.Array;
		var EMPTY_FN = A.Lang.emptyFn;

		var LayoutVisitor = A.Component.create(
			{
				ATTRS: {
					columnHandler: {
						value: EMPTY_FN
					},

					layouts: {
						value: []
					},

					pageHandler: {
						value: EMPTY_FN
					},

					rowHandler: {
						value: EMPTY_FN
					}
				},

				EXTENDS: A.Base,

				NAME: 'liferay-ddl-form-builder-layout-visitor',

				prototype: {
					visit: function() {
						var instance = this;

						return instance.visitPages(instance.get('layouts'));
					},

					visitColumn: function(column) {
						var instance = this;

						var columnHandler = instance.get('columnHandler');

						return columnHandler.apply(instance, arguments);
					},

					visitColumns: function(columns) {
						var instance = this;

						return AArray.map(columns, A.bind('visitColumn', instance));
					},

					visitPage: function(page, index) {
						var instance = this;

						var pageHandler = instance.get('pageHandler');

						instance.visitRows(page.get('rows'));

						return pageHandler.apply(instance, arguments);
					},

					visitPages: function(pages) {
						var instance = this;

						return AArray.map(pages, A.bind('visitPage', instance));
					},

					visitRow: function(row) {
						var instance = this;

						var rowHandler = instance.get('rowHandler');

						instance.visitColumns(row.get('cols'));

						return rowHandler.apply(instance, arguments);
					},

					visitRows: function(rows) {
						var instance = this;

						return AArray.map(rows, A.bind('visitRow', instance));
					}
				}
			}
		);

		Liferay.namespace('DDL').LayoutVisitor = LayoutVisitor;
	},
	'',
	{
		requires: ['aui-layout']
	}
);