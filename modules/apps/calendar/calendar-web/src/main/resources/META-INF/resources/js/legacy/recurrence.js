/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-calendar-recurrence-dialog',
	(A) => {
		const DAYS_OF_WEEK = ['SU', 'MO', 'TU', 'WE', 'TH', 'FR', 'SA'];

		const FREQUENCY_MONTHLY = 'MONTHLY';

		const FREQUENCY_WEEKLY = 'WEEKLY';

		const FREQUENCY_YEARLY = 'YEARLY';

		const LIMIT_COUNT = 'after';

		const LIMIT_DATE = 'on';

		const LIMIT_UNLIMITED = 'never';

		const WEEK_LENGTH = A.DataType.DateMath.WEEK_LENGTH;

		const RecurrenceDialogController = A.Component.create({
			ATTRS: {
				container: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				currentSavedState: {
					value: null,
				},

				dayOfWeekInput: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				daysOfWeek: {
					getter: '_getDaysOfWeek',
					setter: '_setDaysOfWeek',
				},

				daysOfWeekCheckboxes: {
					getter: '_getDaysOfWeekCheckboxes',
				},

				frequency: {
					getter: '_getFrequency',
					setter: '_setFrequency',
				},

				frequencySelect: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				interval: {
					getter: '_getInterval',
					setter: '_setInterval',
				},

				intervalSelect: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				lastPositionCheckbox: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				limitCount: {
					getter: '_getLimitCount',
					setter: '_setLimitCount',
				},

				limitCountInput: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				limitCountRadioButton: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				limitDate: {
					getter: '_getLimitDate',
					setter: '_setLimitDate',
				},

				limitDateDatePicker: {
					setter: '_setDatePicker',
					value: null,
				},

				limitDateRadioButton: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				limitRadioButtons: {
					getter: '_getLimitRadioButtons',
				},

				limitType: {
					getter: '_getLimitType',
					setter: '_setLimitType',
				},

				monthlyRecurrenceOptions: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				noLimitRadioButton: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				position: {
					getter: '_getPosition',
				},

				positionInput: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				positionSelect: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				positionalDayOfWeek: {
					getter: '_getPositionalDayOfWeek',
					setter: '_setPositionalDayOfWeek',
				},

				positionalDayOfWeekOptions: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				recurrence: {
					getter: '_getRecurrence',
					setter: '_setRecurrence',
				},

				repeatCheckbox: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				repeatOnDayOfMonthRadioButton: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				repeatOnDayOfWeekRadioButton: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				startDate: {
					getter: '_getStartDate',
				},

				startDateDatePicker: {
					value: null,
				},

				startDatePosition: {
					getter: '_getStartDatePosition',
				},

				startTimeDayOfWeekInput: {
					getter: '_getStartTimeDayOfWeekInput',
				},

				summary: {
					getter: '_getSummary',
				},

				summaryNode: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},

				weeklyRecurrenceOptions: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},
			},

			NAME: 'recurrence-dialog',

			prototype: {
				_afterVisibilityChange(event) {
					const instance = this;

					const recurrenceDialog =
						window[instance._namespace + 'recurrenceDialog'];

					if (instance._confirmChanges) {
						instance.saveState();
					}
					else {
						const currentRecurrence = instance.get(
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
					const instance = this;

					const lastPositionCheckbox = instance.get(
						'lastPositionCheckbox'
					);

					let position = instance.get('startDatePosition');

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
					const instance = this;

					const mandatoryLastDay =
						instance.get('startDatePosition') > 4;

					return (
						instance._isLastDayOfWeekInMonth() && !mandatoryLastDay
					);
				},

				_getDaysOfWeek() {
					const instance = this;

					const dayOfWeekNodes = instance
						.get('daysOfWeekCheckboxes')
						.filter(':checked');

					return dayOfWeekNodes.val();
				},

				_getDaysOfWeekCheckboxes() {
					const instance = this;

					const weeklyRecurrenceOptions = instance.get(
						'weeklyRecurrenceOptions'
					);

					return weeklyRecurrenceOptions.all(':checkbox');
				},

				_getFrequency() {
					const instance = this;

					const frequencySelect = instance.get('frequencySelect');

					return frequencySelect.val();
				},

				_getInterval() {
					const instance = this;

					const intervalSelect = instance.get('intervalSelect');

					return intervalSelect.val();
				},

				_getLimitCount() {
					const instance = this;

					const limitCountInput = instance.get('limitCountInput');

					return parseInt(limitCountInput.val(), 10);
				},

				_getLimitDate() {
					const instance = this;

					const limitDateDatePicker = instance.get(
						'limitDateDatePicker'
					);

					return limitDateDatePicker.getDate();
				},

				_getLimitRadioButtons() {
					const instance = this;

					return [
						instance.get('limitCountRadioButton'),
						instance.get('limitDateRadioButton'),
						instance.get('noLimitRadioButton'),
					];
				},

				_getLimitType() {
					const instance = this;

					const checkedLimitRadioButton = A.Array.find(
						instance.get('limitRadioButtons'),
						(item) => {
							return item.get('checked');
						}
					);

					return (
						checkedLimitRadioButton && checkedLimitRadioButton.val()
					);
				},

				_getPosition() {
					const instance = this;

					const positionInput = instance.get('positionInput');

					return positionInput.val();
				},

				_getPositionalDayOfWeek() {
					const instance = this;

					const dayOfWeekInput = instance.get('dayOfWeekInput');

					let positionalDayOfWeek = null;

					const repeatOnDayOfWeek = instance
						.get('repeatOnDayOfWeekRadioButton')
						.get('checked');

					const startDate = instance.get('startDate');

					if (
						instance._isPositionalFrequency() &&
						repeatOnDayOfWeek
					) {
						positionalDayOfWeek = {
							month: startDate.getMonth(),
							position: instance.get('position'),
							weekday: dayOfWeekInput.val(),
						};
					}

					return positionalDayOfWeek;
				},

				_getRecurrence() {
					const instance = this;

					return {
						count: instance.get('limitCount'),
						endValue: instance.get('limitType'),
						frequency: instance.get('frequency'),
						interval: instance.get('interval'),
						positionalWeekday: instance.get('positionalDayOfWeek'),
						untilDate: instance.get('limitDate'),
						weekdays: instance.get('daysOfWeek'),
					};
				},

				_getStartDate() {
					const instance = this;

					const startDateDatePicker = instance.get(
						'startDateDatePicker'
					);

					return startDateDatePicker.getDate();
				},

				_getStartDatePosition() {
					const instance = this;

					const startDateDatePicker = instance.get(
						'startDateDatePicker'
					);

					const startDate = startDateDatePicker.getDate();

					return Math.ceil(startDate.getDate() / WEEK_LENGTH);
				},

				_getStartTimeDayOfWeekInput() {
					const instance = this;

					const weeklyRecurrenceOptions = instance.get(
						'weeklyRecurrenceOptions'
					);

					return weeklyRecurrenceOptions.one('input[type=hidden]');
				},

				_getSummary() {
					const instance = this;

					const recurrence = instance.get('recurrence');

					return Liferay.RecurrenceUtil.getSummary(recurrence);
				},

				_hideModal(event, confirmed) {
					const instance = this;

					if (confirmed) {
						instance._confirmChanges = true;
					}

					window[instance._namespace + 'recurrenceDialog'].hide();
				},

				_isLastDayOfWeekInMonth() {
					const instance = this;

					const startDate = instance.get('startDate');

					const lastDate = A.DataType.DateMath.findMonthEnd(
						startDate
					);

					return (
						lastDate.getDate() - startDate.getDate() < WEEK_LENGTH
					);
				},

				_isPositionalFrequency() {
					const instance = this;

					const frequency = instance.get('frequency');

					return (
						frequency === FREQUENCY_MONTHLY ||
						frequency === FREQUENCY_YEARLY
					);
				},

				_onInputChange(event) {
					const instance = this;

					const currentTarget = event.currentTarget;

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
					const instance = this;

					const date = event.newSelection[0];

					const dayOfWeek = DAYS_OF_WEEK[date.getDay()];

					const dayOfWeekInput = instance.get('dayOfWeekInput');

					const positionInput = instance.get('positionInput');

					const repeatCheckbox = instance.get('repeatCheckbox');

					const repeatOnDayOfWeekRadioButton = instance.get(
						'repeatOnDayOfWeekRadioButton'
					);

					const startTimeDayOfWeekInput = instance.get(
						'startTimeDayOfWeekInput'
					);

					startTimeDayOfWeekInput.val(dayOfWeek);

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
					const popover = datePicker.get('popover');

					if (popover) {
						popover.zIndex = Liferay.zIndex.POPOVER;
					}

					return datePicker;
				},

				_setDaysOfWeek(value) {
					const instance = this;

					const dayOfWeekNodes = instance
						.get('daysOfWeekCheckboxes')
						.filter(':not([disabled])');

					dayOfWeekNodes.each((node) => {
						const check = value.indexOf(node.get('value')) > -1;

						node.set('checked', check);
					});

					return value;
				},

				_setFrequency(value) {
					const instance = this;

					const frequencySelect = instance.get('frequencySelect');

					frequencySelect.set('value', value);

					return value;
				},

				_setInterval(value) {
					const instance = this;

					const intervalSelect = instance.get('intervalSelect');

					intervalSelect.set('value', value);

					return value;
				},

				_setLimitCount(value) {
					const instance = this;

					instance.get('limitCountInput').set('value', value || '');

					return value;
				},

				_setLimitDate(value) {
					const instance = this;

					const limitDateDatePicker = instance.get(
						'limitDateDatePicker'
					);

					if (limitDateDatePicker.get('activeInput')) {
						limitDateDatePicker.clearSelection('date');
						limitDateDatePicker.selectDates([value]);
					}

					return value;
				},

				_setLimitType(value) {
					const instance = this;

					A.each(instance.get('limitRadioButtons'), (node) => {
						if (node.get('value') === value) {
							node.set('checked', true);
						}
					});

					return value;
				},

				_setPositionInputValue() {
					const instance = this;

					const positionInput = instance.get('positionInput');

					positionInput.val(instance._calculatePosition());
				},

				_setPositionalDayOfWeek(value) {
					const instance = this;

					const lastPositionCheckbox = instance.get(
						'lastPositionCheckbox'
					);
					const repeatOnDayOfMonthRadioButton = instance.get(
						'repeatOnDayOfMonthRadioButton'
					);
					const repeatOnDayOfWeekRadioButton = instance.get(
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
					const instance = this;

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
					const instance = this;

					const limitCountInput = instance.get('limitCountInput');

					const limitType = instance.get('limitType');

					const disableLimitCountInput =
						limitType === LIMIT_UNLIMITED ||
						limitType === LIMIT_DATE;

					Liferay.Util.toggleDisabled(
						limitCountInput,
						disableLimitCountInput
					);

					limitCountInput.selectText();
				},

				_toggleDisabledLimitDateDatePicker() {
					const instance = this;

					const limitType = instance.get('limitType');

					const disableLimitDateDatePicker =
						limitType === LIMIT_UNLIMITED ||
						limitType === LIMIT_COUNT;

					instance
						.get('limitDateDatePicker')
						.set('disabled', disableLimitDateDatePicker);
				},

				_toggleView(viewName, show) {
					const instance = this;

					const viewNode = instance.get(viewName);

					if (viewNode) {
						viewNode.toggle(show);
					}
				},

				_toggleViewPositionalDayOfWeek() {
					const instance = this;

					const repeatOnDayOfWeek = instance
						.get('repeatOnDayOfWeekRadioButton')
						.get('checked');

					instance._toggleView(
						'positionalDayOfWeekOptions',
						repeatOnDayOfWeek && instance._canChooseLastDayOfWeek()
					);
				},

				_toggleViewWeeklyRecurrence() {
					const instance = this;

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
					const instance = this;

					instance._setPositionInputValue();
					instance._toggleDisabledLimitCountInput();
					instance._toggleDisabledLimitDateDatePicker();
					instance._toggleViewPositionalDayOfWeek();
					instance._toggleViewWeeklyRecurrence();

					instance.fire('recurrenceChange');
				},

				bindUI() {
					const instance = this;

					const container = instance.get('container');

					const limitDateDatePicker = instance.get(
						'limitDateDatePicker'
					);

					const startDateDatePicker = instance.get(
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
					const instance = this;

					instance._namespace = config.namespace;

					instance.bindUI();
				},

				saveState() {
					const instance = this;

					const currentSavedState = instance.get('recurrence');

					currentSavedState.repeatable = instance
						.get('repeatCheckbox')
						.get('checked');

					instance.set('currentSavedState', currentSavedState);
				},
			},
		});

		Liferay.RecurrenceDialogController = RecurrenceDialogController;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-datatype',
			'liferay-calendar-recurrence-util',
		],
	}
);
