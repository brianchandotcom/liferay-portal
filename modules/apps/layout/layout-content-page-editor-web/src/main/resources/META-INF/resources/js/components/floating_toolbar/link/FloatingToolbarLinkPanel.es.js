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

import '../../common/InfoItemSelector.es';
import {debounce, PortletBase} from 'frontend-js-web';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarLinkPanelDelegateTemplate.soy';
import {MAPPING_SOURCE_TYPE_IDS, TARGET_TYPES} from '../../../utils/constants';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from '../../../actions/saveChanges.es';
import templates from './FloatingToolbarLinkPanel.soy';
import {
	ADD_MAPPED_ASSET_ENTRY,
	UPDATE_CONFIG_ATTRIBUTES
} from '../../../actions/actions.es';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import {getMappingSourceTypes} from '../../../utils/FragmentsEditorGetUtils.es';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {encodeAssetId} from '../../../utils/FragmentsEditorIdUtils.es';
import {openItemSelector} from '../../../utils/FragmentsEditorDialogUtils';

/**
 * FloatingToolbarLinkPanel
 */
class FloatingToolbarLinkPanel extends PortletBase {
	/**
	 * @inheritdoc
	 * @review
	 */
	created() {
		this._handleInputHrefKeyUp = debounce(
			this._handleInputHrefKeyUp.bind(this),
			400
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
					['item', 'editableValues', 'config', 'title'],
					mappedAssetEntry.title
				);

				nextState = setIn(
					nextState,
					['item', 'editableValues', 'config', 'encodedId'],
					mappedAssetEntry
				);

				if (
					nextState.item.editableValues.config.fieldId &&
					!nextState._mappedFieldValue
				) {
					const fieldId =
						nextState.item.editableValues.config.fieldId;

					this._getMappedValue(fieldId).then(fieldValue => {
						if (typeof fieldValue === 'string') {
							this._mappedFieldValue = fieldValue;
						}
					});
				}
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
			this._selectedAssetEntry.classNameId = this.item.editableValues.config.classNameId;
			this._selectedAssetEntry.classPK = this.item.editableValues.config.classPK;
			this._selectedSourceTypeId = MAPPING_SOURCE_TYPE_IDS.content;

			if (
				this.item &&
				this.mappingFieldsURL &&
				(!this.item.editableValues.config ||
					!this.item.editableValues.config.classNameId)
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
			newItem &&
			oldItem &&
			newItem.editableValues.config &&
			oldItem.editableValues.config
		) {
			const newConfig = newItem.editableValues.config;
			const oldConfig = newItem.editableValues.config;

			if (
				newConfig.classNameId !== oldConfig.classNameId ||
				newConfig.classPK !== oldConfig.classPK ||
				this._fields.length === 0
			) {
				this._loadFields();
			}
		} else {
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
	 * Clears row config
	 * @private
	 * @review
	 */
	_clearRowConfig() {
		const config = {
			classNameId: '',
			classPK: '',
			fieldId: '',
			href: '',
			mappedField: '',
			mapperType: 'link'
		};

		this._updateRowConfig(config);
	}

	/**
	 * @private
	 * @review
	 */
	_focusPanel() {
		requestAnimationFrame(() => {
			if (this.refs.panel) {
				this.refs.panel.focus();
			}
		});
	}

	_getMappedValue(fieldId) {
		if (fieldId) {
			return this.fetch(this.getAssetFieldValueURL, {
				classNameId: this._selectedAssetEntry.classNameId,
				classPK: this._selectedAssetEntry.classPK,
				fieldId
			})
				.then(response => response.json())
				.then(response => response.fieldValue);
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleAssetBrowserLinkClick() {
		openItemSelector({
			callback: selectedInfoItem => {
				this._selectAssetEntry(selectedInfoItem);

				this._focusPanel();
			},
			eventName: `${this.portletNamespace}selectInfoItem`,
			itemSelectorURL: this.infoItemSelectorURL,
			portletNamespace: this.portletNamespace
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

		this._focusPanel();
	}

	/**
	 * Handle field option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleFieldOptionChange(event) {
		const fieldId = event.delegateTarget.value;

		if (fieldId === '') {
			this._clearRowConfig();
		} else {
			const config = {
				classNameId: this._selectedAssetEntry.classNameId,
				classPK: this._selectedAssetEntry.classPK,
				href: '',
				mapperType: 'link'
			};

			if (
				this._selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.content
			) {
				config.fieldId = fieldId;
			} else if (
				this._selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.structure
			) {
				config.mappedField = fieldId;
			}

			this._updateRowConfig(config);

			if (
				!fieldId ||
				this._selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.structure
			) {
				this._mappedFieldValue = '';
			} else {
				this._getMappedValue(fieldId).then(fieldValue => {
					if (typeof fieldValue === 'string') {
						this._mappedFieldValue = fieldValue;
						this._updateRowConfig({href: fieldValue});
					}
				});
			}
		}
	}

	/**
	 * Callback executed on href keyup
	 * @param {object} event
	 * @private
	 * @review
	 */
	_handleInputHrefKeyUp(event) {
		const hrefElement = event.target;

		const config = {
			classNameId: '',
			classPK: '',
			fieldId: '',
			href: hrefElement.value,
			mappedField: '',
			mapperType: 'link'
		};

		this._updateRowConfig(config);
	}

	/**
	 * Handle source option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleSourceTypeChange(event) {
		this._selectedSourceTypeId = event.delegateTarget.value;

		this._clearFields();
		this._clearRowConfig();
	}

	/**
	 * @param {object} event
	 * @private
	 * @review
	 */
	_handleSubmit(event) {
		event.preventDefault();

		event.stopPropagation();
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
			this._selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.structure &&
			this.selectedMappingTypes.type
		) {
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
					this._fields = response.filter(field =>
						['text', 'url'].includes(field.type)
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
			Object.assign({}, this._selectedAssetEntry, {
				type: ADD_MAPPED_ASSET_ENTRY
			})
		);

		this._loadFields();
	}

	/**
	 * Updates row configuration
	 * @param {object} config Row configuration
	 * @private
	 * @review
	 */
	_updateRowConfig(config) {
		this.store
			.dispatch(enableSavingChangesStatusAction())
			.dispatch({
				config,
				editableId: this.item.editableId,
				fragmentEntryLinkId: this.item.fragmentEntryLinkId,
				type: UPDATE_CONFIG_ATTRIBUTES
			})
			.dispatch(updateLastSaveDateAction())
			.dispatch(disableSavingChangesStatusAction());
	}

	/**
	 * Handle button type option change
	 * @param {Event} event
	 */
	_handleTargetOptionChange(event) {
		const targetElement = event.delegateTarget;

		const config = {
			target: targetElement.options[targetElement.selectedIndex].value
		};

		this._updateRowConfig(config);
	}

	/**
	 * Handle link type option change
	 * @param {Event} event
	 */
	_handleTypeOptionChange(event) {
		const targetElement = event.delegateTarget;

		const type = targetElement.options[targetElement.selectedIndex].value;

		const config = {
			classNameId: '',
			classPK: '',
			fieldId: '',
			href: '',
			mappedField: '',
			mapperType: 'link',
			type
		};

		this._clearFields();

		this._updateRowConfig(config);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarLinkPanel.STATE = {
	/**
	 * @default []
	 * @memberOf FloatingToolbarLinkPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_fields: Config.array()
		.internal()
		.value([]),

	/**
	 * Mapped asset field value
	 * @instance
	 * @memberOf FloatingToolbarLinkPanel
	 * @private
	 * @review
	 * @type {string}
	 */
	_mappedFieldValue: Config.internal().string(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarLinkPanel
	 * @review
	 * @type {string}
	 */
	_selectedAssetEntry: Config.object()
		.internal()
		.value({}),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarLinkPanel
	 * @review
	 * @type {string}
	 */
	_selectedSourceTypeId: Config.oneOf(
		Object.values(MAPPING_SOURCE_TYPE_IDS)
	).internal(),

	/**
	 * @default TARGET_TYPES
	 * @memberOf FloatingToolbarLinkPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_targetTypes: Config.array()
		.internal()
		.value(TARGET_TYPES),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarLinkPanel
	 * @review
	 * @type {object}
	 */
	item: Config.object().value(null),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarLinkPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config.string().required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarLinkPanel
	 * @review
	 * @type {object}
	 */
	store: Config.object().value(null)
};

const ConnectedFloatingToolbarLinkPanel = getConnectedComponent(
	FloatingToolbarLinkPanel,
	[
		'getAssetFieldValueURL',
		'getAssetMappingFieldsURL',
		'infoItemSelectorURL',
		'mappedAssetEntries',
		'mappingFieldsURL',
		'portletNamespace',
		'selectedMappingTypes',
		'spritemap'
	]
);

Soy.register(ConnectedFloatingToolbarLinkPanel, templates);

export {ConnectedFloatingToolbarLinkPanel, FloatingToolbarLinkPanel};
export default ConnectedFloatingToolbarLinkPanel;
