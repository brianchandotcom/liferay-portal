/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import CreateContentDialog from '../components/content/CreateContentDialog.es';
import {UPDATE_LAST_SAVE_DATE} from '../actions/actions.es';

/**
 * @private
 * @review
 * @type {null|{detach: Function}}
 */
let _widgetConfigurationChangeHandler = null;

/**
 * Possible types that can be returned by the image selector
 */
const IMAGE_SELECTOR_RETURN_TYPES = {
	downloadUrl:
		'com.liferay.item.selector.criteria.DownloadURLItemSelectorReturnType',
	fileEntryItemSelector:
		'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
	url: 'URL'
};

/**
 * @param {object} options
 * @param {function} options.callback
 * @param {string} options.assetBrowserURL
 * @param {string} options.eventName
 * @param {string} options.modalTitle
 * @param {function} [options.destroyedCallback=null]
 */
function openAssetBrowser({
	assetBrowserURL,
	callback,
	eventName,
	modalTitle,
	destroyedCallback = null
}) {
	Liferay.Util.selectEntity(
		{
			dialog: {
				constrain: true,
				destroyOnHide: true,
				modal: true
			},
			eventName: eventName,
			title: modalTitle,
			uri: assetBrowserURL
		},
		event => {
			if (event.assetclassnameid) {
				callback({
					className: event.assetclassname,
					classNameId: event.assetclassnameid,
					classPK: event.assetclasspk,
					title: event.assettitle
				});
			} else if (destroyedCallback) {
				destroyedCallback();
			}
		}
	);
}

/**
 * @param {object} store Store
 * @param {function} onCancelButtonClick
 * @param {function} onSubmitButtonClick
 * @param {function} onVisibilityChange
 * @return {CreateContentDialog}
 */
function openCreateContentDialog(
	store,
	onCancelButtonClick,
	onSubmitButtonClick,
	onVisibilityChange
) {
	return new CreateContentDialog({
		store,
		onCancelButtonClick,
		onSubmitButtonClick,
		onVisibilityChange
	});
}

/**
 * @param {object} options
 * @param {function} options.callback
 * @param {string} options.imageSelectorURL
 * @param {string} options.portletNamespace
 * @param {function} [options.destroyedCallback=null]
 */
function openImageSelector({
	callback,
	imageSelectorURL,
	portletNamespace,
	destroyedCallback = null
}) {
	AUI().use('liferay-item-selector-dialog', A => {
		const itemSelector = new A.LiferayItemSelectorDialog({
			eventName: `${portletNamespace}selectImage`,
			on: {
				selectedItemChange: event => {
					const selectedItem = event.newVal || {};

					const {returnType, value} = selectedItem;
					let selectedImageURL = '';

					if (
						returnType ===
							IMAGE_SELECTOR_RETURN_TYPES.downloadUrl ||
						returnType === IMAGE_SELECTOR_RETURN_TYPES.url
					) {
						selectedImageURL = value;
					}

					if (
						returnType ===
						IMAGE_SELECTOR_RETURN_TYPES.fileEntryItemSelector
					) {
						selectedImageURL = JSON.parse(value).url;
					}

					if (selectedImageURL) {
						callback(selectedImageURL);
					}
				},

				visibleChange: event => {
					if (event.newVal === false && destroyedCallback) {
						destroyedCallback();
					}
				}
			},
			title: Liferay.Language.get('select'),
			url: imageSelectorURL
		});

		itemSelector.open();
	});
}

/**
 * @param {{dispatch: Function}} store
 * @review
 */
function startListeningWidgetConfigurationChange(store) {
	stopListeningWidgetConfigurationChange();

	let submitFormHandler = null;

	_widgetConfigurationChangeHandler = Liferay.after('popupReady', event => {
		const configurationForm = event.win.document.querySelector(
			'.portlet-configuration-setup'
		);

		if (configurationForm) {
			if (submitFormHandler) {
				submitFormHandler.detach();

				submitFormHandler = null;
			}

			submitFormHandler = event.win.Liferay.on('submitForm', () => {
				store.dispatch({
					lastSaveDate: new Date(),
					type: UPDATE_LAST_SAVE_DATE
				});
			});
		}
	});
}

/**
 * @review
 */
function stopListeningWidgetConfigurationChange() {
	if (_widgetConfigurationChangeHandler) {
		_widgetConfigurationChangeHandler.detach();

		_widgetConfigurationChangeHandler = null;
	}
}

export {
	openAssetBrowser,
	openCreateContentDialog,
	openImageSelector,
	startListeningWidgetConfigurationChange,
	stopListeningWidgetConfigurationChange
};
