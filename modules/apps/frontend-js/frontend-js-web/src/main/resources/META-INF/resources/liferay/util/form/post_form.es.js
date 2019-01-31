import {
	isString,
	isObject
} from 'metal';
import dom from 'metal-dom';
import setFormValues from './set_form_values.es';

/**
 * Submits the form, with optional setting of form elements.
 * @param {!Element|!string} form The form DOM element or the selector
 * @param {!string} url An action
 * @param {Object=} data Form data to set before submission, an object
 * containing form element names and values
 * @review
 */

export default function postForm(form, url, data) {
	if (!isString(url)) {
		return;
	}

	form = dom.toElement(form);

	if (!form || form.nodeName !== 'FORM') {
		return;
	}

	form.setAttribute('method', 'post');

	if (isObject(data)) {
		setFormValues(form, data);
	}

	submitForm(form, url);
}