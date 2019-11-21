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

import '../FieldBase/FieldBase.es';

import './DocumentLibraryRegister.soy.js';

import {
	createActionURL,
	createPortletURL,
	ItemSelectorDialog
} from 'frontend-js-web';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './DocumentLibrary.soy.js';

class DocumentLibrary extends Component {
	prepareStateForRender(state) {
		let {fileEntryTitle = '', fileEntryURL = ''} = state;
		const {value} = state;

		if (value) {
			try {
				const fileEntry = JSON.parse(value);

				fileEntryTitle = fileEntry.title;
				fileEntryURL = fileEntry.url;
			} catch (e) {
				console.warn('Unable to parse JSON', value);
			}
		}

		return {
			...state,
			fileEntryTitle,
			fileEntryURL,
			value
		};
	}

	getDocumentLibrarySelectorURL() {
		const {itemSelectorAuthToken} = this.initialConfig_;
		const {portletNamespace} = this;

		const criterionJSON = {
			desiredItemSelectorReturnTypes:
				'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType'
		};

		const uploadCriterionJSON = {
			URL: this.getUploadURL(),
			desiredItemSelectorReturnTypes:
				'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType'
		};

		const documentLibrarySelectorParameters = {
			'0_json': JSON.stringify(criterionJSON),
			'1_json': JSON.stringify(criterionJSON),
			'2_json': JSON.stringify(uploadCriterionJSON),
			criteria:
				'com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion',
			doAsGroupId: themeDisplay.getScopeGroupId(),
			itemSelectedEventName: `${portletNamespace}selectDocumentLibrary`,
			p_p_auth: itemSelectorAuthToken,
			p_p_id: Liferay.PortletKeys.ITEM_SELECTOR,
			p_p_mode: 'view',
			p_p_state: 'pop_up',
			refererGroupId: themeDisplay.getScopeGroupId()
		};

		const documentLibrarySelectorURL = createPortletURL(
			themeDisplay.getLayoutRelativeControlPanelURL(),
			documentLibrarySelectorParameters
		);

		return documentLibrarySelectorURL.toString();
	}

	getUploadURL() {
		const uploadParameters = {
			cmd: 'add_temp',
			'javax.portlet.action': '/document_library/upload_file_entry',
			p_auth: Liferay.authToken,
			p_p_id: Liferay.PortletKeys.DOCUMENT_LIBRARY
		};

		const uploadURL = createActionURL(
			themeDisplay.getLayoutRelativeURL(),
			uploadParameters
		);

		return uploadURL.toString();
	}

	_handleClearButtonClicked() {
		this.setState(
			{
				value: '{}'
			},
			() => {
				this.emit('fieldEdited', {
					fieldInstance: this,
					value: '{}'
				});
			}
		);
	}

	_handleFieldChanged(event) {
		var selectedItem = event.selectedItem;

		if (selectedItem) {
			const {value} = selectedItem;

			this.setState(
				{
					value
				},
				() => {
					this.emit('fieldEdited', {
						fieldInstance: this,
						originalEvent: event,
						value
					});
				}
			);
		}
	}

	_handleSelectButtonClicked() {
		var {portletNamespace} = this;

		const itemSelectorDialog = new ItemSelectorDialog({
			eventName: `${portletNamespace}selectDocumentLibrary`,
			url: this.getDocumentLibrarySelectorURL()
		});

		itemSelectorDialog.on(
			'selectedItemChange',
			this._handleFieldChanged.bind(this)
		);
		itemSelectorDialog.on(
			'visibleChange',
			this._handleVisibleChange.bind(this)
		);

		itemSelectorDialog.open();
	}

	_handleVisibleChange(event) {
		if (event.selectedItem) {
			this.emit('fieldFocused', {
				fieldInstance: this,
				originalEvent: event
			});
		} else {
			this.emit('fieldBlurred', {
				fieldInstance: this,
				originalEvent: event
			});
		}
	}
}

DocumentLibrary.STATE = {
	/**
	 * @default 'string'
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	dataType: Config.string().value('date'),

	/**
	 * @default false
	 * @memberof DocumentLibrary
	 * @type {?bool}
	 */

	evaluable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @memberof DocumentLibrary
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	/**
	 * @default undefined
	 * @memberof DocumentLibrary
	 * @type {?(string|undefined)}
	 */

	id: Config.string(),

	/**
	 * @default undefined
	 * @memberof DocumentLibrary
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default undefined
	 * @memberof DocumentLibrary
	 * @type {?(string|undefined)}
	 */

	name: Config.string().required(),

	/**
	 * @default undefined
	 * @memberof DocumentLibrary
	 * @type {?(string|undefined)}
	 */

	placeholder: Config.string(),

	/**
	 * @default false
	 * @memberof DocumentLibrary
	 * @type {?bool}
	 */

	readOnly: Config.bool().value(false),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(bool|undefined)}
	 */

	repeatable: Config.bool(),

	/**
	 * @default false
	 * @memberof DocumentLibrary
	 * @type {?(bool|undefined)}
	 */

	required: Config.bool().value(false),

	/**
	 * @default true
	 * @memberof DocumentLibrary
	 * @type {?(bool|undefined)}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @memberof DocumentLibrary
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @memberof DocumentLibrary
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	tooltip: Config.string(),

	/**
	 * @default undefined
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	type: Config.string().value('document_library'),

	/**
	 * @default undefined
	 * @memberof DocumentLibrary
	 * @type {?(string|undefined)}
	 */

	value: Config.oneOfType([Config.object(), Config.string()])
};

Soy.register(DocumentLibrary, templates);

export default DocumentLibrary;
