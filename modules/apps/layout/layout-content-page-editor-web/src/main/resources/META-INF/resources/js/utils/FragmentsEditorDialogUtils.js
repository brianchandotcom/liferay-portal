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
const DOWNLOAD_FILE_ENTRY_IMAGE_SELECTOR_RETURN_TYPE =
	'com.liferay.item.selector.criteria.DownloadFileEntryItemSelectorReturnType';

/**
 * @param {object} store Store
 * @return {CreateContentDialog}
 */
function openCreateContentDialog(store) {
	return new CreateContentDialog({
		store
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
					const selectedImage = {};

					if (returnType === 'URL') {
						selectedImage.title = value;
						selectedImage.url = value;
					}

					if (
						returnType ===
						DOWNLOAD_FILE_ENTRY_IMAGE_SELECTOR_RETURN_TYPE
					) {
						const fileEntry = JSON.parse(value);

						selectedImage.title = fileEntry.title;
						selectedImage.url = fileEntry.url;
					}

					if (selectedImage.url) {
						callback(selectedImage);
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
 * @param {object} options
 * @param {function} options.callback
 * @param {string} options.eventName
 * @param {string} options.itemSelectorURL
 * @param {function} [options.destroyedCallback=null]
 */
function openItemSelector({
	callback,
	eventName,
	itemSelectorURL,
	destroyedCallback = null
}) {
	AUI().use('liferay-item-selector-dialog', A => {
		const itemSelector = new A.LiferayItemSelectorDialog({
			eventName,
			on: {
				selectedItemChange: event => {
					const selectedItem = event.newVal;

					if (selectedItem && selectedItem.value) {
						const infoItem = JSON.parse(selectedItem.value);

						callback({
							className: infoItem.className,
							classNameId: infoItem.classNameId,
							classPK: infoItem.classPK,
							title: infoItem.title
						});
					}
				},

				visibleChange: event => {
					if (event.newVal === false && destroyedCallback) {
						destroyedCallback();
					}
				}
			},
			'strings.add': Liferay.Language.get('done'),
			title: Liferay.Language.get('select'),
			url: itemSelectorURL
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
	openCreateContentDialog,
	openImageSelector,
	openItemSelector,
	startListeningWidgetConfigurationChange,
	stopListeningWidgetConfigurationChange
};
