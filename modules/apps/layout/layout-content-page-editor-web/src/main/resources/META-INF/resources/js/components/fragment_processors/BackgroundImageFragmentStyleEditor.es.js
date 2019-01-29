/**
 * Possible types that can be returned by the image selector
 */
const RETURN_TYPES = {
	fileEntryItemSelector: 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
	url: 'URL'
};

/**
 * Handle item selector image changes and propagate them with an
 * "styleChanged" event.
 * @param {Event} changeEvent
 * @param {HTMLElement} styledElement
 * @param {function} changedCallback
 * @private
 */
function _handleImageEditorChange(
	changeEvent,
	styledElement,
	changedCallback
) {
	const selectedItem = changeEvent.newVal;

	if (styledElement && selectedItem) {
		const {returnType} = selectedItem;
		let url = '';

		if (returnType === RETURN_TYPES.url) {
			url = selectedItem.value;
		}
		else if (returnType === RETURN_TYPES.fileEntryItemSelector) {
			url = JSON.parse(selectedItem.value).url;
		}

		styledElement.setAttribute('style', `background-image: url('${url}');`);

		changedCallback(
			{
				name: 'background-image',
				value: `url('${url}')`
			}
		);
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
 * Returns the list of buttons to be shown inside the tooltip.
 * @param {boolean} showMapping
 * @return {Array<{id: string, label: string}>}
 * @review
 */
function getButtons(showMapping) {
	const buttons = [
		{
			id: 'select',
			label: Liferay.Language.get('select-background')
		}
	];

	if (showMapping) {
		buttons.push(
			{
				id: 'map',
				label: Liferay.Language.get('map-background')
			}
		);
	}

	return buttons;
}

/**
 * Show the image selector dialog and calls the given callback when an
 * image is selected.
 * @param {string} buttonId
 * @param {HTMLElement} styledElement
 * @param {string} portletNamespace
 * @param {{imageSelectorURL: string}} options
 * @param {function} changedCallback
 * @param {function} destroyedCallback
 * @review
 */
function init(
	buttonId,
	styledElement,
	portletNamespace,
	options,
	changedCallback,
	destroyedCallback
) {
	if (buttonId === 'select') {
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
							selectedItemChange: changeEvent => {
								_handleImageEditorChange(
									changeEvent,
									styledElement,
									changedCallback
								);
							},

							visibleChange: change => {
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
	else {
		changedCallback(
			{
				eventType: 'map',
				name: 'background-image',
				type: 'image'
			}
		);
	}
}

export {destroy, getButtons, init};

export default {
	destroy,
	getButtons,
	init
};