import {
	isDef,
	isObject
} from 'metal';
import getFormElement from './get_form_element.es';

/**
 * Sets the form elements to given values.
 * @param {!Element} form The form DOM element
 * @param {!Object} data An Object containing names and values of form
 * elements
 * @review
 */

export default function setFormValues(form, data) {
	if (!isDef(form) || form.nodeName !== 'FORM' || !isObject(data)) {
		return;
	}

	for (const [elementName, elementValue] of Object.entries(data)) {
		const element = getFormElement(form, elementName);

		if (element) {
			element.value = elementValue;
		}
	}
}