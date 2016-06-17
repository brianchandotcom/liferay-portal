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
			}
		};
	},
	'',
	{
		requires: ['aui-base']
	}
);