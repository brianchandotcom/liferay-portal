/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {slugify} from 'commerce-frontend-js';
import {debounce} from 'frontend-js-web';

class OptionValueSelectDate {
	constructor() {
		this.date = null;
		this.duration = null;
		this.durationType = null;
		this.time = null;
		this.timeZone = null;
	}

	setDate(date) {
		this.date = date;
	}

	setDuration(duration) {
		this.duration = duration;
	}

	setDurationType(durationType) {
		this.durationType = durationType;
	}

	setTime(time) {
		this.time = time;
	}

	setTimezone(timeZone) {
		this.timeZone = timeZone;
	}

	getLabel() {
		const dateSplit = this.date.split('-');
		const [hour, minute] = this.time.split('-');
		const date = new Date(
			dateSplit[2],
			dateSplit[0] - 1,
			dateSplit[1],
			hour,
			minute
		);
		const options = {
			day: 'numeric',
			hour: 'numeric',
			minute: 'numeric',
			month: 'numeric',
			year: 'numeric',
		};
		const formattedDate = date.toLocaleDateString(
			Liferay.ThemeDisplay.getBCP47LanguageId(),
			options
		);

		if (this.duration && this.durationType) {
			return (
				formattedDate +
				' (' +
				this.timeZone +
				'), ' +
				this.duration +
				' ' +
				this.durationType
			);
		}

		return formattedDate + ' (' + this.timeZone + ')';
	}
}

function handleBaseOptionRel(form, namespace) {
	const nameInput = form.querySelector(`#${namespace}optionValueName`);

	if (nameInput) {
		const keyInput = form.querySelector(`#${namespace}key`);

		const handleOnNameInput = () => {
			keyInput.value = slugify(nameInput.value);
		};

		nameInput.addEventListener('input', debounce(handleOnNameInput, 200));
	}
}

function handleDateOptionRel(form, namespace) {
	const dateInput = form.querySelector(`#${namespace}date`);
	const durationInput = form.querySelector(`#${namespace}duration`);
	const durationTypeInput = form.querySelector(`#${namespace}durationType`);
	const labelInput = form.querySelector(
		`#${namespace}optionValueSelectDateLabel`
	);
	const timeInput = form.querySelector(`#${namespace}time`);
	const timeZoneInput = form.querySelector(`#${namespace}timeZone`);

	const optionValueSelectDate = new OptionValueSelectDate();

	const handleOnLabelInput = () => {
		optionValueSelectDate.setDate(slugify(dateInput.value));
		optionValueSelectDate.setTime(slugify(timeInput.value));
		optionValueSelectDate.setTimezone(timeZoneInput.value);
		optionValueSelectDate.setDuration(durationInput.value);
		optionValueSelectDate.setDurationType(durationTypeInput.value);

		labelInput.value = optionValueSelectDate.getLabel(
			Liferay.ThemeDisplay.getBCP47LanguageId()
		);
	};

	dateInput.addEventListener('focus', debounce(handleOnLabelInput, 200));
	durationInput.addEventListener('input', debounce(handleOnLabelInput, 200));
	durationTypeInput.addEventListener(
		'input',
		debounce(handleOnLabelInput, 200)
	);
	timeInput.addEventListener('change', debounce(handleOnLabelInput, 200));
	timeZoneInput.addEventListener('change', debounce(handleOnLabelInput, 200));
}

export default function main({isDateOptionRel, namespace}) {
	const form = document.getElementById(
		`${namespace}cpDefinitionOptionValueRelfm`
	);

	if (form) {
		if (isDateOptionRel) {
			handleDateOptionRel(form, namespace);
		}
		else {
			handleBaseOptionRel(form, namespace);
		}
	}
}
