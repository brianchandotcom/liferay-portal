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
					},

					value: {
						value: {}
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-grid',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							instance.bindContainerEvent('blur', instance._onBlurItem, '.form-builder-grid-field'),
							instance.bindContainerEvent('change', instance._onCheckItem, '.form-builder-grid-field'),
							instance.bindContainerEvent('focus', instance._onFocusItem, '.form-builder-grid-field')
						);
					},

					focusOnTarget: function() {
						var instance = this;

						var container = instance.get('container');

						var focusTarget = instance._getFocusTarget();

						var row = container.one('tr[name="' + focusTarget.row + '"]');

						var column = row.one('[data-row-index="' + focusTarget.index + '"]');

						column.focus();
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							GridField.superclass.getTemplateContext.apply(instance, arguments),
							{
								columns: instance.get('columns'),
								focusTarget: instance._getFocusTarget(),
								rows: instance.get('rows')
							}
						);
					},

					getValue: function() {
						var instance = this;

						return instance.get('value');
					},

					setValue: function(value) {
						var instance = this;

						instance.set('value', value);

						instance.render();

						instance.focusOnTarget();
					},

					showErrorMessage: function() {
						var instance = this;

						var container = instance.get('container');

						GridField.superclass.showErrorMessage.apply(instance, arguments);

						container.all('.form-feedback-indicator').appendTo(container.one('.form-group'));
					},

					_getFocusTarget: function() {
						var instance = this;

						return instance._focusTarget;
					},

					_getLabel: function(option) {
						return option.label ? option.label: option.value;
					},

					_mapItemsLabels: function(items) {
						var instance = this;

						items.forEach(
							function(item) {
								item.label = instance._getLabel(item);
							}
						);
					},

					_onBlurItem: function() {
						var instance = this;

						instance._fireBlurEvent();
					},

					_onCheckItem: function(event) {
						var instance = this;

						var target = event.currentTarget;

						var value = instance.get('value');

						value[target.attr('data-row')] = target.attr('value');

						instance._setFocusTarget(target);

						instance.setValue(value);

						instance._fireStartedFillingEvent();
					},

					_onFocusItem: function() {
						var instance = this;

						instance._fireFocusEvent();
					},

					_setColumns: function(columns) {
						var instance = this;

						instance._mapItemsLabels(columns);
					},

					_setFocusTarget: function(target) {
						var instance = this;

						var focusTarget = {};

						focusTarget.row = target.attr('name');

						focusTarget.index = target.attr('data-row-index');

						instance._focusTarget = focusTarget;
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
		requires: ['liferay-ddm-form-field-grid', 'liferay-ddm-form-renderer-field']
	}
);