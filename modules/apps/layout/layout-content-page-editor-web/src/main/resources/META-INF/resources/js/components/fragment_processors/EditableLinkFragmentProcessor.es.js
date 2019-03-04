import {FLOATING_TOOLBAR_PANELS} from '../../utils/constants';
import {destroy, init} from './EditableTextFragmentProcessor.es';

/**
 * @param {object} editableValues
 * @return {object[]} Floating toolbar panels
 */
function getFloatingToolbarPanels(editableValues) {
	return [
		FLOATING_TOOLBAR_PANELS.edit,
		FLOATING_TOOLBAR_PANELS.link
	];
}

/**
 * @param {string} content editableField's original HTML
 * @param {string} value Translated/segmented value
 * @param {object} editable values of the element
 * @return {string} Transformed content
 */
function render(content, value, editableValues) {
	const wrapper = document.createElement('div');

	wrapper.innerHTML = content;

	const link = wrapper.querySelector('a');

	if (link) {
		link.innerHTML = value;

		if (editableValues && editableValues.config) {
			if (editableValues.config.href) {
				link.setAttribute('href', editableValues.config.href);
			}

			if (editableValues.config.target) {
				link.setAttribute('target', editableValues.config.target);
			}

			if (editableValues.config.buttonType) {
				let cssClass = link.getAttribute('class') || '';

				link.setAttribute('class', cssClass + ' btn btn-' + editableValues.config.buttonType);
			}
		}
	}

	return wrapper.innerHTML;
}

export default {
	destroy,
	getFloatingToolbarPanels,
	init,
	render
};