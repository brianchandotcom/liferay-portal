/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-calendar-recurrence-dialog',
	A => {
		var DAYS_OF_WEEK = ['SU', 'MO', 'TU', 'WE', 'TH', 'FR', 'SA'];

		var FREQUENCY_MONTHLY = 'MONTHLY';

		var FREQUENCY_WEEKLY = 'WEEKLY';

		var FREQUENCY_YEARLY = 'YEARLY';

		var LIMIT_COUNT = 'after';

		var LIMIT_DATE = 'on';

		var LIMIT_UNLIMITED = 'never';

		var WEEK_LENGTH = A.DataType.DateMath.WEEK_LENGTH;

		var RecurrenceDialogController = A.Component.create({
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

				positionInput: {
					setter: A.one,
					value: null
				},

				positionSelect: {
					setter: A.one,
					value: null
				},

				positionalDayOfWeek: {
					getter: '_getPositionalDayOfWeek',
					setter: '_setPositionalDayOfWeek'
				},

				positionalDayOfWeekOptions: {
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
				_afterVisibilityChange(event) {
					var instance = this;

					var recurrenceDialog =
						window[instance._namespace + 'recurrenceDialog'];

					if (instance._confirmChanges) {
						instance.saveState();
					} else {
						var currentRecurrence = instance.get(
							'currentSavedState'
						);

						instance.set('recurrence', currentRecurrence);

						instance
							.get('repeatCheckbox')
							.set('checked', currentRecurrence.repeatable);

						if (!currentRecurrence.repeatable) {
							instance.get('summaryNode').empty();
						}
					}

					delete instance._confirmChanges;

					recurrenceDialog.bodyNode.toggle(event.newVal);

					recurrenceDialog.fillHeight(recurrenceDialog.bodyNode);
				},

				_calculatePosition() {
					var instance = this;

					var lastPositionCheckbox = instance.get(
						'lastPositionCheckbox'
					);

					var position = instance.get('startDatePosition');

					if (instance._isLastDayOfWeekInMonth()) {
						if (
							position > 4 ||
							lastPositionCheckbox.get('checked')
						) {
							position = -1;
						}
					}

					return position;
				},

				_canChooseLastDayOfWeek() {
					var instance = this;

					var mandatoryLastDay =
						instance.get('startDatePosition') > 4;

					return (
						instance._isLastDayOfWeekInMonth() && !mandatoryLastDay
					);
				},

				_getDaysOfWeek() {
					var instance = this;

					var dayOfWeekNodes = instance
						.get('daysOfWeekCheckboxes')
						.filter(':checked');

					return dayOfWeekNodes.val();
				},

				_getDaysOfWeekCheckboxes() {
					var instance = this;

					var weeklyRecurrenceOptions = instance.get(
						'weeklyRecurrenceOptions'
					);

					return weeklyRecurrenceOptions.all(':checkbox');
				},

				_getFrequency() {
					var instance = this;

					var frequencySelect = instance.get('frequencySelect');

					return frequencySelect.val();
				},

				_getInterval() {
					var instance = this;

					var intervalSelect = instance.get('intervalSelect');

					return intervalSelect.val();
				},

				_getLimitCount() {
					var instance = this;

					var limitCountInput = instance.get('limitCountInput');

					return parseInt(limitCountInput.val(), 10);
				},

				_getLimitDate() {
					var instance = this;

					var limitDateDatePicker = instance.get(
						'limitDateDatePicker'
					);

					return limitDateDatePicker.getDate();
				},

				_getLimitRadioButtons() {
					var instance = this;

					return [
						instance.get('limitCountRadioButton'),
						instance.get('limitDateRadioButton'),
						instance.get('noLimitRadioButton')
					];
				},

				_getLimitType() {
					var instance = this;

					var checkedLimitRadioButton = A.Array.find(
						instance.get('limitRadioButtons'),
						item => {
							return item.get('checked');
						}
					);

					return (
						checkedLimitRadioButton && checkedLimitRadioButton.val()
					);
				},

				_getPosition() {
					var instance = this;

					var positionInput = instance.get('positionInput');

					return positionInput.val();
				},

				_getPositionalDayOfWeek() {
					var instance = this;

					var dayOfWeekInput = instance.get('dayOfWeekInput');

					var positionalDayOfWeek = null;

					var repeatOnDayOfWeek = instance
						.get('repeatOnDayOfWeekRadioButton')
						.get('checked');

					var startDate = instance.get('startDate');

					if (
						instance._isPositionalFrequency() &&
						repeatOnDayOfWeek
					) {
						positionalDayOfWeek = {
							month: startDate.getMonth(),
							position: instance.get('position'),
							weekday: dayOfWeekInput.val()
						};
					}

					return positionalDayOfWeek;
				},

				_getRecurrence() {
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

				_getStartDate() {
					var instance = this;

					var startDateDatePicker = instance.get(
						'startDateDatePicker'
					);

					return startDateDatePicker.getDate();
				},

				_getStartDatePosition() {
					var instance = this;

					var startDateDatePicker = instance.get(
						'startDateDatePicker'
					);

					var startDate = startDateDatePicker.getDate();

					return Math.ceil(startDate.getDate() / WEEK_LENGTH);
				},

				_getStartTimeDayOfWeekInput() {
					var instance = this;

					var weeklyRecurrenceOptions = instance.get(
						'weeklyRecurrenceOptions'
					);

					return weeklyRecurrenceOptions.one('input[type=hidden]');
				},

				_getSummary() {
					var instance = this;

					var recurrence = instance.get('recurrence');

					return Liferay.RecurrenceUtil.getSummary(recurrence);
				},

				_hideModal(event, confirmed) {
					var instance = this;

					if (confirmed) {
						instance._confirmChanges = true;
					}

					window[instance._namespace + 'recurrenceDialog'].hide();
				},

				_isLastDayOfWeekInMonth() {
					var instance = this;

					var startDate = instance.get('startDate');

					var lastDate = A.DataType.DateMath.findMonthEnd(startDate);

					return (
						lastDate.getDate() - startDate.getDate() < WEEK_LENGTH
					);
				},

				_isPositionalFrequency() {
					var instance = this;

					var frequency = instance.get('frequency');

					return (
						frequency === FREQUENCY_MONTHLY ||
						frequency === FREQUENCY_YEARLY
					);
				},

				_onInputChange(event) {
					var instance = this;

					var currentTarget = event.currentTarget;

					if (currentTarget === instance.get('frequencySelect')) {
						instance._toggleViewWeeklyRecurrence();
					}

					if (
						currentTarget ===
							instance.get('repeatOnDayOfMonthRadioButton') ||
						currentTarget ===
							instance.get('repeatOnDayOfWeekRadioButton')
					) {
						instance._toggleViewPositionalDayOfWeek();
					}

					if (
						currentTarget === instance.get('lastPositionCheckbox')
					) {
						instance._setPositionInputValue();
					}

					instance._toggleDisabledLimitCountInput();
					instance._toggleDisabledLimitDateDatePicker();

					instance.fire('recurrenceChange');
				},

				_onStartDateDatePickerChange(event) {
					var instance = this;

					var date = event.newSelection[0];

					var dayOfWeek = DAYS_OF_WEEK[date.getDay()];

					var dayOfWeekInput = instance.get('dayOfWeekInput');

					var daysOfWeekCheckboxes = instance.get(
						'daysOfWeekCheckboxes'
					);

					var positionInput = instance.get('positionInput');

					var repeatCheckbox = instance.get('repeatCheckbox');

					var repeatOnDayOfWeekRadioButton = instance.get(
						'repeatOnDayOfWeekRadioButton'
					);

					var startTimeDayOfWeekInput = instance.get(
						'startTimeDayOfWeekInput'
					);

					startTimeDayOfWeekInput.val(dayOfWeek);

					daysOfWeekCheckboxes.each(item => {
						if (item.val() == dayOfWeek) {
							item.set('checked', true);
							item.set('disabled', true);
						} else if (item.get('disabled')) {
							item.set('disabled', false);

							if (!repeatCheckbox.get('checked')) {
								item.set('checked', false);
							}
						}
					});

					dayOfWeekInput.val(dayOfWeek);

					positionInput.val(instance._calculatePosition());

					if (repeatOnDayOfWeekRadioButton.get('checked')) {
						instance._toggleView(
							'positionalDayOfWeekOptions',
							instance._canChooseLastDayOfWeek()
						);
					}

					if (repeatCheckbox.get('checked')) {
						instance.fire('recurrenceChange');
					}
				},

				_setDatePicker(datePicker) {
					var popover = datePicker.get('popover');

					if (popover) {
						popover.zIndex = Liferay.zIndex.POPOVER;
					}

					return datePicker;
				},

				_setDaysOfWeek(value) {
					var instance = this;

					var dayOfWeekNodes = instance
						.get('daysOfWeekCheckboxes')
						.filter(':not([disabled])');

					dayOfWeekNodes.each(node => {
						var check = value.indexOf(node.get('value')) > -1;

						node.set('checked', check);
					});

					return value;
				},

				_setFrequency(value) {
					var instance = this;

					var frequencySelect = instance.get('frequencySelect');

					frequencySelect.set('value', value);

					return value;
				},

				_setInterval(value) {
					var instance = this;

					var intervalSelect = instance.get('intervalSelect');

					intervalSelect.set('value', value);

					return value;
				},

				_setLimitCount(value) {
					var instance = this;

					instance.get('limitCountInput').set('value', value || '');

					return value;
				},

				_setLimitDate(value) {
					var instance = this;

					var limitDateDatePicker = instance.get(
						'limitDateDatePicker'
					);

					if (limitDateDatePicker.get('activeInput')) {
						limitDateDatePicker.clearSelection('date');
						limitDateDatePicker.selectDates([value]);
					}

					return value;
				},

				_setLimitType(value) {
					var instance = this;

					A.each(instance.get('limitRadioButtons'), node => {
						if (node.get('value') === value) {
							node.set('checked', true);
						}
					});

					return value;
				},

				_setPositionInputValue() {
					var instance = this;

					var positionInput = instance.get('positionInput');

					positionInput.val(instance._calculatePosition());
				},

				_setPositionalDayOfWeek(value) {
					var instance = this;

					var lastPositionCheckbox = instance.get(
						'lastPositionCheckbox'
					);
					var repeatOnDayOfMonthRadioButton = instance.get(
						'repeatOnDayOfMonthRadioButton'
					);
					var repeatOnDayOfWeekRadioButton = instance.get(
						'repeatOnDayOfWeekRadioButton'
					);

					lastPositionCheckbox.set(
						'checked',
						value && value.position === '-1'
					);
					repeatOnDayOfMonthRadioButton.set('checked', !value);
					repeatOnDayOfWeekRadioButton.set('checked', !!value);

					return value;
				},

				_setRecurrence(data) {
					var instance = this;

					if (data) {
						instance.set('daysOfWeek', data.weekdays);
						instance.set('frequency', data.frequency);
						instance.set('interval', data.interval);
						instance.set('limitCount', data.count);
						instance.set('limitDate', data.untilDate);
						instance.set('limitType', data.endValue);
						instance.set(
							'positionalDayOfWeek',
							data.positionalWeekday
						);

						instance._updateUI();
					}
				},

				_toggleDisabledLimitCountInput() {
					var instance = this;

					var limitCountInput = instance.get('limitCountInput');

					var limitType = instance.get('limitType');

					var disableLimitCountInput =
						limitType === LIMIT_UNLIMITED ||
						limitType === LIMIT_DATE;

					Liferay.Util.toggleDisabled(
						limitCountInput,
						disableLimitCountInput
					);

					limitCountInput.selectText();
				},

				_toggleDisabledLimitDateDatePicker() {
					var instance = this;

					var limitType = instance.get('limitType');

					var disableLimitDateDatePicker =
						limitType === LIMIT_UNLIMITED ||
						limitType === LIMIT_COUNT;

					instance
						.get('limitDateDatePicker')
						.set('disabled', disableLimitDateDatePicker);
				},

				_toggleView(viewName, show) {
					var instance = this;

					var viewNode = instance.get(viewName);

					if (viewNode) {
						viewNode.toggle(show);
					}
				},

				_toggleViewPositionalDayOfWeek() {
					var instance = this;

					var repeatOnDayOfWeek = instance
						.get('repeatOnDayOfWeekRadioButton')
						.get('checked');

					instance._toggleView(
						'positionalDayOfWeekOptions',
						repeatOnDayOfWeek && instance._canChooseLastDayOfWeek()
					);
				},

				_toggleViewWeeklyRecurrence() {
					var instance = this;

					instance._toggleView(
						'weeklyRecurrenceOptions',
						instance.get('frequency') === FREQUENCY_WEEKLY
					);
					instance._toggleView(
						'monthlyRecurrenceOptions',
						instance._isPositionalFrequency()
					);
				},

				_updateUI() {
					var instance = this;

					instance._setPositionInputValue();
					instance._toggleDisabledLimitCountInput();
					instance._toggleDisabledLimitDateDatePicker();
					instance._toggleViewPositionalDayOfWeek();
					instance._toggleViewWeeklyRecurrence();

					instance.fire('recurrenceChange');
				},

				bindUI() {
					var instance = this;

					var container = instance.get('container');

					var limitDateDatePicker = instance.get(
						'limitDateDatePicker'
					);

					var startDateDatePicker = instance.get(
						'startDateDatePicker'
					);

					container.delegate(
						'change',
						A.bind(instance._onInputChange, instance),
						'select,input'
					);
					container.delegate(
						'keypress',
						A.bind(instance._onInputChange, instance),
						'select'
					);

					limitDateDatePicker.after(
						'selectionChange',
						A.bind(instance._onInputChange, instance)
					);
					startDateDatePicker.after(
						'selectionChange',
						A.bind(instance._onStartDateDatePickerChange, instance)
					);
				},

				initializer(config) {
					var instance = this;

					instance._namespace = config.namespace;

					instance.bindUI();
				},

				saveState() {
					var instance = this;

					var currentSavedState = instance.get('recurrence');

					currentSavedState.repeatable = instance
						.get('repeatCheckbox')
						.get('checked');

					instance.set('currentSavedState', currentSavedState);
				}
			}
		});

		Liferay.RecurrenceDialogController = RecurrenceDialogController;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-datatype',
			'liferay-calendar-recurrence-util'
		]
	}
);
