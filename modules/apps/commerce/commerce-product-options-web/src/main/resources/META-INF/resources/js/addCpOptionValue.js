/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CommerceServiceProvider,
	modalUtils,
	slugify,
} from 'commerce-frontend-js';
import {createPortletURL, debounce} from 'frontend-js-web';

export default function ({
	bcp47LanguageId,
	cpOptionId,
	defaultLanguageId,
	editOptionURL,
	isCPOptionSelectDate,
	namespace,
	windowState,
}) {
	const form = document.getElementById(namespace + 'fm');

	const dateInput = form.querySelector('#' + namespace + 'date');
	const durationInput = form.querySelector('#' + namespace + 'duration');
	const durationTypeInput = form.querySelector(
		'#' + namespace + 'durationType'
	);
	const keyInput = form.querySelector('#' + namespace + 'key');
	const labelInput = form.querySelector(
		'#' + namespace + 'optionValueSelectDateLabel'
	);
	const nameInput = form.querySelector('#' + namespace + 'name');
	const priorityInput = form.querySelector('#' + namespace + 'priority');
	const timeInput = form.querySelector('#' + namespace + 'time');
	const timeZoneInput = form.querySelector('#' + namespace + 'timeZone');

	const optionValueSelectDateObj = new optionValueSelectDate();

	function optionValueSelectDate() {
		this.date = null;
		this.duration = null;
		this.durationType = null;
		this.time = null;
		this.timeZone = null;

		this.setDate = function (date) {
			this.date = date;
		};

		this.setDuration = function (duration) {
			this.duration = duration;
		};

		this.setDurationType = function (durationType) {
			this.durationType = durationType;
		};

		this.setTime = function (time) {
			this.time = time;
		};

		this.setTimezone = function (timeZone) {
			this.timeZone = timeZone;
		};

		this.getKey = function () {
			return (
				this.date +
				'-' +
				this.time +
				'-' +
				this.duration +
				'-' +
				this.durationType +
				'-' +
				this.timeZone
			);
		};

		this.getLabel = function (locale) {
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
			const formattedDate = date.toLocaleDateString(locale, options);

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
		};
	}

	if (isCPOptionSelectDate) {
		const handleOnLabelInput = function () {
			optionValueSelectDateObj.setDate(slugify(dateInput.value));
			optionValueSelectDateObj.setTime(slugify(timeInput.value));
			optionValueSelectDateObj.setTimezone(timeZoneInput.value);
			optionValueSelectDateObj.setDuration(durationInput.value);
			optionValueSelectDateObj.setDurationType(durationTypeInput.value);
			labelInput.value =
				optionValueSelectDateObj.getLabel(bcp47LanguageId);
		};

		dateInput.addEventListener('focus', debounce(handleOnLabelInput, 200));
		durationInput.addEventListener(
			'input',
			debounce(handleOnLabelInput, 200)
		);
		durationTypeInput.addEventListener(
			'input',
			debounce(handleOnLabelInput, 200)
		);
		timeInput.addEventListener('change', debounce(handleOnLabelInput, 200));
		timeZoneInput.addEventListener(
			'change',
			debounce(handleOnLabelInput, 200)
		);
	}
	else {
		const handleOnNameInput = () => {
			keyInput.value = slugify(nameInput.value);
		};
		nameInput.addEventListener('input', debounce(handleOnNameInput, 200));
	}

	const AdminCatalogResource = CommerceServiceProvider.AdminCatalogAPI('v1');

	Liferay.provide(window, namespace + 'apiSubmit', () => {
		modalUtils.isSubmitting();
		const formattedData = {
			id: '',
			key: '',
			name: {},
			priority: 0,
		};

		if (isCPOptionSelectDate) {
			formattedData.key = slugify(optionValueSelectDateObj.getKey());
			formattedData.name[defaultLanguageId] = labelInput.value;
		}
		else {
			formattedData.key = keyInput.value;
			formattedData.name[defaultLanguageId] = nameInput.value;
		}

		formattedData.id = cpOptionId;
		formattedData.priority = priorityInput.value;

		AdminCatalogResource.createOptionValue(cpOptionId, formattedData)
			.then(() => {
				const redirectURL = createPortletURL(editOptionURL, {
					cpOptionId,
					p_p_state: windowState,
				});

				modalUtils.closeAndRedirect(redirectURL);
			})
			.catch(modalUtils.onSubmitFail);
	});
}
