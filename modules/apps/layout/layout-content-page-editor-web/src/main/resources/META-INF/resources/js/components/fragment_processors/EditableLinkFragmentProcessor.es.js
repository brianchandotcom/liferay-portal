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
	const config = (editableValues && editableValues.config) || {};

	if (link) {
		link.innerHTML = value;

		if (config.href) {
			link.href = config.href;
		}

		if (config.target) {
			link.target = config.target;
		}

		if (config.buttonType) {
			link.classList.add('btn');
			link.classList.add(`btn-${config.buttonType}`);
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