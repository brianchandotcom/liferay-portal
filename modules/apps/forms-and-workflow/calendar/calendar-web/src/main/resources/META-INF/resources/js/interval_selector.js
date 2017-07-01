AUI.add(
	'liferay-calendar-interval-selector',
	function(A) {
		var AArray = A.Array;

		var EVENT_SELECTION_CHANGE = 'selectionChange';

		var IntervalSelector = A.Component.create(
			{
				ATTRS: {
					endDatePicker: {
						value: null
					},

					endTimePicker: {
						value: null
					},

					startDatePicker: {
						value: null
					},

					startTimePicker: {
						value: null
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'interval-selector',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance.eventHandlers = [];

						instance._containerNode = instance.byId(config.containerId);
						instance._submitButtonNode = instance.byId(config.submitButtonId);

						instance._duration = 0;
						instance._endDate = new Date();
						instance._startDate = new Date();
						instance._validDate = true;

						instance._initPicker(instance.get('endDatePicker'));
						instance._initPicker(instance.get('endTimePicker'));
						instance._initPicker(instance.get('startDatePicker'));
						instance._initPicker(instance.get('startTimePicker'));

						instance._setEndDate();
						instance._setEndTime();
						instance._setStartDate();
						instance._setStartTime();
						instance._setDuration();

						instance.bindUI();
					},

					bindUI: function() {
						var instance = this;

						instance.startDurationPreservation();
					},

					destructor: function() {
						var instance = this;

						instance.stopDurationPreservation();

						instance.eventHandlers = null;
					},

					startDurationPreservation: function() {
						var instance = this;

						instance.eventHandlers.push(
							instance.get('endDatePicker').after(EVENT_SELECTION_CHANGE, instance._onEndDatePickerSelectionChange, instance),
							instance.get('endTimePicker').after(EVENT_SELECTION_CHANGE, instance._onEndTimePickerSelectionChange, instance),
							instance.get('startDatePicker').after(EVENT_SELECTION_CHANGE, instance._onStartDatePickerSelectionChange, instance),
							instance.get('startTimePicker').after(EVENT_SELECTION_CHANGE, instance._onStartTimePickerSelectionChange, instance)
						);
					},

					stopDurationPreservation: function() {
						var instance = this;

						AArray.invoke(instance.eventHandlers, 'detach');
					},

					_initPicker: function(picker) {
						var instance = this;

						var attrs = picker.getAttrs();

						var inputNode = A.one(attrs.container._node.children[0]);

						picker.useInputNodeOnce(inputNode);
					},

					_onEndDatePickerSelectionChange: function() {
						var instance = this;

						instance._setEndDate();

						var endDateValue = instance._endDate.valueOf();

						if (instance._validDate && (instance._startDate.valueOf() >= endDateValue)) {
							instance._startDate = new Date(endDateValue - instance._duration);

							instance._setStartDatePickerDate();
						}

						instance._setDuration();
						instance._validate();
					},

					_onEndTimePickerSelectionChange: function() {
						var instance = this;

						instance._setEndTime();

						var endDateValue = instance._endDate.valueOf();

						if (instance._validDate && (instance._startDate.valueOf() >= endDateValue)) {
							instance._startDate = new Date(endDateValue - instance._duration);

							instance._setStartDatePickerDate();
							instance._setStartTimePickerTime();
						}

						instance._setDuration();
						instance._validate();
					},

					_onStartDatePickerSelectionChange: function() {
						var instance = this;

						instance._setStartDate();

						if (instance._validDate) {
							instance._endDate = new Date(instance._startDate.valueOf() + instance._duration);

							instance._setEndDatePickerDate();
						}

						instance._setDuration();
						instance._validate();
					},

					_onStartTimePickerSelectionChange: function() {
						var instance = this;

						instance._setStartTime();

						if (instance._validDate) {
							instance._endDate = new Date(instance._startDate.valueOf() + instance._duration);

							instance._setEndDatePickerDate();
							instance._setEndTimePickerTime();
						}

						instance._setDuration();
						instance._validate();
					},

					_setDuration: function() {
						var instance = this;

						instance._duration = (instance._endDate.valueOf() - instance._startDate.valueOf());
					},

					_setEndDate: function() {
						var instance = this;

						var endDatePicker = instance.get('endDatePicker');

						var endDateObj = endDatePicker.getDate();

						var endDate = instance._endDate;

						endDate.setDate(endDateObj.getDate());
						endDate.setMonth(endDateObj.getMonth());
						endDate.setYear(endDateObj.getFullYear());
					},

					_setEndDatePickerDate: function() {
						var instance = this;

						var endDatePicker = instance.get('endDatePicker');

						endDatePicker.clearSelection(true);

						endDatePicker.selectDates([instance._endDate]);
					},

					_setEndTime: function() {
						var instance = this;

						var endTimePicker = instance.get('endTimePicker');

						var endTime = endTimePicker.getTime();

						instance._endDate.setHours(endTime.getHours());
						instance._endDate.setMinutes(endTime.getMinutes());
					},

					_setEndTimePickerTime: function() {
						var instance = this;

						var endTimePicker = instance.get('endTimePicker');

						endTimePicker.selectDates([instance._endDate]);
					},

					_setStartDate: function() {
						var instance = this;

						var startDatePicker = instance.get('startDatePicker');

						var startDateObj = startDatePicker.getDate();

						var startDate = instance._startDate;

						startDate.setDate(startDateObj.getDate());
						startDate.setMonth(startDateObj.getMonth());
						startDate.setYear(startDateObj.getFullYear());
					},

					_setStartDatePickerDate: function() {
						var instance = this;

						var startDatePicker = instance.get('startDatePicker');

						startDatePicker.clearSelection(true);

						startDatePicker.selectDates([instance._startDate]);
					},

					_setStartTime: function() {
						var instance = this;

						var startTimePicker = instance.get('startTimePicker');

						var startTime = startTimePicker.getTime();

						var startDate = instance._startDate;

						startDate.setHours(startTime.getHours());
						startDate.setMinutes(startTime.getMinutes());
					},

					_setStartTimePickerTime: function() {
						var instance = this;

						var startTimePicker = instance.get('startTimePicker');

						startTimePicker.selectDates([instance._startDate]);
					},

					_validate: function() {
						var instance = this;

						var validDate = (instance._duration > 0);

						instance._validDate = validDate;

						var meetingEventDate = instance._containerNode;

						if (meetingEventDate) {
							meetingEventDate.toggleClass('error', !validDate);

							var helpInline = meetingEventDate.one('.help-inline');

							if (validDate && helpInline) {
								helpInline.remove();
							}

							if (!validDate && !helpInline) {
								var inlineHelp = A.Node.create('<div class="help-inline">' + Liferay.Language.get('the-end-time-must-be-after-the-start-time') + '</div>');

								meetingEventDate.insert(inlineHelp);
							}

							var submitButton = instance._submitButtonNode;

							if (submitButton) {
								submitButton.attr('disabled', !validDate);
							}
						}
					}
				}
			}
		);

		Liferay.IntervalSelector = IntervalSelector;
	},
	'',
	{
		requires: ['aui-base', 'liferay-portlet-base']
	}
);