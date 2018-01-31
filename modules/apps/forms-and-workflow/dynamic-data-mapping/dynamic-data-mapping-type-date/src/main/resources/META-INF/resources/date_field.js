AUI.add(
	'liferay-ddm-form-field-date',
	function(A) {
		var isArray = Array.isArray;

		var datePicker = new A.DatePicker(
			{
				popover: {
					zIndex: Liferay.zIndex.TOOLTIP
				},
				trigger: '.liferay-ddm-form-field-date .form-control'
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

					predefinedValue: {
						value: ''
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

						var predefinedValue = instance.get('predefinedValue');
						var value = instance.get('value');

						return A.merge(
							DateField.superclass.getTemplateContext.apply(instance, arguments),
							{
								formattedValue: instance.formatDate(value),
								predefinedValue: instance.formatDate(predefinedValue),
								value: value
							}
						);
					},

					getTriggerNode: function() {
						var instance = this;

						var container = instance.get('container');

						var triggerNode;

						triggerNode = container.one('.form-control');
						return triggerNode;
					},

					setValue: function(isoDate) {
						var instance = this;

						DateField.superclass.setValue.apply(instance, arguments);

						var formattedValue = instance.get('formattedValue');

						instance.getTriggerNode().val(formattedValue);

						instance.set('value', isoDate);
					},

					showErrorMessage: function() {
						var instance = this;

						DateField.superclass.showErrorMessage.apply(instance, arguments);

						var container = instance.get('container');

						var formGroup = container.one('.form-group');

						formGroup.append(container.one('.form-feedback-item'));
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

						instance._fireStartedFillingEvent();
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