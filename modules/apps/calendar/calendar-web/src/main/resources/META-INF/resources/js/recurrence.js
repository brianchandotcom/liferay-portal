AUI.add(
	'liferay-calendar-recurrence-dialog',
	function(A) {
		var DAYS_OF_WEEK = ['SU', 'MO', 'TU', 'WE', 'TH', 'FR', 'SA'];

		var FREQUENCY_MONTHLY = 'MONTHLY';

		var FREQUENCY_WEEKLY = 'WEEKLY';

		var FREQUENCY_YEARLY = 'YEARLY';

		var LIMIT_COUNT = 'after';

		var LIMIT_DATE = 'on';

		var LIMIT_UNLIMITED = 'never';

		var WEEK_LENGTH = A.DataType.DateMath.WEEK_LENGTH;

		var RecurrenceDialogController = A.Component.create(
			{

				ATTRS: {
					container: {
						setter: A.one,
						value: null
					},

					currentSavedState: {
						value: null
					},

					dayOfWeekInput: {
						setter: A.one,
						value: null
					},

					daysOfWeek: {
						getter: '_getDaysOfWeek',
						setter: '_setDaysOfWeek'
					},

					daysOfWeekCheckboxes: {
						getter: '_getDaysOfWeekCheckboxes'
					},

					frequency: {
						getter: '_getFrequency',
						setter: '_setFrequency'
					},

					frequencySelect: {
						setter: A.one,
						value: null
					},

					interval: {
						getter: '_getInterval',
						setter: '_setInterval'
					},

					intervalSelect: {
						setter: A.one,
						value: null
					},

					lastPositionCheckbox: {
						setter: A.one,
						value: null
					},

					limitCount: {
						getter: '_getLimitCount',
						setter: '_setLimitCount'
					},

					limitCountInput: {
						setter: A.one,
						value: null
					},

					limitCountRadioButton: {
						setter: A.one,
						value: null
					},

					limitDate: {
						getter: '_getLimitDate',
						setter: '_setLimitDate'
					},

					limitDateDatePicker: {
						setter: '_setDatePicker',
						value: null
					},

					limitDateRadioButton: {
						setter: A.one,
						value: null
					},

					limitRadioButtons: {
						getter: '_getLimitRadioButtons'
					},

					limitType: {
						getter: '_getLimitType',
						setter: '_setLimitType'
					},

					monthlyRecurrenceOptions: {
						setter: A.one,
						value: null
					},

					noLimitRadioButton: {
						setter: A.one,
						value: null
					},

					position: {
						getter: '_getPosition'
					},

					positionalDayOfWeek: {
						getter: '_getPositionalDayOfWeek',
						setter: '_setPositionalDayOfWeek'
					},

					positionalDayOfWeekOptions: {
						setter: A.one,
						value: null
					},

					positionInput: {
						setter: A.one,
						value: null
					},

					positionSelect: {
						setter: A.one,
						value: null
					},

					recurrence: {
						getter: '_getRecurrence',
						setter: '_setRecurrence'
					},

					repeatCheckbox: {
						setter: A.one,
						value: null
					},

					repeatOnDayOfMonthRadioButton: {
						setter: A.one,
						value: null
					},

					repeatOnDayOfWeekRadioButton: {
						setter: A.one,
						value: null
					},

					startDate: {
						getter: '_getStartDate'
					},

					startDateDatePicker: {
						value: null
					},

					startDatePosition: {
						getter: '_getStartDatePosition'
					},

					startTimeDayOfWeekInput: {
						getter: '_getStartTimeDayOfWeekInput'
					},

					summary: {
						getter: '_getSummary'
					},

					summaryNode: {
						setter: A.one,
						value: null
					},

					weeklyRecurrenceOptions: {
						setter: A.one,
						value: null
					}
				},

				NAME: 'recurrence-dialog',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._namespace = config.namespace;

						instance.bindUI();
					},

					bindUI: function() {
						var instance = this;

						var container = instance.get('container');

						var limitDateDatePicker = instance.get('limitDateDatePicker');

						var startDateDatePicker = instance.get('startDateDatePicker');

						container.delegate('change', A.bind(instance._onInputChange, instance), 'select,input');
						container.delegate('keypress', A.bind(instance._onInputChange, instance), 'select');

						limitDateDatePicker.after('selectionChange', A.bind(instance._onInputChange, instance));
						startDateDatePicker.after('selectionChange', A.bind(instance._onStartDateDatePickerChange, instance));
					},

					saveState: function() {
						var instance = this;

						var currentSavedState = instance.get('recurrence');

						currentSavedState.repeatable = instance.get('repeatCheckbox').get('checked');

						instance.set('currentSavedState', currentSavedState);
					},

					_afterVisibilityChange: function(event) {
						var instance = this;

						var recurrenceDialog = window[instance._namespace + 'recurrenceDialog'];

						if (instance._confirmChanges) {
							instance.saveState();
						}
						else {
							var currentRecurrence = instance.get('currentSavedState');

							instance.set('recurrence', currentRecurrence);

							instance.get('repeatCheckbox').set('checked', currentRecurrence.repeatable);

							if (!currentRecurrence.repeatable) {
								instance.get('summaryNode').empty();
							}
						}

						delete instance._confirmChanges;

						recurrenceDialog.bodyNode.toggle(event.newVal);

						recurrenceDialog.fillHeight(recurrenceDialog.bodyNode);
					},

					_calculatePosition: function() {
						var instance = this;

						var lastPositionCheckbox = instance.get('lastPositionCheckbox');

						var position = instance.get('startDatePosition');

						if (instance._isLastDayOfWeekInMonth()) {
							if ((position > 4) || lastPositionCheckbox.get('checked')) {
								position = -1;
							}
						}

						return position;
					},

					_canChooseLastDayOfWeek: function() {
						var instance = this;

						var mandatoryLastDay = instance.get('startDatePosition') > 4;

						return instance._isLastDayOfWeekInMonth() && !mandatoryLastDay;
					},

					_getDaysOfWeek: function() {
						var instance = this;

						var dayOfWeekNodes = instance.get('daysOfWeekCheckboxes').filter(':checked');

						return dayOfWeekNodes.val();
					},

					_getDaysOfWeekCheckboxes: function() {
						var instance = this;

						var weeklyRecurrenceOptions = instance.get('weeklyRecurrenceOptions');

						return weeklyRecurrenceOptions.all(':checkbox');
					},

					_getFrequency: function() {
						var instance = this;

						var frequencySelect = instance.get('frequencySelect');

						return frequencySelect.val();
					},

					_getInterval: function() {
						var instance = this;

						var intervalSelect = instance.get('intervalSelect');

						return intervalSelect.val();
					},

					_getLimitCount: function() {
						var instance = this;

						var limitCountInput = instance.get('limitCountInput');

						return parseInt(limitCountInput.val(), 10);
					},

					_getLimitDate: function() {
						var instance = this;

						var limitDateDatePicker = instance.get('limitDateDatePicker');

						return limitDateDatePicker.getDate();
					},

					_getLimitRadioButtons: function() {
						var instance = this;

						return [instance.get('limitCountRadioButton'), instance.get('limitDateRadioButton'), instance.get('noLimitRadioButton')];
					},

					_getLimitType: function() {
						var instance = this;

						var checkedLimitRadioButton = A.Array.find(
							instance.get('limitRadioButtons'),
							function(item, index) {
								return item.get('checked');
							}
						);

						return checkedLimitRadioButton && checkedLimitRadioButton.val();
					},

					_getPosition: function() {
						var instance = this;

						var positionInput = instance.get('positionInput');

						return positionInput.val();
					},

					_getPositionalDayOfWeek: function() {
						var instance = this;

						var dayOfWeekInput = instance.get('dayOfWeekInput');

						var positionalDayOfWeek = null;

						var repeatOnDayOfWeek = instance.get('repeatOnDayOfWeekRadioButton').get('checked');

						var startDate = instance.get('startDate');

						if (instance._isPositionalFrequency() && repeatOnDayOfWeek) {
							positionalDayOfWeek = {
								month: startDate.getMonth(),
								position: instance.get('position'),
								weekday: dayOfWeekInput.val()
							};
						}

						return positionalDayOfWeek;
					},

					_getRecurrence: function() {
						var instance = this;

						return {
							count: instance.get('limitCount'),
							endValue: instance.get('limitType'),
							frequency: instance.get('frequency'),
							interval: instance.get('interval'),
							positionalWeekday: instance.get('positionalDayOfWeek'),
							untilDate: instance.get('limitDate'),
							weekdays: instance.get('daysOfWeek')
						};
					},

					_getStartDate: function() {
						var instance = this;

						var startDateDatePicker = instance.get('startDateDatePicker');

						return startDateDatePicker.getDate();
					},

					_getStartDatePosition: function() {
						var instance = this;

						var startDateDatePicker = instance.get('startDateDatePicker');

						var startDate = startDateDatePicker.getDate();

						return Math.ceil(startDate.getDate() / WEEK_LENGTH);
					},

					_getStartTimeDayOfWeekInput: function() {
						var instance = this;

						var weeklyRecurrenceOptions = instance.get('weeklyRecurrenceOptions');

						return weeklyRecurrenceOptions.one('input[type=hidden]');
					},

					_getSummary: function() {
						var instance = this;

						var recurrence = instance.get('recurrence');

						return Liferay.RecurrenceUtil.getSummary(recurrence);
					},

					_hideModal: function(event, confirmed) {
						var instance = this;

						if (confirmed) {
							instance._confirmChanges = true;
						}

						window[instance._namespace + 'recurrenceDialog'].hide();
					},

					_isLastDayOfWeekInMonth: function() {
						var instance = this;

						var startDate = instance.get('startDate');

						var lastDate = A.DataType.DateMath.findMonthEnd(startDate);

						return lastDate.getDate() - startDate.getDate() < WEEK_LENGTH;
					},

					_isPositionalFrequency: function() {
						var instance = this;

						var frequency = instance.get('frequency');

						return (frequency === FREQUENCY_MONTHLY) || (frequency === FREQUENCY_YEARLY);
					},

					_onInputChange: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						if (currentTarget === instance.get('frequencySelect')) {
							instance._toggleViewWeeklyRecurrence();
						}

						if (currentTarget === instance.get('repeatOnDayOfMonthRadioButton') || currentTarget === instance.get('repeatOnDayOfWeekRadioButton')) {
							instance._toggleViewPositionalDayOfWeek();
						}

						if (currentTarget === instance.get('lastPositionCheckbox')) {
							instance._setPositionInputValue();
						}

						instance._toggleDisabledLimitCountInput();
						instance._toggleDisabledLimitDateDatePicker();

						instance.fire('recurrenceChange');
					},

					_onStartDateDatePickerChange: function(event) {
						var instance = this;

						var date = event.newSelection[0];

						var dayOfWeek = DAYS_OF_WEEK[date.getDay()];

						var dayOfWeekInput = instance.get('dayOfWeekInput');

						var daysOfWeekCheckboxes = instance.get('daysOfWeekCheckboxes');

						var positionInput = instance.get('positionInput');

						var repeatCheckbox = instance.get('repeatCheckbox');

						var repeatOnDayOfWeekRadioButton = instance.get('repeatOnDayOfWeekRadioButton');

						var startTimeDayOfWeekInput = instance.get('startTimeDayOfWeekInput');

						startTimeDayOfWeekInput.val(dayOfWeek);

						daysOfWeekCheckboxes.each(
							function(item) {
								if (item.val() == dayOfWeek) {
									item.set('checked', true);
									item.set('disabled', true);
								}
								else if (item.get('disabled')) {
									item.set('disabled', false);

									if (!repeatCheckbox.get('checked')) {
										item.set('checked', false);
									}
								}
							}
						);

						dayOfWeekInput.val(dayOfWeek);

						positionInput.val(instance._calculatePosition());

						if (repeatOnDayOfWeekRadioButton.get('checked')) {
							instance._toggleView('positionalDayOfWeekOptions', instance._canChooseLastDayOfWeek());
						}

						if (repeatCheckbox.get('checked')) {
							instance.fire('recurrenceChange');
						}
					},

					_setDatePicker: function(datePicker) {
						var popover = datePicker.get('popover');

						if (popover) {
							popover.zIndex = Liferay.zIndex.POPOVER;
						}

						return datePicker;
					},

					_setDaysOfWeek: function(value) {
						var instance = this;

						var dayOfWeekNodes = instance.get('daysOfWeekCheckboxes').filter(':not([disabled])');

						dayOfWeekNodes.each(
							function(node, index) {
								var check = value.indexOf(node.get('value')) > -1;

								node.set('checked', check);
							}
						);

						return value;
					},

					_setFrequency: function(value) {
						var instance = this;

						var frequencySelect = instance.get('frequencySelect');

						frequencySelect.set('value', value);

						return value;
					},

					_setInterval: function(value) {
						var instance = this;

						var intervalSelect = instance.get('intervalSelect');

						intervalSelect.set('value', value);

						return value;
					},

					_setLimitCount: function(value) {
						var instance = this;

						instance.get('limitCountInput').set('value', value || '');

						return value;
					},

					_setLimitDate: function(value) {
						var instance = this;

						var limitDateDatePicker = instance.get('limitDateDatePicker');

						if (limitDateDatePicker.get('activeInput')) {
							limitDateDatePicker.clearSelection('date');
							limitDateDatePicker.selectDates([value]);
						}

						return value;
					},

					_setLimitType: function(value) {
						var instance = this;

						A.each(
							instance.get('limitRadioButtons'),
							function(node, index) {
								if (node.get('value') === value) {
									node.set('checked', true);
								}
							}
						);

						return value;
					},

					_setPositionalDayOfWeek: function(value) {
						var instance = this;

						var lastPositionCheckbox = instance.get('lastPositionCheckbox');
						var repeatOnDayOfMonthRadioButton = instance.get('repeatOnDayOfMonthRadioButton');
						var repeatOnDayOfWeekRadioButton = instance.get('repeatOnDayOfWeekRadioButton');

						lastPositionCheckbox.set('checked', (value && value.position === '-1'));
						repeatOnDayOfMonthRadioButton.set('checked', !value);
						repeatOnDayOfWeekRadioButton.set('checked', !!value);

						return value;
					},

					_setPositionInputValue: function() {
						var instance = this;

						var positionInput = instance.get('positionInput');

						positionInput.val(instance._calculatePosition());
					},

					_setRecurrence: function(data) {
						var instance = this;

						if (data) {
							instance.set('daysOfWeek', data.weekdays);
							instance.set('frequency', data.frequency);
							instance.set('interval', data.interval);
							instance.set('limitCount', data.count);
							instance.set('limitDate', data.untilDate);
							instance.set('limitType', data.endValue);
							instance.set('positionalDayOfWeek', data.positionalWeekday);

							instance._updateUI();
						}
					},

					_toggleDisabledLimitCountInput: function() {
						var instance = this;

						var limitCountInput = instance.get('limitCountInput');

						var limitType = instance.get('limitType');

						var disableLimitCountInput = (limitType === LIMIT_UNLIMITED) || (limitType === LIMIT_DATE);

						Liferay.Util.toggleDisabled(limitCountInput, disableLimitCountInput);

						limitCountInput.selectText();
					},

					_toggleDisabledLimitDateDatePicker: function() {
						var instance = this;

						var limitType = instance.get('limitType');

						var disableLimitDateDatePicker = (limitType === LIMIT_UNLIMITED) || (limitType === LIMIT_COUNT);

						instance.get('limitDateDatePicker').set('disabled', disableLimitDateDatePicker);
					},

					_toggleView: function(viewName, show) {
						var instance = this;

						var viewNode = instance.get(viewName);

						if (viewNode) {
							viewNode.toggle(show);
						}
					},

					_toggleViewPositionalDayOfWeek: function() {
						var instance = this;

						var repeatOnDayOfWeek = instance.get('repeatOnDayOfWeekRadioButton').get('checked');

						instance._toggleView('positionalDayOfWeekOptions', repeatOnDayOfWeek && instance._canChooseLastDayOfWeek());
					},

					_toggleViewWeeklyRecurrence: function() {
						var instance = this;

						instance._toggleView('weeklyRecurrenceOptions', instance.get('frequency') === FREQUENCY_WEEKLY);
						instance._toggleView('monthlyRecurrenceOptions', instance._isPositionalFrequency());
					},

					_updateUI: function() {
						var instance = this;

						instance._setPositionInputValue();
						instance._toggleDisabledLimitCountInput();
						instance._toggleDisabledLimitDateDatePicker();
						instance._toggleViewPositionalDayOfWeek();
						instance._toggleViewWeeklyRecurrence();

						instance.fire('recurrenceChange');
					}
				}
			}
		);

		Liferay.RecurrenceDialogController = RecurrenceDialogController;
	},
	'',
	{
		requires: ['aui-base', 'aui-datatype', 'liferay-calendar-recurrence-util']
	}
);