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

import {onReady} from '../utils/events';

const applicationId = 'Form';

/**
 * Returns an identifier for a form element.
 * @param {object} form The form DOM element
 * @return {object} Either form id, name or action.
 */
function getFormKey(form) {
	return (
		form.dataset.analyticsAssetId ||
		form.dataset.analyticsFormId ||
		form.id ||
		form.getAttribute('name') ||
		form.action
	);
}

/**
 * Returns analytics payload with field information.
 * @param {object} form The field DOM element
 * @return {object} The payload with field information
 */
function getFieldPayload({form, name}) {
	return {
		fieldName: name,
		formId: getFormKey(form)
	};
}

/**
 * Returns analytics payload with form information.
 * @param {object} form The form DOM element
 * @return {object} The payload with form information
 */
function getFormPayload(form) {
	let payload = {
		formId: getFormKey(form)
	};

	if (form.dataset.analyticsAssetTitle) {
		payload = {
			...payload,
			title: form.dataset.analyticsAssetTitle
		};
	}

	return payload;
}

/**
 * Wether a form is trackable or not.
 * @param {object} form The form DOM element
 * @return {boolean} True if the form is trackable.
 */
function isTrackableForm(form) {
	return (
		('analyticsAssetId' in form.dataset ||
			'analyticsFormId' in form.dataset) &&
		!!getFormKey(form)
	);
}

/**
 * Adds an event listener for the blur event and sends analytics information
 * when that event happens.
 * @param {object} The Analytics client instance
 */
function trackFieldBlurred(analytics) {
	const onBlur = ({target}) => {
		const {form} = target;

		if (!form || !isTrackableForm(form)) {
			return;
		}

		const payload = getFieldPayload(target);

		const blurMark = `${payload.formId}${payload.fieldName}blurred`;
		performance.mark(blurMark);

		const focusMark = `${payload.formId}${payload.fieldName}focused`;
		performance.measure('focusDuration', focusMark, blurMark);

		const perfData = performance.getEntriesByName('focusDuration').pop();

		const focusDuration = perfData.duration;

		analytics.send('fieldBlurred', applicationId, {
			...payload,
			focusDuration
		});

		performance.clearMarks('focusDuration');
	};

	document.addEventListener('blur', onBlur, true);

	return () => document.removeEventListener('blur', onBlur, true);
}

/**
 * Adds an event listener for the focus event and sends analytics information
 * when that event happens.
 * @param {object} The Analytics client instance
 */
function trackFieldFocused(analytics) {
	const onFocus = ({target}) => {
		const {form} = target;

		if (!form || !isTrackableForm(form)) {
			return;
		}

		const payload = getFieldPayload(target);

		const focusMark = `${payload.formId}${payload.fieldName}focused`;
		performance.mark(focusMark);

		analytics.send('fieldFocused', applicationId, payload);
	};

	document.addEventListener('focus', onFocus, true);

	return () => document.removeEventListener('focus', onFocus, true);
}

/**
 * Adds an event listener for a form submission and sends information when that
 * event happens.
 * @param {object} The Analytics client instance
 */
function trackFormSubmitted(analytics) {
	const onSubmit = event => {
		const {target} = event;

		if (
			!isTrackableForm(target) ||
			(isTrackableForm(target) && event.defaultPrevented)
		) {
			return;
		}

		analytics.send('formSubmitted', applicationId, getFormPayload(target));
	};

	document.addEventListener('submit', onSubmit, true);

	return () => document.removeEventListener('submit', onSubmit, true);
}

/**
 * Sends information about forms rendered on the page when it was loaded.
 * @param {object} The Analytics client instance
 */
function trackFormViewed(analytics) {
	return onReady(() => {
		Array.prototype.slice
			.call(document.querySelectorAll('form'))
			.filter(form => isTrackableForm(form))
			.forEach(form => {
				const payload = getFormPayload(form);

				analytics.send('formViewed', applicationId, payload);
			});
	});
}

/**
 * Plugin function that registers listener against form events
 * @param {object} analytics The Analytics client
 */
function forms(analytics) {
	const stopTrackingFieldBlurred = trackFieldBlurred(analytics);
	const stopTrackingFieldFocused = trackFieldFocused(analytics);
	const stopTrackingFormSubmitted = trackFormSubmitted(analytics);
	const stopTrackingFormViewed = trackFormViewed(analytics);

	return () => {
		stopTrackingFieldBlurred();
		stopTrackingFieldFocused();
		stopTrackingFormSubmitted();
		stopTrackingFormViewed();
	};
}

export {forms};
export default forms;
