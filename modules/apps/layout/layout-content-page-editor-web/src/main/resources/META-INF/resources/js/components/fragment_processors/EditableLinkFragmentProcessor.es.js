import {FLOATING_TOOLBAR_BUTTONS} from '../../utils/constants';
import {destroy, init} from './EditableTextFragmentProcessor.es';

/**
 * @param {object} editableValues
 * @return {object[]} Floating toolbar panels
 */
function getFloatingToolbarButtons(editableValues) {
	return [FLOATING_TOOLBAR_BUTTONS.edit, FLOATING_TOOLBAR_BUTTONS.link];
}

/**
 * @param {string} content editableField's original HTML
 * @param {string} value Translated/segmented value
 * @param {object} editableValues values of the element
 * @return {string} Transformed content
 */
function render(content, value, editableValues) {
	const wrapper = document.createElement('div');

	wrapper.innerHTML = content;

	const config = (editableValues && editableValues.config) || {};
	const link = wrapper.querySelector('a');

	if (link) {
		link.innerHTML = value;

		if (config.href) {
			link.href = config.href;
		}

		if (config.target) {
			link.target = config.target;
		}

		Array.from(link.classList).forEach(elementClass => {
			if (elementClass.indexOf('btn-') === 0 || elementClass === 'btn') {
				link.classList.remove(elementClass);
			}
		});

		if (config.buttonType && config.buttonType === 'link') {
			link.classList.add('link');
		} else {
			link.classList.add('btn');
			link.classList.add(`btn-${config.buttonType}`);
		}
	}

	return wrapper.innerHTML;
}

export default {
	destroy,
	getFloatingToolbarButtons,
	init,
	render
};
