AUI.add(
	'liferay-calendar-recurrence-util',
	function(A) {
		var STR_DASH = '-';

		Liferay.RecurrenceUtil = {
			FREQUENCY: {
				DAILY: 'DAILY',
				MONTHLY: 'MONTHLY',
				WEEKLY: 'WEEKLY',
				YEARLY: 'YEARLY'
			},

			INTERVAL_UNITS: {},

			MONTH_LABELS: [],

			POSITION_LABELS: {},

			RECURRENCE_SUMMARIES: {},

			WEEKDAY_LABELS: {},

			getSummary: function(recurrence) {
				var instance = this;

				var key;
				var params = [];
				var parts = [];

				if (recurrence.interval == 1) {
					parts.push(A.Lang.String.toLowerCase(recurrence.frequency));
				}
				else {
					parts.push('every-x-' + instance.INTERVAL_UNITS[recurrence.frequency]);

					params.push(recurrence.interval);
				}

				if (recurrence.positionalWeekday) {
					if (recurrence.frequency == instance.FREQUENCY.MONTHLY) {
						parts.push('on-x-x');

						params.push(instance.POSITION_LABELS[recurrence.positionalWeekday.position]);
						params.push(instance.WEEKDAY_LABELS[recurrence.positionalWeekday.weekday]);
					}
					else {
						parts.push('on-x-x-of-x');

						params.push(instance.POSITION_LABELS[recurrence.positionalWeekday.position]);
						params.push(instance.WEEKDAY_LABELS[recurrence.positionalWeekday.weekday]);
						params.push(instance.MONTH_LABELS[recurrence.positionalWeekday.month]);
					}
				}
				else if (recurrence.frequency == instance.FREQUENCY.WEEKLY && recurrence.weekdays.length > 0) {
					parts.push('on-x');

					var weekdays = recurrence.weekdays.map(
						function(item) {
							return instance.WEEKDAY_LABELS[item];
						}
					);

					params.push(weekdays.join(', '));
				}

				if (recurrence.count && recurrence.endValue === 'after') {
					parts.push('x-times');

					params.push(recurrence.count);
				}
				else if (recurrence.untilDate && recurrence.endValue === 'on') {
					parts.push('until-x-x-x');

					var untilDate = recurrence.untilDate;

					params.push(instance.MONTH_LABELS[untilDate.getMonth()]);
					params.push(untilDate.getDate());
					params.push(untilDate.getFullYear());
				}

				key = parts.join(STR_DASH);

				return A.Lang.sub(instance.RECURRENCE_SUMMARIES[key], params);
			},

			openConfirmationPanel: function(actionName, onlyThisInstanceFn, allFollowingFn, allEventsInFn, cancelFn) {
				var instance = this;

				var changeDeleteText;
				var confirmationPanel;
				var titleText;

				if (actionName === 'delete') {
					titleText = Liferay.Language.get('delete-recurring-event');
					changeDeleteText = Liferay.Language.get('would-you-like-to-delete-only-this-event-all-events-in-the-series-or-this-and-all-future-events-in-the-series');
				}
				else {
					titleText = Liferay.Language.get('change-recurring-event');
					changeDeleteText = Liferay.Language.get('would-you-like-to-change-only-this-event-all-events-in-the-series-or-this-and-all-future-events-in-the-series');
				}

				var getButtonConfig = function(label, callback) {
					return {
						label: label,
						on: {
							click: function() {
								if (callback) {
									callback.apply(this, arguments);
								}

								confirmationPanel.hide();
							}
						}
					};
				};

				confirmationPanel = Liferay.Util.Window.getWindow(
					{
						dialog:	{
							bodyContent: changeDeleteText,
							destroyOnHide: true,
							height: 400,
							hideOn: [],
							resizable: false,
							toolbars: {
								footer: [
									getButtonConfig(Liferay.Language.get('only-this-instance'), onlyThisInstanceFn),
									getButtonConfig(Liferay.Language.get('all-following'), allFollowingFn),
									getButtonConfig(Liferay.Language.get('all-events-in-the-series'), allEventsInFn),
									getButtonConfig(Liferay.Language.get('cancel-this-change'), cancelFn)
								]
							},
							width: 700
						},
						title: titleText
					}
				);

				return confirmationPanel.render().show();
			}
		};
	},
	'',
	{
		requires: ['aui-base', 'liferay-util-window']
	}
);