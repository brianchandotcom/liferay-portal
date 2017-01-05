AUI.add(
	'liferay-ddm-form-field-grid',
	function(A) {
		var GridField = A.Component.create(
			{
				ATTRS: {
					columns: {
						setter: '_setColumns',
						state: true,
						validator: Array.isArray,
						value: []
					},

					rows: {
						setter: '_setRows',
						state: true,
						validator: Array.isArray,
						value: []
					},

					type: {
						value: 'grid'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-grid',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							instance.bindContainerEvent('change', instance._onCheckItem, '.form-builder-grid-field')
						);
					},

					getGridRowsNode: function() {
						var instance = this;

						var container = instance.get('container');

						var gridRowsNode = container.all('tbody tr');

						return gridRowsNode;
					},

					getInputNode: function() {
						var instance = this;

						var container = instance.get('container');

						var inputNode = container.all('input[type="hidden"]');

						return inputNode;
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							GridField.superclass.getTemplateContext.apply(instance, arguments),
							{
								columns: instance.get('columns'),
								rows: instance.get('rows')
							}
						);
					},

					getValue: function() {
						var instance = this;

						var gridRowsNode = instance.getGridRowsNode();

						var gridRows = gridRowsNode.get(0);

						var value = {};

						gridRows.forEach(
							function(gridRow) {
								var colValue = gridRow.all('td').one('input[type="radio"]:checked');

								if (colValue) {
									var rowValue = colValue.attr('name');

									value[rowValue] = colValue.val();
								}
							}
						);

						return value;
					},

					setValue: function(value) {
						var instance = this;

						var gridRowsNode = instance.getGridRowsNode();

						var gridRows = gridRowsNode.get(0);

						gridRows.forEach(
							function(gridRow, index) {
								var tableCellNodeList = gridRow.all('td');

								var radioNodeList = tableCellNodeList.all('input[type="radio"]');

								radioNodeList.removeAttribute('checked');

								var radioToCheck = radioNodeList.filter(
									function(node) {
										return node.val() === value[node.attr('name')];
									}
								).item(0);

								if (radioToCheck) {
									radioToCheck.attr('checked', true);
								}
							}
						);
					},

					showErrorMessage: function() {
						var instance = this;

						var container = instance.get('container');

						GridField.superclass.showErrorMessage.apply(instance, arguments);

						container.all('.help-block').appendTo(container.one('.form-group'));
					},

					_getLocalizedLabel: function(option) {
						var defaultLanguageId = themeDisplay.getDefaultLanguageId();

						return option.label[defaultLanguageId] ? option.label[defaultLanguageId] : option.label;
					},

					_mapItemsLabels: function(items) {
						var instance = this;

						items.forEach(
							function(item) {
								item.label = instance._getLocalizedLabel(item);
							}
						);
					},

					_onCheckItem: function(event) {
						var instance = this;

						var inputNode = this.getInputNode();

						var target = event.currentTarget;

						var rowValue = target.attr('name');

						var rowIndex = target.attr('data-row-index');

						var colValue = target.attr('value');

						var currentItem = inputNode.item(rowIndex);

						currentItem.attr('value', rowValue + ';' + colValue);
					},

					_setColumns: function(columns) {
						var instance = this;

						instance._mapItemsLabels(columns);
					},

					_setRows: function(rows) {
						var instance = this;

						instance._mapItemsLabels(rows);
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Grid = GridField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);