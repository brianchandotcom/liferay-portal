AUI.add(
	'liferay-ddl-form-builder-layout-serializer',
	function(A) {
		var AArray = A.Array;

		var Field = Liferay.DDM.Renderer.Field;

		var LayoutSerializer = A.Component.create(
			{
				ATTRS: {
					builder: {
						value: {}
					},

					columnHandler: {
						valueFn: '_valueColumnHandler'
					},

					fieldHandler: {
						valueFn: '_valueFieldHandler'
					},

					pageHandler: {
						valueFn: '_valuePageHandler'
					},

					rowHandler: {
						valueFn: '_valueRowHandler'
					}
				},

				EXTENDS: Liferay.DDL.LayoutVisitor,

				NAME: 'liferay-ddl-form-builder-layout-serializer',

				prototype: {
					_valueColumnHandler: function() {
						var instance = this;

						return instance.serializeColumn;
					},

					_valueFieldHandler: function() {
						var instance = this;

						return instance.serializeField;
					},

					_valuePageHandler: function() {
						var instance = this;

						return instance.serializePage;
					},

					_valueRowHandler: function() {
						var instance = this;

						return instance.serializeRow;
					},

					serialize: function() {
						var instance = this;

						return A.JSON.stringify(
							{
								pages: instance.visit()
							}
						);
					},

					serializeColumn: function(column) {
						var instance = this;

						var fieldsList = column.get('value');

						var serializedColumn = {
							size: column.get('size')
						};

						serializedColumn.fieldNames = [];

						if (A.instanceOf(fieldsList, A.FormBuilderFieldList)) {
							serializedColumn.fieldNames = instance.visitFields(fieldsList.get('fields'));
						}

						return serializedColumn;
					},

					serializeField: function(field) {
						var instance = this;

						return field.get('name');
					},

					serializePage: function(page, index) {
						var instance = this;

						var builder = instance.get('builder');

						var pages = builder._pages;

						var descriptions = pages.get('descriptions');
						var titles = pages.get('titles');

						return {
							description: {
								en_US: descriptions[index] || ''
							},
							rows: instance.visitRows(page.get('rows')),
							title: {
								en_US: titles[index] || index + 1
							}
						};
					},

					serializeRow: function(row) {
						var instance = this;

						return {
							columns: instance.visitColumns(row.get('cols'))
						};
					},

					visitColumns: function(rows) {
						var instance = this;

						return AArray.filter(
							LayoutSerializer.superclass.visitColumns.apply(instance, arguments),
							function(item) {
								return item.fieldNames.length > 0;
							}
						);
					},

					visitRows: function(rows) {
						var instance = this;

						return AArray.filter(
							LayoutSerializer.superclass.visitRows.apply(instance, arguments),
							function(item) {
								return item.columns.length > 0;
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDL').LayoutSerializer = LayoutSerializer;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-layout-visitor', 'liferay-ddm-form-renderer-field']
	}
);