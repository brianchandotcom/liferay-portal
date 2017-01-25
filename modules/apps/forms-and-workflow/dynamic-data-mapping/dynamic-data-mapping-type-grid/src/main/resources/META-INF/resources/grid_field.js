AUI.add(
	'liferay-ddm-form-field-grid',
	function(A) {
		var Lang = A.Lang;

		var TPL_QUERY_INPUT_CHECKED = 'input[name="{name}"]:checked';

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

						var gridRowsNode = instance._getGridRowsNode();

						var value = {};

						for (var i = 0; i < gridRowsNode.size(); i++) {
							var gridRow = gridRowsNode.item(i);

							var rowName = gridRow.attr('name');

							var queryChecked = Lang.sub(
								TPL_QUERY_INPUT_CHECKED,
								{
									name: rowName
								}
							);

							var inputChecked = gridRow.one(queryChecked);

							if (inputChecked) {
								value[rowName] = inputChecked.val();
							}
						}

						return value;
					},

					setValue: function(value) {
						var instance = this;

						var contextValue = {};

						var gridRowsNode = instance._getGridRowsNode();

						for (var i = 0; i < gridRowsNode.size(); i++) {
							var gridRow = gridRowsNode.item(i);

							var tableCellNodeList = gridRow.all('td');

							var radioNodeList = tableCellNodeList.all('input');

							radioNodeList.attr('checked', false);

							var radioToCheck = instance._getRadioToCheck(radioNodeList, value);

							if (radioToCheck) {
								radioToCheck.attr('checked', true);

								contextValue[radioToCheck.attr('name')] = radioToCheck.attr('value');

								instance._setValueInputHidden(i, radioToCheck);
							}
						}

						instance.set('value', contextValue);
					},

					showErrorMessage: function() {
						var instance = this;

						var container = instance.get('container');

						GridField.superclass.showErrorMessage.apply(instance, arguments);

						container.all('.help-block').appendTo(container.one('.form-group'));
					},

					_getGridRowsNode: function() {
						var instance = this;

						var container = instance.get('container');

						var gridRowsNode = container.all('tbody tr');

						return gridRowsNode;
					},

					_getInputNode: function() {
						var instance = this;

						var container = instance.get('container');

						var inputNode = container.all('input[type="hidden"]');

						return inputNode;
					},

					_getLocalizedLabel: function(option) {
						var defaultLanguageId = themeDisplay.getDefaultLanguageId();

						return option.label[defaultLanguageId] ? option.label[defaultLanguageId] : option.label;
					},

					_getRadioToCheck: function(radioNodeList, value) {
						var radioToCheck = radioNodeList.filter(
							function(node) {
								return node.val() === value[node.attr('name')];
							}
						).item(0);

						return radioToCheck;
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

						var target = event.currentTarget;

						var rowIndex = target.attr('data-row-index');

						instance._setValueInputHidden(rowIndex, target);

						var value = instance.get('value');

						if (!value) {
							value = {};
						}

						value[target.attr('name')] = target.attr('value');

						instance.set('value', value);
					},

					_setColumns: function(columns) {
						var instance = this;

						instance._mapItemsLabels(columns);
					},

					_setRows: function(rows) {
						var instance = this;

						instance._mapItemsLabels(rows);
					},

					_setValueInputHidden: function(index, radio) {
						var instance = this;

						var inputNode = this._getInputNode();

						var currentItem = inputNode.item(index);

						currentItem.attr('value', radio.attr('name') + ';' + radio.attr('value'));
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Grid = GridField;
	},
	'',
	{
		requires: ['liferay-ddm-form-field-grid', 'liferay-ddm-form-renderer-field']
	}
);