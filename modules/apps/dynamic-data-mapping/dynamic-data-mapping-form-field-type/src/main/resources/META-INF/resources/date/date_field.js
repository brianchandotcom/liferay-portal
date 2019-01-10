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
						valueFn: '_maskValueFn'
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
							instance.bindContainerEvent('blur', instance._onBlurInput, '.form-control');
							instance.bindContainerEvent('click', instance._onClickCalendar, '.input-group-addon');
							instance.bindContainerEvent('focus', instance._fireFocusEvent, '.form-control');
						}
					},

					destructor: function() {
						var instance = this;

						if (instance.datePicker) {
							instance.datePicker.destroy();
						}
					},

					clearDateInputValue: function() {
						var instance = this;
						var placeholder = instance._getDatePlaceholder();
						var triggerNode = instance.getTriggerNode();

						triggerNode.setAttribute('placeholder', placeholder);
						triggerNode.val('');

						instance.setValue('');
						instance.invalidValue = false;
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

					getDateMask: function() {
						var instance = this;

						var mask = instance.get('mask');

						var dateMask = [];

						var items = mask.split(instance._dateDelimiter);

						items.forEach(
							function(item, index) {
								if (item == '%Y') {
									dateMask.push(/\d/, /\d/, /\d/, /\d/);
								}
								else if (item) {
									dateMask.push(/\d/, /\d/);
								}

								if (index < 2) {
									dateMask.push(instance._dateDelimiter);
								}
							}
						);

						if (instance._endDelimiter) {
							dateMask.push(instance._dateDelimiter);
						}

						return dateMask;
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

					hasFocus: function(node) {
						var instance = this;

						var hasFocus = DateField.superclass.hasFocus.apply(instance, arguments);

						if (!hasFocus && node) {
							var datePicker = instance.datePicker;

							var calendar = datePicker.getCalendar();

							var popover = datePicker.getPopover();

							if (calendar.get('boundingBox').contains(node) || popover.get('visible')) {
								hasFocus = true;
							}
						}

						return hasFocus;
					},

					render: function() {
						var instance = this;

						DateField.superclass.render.apply(instance, arguments);

						var element = instance.getTriggerNode().getDOM();

						instance.dateMask = DDMDate.vanillaTextMask(
							{
								inputElement: element,
								mask: instance.getDateMask(),
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
								mask: instance.get('mask'),
								on: {
									selectionChange: A.bind('_onSelectionChange', instance)
								},
								popover: {
									zIndex: Liferay.zIndex.TOOLTIP
								},
								trigger: '[data-fieldname=' + qualifiedName + '] .form-control'
							}
						);

						instance.datePicker.getCalendar().on('dateClick', instance._onDateClick.bind(this));

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

						instance.dateValue = instance.getTriggerNode().val();

						if (instance.invalidValue) {
							instance.clearDateInputValue();
						}
						else {
							instance._setDatePickerValue(date);
						}
					},

					_getDatePlaceholder: function() {
						var instance = this;

						var placeholder = null;

						if (instance.dateMask) {
							placeholder = instance.dateMask.textMaskInputElement.state.previousPlaceholder;
						}

						return placeholder;
					},

					_maskValueFn: function() {
						var instance = this;

						var dateFormat = Liferay.AUI.getDateFormat();
						var languageId = Liferay.ThemeDisplay.getLanguageId().replace('_', '-');

						var customDateFormat = A.Intl.get('datatype-date-format', 'x', languageId);

						if (customDateFormat) {
							dateFormat = customDateFormat;
						}

						instance._dateDelimiter = '/';
						instance._endDelimiter = false;

						if (dateFormat.indexOf('.') != -1) {
							instance._dateDelimiter = '.';

							if (dateFormat.lastIndexOf('.') == dateFormat.length - 1) {
								instance._endDelimiter = true;
							}
						}

						if (dateFormat.indexOf('-') != -1) {
							instance._dateDelimiter = '-';
						}

						return dateFormat;
					},

					_onBlurInput: function() {
						var instance = this;

						if (!instance.hasFocus(document.activeElement)) {
							instance._fireBlurEvent();
						}
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
					},

					_onDateClick: function(event) {
						var instance = this;

						instance.getTriggerNode().val(instance.dateValue);
						instance._setDatePickerValue(event.date);
					},

					_onSelectionChange: function(event) {
						var instance = this;
						var triggerNode = instance.getTriggerNode();
						var value = triggerNode.val();

						if (!value || (value == instance._getDatePlaceholder())) {
							instance.invalidValue = true;
						}
					},

					_setDatePickerValue: function(date) {
						var instance = this;

						if (isArray(date) && date.length) {
							date = date[0];
						}

						instance.setValue(instance.getISODate(date));
						instance.validate();

						instance._fireStartedFillingEvent();

						instance.actionByCalendar = false;
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