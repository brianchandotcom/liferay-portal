import {FLOATING_TOOLBAR_PANELS} from '../../utils/constants';
import {openImageSelector} from '../../utils/FragmentsEditorDialogUtils';

/**
 * Handle item selector image changes and propagate them with an
 * "editableChanged" event.
 * @param {string} url
 * @param {HTMLElement} editableElement
 * @param {string} fragmentEntryLinkId
 * @param {function} changedCallback
 * @private
 */
function _handleImageEditorChange(
	url,
	editableElement,
	fragmentEntryLinkId,
	changedCallback
) {
	const imageElement = editableElement.querySelector('img');

	if (imageElement && url) {
		imageElement.src = url;

		changedCallback(url);
	}
}

/**
 * Do nothing, as LiferayItemSelectorDialog is automatically
 * destroyed on hide.
 * @review
 */
function destroy() {
}

/**
 * @param {object} editableValues
 * @return {object[]} Floating toolbar panels
 */
function getFloatingToolbarPanels(editableValues) {
	return [
		FLOATING_TOOLBAR_PANELS.edit,
		FLOATING_TOOLBAR_PANELS.map
	];
}

/**
 * Show the image selector dialog and calls the given callback when an
 * image is selected.
 * @param {HTMLElement} editableElement
 * @param {string} fragmentEntryLinkId
 * @param {string} portletNamespace
 * @param {{imageSelectorURL: string}} options
 * @param {function} changedCallback
 * @param {function} destroyedCallback
 * @review
 */
function init(
	editableElement,
	fragmentEntryLinkId,
	portletNamespace,
	options,
	changedCallback,
	destroyedCallback
) {
	const {imageSelectorURL} = options;

	openImageSelector(
		imageSelectorURL,
		portletNamespace,
		url => {
			_handleImageEditorChange(
				url,
				editableElement,
				fragmentEntryLinkId,
				changedCallback
			);
		},
		destroyedCallback
	);
}

/**
 * @param {string} content editableField's original HTML
 * @param {string} value Translated/segmented value
 * @return {string} Transformed content
 */
function render(content, value) {
	const wrapper = document.createElement('div');

	wrapper.innerHTML = content;

	const image = wrapper.querySelector('img');

	if (image) {
		image.src = value;
	}

	return wrapper.innerHTML;
}

export default {
	destroy,
	getFloatingToolbarPanels,
	init,
	render
};