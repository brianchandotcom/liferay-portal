import dom from 'metal-dom';
import {isDef, isObject, isString} from 'metal';
import setFormValues from './set_form_values.es';

/**
 * Submits the form, with optional setting of form elements.
 * @param {!Element|!string} form The form DOM element or the selector
 * @param {Object=} options An object containing optional settings:
 * - `url` : a string containing form action url
 * - `data` : an object containing form elements keys and values, to be set
 * before submission
 * @review
 */

export default function postForm(form, options) {
	form = dom.toElement(form);

	if (form && form.nodeName === 'FORM') {
		form.setAttribute('method', 'post');

		if (isObject(options)) {
			const {data, url} = options;

			if (isObject(data)) {
				setFormValues(form, data);
			} else {
				return;
			}

			if (!isDef(url)) {
				submitForm(form);
			} else if (isString(url)) {
				submitForm(form, url);
			}
		} else {
			submitForm(form);
		}
	}
}
