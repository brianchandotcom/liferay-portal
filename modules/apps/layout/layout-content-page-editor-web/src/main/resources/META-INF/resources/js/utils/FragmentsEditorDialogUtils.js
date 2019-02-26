/**
 * Possible types that can be returned by the image selector
 */
const IMAGE_SELECTOR_RETURN_TYPES = {
	fileEntryItemSelector: 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
	url: 'URL'
};

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

							if (returnType === IMAGE_SELECTOR_RETURN_TYPES.url) {
								selectedImageURL = value;
							}

							if (returnType === IMAGE_SELECTOR_RETURN_TYPES.fileEntryItemSelector) {
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

export {openImageSelector};