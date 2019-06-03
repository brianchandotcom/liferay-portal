import {openImageSelector} from '../../utils/FragmentsEditorDialogUtils';

/**
 * Do nothing, as LiferayItemSelectorDialog is automatically
 * destroyed on hide.
 * @review
 */
function destroy() {}

/**
 * Show the image selector dialog and calls the given callback when an
 * image is selected.
 * @param {function} callback
 * @param {string} imageSelectorURL
 * @param {string} portletNamespace
 * @review
 */
function init(callback, imageSelectorURL, portletNamespace) {
	openImageSelector({
		callback,
		imageSelectorURL,
		portletNamespace
	});
}

/**
 * @param {HTMLElement} element
 * @param {string} [backgroundImageURL='']
 */
function render(element, backgroundImageURL = '') {
	element.style.backgroundImage = backgroundImageURL
		? `url("${backgroundImageURL}")`
		: '';
	element.style.backgroundSize = 'cover';
}

export default {
	destroy,
	init,
	render
};
