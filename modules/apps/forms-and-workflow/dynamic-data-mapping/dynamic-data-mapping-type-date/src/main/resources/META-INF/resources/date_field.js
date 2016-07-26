AUI.add(
	'liferay-ddm-form-field-date',
	function(A) {
		var isArray = Array.isArray;

		var datePicker = new A.DatePicker(
			{
				popover: {
					zIndex: Liferay.zIndex.TOOLTIP
				},
				trigger: '.liferay-ddm-form-field-date .trigger'
			}
		);

		var DateField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: 'string'
					},

					mask: {
						value: Liferay.AUI.getDateFormat()
					},

					type: {
						value: 'date'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-date',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							datePicker.after('selectionChange', A.bind('_afterSelectionChange', instance)),
							datePicker.on('activeInputChange', A.bind('_onActiveInputChange', instance))
						);

						if (!instance.get('readOnly')) {
							instance.bindContainerEvent('click', instance._onClickCalendar, '.input-group-addon');
						}
					},

					formatDate: function(isoDate) {
						var instance = this;

						var formattedDate;

						if (isoDate) {
							formattedDate = A.Date.format(
								A.Date.parse('%Y-%m-%d', isoDate),
								{
									format: instance.get('mask')
								}
							);
						}

						return formattedDate || '';
					},

					getISODate: function(date) {
						var instance = this;

						return A.Date.format(date);
					},

					getTemplateContext: function() {
						var instance = this;

						var value = instance.get('value');

						return A.merge(
							DateField.superclass.getTemplateContext.apply(instance, arguments),
							{
								displayValue: instance.formatDate(value),
								value: value
							}
						);
					},

					getTriggerNode: function() {
						var instance = this;

						var container = instance.get('container');

						return container.one('.trigger');
					},

					setValue: function(isoDate) {
						var instance = this;

						DateField.superclass.setValue.apply(instance, arguments);

						var formattedDate = instance.formatDate(isoDate);

						instance.getTriggerNode().val(formattedDate);
					},

					showErrorMessage: function() {
						var instance = this;

						DateField.superclass.showErrorMessage.apply(instance, arguments);

						var container = instance.get('container');

						var inputGroup = container.one('.input-group-container');

						inputGroup.insert(container.one('.help-block'), 'after');
					},

					_afterSelectionChange: function(event) {
						var instance = this;

						var triggerNode = instance.getTriggerNode();

						if (datePicker.get('activeInput') === triggerNode) {
							var date = event.newSelection;

							if (isArray(date) && date.length) {
								date = date[0];
							}

							instance.setValue(instance.getISODate(date));

							instance.validate();
						}
					},

					_onActiveInputChange: function(event) {
						var instance = this;

						var triggerNode = instance.getTriggerNode();

						if (event.newVal === triggerNode) {
							datePicker.set('mask', instance.get('mask'));
						}
					},

					_onClickCalendar: function() {
						var instance = this;

						instance.getTriggerNode().focus();

						datePicker.show();
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Date = DateField;
	},
	'',
	{
		requires: ['aui-datepicker', 'liferay-ddm-form-renderer-field']
	}
);