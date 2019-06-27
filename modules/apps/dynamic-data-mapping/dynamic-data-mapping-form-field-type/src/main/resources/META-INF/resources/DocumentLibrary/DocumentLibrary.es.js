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

/* eslint no-unused-vars: "warn" */

import '../FieldBase/FieldBase.es';
import './DocumentLibraryRegister.soy.js';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './DocumentLibrary.soy.js';
import {Config} from 'metal-state';

class DocumentLibrary extends Component {
	created() {
		AUI().use('liferay-item-selector-dialog', 'liferay-portlet-url', A => {
			this.A = A;
		});
	}

	prepareStateForRender(state) {
		let {fileEntryTitle, fileEntryURL, value} = state;

		if (value) {
			if (typeof value === 'object') {
				fileEntryTitle = value.title;
				fileEntryURL = value.url;

				value = JSON.stringify(value);
			} else if (typeof value === 'string') {
				const object = JSON.parse(value);

				fileEntryTitle = object.title;
				fileEntryURL = object.url;
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
		const {itemSelectorAuthToken, portletNamespace} = this;

		const portletURL = Liferay.PortletURL.createURL(
			themeDisplay.getLayoutRelativeControlPanelURL()
		);

		portletURL.setParameter(
			'criteria',
			'com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion'
		);
		portletURL.setParameter('doAsGroupId', themeDisplay.getScopeGroupId());
		portletURL.setParameter(
			'itemSelectedEventName',
			`${portletNamespace}selectDocumentLibrary`
		);
		portletURL.setParameter('p_p_auth', itemSelectorAuthToken);
		portletURL.setParameter(
			'refererGroupId',
			themeDisplay.getScopeGroupId()
		);

		const criterionJSON = {
			desiredItemSelectorReturnTypes:
				'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType'
		};

		portletURL.setParameter('0_json', JSON.stringify(criterionJSON));
		portletURL.setParameter('1_json', JSON.stringify(criterionJSON));

		const uploadCriterionJSON = {
			desiredItemSelectorReturnTypes:
				'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
			URL: this.getUploadURL()
		};

		portletURL.setParameter('2_json', JSON.stringify(uploadCriterionJSON));

		portletURL.setPortletId(Liferay.PortletKeys.ITEM_SELECTOR);
		portletURL.setPortletMode('view');
		portletURL.setWindowState('pop_up');

		return portletURL.toString();
	}

	getUploadURL() {
		const {groupId} = this;

		const portletURL = Liferay.PortletURL.createURL(
			themeDisplay.getLayoutRelativeURL()
		);

		portletURL.setLifecycle(Liferay.PortletURL.ACTION_PHASE);
		portletURL.setParameter(
			'javax.portlet.action',
			'/document_library/upload_file_entry'
		);
		portletURL.setParameter('p_auth', Liferay.authToken);
		portletURL.setParameter('cmd', 'add_temp');
		portletURL.setParameter('refererGroupId', groupId);
		portletURL.setPortletId(Liferay.PortletKeys.DOCUMENT_LIBRARY);

		return portletURL.toString();
	}

	_handleClearButtonClicked() {
		this.setState({
			value: ''
		});
	}

	_handleFieldChanged(event) {
		var selectedItem = event.newVal;

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
		var {A, portletNamespace} = this;

		var itemSelectorDialog = new A.LiferayItemSelectorDialog({
			eventName: `${portletNamespace}selectDocumentLibrary`,
			on: {
				selectedItemChange: this._handleFieldChanged.bind(this),
				visibleChange: this._handleVisibleChange.bind(this)
			},
			url: this.getDocumentLibrarySelectorURL()
		});

		itemSelectorDialog.open();
	}

	_handleVisibleChange(event) {
		if (event.newVal) {
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
