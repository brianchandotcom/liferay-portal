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
 * @param {Event} changeEvent
 * @param {HTMLElement} editableElement
 * @param {string} fragmentEntryLinkId
 * @param {function} changedCallback
 * @private
 */

function _handleImageEditorChange(
	changeEvent,
	editableElement,
	fragmentEntryLinkId,
	changedCallback
) {
	const imageElement = editableElement.querySelector('img');
	const selectedItem = changeEvent.newVal;

	if (imageElement && selectedItem) {
		const returnType = selectedItem.returnType;
		let url = '';

		if (returnType === RETURN_TYPES.url) {
			url = selectedItem.value;
		}
		else if (returnType === RETURN_TYPES.fileEntryItemSelector) {
			url = JSON.parse(selectedItem.value).url;
		}

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
	const eventName = `${portletNamespace}selectImage`;
	const title = Liferay.Language.get('select');
	const {imageSelectorURL} = options;

	AUI().use(
		'liferay-item-selector-dialog',
		A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName,
					on: {
						selectedItemChange: (changeEvent) => {
							_handleImageEditorChange(
								changeEvent,
								editableElement,
								fragmentEntryLinkId,
								changedCallback
							);
						},

						visibleChange: (change) => {
							if (change.newVal === false) {
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

export {destroy, init};

export default {
	destroy,
	init
};