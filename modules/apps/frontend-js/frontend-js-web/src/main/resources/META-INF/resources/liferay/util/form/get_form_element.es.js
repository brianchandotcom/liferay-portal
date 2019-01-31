import {
	isDef,
	isString
} from 'metal';

/**
 * Returns a DOM element or elements in a form.
 * @param {!Element} form The form DOM element
 * @param {!string} elementName The name of the DOM element
 * @return {Element|NodeList|null} The DOM element or elements in the form, with
 * the given name
 * @review
 */

export default function getFormElement(form, elementName) {
	if (!isDef(form) || form.nodeName !== 'FORM' || !isString(elementName)) {
		return null;
	}

	const ns = form.dataset.fmNamespace || '';

	return form.elements[ns + elementName];
}