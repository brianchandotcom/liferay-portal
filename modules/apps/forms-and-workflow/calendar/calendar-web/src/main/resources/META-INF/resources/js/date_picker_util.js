AUI.add(
	'liferay-calendar-date-picker-util',
	function(A) {
		var Lang = A.Lang;

		var toInt = Lang.toInt;

		Liferay.DatePickerUtil = {
			syncUI: function(schedulerEvent, intervalSelector) {
				var instance = this;

				intervalSelector.stopDurationPreservation();

				var startDate = schedulerEvent.get('startDate');

				var startDatePicker = intervalSelector.get('startDatePicker');

				var startTimePicker = intervalSelector.get('startTimePicker');

				startDatePicker.deselectDates();
				startDatePicker.selectDates([startDate]);
				startTimePicker.selectDates([startDate]);

				var endDate = schedulerEvent.get('endDate');

				var endDatePicker = intervalSelector.get('endDatePicker');

				var endTimePicker = intervalSelector.get('endTimePicker');

				endDatePicker.deselectDates();
				endDatePicker.selectDates([endDate]);
				endTimePicker.selectDates([endDate]);

				intervalSelector.startDurationPreservation();
			},

			linkToSchedulerEvent: function(datePickerContainer, schedulerEvent, dateAttr) {
				var instance = this;

				var selects = A.one(datePickerContainer).all('select');

				selects.on(
					'change',
					function(event) {
						var currentTarget = event.currentTarget;

						var date = schedulerEvent.get(dateAttr);

						var selectedSetter = selects.indexOf(currentTarget);

						var setters = [date.setMonth, date.setDate, date.setFullYear, date.setHours, date.setMinutes, date.setHours];

						var value = toInt(currentTarget.val());

						if (selectedSetter === 3 && date.getHours() > 12) {
							value += 12;
						}

						if (selectedSetter === 5) {
							value = date.getHours() + (value === 1 ? 12 : -12);
						}

						setters[selectedSetter].call(date, value);

						schedulerEvent.get('scheduler').syncEventsUI();
					}
				);
			}
		};
	},
	'',
	{
		requires: ['aui-base']
	}
);