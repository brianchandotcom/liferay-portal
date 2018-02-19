AUI.add(
	'liferay-ddm-form-field-date',
	function(A) {
		var isArray = Array.isArray;

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

						if (!instance.get('readOnly')) {
							instance.bindContainerEvent('click', instance._onClickCalendar, '.input-group-addon');
						}
					},

					destructor: function() {
						var instance = this;

						instance.datePicker.destroy();
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

						return container.one('.form-control');
					},

					hasFocus: function() {
						var instance = this;

						var datePicker = instance.datePicker;

						var hasFocus = DateField.superclass.hasFocus.apply(instance, arguments);

						if (datePicker.calendar) {
							var calendarNode = datePicker.calendar.get('boundingBox');

							hasFocus = hasFocus || calendarNode.contains(document.activeElement);
						}

						return hasFocus;
					},

					render: function() {
						var instance = this;

						var pattern = instance.get('mask');

						pattern = pattern.replace(/%d/, 'dd');
						pattern = pattern.replace(/%m/, 'mm');
						pattern = pattern.replace(/%y/, 'yy');

						var autoCorrectedDatePipe = DDMDate.createAutoCorrectedDatePipe(pattern + 'HH:MM');

						DateField.superclass.render.apply(instance, arguments);

						var element = instance.getTriggerNode().getDOM();

						DDMDate.vanillaTextMask(
							{
								inputElement: element,
								mask: [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/],
								pipe: autoCorrectedDatePipe,
								placeholderChar: '_',
								showMask: true
							}
						);

						var qualifiedName = instance.getQualifiedName().replace(/\$/ig, '\\$');

						instance.datePicker = new A.DatePicker(
							{
								after: {
									selectionChange: A.bind('_afterSelectionChange', instance)
								},
								calendar: {
									on: {
										focusedChange: function() {
											element.focus();
										}
									}
								},
								mask: instance.get('mask'),
								popover: {
									zIndex: Liferay.zIndex.TOOLTIP
								},
								trigger: '[data-fieldname=' + qualifiedName + '] .form-control'
							}
						);

						return instance;
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

						var date = event.newSelection;

						if (isArray(date) && date.length) {
							date = date[0];
						}

						instance.setValue(instance.getISODate(date));

						instance.validate();

						instance._fireStartedFillingEvent();
					},

					_onCalendarFocusedChange: function(event) {
						var instance = this;

						event.preventDefault();

						if (event.newVal) {
							var triggerNode = instance.getTriggerNode();

							triggerNode.focus();
						}
					},

					_onClickCalendar: function() {
						var instance = this;

						var datePicker = instance.datePicker;

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