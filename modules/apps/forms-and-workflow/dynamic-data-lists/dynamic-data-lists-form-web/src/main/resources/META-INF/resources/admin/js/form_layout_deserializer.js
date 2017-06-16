AUI.add(
	'liferay-ddl-form-builder-layout-deserializer',
	function(A) {
		var FormBuilderUtil = Liferay.DDL.FormBuilderUtil;
		var Settings = Liferay.DDL.Settings;

		var LayoutDeserializer = A.Component.create(
			{
				ATTRS: {
					builder: {
					},

					descriptions: {
						validator: Array.isArray,
						value: []
					},

					pages: {
						validator: Array.isArray,
						value: []
					},

					titles: {
						validator: Array.isArray,
						value: []
					}
				},

				EXTENDS: A.Base,

				NAME: 'liferay-ddl-form-builder-layout-deserializer',

				prototype: {
					deserialize: function() {
						var instance = this;

						var pages = instance.get('pages');

						return instance._deserializePages(pages);
					},

					_deserializeColumn: function(column) {
						var instance = this;

						var deserializedColumn = new A.LayoutCol(
							{
								size: column.size
							}
						);

						if (column.fields && column.fields.length > 0) {
							var fieldsList = new Liferay.DDL.FormBuilderFieldList(
								{
									fields: instance._deserializeFields(deserializedColumn, column.fields)
								}
							);

							deserializedColumn.set('value', fieldsList);
						}

						return deserializedColumn;
					},

					_deserializeColumns: function(columns) {
						var instance = this;

						return columns.map(A.bind('_deserializeColumn', instance));
					},

					_deserializeField: function(deserializedColumn, context) {
						var instance = this;

						var builder = instance.get('builder');

						context.portletNamespace = Settings.portletNamespace;
						context.visible = true;
						context.value = '';

						delete context.name;

						var fieldClass = FormBuilderUtil.getFieldClass(context.type, context);

						return new fieldClass(
							A.merge(
								context,
								{
									context: context,
									parent: builder
								}
							)
						);
					},

					_deserializeFields: function(deserializedColumn, fields) {
						var instance = this;

						return fields.map(A.bind('_deserializeField', instance, deserializedColumn));
					},

					_deserializePage: function(page) {
						var instance = this;

						if (page.localizedDescription) {
							instance.get('descriptions').push(page.localizedDescription);
						}

						if (page.localizedTitle) {
							instance.get('titles').push(page.localizedTitle);
						}

						return new A.Layout(
							{
								rows: instance._deserializeRows(page.rows)
							}
						);
					},

					_deserializePages: function(pages) {
						var instance = this;

						var deserializedPages;

						if (pages.length) {
							deserializedPages = pages.map(A.bind('_deserializePage', instance));
						}
						else {
							deserializedPages = [
								new A.Layout(
									{
										rows: [
											new A.LayoutRow()
										]
									}
								)
							];
						}

						return deserializedPages;
					},

					_deserializeRow: function(row) {
						var instance = this;

						return new A.LayoutRow(
							{
								cols: instance._deserializeColumns(row.columns)
							}
						);
					},

					_deserializeRows: function(rows) {
						var instance = this;

						return rows.map(A.bind('_deserializeRow', instance));
					}
				}
			}
		);

		Liferay.namespace('DDL').LayoutDeserializer = LayoutDeserializer;
	},
	'',
	{
		requires: ['aui-layout', 'liferay-ddl-form-builder-field', 'liferay-ddl-form-builder-field-list', 'liferay-ddl-form-builder-util', 'liferay-ddm-form-field-types', 'liferay-ddm-form-renderer-util']
	}
);