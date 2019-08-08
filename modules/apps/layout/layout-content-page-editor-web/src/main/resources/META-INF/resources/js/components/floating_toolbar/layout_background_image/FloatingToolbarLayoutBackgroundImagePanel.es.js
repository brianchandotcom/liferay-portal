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

import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './FloatingToolbarLayoutBackgroundImagePanelDelegateTemplate.soy';
import {MAPPING_SOURCE_TYPE_IDS} from '../../../utils/constants';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from '../../../actions/saveChanges.es';
import {encodeAssetId} from '../../../utils/FragmentsEditorIdUtils.es';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {
	openAssetBrowser,
	openImageSelector
} from '../../../utils/FragmentsEditorDialogUtils';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import templates from './FloatingToolbarLayoutBackgroundImagePanel.soy';
import {
	ADD_MAPPED_ASSET_ENTRY,
	UPDATE_ROW_CONFIG
} from '../../../actions/actions.es';

const IMAGE_SOURCE_TYPE_IDS = {
	content: 'content_mapping',
	selection: 'manual_selection'
};

/**
 * FloatingToolbarLayoutBackgroundImagePanel
 */
class FloatingToolbarLayoutBackgroundImagePanel extends Component {
	/**
	 * @return {Array<{id: string, label: string}>} Image source types
	 * @private
	 * @static
	 * @review
	 */
	static getImageSourceTypes() {
		return [
			{
				id: IMAGE_SOURCE_TYPE_IDS.selection,
				label: Liferay.Language.get('manual-selection')
			},
			{
				id: IMAGE_SOURCE_TYPE_IDS.content,
				label: Liferay.Language.get('content-mapping')
			}
		];
	}

	/**
	 * @inheritdoc
	 * @param {object} state
	 * @return {object}
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = state;

		nextState = setIn(
			nextState,
			['_imageSourceTypeIds'],
			IMAGE_SOURCE_TYPE_IDS
		);

		nextState = setIn(
			nextState,
			['_imageSourceTypes'],
			FloatingToolbarLayoutBackgroundImagePanel.getImageSourceTypes()
		);

		nextState = setIn(
			nextState,
			['mappedAssetEntries'],
			nextState.mappedAssetEntries.map(encodeAssetId)
		);

		if (
			nextState.mappedAssetEntries &&
			nextState.item.config.classNameId &&
			nextState.item.config.classPK
		) {
			const mappedAssetEntry = nextState.mappedAssetEntries.find(
				assetEntry =>
					nextState.item.config.classNameId ===
						assetEntry.classNameId &&
					nextState.item.config.classPK === assetEntry.classPK
			);

			if (mappedAssetEntry) {
				nextState = setIn(
					nextState,
					['item', 'config', 'title'],
					mappedAssetEntry.title
				);
			}
		}

		return nextState;
	}

	/**
	 * @inheritdoc
	 * @param {boolean} firstRender
	 * @review
	 */
	rendered(firstRender) {
		if (firstRender) {
			this._selectedImageSourceTypeId =
				this.item.config.classNameId || this.item.config.mappedField
					? IMAGE_SOURCE_TYPE_IDS.content
					: IMAGE_SOURCE_TYPE_IDS.selection;

			this._selectedMappingSourceTypeId = this.item.config.mappedField
				? MAPPING_SOURCE_TYPE_IDS.structure
				: MAPPING_SOURCE_TYPE_IDS.content;
		}
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleAssetBrowserLinkClick(event) {
		const {
			assetBrowserUrl,
			assetBrowserWindowTitle
		} = event.delegateTarget.dataset;

		openAssetBrowser({
			assetBrowserURL: assetBrowserUrl,
			callback: selectedAssetEntry => {
				this._selectAssetEntry(selectedAssetEntry);

				this.store.dispatch(
					Object.assign({}, selectedAssetEntry, {
						type: ADD_MAPPED_ASSET_ENTRY
					})
				);
			},
			eventName: `${this.portletNamespace}selectAsset`,
			modalTitle: assetBrowserWindowTitle
		});
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleAssetEntryLinkClick(event) {
		const data = event.delegateTarget.dataset;

		this._selectAssetEntry({
			classNameId: data.classNameId,
			classPK: data.classPk
		});
	}

	/**
	 * Show image selector
	 * @private
	 * @review
	 */
	_handleSelectButtonClick() {
		openImageSelector({
			callback: url => this._updateRowBackgroundImage(url),
			imageSelectorURL: this.imageSelectorURL,
			portletNamespace: this.portletNamespace
		});
	}

	/**
	 * Remove existing image if any
	 * @private
	 * @review
	 */
	_handleClearButtonClick() {
		this._updateRowBackgroundImage('');
	}

	/**
	 * @private
	 * @review
	 */
	_handleImageSourceTypeSelect(event) {
		this._selectedImageSourceTypeId = event.delegateTarget.value;
	}

	/**
	 * @param {object} assetEntry
	 * @param {string} assetEntry.classNameId
	 * @param {string} assetEntry.classPK
	 * @private
	 * @review
	 */
	_selectAssetEntry(assetEntry) {
		this.store
			.dispatch(enableSavingChangesStatusAction())
			.dispatch({
				config: {
					classNameId: assetEntry.classNameId,
					classPK: assetEntry.classPK
				},
				rowId: this.itemId,
				type: UPDATE_ROW_CONFIG
			})
			.dispatch(updateLastSaveDateAction())
			.dispatch(disableSavingChangesStatusAction());
	}

	/**
	 * Updates row image
	 * @param {string} backgroundImage Row image
	 * @private
	 * @review
	 */
	_updateRowBackgroundImage(backgroundImage) {
		this.store
			.dispatch(enableSavingChangesStatusAction())
			.dispatch({
				config: {
					backgroundImage
				},
				rowId: this.itemId,
				type: UPDATE_ROW_CONFIG
			})
			.dispatch(updateLastSaveDateAction())
			.dispatch(disableSavingChangesStatusAction());
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarLayoutBackgroundImagePanel.STATE = {
	/**
	 * @default undefined
	 * @memberof FloatingToolbarLayoutBackgroundImagePanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config.string().required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarLayoutBackgroundImagePanel
	 * @review
	 * @type {string}
	 */
	_selectedImageSourceTypeId: Config.oneOf(
		Object.values(IMAGE_SOURCE_TYPE_IDS)
	).internal(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarLayoutBackgroundImagePanel
	 * @review
	 * @type {string}
	 */
	_selectedMappingSourceTypeId: Config.oneOf(
		Object.values(MAPPING_SOURCE_TYPE_IDS)
	).internal()
};

const ConnectedFloatingToolbarLayoutBackgroundImagePanel = getConnectedComponent(
	FloatingToolbarLayoutBackgroundImagePanel,
	[
		'assetBrowserLinks',
		'imageSelectorURL',
		'mappedAssetEntries',
		'portletNamespace'
	]
);

Soy.register(ConnectedFloatingToolbarLayoutBackgroundImagePanel, templates);

export {
	ConnectedFloatingToolbarLayoutBackgroundImagePanel,
	FloatingToolbarLayoutBackgroundImagePanel
};
export default ConnectedFloatingToolbarLayoutBackgroundImagePanel;
