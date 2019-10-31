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

import '../../common/InfoItemSelector.es';

import './FloatingToolbarMappingPanelDelegateTemplate.soy';
import {ADD_MAPPED_INFO_ITEM} from '../../../actions/actions.es';
import {
	updateEditableValueFieldIdAction,
	updateEditableValueMappedFieldAction
} from '../../../actions/updateEditableValue.es';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import {
	openItemSelector,
	openCreateContentDialog
} from '../../../utils/FragmentsEditorDialogUtils';
import {getMappingSourceTypes} from '../../../utils/FragmentsEditorGetUtils.es';
import {encodeAssetId} from '../../../utils/FragmentsEditorIdUtils.es';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {
	BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
	COMPATIBLE_TYPES,
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
	FRAGMENTS_EDITOR_ITEM_TYPES,
	MAPPING_SOURCE_TYPE_IDS
} from '../../../utils/constants';
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
			['mappedInfoItems'],
			nextState.mappedInfoItems.map(encodeAssetId)
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
					['item', 'editableValues', 'title'],
					mappedInfoItem.title
				);

				nextState = setIn(
					nextState,
					['item', 'editableValues', 'encodedId'],
					mappedInfoItem
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
			this._selectedInfoItem.classNameId = this.item.editableValues.classNameId;
			this._selectedInfoItem.classPK = this.item.editableValues.classPK;
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
	 * @private
	 * @review
	 */
	_handleAssetBrowserLinkClick() {
		openItemSelector(selectedInfoItem => {
			this._selectInfoItem(selectedInfoItem);

			requestAnimationFrame(() => {
				this.refs.panel.focus();
			});
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
							: this._selectedInfoItem.classNameId,
						classPK: shouldRemoveValues
							? ''
							: this._selectedInfoItem.classPK,
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
			this._selectedInfoItem.classNameId &&
			this._selectedInfoItem.classPK
		) {
			promise = this.fetch(this.getAssetMappingFieldsURL, {
				classNameId: this._selectedInfoItem.classNameId,
				classPK: this._selectedInfoItem.classPK
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
	 * @param {object} infoItem
	 * @param {string} infoItem.classNameId
	 * @param {string} infoItem.classPK
	 * @private
	 * @review
	 */
	_selectInfoItem(infoItem) {
		this._selectedInfoItem = infoItem;

		this.store.dispatch(
			updateEditableValueFieldIdAction(
				this.item.fragmentEntryLinkId,
				this._getFragmentEntryProcessor(),
				this.item.editableId,
				{
					classNameId: null,
					classPK: null,
					fieldId: null
				}
			)
		);

		this.store.dispatch({
			...this._selectedInfoItem,
			type: ADD_MAPPED_INFO_ITEM
		});

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
	_selectedInfoItem: Config.object()
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
		'mappedInfoItems',
		'mappingFieldsURL',
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
