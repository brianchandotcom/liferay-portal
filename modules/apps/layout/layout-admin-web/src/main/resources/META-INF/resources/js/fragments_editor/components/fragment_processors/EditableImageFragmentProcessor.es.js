/**
 * Possible types that can be returned by the image selector
 */
const RETURN_TYPES = {
	fileEntryItemSelector: 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
	url: 'URL'
};

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
 * @param {string} imageSelectorURL
 * @param {string} portletNamespace
 * @param {function} callback
 * @param {function} destroyedCallback
 */
function openImageSelector(imageSelectorURL, portletNamespace, callback, destroyedCallback) {
	const eventName = `${portletNamespace}selectImage`;
	const title = Liferay.Language.get('select');

	AUI().use(
		'liferay-item-selector-dialog',
		A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName,
					on: {
						selectedItemChange: changeEvent => {
							const selectedItem = changeEvent.newVal || {};

							const {returnType, value} = selectedItem;
							let selectedImageURL = '';

							if (returnType === RETURN_TYPES.url) {
								selectedImageURL = value;
							}

							if (returnType === RETURN_TYPES.fileEntryItemSelector) {
								selectedImageURL = JSON.parse(value).url;
							}

							if (selectedImageURL) {
								callback(selectedImageURL);
							}
						},

						visibleChange: change => {
							if ((change.newVal === false) && destroyedCallback) {
								destroyedCallback();
							}
						}
					},
					title,
					url: imageSelectorURL
				}
			);

			itemSelectorDialog.open();
		}
	);
}

export {destroy, init, openImageSelector};

export default {
	destroy,
	init
};