/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ServiceProvider from 'commerce-frontend-js/ServiceProvider/index';
import * as modalUtils from 'commerce-frontend-js/utilities/modals/index';
import slugify from 'commerce-frontend-js/utilities/slugify';
import {createPortletURL, debounce} from 'frontend-js-web';

export default function ({
	cpOptionId,
	bcp47LanguageId,
	defaultLanguageId,
	editOptionURL,
	isCPOptionSelectDate,
	namespace,
	windowState,
}) {
	const form = document.getElementById(namespace + 'fm');
	const keyInput = form.querySelector('#' + namespace + 'key');
	const nameInput = form.querySelector('#' + namespace + 'name');
	const priorityInput = form.querySelector('#' + namespace + 'priority');

	const labelInput = form.querySelector( '#'+namespace+'optionValueSelectDateLabel' );
	const dateInput = form.querySelector('#'+namespace+'date');
	const durationInput = form.querySelector('#'+namespace+'duration');
	const durationTypeInput = form.querySelector('#'+namespace+'durationType');
	const timeInput = form.querySelector('#'+namespace+'time');
	const timeZoneInput = form.querySelector('#'+namespace+'timezone');

	const optionValueSelectDateObj = new optionValueSelectDate();

	function optionValueSelectDate() {
		this.date = null;
		this.duration = null;
		this.durationType = null;
		this.time = null;
		this.timezone = null;

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

		this.setTimezone = function (timezone) {
			this.timezone = timezone;
		};

		this.getKey = function (){
			return this.date + '-' + this.time + '-' + this.duration + '-' + this.durationType + '-' + this.timezone;
		}

		this.getLabel = function (locale) {
			const [month, day, year] = this.date.split('-');
			const [hour, minute] = this.time.split('-');
			const date = new Date(year, month - 1, day, hour, minute);
			const options = {
				year: 'numeric',
				month: 'long',
				day: 'numeric',
				hour: 'numeric',
				minute: 'numeric',
			};
			const formattedDate = date.toLocaleDateString(locale, options);

			if (this.duration && this.durationType) {
				return (
					formattedDate +
					' (' +
					this.timezone +
					'), ' +
					this.duration +
					' ' +
					this.durationType
				);
			}
			return formattedDate + ' (' + this.timezone + ')';
		};
	}

	if (isCPOptionSelectDate) {
		const handleOnLabelInput = function () {
			optionValueSelectDateObj.setDate(slugify(dateInput.value));
			optionValueSelectDateObj.setTime(slugify(timeInput.value));
			optionValueSelectDateObj.setTimezone(timeZoneInput.value);
			optionValueSelectDateObj.setDuration(durationInput.value);
			optionValueSelectDateObj.setDurationType(durationTypeInput.value);
			labelInput.value = optionValueSelectDateObj.getLabel(bcp47LanguageId);
		};

		dateInput.addEventListener('focus', debounce(handleOnLabelInput, 200));
		durationInput.addEventListener('input', debounce(handleOnLabelInput, 200));
		durationTypeInput.addEventListener('input', debounce(handleOnLabelInput, 200));
		timeInput.addEventListener('change', debounce(handleOnLabelInput, 200));
		timeZoneInput.addEventListener('change', debounce(handleOnLabelInput, 200));

	} else {
		const handleOnNameInput = () => {
			keyInput.value = slugify(nameInput.value);
		};
		nameInput.addEventListener('input', debounce(handleOnNameInput, 200));
	}

	const AdminCatalogResource = ServiceProvider.AdminCatalogAPI('v1');

	Liferay.provide(window, namespace + 'apiSubmit', () => {
		modalUtils.isSubmitting();
		const formattedData = {
			id: '',
			key: '',
			name: {},
			priority: 0,
		};

		if (isCPOptionSelectDate){
			formattedData.key = slugify(optionValueSelectDateObj.getKey());
			formattedData.name[defaultLanguageId] = labelInput.value;
		} else {
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
