AUI.add(
	'liferay-ddl-form-builder-layout-deserializer',
	function(A) {
		var Lang = A.Lang;

		var FormBuilderUtil = Liferay.DDL.FormBuilderUtil;
		var RendererUtil = Liferay.DDM.Renderer.Util;

		var LayoutDeserializer = A.Component.create(
			{
				ATTRS: {
					builder: {
					},

					definition: {
						validator: Lang.isObject
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

						if (column.fieldNames && column.fieldNames.length > 0) {
							var fieldsList = new A.FormBuilderFieldList(
								{
									fields: instance._deserializeFields(deserializedColumn, column.fieldNames)
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

					_deserializeField: function(deserializedColumn, fieldName) {
						var instance = this;

						var builder = instance.get('builder');

						var definition = instance.get('definition');

						var fieldDefinition = RendererUtil.getFieldByKey(definition, fieldName);

						fieldDefinition.dataProviders = builder.get('dataProviders');
						fieldDefinition.parent = builder;
						fieldDefinition.portletNamespace = builder.get('portletNamespace');
						fieldDefinition.readOnly = true;

						var fieldClass = FormBuilderUtil.getFieldClass(fieldDefinition.type);

						return new fieldClass(fieldDefinition);
					},

					_deserializeFields: function(deserializedColumn, fieldNames) {
						var instance = this;

						return fieldNames.map(A.bind('_deserializeField', instance, deserializedColumn));
					},

					_deserializePage: function(page) {
						var instance = this;

						var languageId = themeDisplay.getLanguageId();

						var description = page.description && page.description[languageId];

						instance.get('descriptions').push(description);

						var title = page.title && page.title[languageId];

						instance.get('titles').push(title);

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
		requires: ['aui-form-builder-field-list', 'aui-layout', 'liferay-ddl-form-builder-field', 'liferay-ddl-form-builder-util', 'liferay-ddm-form-field-types', 'liferay-ddm-form-renderer-util']
	}
);