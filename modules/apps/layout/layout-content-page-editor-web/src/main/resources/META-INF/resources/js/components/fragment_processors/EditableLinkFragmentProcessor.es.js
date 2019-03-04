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
 * @return {string} Transformed content
 */
function render(content, value) {
	const wrapper = document.createElement('div');

	wrapper.innerHTML = content;

	const link = wrapper.querySelector('a');

	if (link) {
		link.innerHTML = value;
	}

	return wrapper.innerHTML;
}

export default {
	destroy,
	getFloatingToolbarPanels,
	init,
	render
};