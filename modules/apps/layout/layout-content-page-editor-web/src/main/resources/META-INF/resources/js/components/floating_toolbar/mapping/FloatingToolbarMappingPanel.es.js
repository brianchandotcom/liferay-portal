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

import {PortletBase} from 'frontend-js-web';
import Soy, {Config} from 'metal-soy';

import '../../common/AssetSelector.es';
import './FloatingToolbarMappingPanelDelegateTemplate.soy';
import {ADD_MAPPED_ASSET_ENTRY} from '../../../actions/actions.es';
import {
	BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
	COMPATIBLE_TYPES,
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
	FRAGMENTS_EDITOR_ITEM_TYPES,
	MAPPING_SOURCE_TYPE_IDS
} from '../../../utils/constants';
import {encodeAssetId} from '../../../utils/FragmentsEditorIdUtils.es';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import {getMappingSourceTypes} from '../../../utils/FragmentsEditorGetUtils.es';
import {
	openAssetBrowser,
	openCreateContentDialog
} from '../../../utils/FragmentsEditorDialogUtils';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {
	updateEditableValueFieldIdAction,
	updateEditableValueMappedFieldAction
} from '../../../actions/updateEditableValue.es';
import templates from './FloatingToolbarMappingPanel.soy';

/**
 * FloatingToolbarMappingPanel
 */
class FloatingToolbarMappingPanel extends PortletBase {
	/**
	 * @return {boolean} Mapping values are empty
	 * @private
	 * @static
	 * @review
	 */
	static emptyEditableValues(editableValues) {
		return (
			!editableValues.classNameId &&
			!editableValues.classPK &&
			!editableValues.fieldId &&
			!editableValues.mappedField
		);
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
			['mappedAssetEntries'],
			nextState.mappedAssetEntries.map(encodeAssetId)
		);

		nextState = setIn(
			nextState,
			['_sourceTypeIds'],
			MAPPING_SOURCE_TYPE_IDS
		);

		if (
			nextState.mappingFieldsURL &&
			nextState.selectedMappingTypes &&
			nextState.selectedMappingTypes.type
		) {
			nextState = setIn(
				nextState,
				['_sourceTypes'],
				getMappingSourceTypes(
					nextState.selectedMappingTypes.subtype
						? nextState.selectedMappingTypes.subtype.label
						: nextState.selectedMappingTypes.type.label
				)
			);
		}

		if (
			nextState.mappedAssetEntries &&
			nextState._selectedAssetEntry &&
			nextState._selectedAssetEntry.classNameId &&
			nextState._selectedAssetEntry.classPK
		) {
			const mappedAssetEntry = nextState.mappedAssetEntries.find(
				assetEntry =>
					nextState._selectedAssetEntry.classNameId ===
						assetEntry.classNameId &&
					nextState._selectedAssetEntry.classPK === assetEntry.classPK
			);

			if (mappedAssetEntry) {
				nextState = setIn(
					nextState,
					['item', 'editableValues', 'title'],
					mappedAssetEntry.title
				);

				nextState = setIn(
					nextState,
					['item', 'editableValues', 'encodedId'],
					mappedAssetEntry
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
			this._selectedAssetEntry.classNameId = this.item.editableValues.classNameId;
			this._selectedAssetEntry.classPK = this.item.editableValues.classPK;
			this._selectedSourceTypeId = MAPPING_SOURCE_TYPE_IDS.content;

			if (
				this.item &&
				this.mappingFieldsURL &&
				!this.item.editableValues.classNameId
			) {
				this._selectedSourceTypeId = MAPPING_SOURCE_TYPE_IDS.structure;
			}
		}
	}

	/**
	 * @param {{editableValues: object}} newItem
	 * @param {{editableValues: object}} [oldItem]
	 * @inheritdoc
	 * @review
	 */
	syncItem(newItem, oldItem) {
		if (
			!oldItem ||
			newItem.editableValues.classNameId !==
				oldItem.editableValues.classNameId ||
			newItem.editableValues.mappedField !==
				oldItem.editableValues.mappedField
		) {
			this._loadFields();
		}
	}

	/**
	 * Clears editable values
	 * @private
	 * @review
	 */
	_clearEditableValues() {
		this.store.dispatch(
			updateEditableValueFieldIdAction(
				this.item.fragmentEntryLinkId,
				this._getFragmentEntryProcessor(),
				this.item.editableId,
				{}
			)
		);
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
	 * Gets right processor depending on itemType
	 * @private
	 * @review
	 */
	_getFragmentEntryProcessor() {
		return this.itemType ===
			FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable
			? BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
			: EDITABLE_FRAGMENT_ENTRY_PROCESSOR;
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

				requestAnimationFrame(() => {
					this.refs.panel.focus();
				});
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

		requestAnimationFrame(() => {
			this.refs.panel.focus();
		});
	}

	/**
	 * Opens content creation dialog
	 * @private
	 * @review
	 */
	_handleCreateContentClick() {
		openCreateContentDialog(this.store);
	}

	/**
	 * Handle field option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleFieldOptionChange(event) {
		const fieldId = event.delegateTarget.value;

		const shouldRemoveValues = fieldId === '';

		if (this._selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.content) {
			this.store.dispatch(
				updateEditableValueFieldIdAction(
					this.item.fragmentEntryLinkId,
					this._getFragmentEntryProcessor(),
					this.item.editableId,
					{
						classNameId: shouldRemoveValues
							? ''
							: this._selectedAssetEntry.classNameId,
						classPK: shouldRemoveValues
							? ''
							: this._selectedAssetEntry.classPK,
						fieldId
					}
				)
			);
		} else {
			this.store.dispatch(
				updateEditableValueMappedFieldAction(
					this.item.fragmentEntryLinkId,
					this._getFragmentEntryProcessor(),
					this.item.editableId,
					fieldId
				)
			);
		}
	}

	/**
	 * Handle source option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleSourceTypeChange(event) {
		this._selectedSourceTypeId = event.delegateTarget.value;

		if (
			FloatingToolbarMappingPanel.emptyEditableValues(
				this.item.editableValues
			)
		) {
			this._loadFields();
		} else {
			this._clearEditableValues();
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

		if (this._selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.structure) {
			const data = {
				classNameId: this.selectedMappingTypes.type.id
			};

			if (this.selectedMappingTypes.subtype) {
				data.classTypeId = this.selectedMappingTypes.subtype.id;
			}

			promise = this.fetch(this.mappingFieldsURL, data);
		} else if (
			this._selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.content &&
			this._selectedAssetEntry.classNameId &&
			this._selectedAssetEntry.classPK
		) {
			promise = this.fetch(this.getAssetMappingFieldsURL, {
				classNameId: this._selectedAssetEntry.classNameId,
				classPK: this._selectedAssetEntry.classPK
			});
		}

		if (promise) {
			promise
				.then(response => response.json())
				.then(response => {
					this._fields = response.filter(
						field =>
							COMPATIBLE_TYPES[this.item.type].indexOf(
								field.type
							) !== -1
					);
				});
		} else if (this._fields.length) {
			this._clearFields();
		}
	}

	/**
	 * @param {object} assetEntry
	 * @param {string} assetEntry.classNameId
	 * @param {string} assetEntry.classPK
	 * @private
	 * @review
	 */
	_selectAssetEntry(assetEntry) {
		this._selectedAssetEntry = assetEntry;

		this.store.dispatch(
			updateEditableValueFieldIdAction(
				this.item.fragmentEntryLinkId,
				this._getFragmentEntryProcessor(),
				this.item.editableId,
				{
					classNameId: '',
					classPK: '',
					fieldId: ''
				}
			)
		);

		this.store.dispatch(
			Object.assign({}, this._selectedAssetEntry, {
				type: ADD_MAPPED_ASSET_ENTRY
			})
		);

		this._loadFields();
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {object}
 */
FloatingToolbarMappingPanel.STATE = {
	/**
	 * @default []
	 * @memberOf FloatingToolbarMappingPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_fields: Config.array()
		.internal()
		.value([]),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {string}
	 */
	_selectedAssetEntry: Config.object()
		.internal()
		.value({}),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {string}
	 */
	_selectedSourceTypeId: Config.oneOf(
		Object.values(MAPPING_SOURCE_TYPE_IDS)
	).internal(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {object}
	 */
	item: Config.required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {string}
	 */
	itemId: Config.string().required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {string}
	 */
	itemType: Config.string().required()
};

const ConnectedFloatingToolbarMappingPanel = getConnectedComponent(
	FloatingToolbarMappingPanel,
	[
		'contentCreationEnabled',
		'defaultSegmentsExperienceId',
		'getAssetMappingFieldsURL',
		'languageId',
		'mappedAssetEntries',
		'mappingFieldsURL',
		'portletNamespace',
		'segmentsExperienceId',
		'selectedItems',
		'selectedMappingTypes'
	]
);

Soy.register(ConnectedFloatingToolbarMappingPanel, templates);

export {
	ConnectedFloatingToolbarMappingPanel,
	FloatingToolbarMappingPanel,
	MAPPING_SOURCE_TYPE_IDS
};
export default ConnectedFloatingToolbarMappingPanel;
