/**
 * Possible types that can be returned by the image selector
 */
const IMAGE_SELECTOR_RETURN_TYPES = {
	fileEntryItemSelector: 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
	url: 'URL'
};

/**
 * @param {string} url
 * @param {string} title
 * @param {string} eventName
 * @param {function} callback
 * @param {function} destroyedCallback
 */
function _openItemSelector(url, title, eventName, callback, destroyedCallback) {
	AUI().use(
		'liferay-item-selector-dialog',
		A => {
			const itemSelector = new A.LiferayItemSelectorDialog(
				{
					eventName,
					on: {
						selectedItemChange: event => {
							callback(event);
						},

						visibleChange: event => {
							if ((event.newVal === false) && destroyedCallback) {
								destroyedCallback();
							}
						}
					},
					title,
					url
				}
			);

			itemSelector.open();
		}
	);
}

/**
 * @param {string} assetBrowserURL
 * @param {string} modalTitle
 * @param {string} portletNamespace
 * @param {function} callback
 * @param {function} [destroyedCallback=null]
 */
function openAssetBrowser(assetBrowserURL, modalTitle, portletNamespace, callback, destroyedCallback = null) {
	Liferay.Util.selectEntity(
		{
			dialog: {
				constrain: true,
				destroyOnHide: true,
				modal: true
			},
			eventName: `${portletNamespace}selectAsset`,
			title: modalTitle,
			uri: assetBrowserURL
		},
		event => {
			if (event.assetclassnameid) {
				callback(
					{
						classNameId: event.assetclassnameid,
						classPK: event.assetclasspk,
						title: event.assettitle
					}
				);
			}
			else if (destroyedCallback) {
				destroyedCallback();
			}
		}
	);
}

/**
 * @param {string} imageSelectorURL
 * @param {string} portletNamespace
 * @param {function} callback
 * @param {function} destroyedCallback
 */
function openImageSelector(imageSelectorURL, portletNamespace, callback, destroyedCallback) {
	_openItemSelector(
		imageSelectorURL,
		Liferay.Language.get('select'),
		`${portletNamespace}selectImage`,
		event => {
			const selectedItem = event.newVal || {};

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
		destroyedCallback
	);
}

export {openAssetBrowser, openImageSelector};