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
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import './FloatingToolbarLayoutBackgroundImagePanelDelegateTemplate.soy';
import {ADD_MAPPED_INFO_ITEM} from '../../../actions/actions.es';
import {updateRowConfigAction} from '../../../actions/updateRowConfig.es';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {
	openItemSelector,
	openImageSelector
} from '../../../utils/FragmentsEditorDialogUtils';
import {
	getAssetMappingFields,
	getStructureMappingFields
} from '../../../utils/FragmentsEditorFetchUtils.es';
import {getMappingSourceTypes} from '../../../utils/FragmentsEditorGetUtils.es';
import {encodeAssetId} from '../../../utils/FragmentsEditorIdUtils.es';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {
	MAPPING_SOURCE_TYPE_IDS,
	COMPATIBLE_TYPES
} from '../../../utils/constants';
import templates from './FloatingToolbarLayoutBackgroundImagePanel.soy';

const IMAGE_SOURCE_TYPE_IDS = {
	content: 'content_mapping',
	selection: 'manual_selection'
};

/**
 * FloatingToolbarLayoutBackgroundImagePanel
 */
class FloatingToolbarLayoutBackgroundImagePanel extends Component {
	/**
	 * @return {boolean} Mapping values are empty
	 * @private
	 * @static
	 * @review
	 */
	static emptyMappingValues(config) {
		if (config.backgroundImage) {
			return (
				!config.backgroundImage.classNameId &&
				!config.backgroundImage.classPK &&
				!config.backgroundImage.fieldId &&
				!config.backgroundImage.mappedField
			);
		}

		return true;
	}

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
			['_backgroundImage'],
			this._getBackgroundImage()
		);

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
			['mappedInfoItems'],
			nextState.mappedInfoItems.map(encodeAssetId)
		);

		nextState = setIn(
			nextState,
			['_mappingSourceTypeIds'],
			MAPPING_SOURCE_TYPE_IDS
		);

		if (
			nextState.mappingFieldsURL &&
			nextState.selectedMappingTypes &&
			nextState.selectedMappingTypes.type
		) {
			nextState = setIn(
				nextState,
				['_mappingSourceTypes'],
				getMappingSourceTypes(
					nextState.selectedMappingTypes.subtype
						? nextState.selectedMappingTypes.subtype.label
						: nextState.selectedMappingTypes.type.label
				)
			);
		}

		if (
			nextState.mappedInfoItems &&
			nextState._selectedInfoItem &&
			nextState._selectedInfoItem.classNameId &&
			nextState._selectedInfoItem.classPK
		) {
			const mappedInfoItem = nextState.mappedInfoItems.find(
				infoItem =>
					nextState._selectedInfoItem.classNameId ===
						infoItem.classNameId &&
					nextState._selectedInfoItem.classPK === infoItem.classPK
			);

			if (mappedInfoItem) {
				nextState = setIn(
					nextState,
					['item', 'config', 'title'],
					mappedInfoItem.title
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
			if (this.item.config.backgroundImage) {
				const {backgroundImage} = this.item.config;

				this._selectedInfoItem.classNameId = this.item.config.backgroundImage.classNameId;
				this._selectedInfoItem.classPK = this.item.config.backgroundImage.classPK;

				this._selectedImageSourceTypeId =
					backgroundImage.classNameId || backgroundImage.mappedField
						? IMAGE_SOURCE_TYPE_IDS.content
						: IMAGE_SOURCE_TYPE_IDS.selection;
				this._selectedMappingSourceTypeId = backgroundImage.mappedField
					? MAPPING_SOURCE_TYPE_IDS.structure
					: MAPPING_SOURCE_TYPE_IDS.content;
			}
			else {
				this._selectedImageSourceTypeId =
					IMAGE_SOURCE_TYPE_IDS.selection;
				this._selectedMappingSourceTypeId =
					MAPPING_SOURCE_TYPE_IDS.content;
			}
		}
	}

	/**
	 * @param {{config: object}} newItem
	 * @param {{config: object}} [oldItem]
	 * @inheritdoc
	 * @review
	 */
	syncItem(newItem, oldItem) {
		const changedBackgroundImage =
			newItem.config &&
			oldItem &&
			oldItem.config &&
			newItem.config.backgroundImage &&
			!oldItem.config.backgroundImage;

		const changedMappedAsset =
			newItem.config &&
			newItem.config.backgroundImage &&
			oldItem &&
			oldItem.config &&
			oldItem.config.backgroundImage &&
			newItem.config.backgroundImage.classNameId !==
				oldItem.config.backgroundImage.classNameId;

		if (!oldItem || changedBackgroundImage || changedMappedAsset) {
			this._loadFields();
		}
	}

	/**
	 * Clears fields
	 * @private
	 * @review
	 */
	_clearFields() {
		this._fields = [];
	}

	/**
	 * Clears mapping values
	 * @private
	 * @review
	 */
	_clearMappingValues() {
		this.store.dispatch(
			updateRowConfigAction(this.itemId, {
				backgroundImage: ''
			})
		);
	}

	/**
	 * Takes the string of the background image
	 * @private
	 */
	_getBackgroundImage() {
		const {config} = this.item;

		if (!config) {
			return '';
		}

		if (typeof config.backgroundImage === 'string') {
			return config.backgroundImage;
		}

		if (typeof config.backgroundImage === 'object') {
			return config.backgroundImage.title || '';
		}

		return '';
	}

	/**
	 * @private
	 * @review
	 */
	_handleAssetBrowserLinkClick() {
		openItemSelector(selectedInfoItem => {
			this._selectInfoItem(selectedInfoItem);
		});
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleInfoItemLinkClick(event) {
		const data = event.delegateTarget.dataset;

		this._selectInfoItem({
			classNameId: data.classNameId,
			classPK: data.classPk
		});
	}

	/**
	 * Handle field option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleFieldOptionChange(event) {
		const fieldId = event.delegateTarget.value;

		this._selectField(fieldId);
	}

	/**
	 * Show image selector
	 * @private
	 * @review
	 */
	_handleSelectButtonClick() {
		openImageSelector(image => this._updateRowBackgroundImage(image));
	}

	/**
	 * Remove existing image if any
	 * @private
	 * @review
	 */
	_handleClearButtonClick() {
		this._updateRowBackgroundImage({});
	}

	/**
	 * @private
	 * @review
	 */
	_handleImageSourceTypeSelect(event) {
		this._selectedImageSourceTypeId = event.delegateTarget.value;

		if (
			FloatingToolbarLayoutBackgroundImagePanel.emptyMappingValues(
				this.item.config
			)
		) {
			this._loadFields();
		}
		else {
			this._clearMappingValues();
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleMappingSourceTypeSelect(event) {
		this._selectedMappingSourceTypeId = event.delegateTarget.value;

		if (
			FloatingToolbarLayoutBackgroundImagePanel.emptyMappingValues(
				this.item.config
			)
		) {
			this._loadFields();
		}
		else {
			this._clearMappingValues();
		}
	}

	/**
	 * Load the list of fields
	 * @private
	 * @review
	 */
	_loadFields() {
		let promise;

		this._clearFields();

		if (
			this._selectedMappingSourceTypeId ===
			MAPPING_SOURCE_TYPE_IDS.structure
		) {
			promise = getStructureMappingFields(
				this.selectedMappingTypes.type.id,
				this.selectedMappingTypes.subtype.id
			);
		}
		else if (
			this._selectedMappingSourceTypeId ===
				MAPPING_SOURCE_TYPE_IDS.content &&
			this._selectedInfoItem.classNameId &&
			this._selectedInfoItem.classPK
		) {
			promise = getAssetMappingFields(
				this._selectedInfoItem.classNameId,
				this._selectedInfoItem.classPK
			);
		}

		if (promise) {
			promise.then(response => {
				this._fields = response.filter(
					field =>
						COMPATIBLE_TYPES['image'].indexOf(field.type) !== -1
				);
			});
		}
		else if (this._fields.length) {
			this._clearFields();
		}
	}

	/**
	 * @param {object} infoItem
	 * @param {string} infoItem.classNameId
	 * @param {string} infoItem.classPK
	 * @private
	 * @review
	 */
	_selectInfoItem(infoItem) {
		this._selectedInfoItem = infoItem;

		this.store.dispatch({
			...this._selectedInfoItem,
			type: ADD_MAPPED_INFO_ITEM
		});

		this._loadFields();
	}

	/**
	 * @param {string} fieldId
	 * @private
	 * @review
	 */
	_selectField(fieldId) {
		let fieldData =
			this._selectedMappingSourceTypeId ===
			MAPPING_SOURCE_TYPE_IDS.content
				? {
						classNameId: this._selectedInfoItem.classNameId,
						classPK: this._selectedInfoItem.classPK,
						fieldId
				  }
				: {mappedField: fieldId};

		if (fieldId === '') {
			fieldData = {
				classNameId: '',
				classPK: '',
				fieldId: '',
				mappedField: ''
			};
		}

		this.store.dispatch(
			updateRowConfigAction(this.itemId, {
				backgroundImage: {
					...fieldData
				}
			})
		);
	}

	/**
	 * Updates row image
	 * @param {object} backgroundImage Row image
	 * @private
	 * @review
	 */
	_updateRowBackgroundImage(backgroundImage) {
		this.store.dispatch(
			updateRowConfigAction(this.itemId, {
				backgroundImage
			})
		);
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
	 * @default []
	 * @memberOf FloatingToolbarLayoutBackgroundImagePanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_fields: Config.array()
		.internal()
		.value([]),

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
	_selectedInfoItem: Config.object()
		.internal()
		.value({}),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarLayoutBackgroundImagePanel
	 * @review
	 * @type {string}
	 */
	_selectedMappingSourceTypeId: Config.oneOf(
		Object.values(MAPPING_SOURCE_TYPE_IDS)
	).internal(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarLayoutBackgroundImagePanel
	 * @review
	 * @type {object}
	 */
	item: Config.required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarLayoutBackgroundImagePanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config.string().required()
};

const ConnectedFloatingToolbarLayoutBackgroundImagePanel = getConnectedComponent(
	FloatingToolbarLayoutBackgroundImagePanel,
	['mappedInfoItems', 'mappingFieldsURL', 'selectedMappingTypes']
);

Soy.register(ConnectedFloatingToolbarLayoutBackgroundImagePanel, templates);

export {
	ConnectedFloatingToolbarLayoutBackgroundImagePanel,
	FloatingToolbarLayoutBackgroundImagePanel
};
export default ConnectedFloatingToolbarLayoutBackgroundImagePanel;
