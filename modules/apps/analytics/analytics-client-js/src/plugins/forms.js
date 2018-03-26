/**
 * Returns an identifier for a form element.
 * @param {object} form The form DOM element
 * @return {object} Either form id, name or action.
 */
function getFormKey(form) {
	return (
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
		formId: getFormKey(form),
	};
}

/**
 * Returns analytics payload with form information.
 * @param {object} form The form DOM element
 * @return {object} The payload with form information
 */
function getFormPayload(form) {
	return {formId: getFormKey(form)};
}

/**
 * Wether a form is trackable or not.
 * @param {object} form The form DOM element
 * @return {boolean} True if the form is trackable.
 */
function isTrackableForm(form) {
	return 'analytics' in form.dataset && !!getFormKey(form);
}

/**
 * Adds an event listener for the blur event and sends analytics information
 * when that event happens.
 * @param {object} The Analytics client instance
 */
function trackFieldBlurred(analytics) {
	document.addEventListener(
		'blur',
		({target}) => {
			const {form} = target;

			if (!form || !isTrackableForm(form)) return;

			const payload = getFieldPayload(target);

			const blurMark = `${payload.formId}${payload.fieldName}blurred`;
			performance.mark(blurMark);

			const focusMark = `${payload.formId}${payload.fieldName}focused`;
			performance.measure('focusDuration', focusMark, blurMark);

			const perfData = performance
				.getEntriesByName('focusDuration')
				.pop();
			const focusDuration = perfData.duration;

			analytics.send('fieldBlurred', 'forms', {
				...payload,
				focusDuration,
			});

			performance.clearMarks('focusDuration');
		},
		true
	);
}

/**
 * Adds an event listener for the focus event and sends analytics information
 * when that event happens.
 * @param {object} The Analytics client instance
 */
function trackFieldFocused(analytics) {
	document.addEventListener(
		'focus',
		({target}) => {
			const {form} = target;

			if (!form || !isTrackableForm(form)) return;

			const payload = getFieldPayload(target);

			const focusMark = `${payload.formId}${payload.fieldName}focused`;
			performance.mark(focusMark);

			analytics.send('fieldFocused', 'forms', payload);
		},
		true
	);
}

/**
 * Adds an event listener for a form submission and sends information when that
 * event happens.
 * @param {object} The Analytics client instance
 */
function trackFormSubmitted(analytics) {
	document.addEventListener(
		'submit',
		({target}) => {
			if (!isTrackableForm(target)) return;

			analytics.send('formSubmitted', 'forms', getFormPayload(target));
		},
		true
	);
}

/**
 * Sends information about forms rendered on the page when it was loaded.
 * @param {object} The Analytics client instance
 */
function trackFormViewed(analytics) {
	const sendFormViewed = () => {
		Array.prototype.slice
			.call(document.querySelectorAll('form'))
			.filter(form => isTrackableForm(form))
			.forEach(form => {
				let payload = getFormPayload(form);
				const title = form.dataset.analyticsTitle;

				if (title) {
					payload = {title, ...payload};
				}

				analytics.send('formViewed', 'forms', payload);
			});
	};

	if (
		document.readyState === 'interactive' ||
		document.readyState === 'complete' ||
		document.readyState === 'loaded'
	) {
		sendFormViewed();
	} else {
		document.addEventListener('DOMContentLoaded', () => sendFormViewed());
	}
}

/**
 * Plugin function that registers listener against form events
 * @param {object} analytics The Analytics client
 */
function forms(analytics) {
	trackFieldBlurred(analytics);
	trackFieldFocused(analytics);
	trackFormSubmitted(analytics);
	trackFormViewed(analytics);
}

export {forms};
export default forms;