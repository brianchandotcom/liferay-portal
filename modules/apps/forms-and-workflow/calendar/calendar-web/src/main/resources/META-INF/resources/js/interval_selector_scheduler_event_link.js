AUI.add(
	'liferay-calendar-interval-selector-scheduler-event-link',
	function(A) {
		var AArray = A.Array;

		var IntervalSelectorSchedulerEventLink = A.Component.create(
			{
				ATTRS: {
					intervalSelector: {
					},

					schedulerEvent: {
					}
				},

				EXTENDS: A.Base,

				NAME: 'interval-selector-scheduler-event-link',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._intervalSelectorUpdated = false;

						instance.bindUI();
					},

					bindUI: function() {
						var instance = this;

						var intervalSelector = instance.get('intervalSelector');

						var schedulerEvent = instance.get('schedulerEvent');

						var endDatePicker = intervalSelector.get('endDatePicker');

						var endTimePicker = intervalSelector.get('endTimePicker');

						var startDatePicker = intervalSelector.get('startDatePicker');

						var startTimePicker = intervalSelector.get('startTimePicker');

						instance.eventHandlers = [
						    endDatePicker.after('selectionChange', A.bind(instance._updateEndDate, instance)),
						    endTimePicker.after('selectionChange', A.bind(instance._updateEndDate, instance)),
						    startDatePicker.after('selectionChange', A.bind(instance._updateStartDate, instance)),
						    startTimePicker.after('selectionChange', A.bind(instance._updateStartDate, instance)),
						    schedulerEvent.after('endDateChange', A.bind(instance._updateIntervalSelector, instance)),
						    schedulerEvent.after('startDateChange', A.bind(instance._updateIntervalSelector, instance))
						];
					},

					destructor: function() {
						var instance = this;

						instance.unlink();

						instance.eventHandlers = null;
					},

					unlink: function() {
						var instance = this;

						AArray.invoke(instance.eventHandlers, 'detach');
					},

					_updateEndDate: function() {
						var instance = this;

						var intervalSelector = instance.get('intervalSelector');

						var endDatePicker = intervalSelector.get('endDatePicker');

						var endTimePicker = intervalSelector.get('endTimePicker');

						var endDate = endDatePicker.getDate();

						var endTime = endTimePicker.getTime();

						endDate.setHours(endTime.getHours());

						endDate.setMinutes(endTime.getMinutes());

						instance._updateSchedulerEvent('endDate', endDate);
					},

					_updateIntervalSelector: function(event) {
						var instance = this;

						var prevDate = event.prevVal;

						var newDate = event.newVal;

						if (!instance._intervalSelectorUpdated && (prevDate.getTime() !== newDate.getTime())) {
							var intervalSelector = instance.get('intervalSelector');

							var schedulerEvent = instance.get('schedulerEvent');

							var attribute = event.attrName;

							var prefix = attribute.replace('Date', '');

							var date = schedulerEvent.get(attribute);

							var datePicker = intervalSelector.get(prefix + 'DatePicker');

							var timePicker = intervalSelector.get(prefix + 'TimePicker');

							intervalSelector.stopDurationPreservation();

							datePicker.deselectDates();
							datePicker.selectDates([date]);
							timePicker.selectDates([date]);

							intervalSelector.startDurationPreservation();
						}
					},

					_updateStartDate: function() {
						var instance = this;

						var intervalSelector = instance.get('intervalSelector');

						var startDatePicker = intervalSelector.get('startDatePicker');

						var startTimePicker = intervalSelector.get('startTimePicker');

						var startDate = startDatePicker.getDate();

						var startTime = startTimePicker.getTime();

						startDate.setHours(startTime.getHours());

						startDate.setMinutes(startTime.getMinutes());

						instance._updateSchedulerEvent('startDate', startDate);
					},

					_updateSchedulerEvent: function(eventDateType, eventDate) {
						var instance = this;

						var schedulerEvent = instance.get('schedulerEvent');

						var scheduler = schedulerEvent.get('scheduler');

						instance._intervalSelectorUpdated = true;

						schedulerEvent.set(eventDateType, eventDate);

						instance._intervalSelectorUpdated = false;

						scheduler.syncEventsUI();
					}
				}
			}
		);

		Liferay.IntervalSelectorSchedulerEventLink = IntervalSelectorSchedulerEventLink;
	},
	'',
	{
		requires: ['aui-base', 'liferay-portlet-base']
	}
);