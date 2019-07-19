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
import {debounce} from 'frontend-js-web';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarLinkPanelDelegateTemplate.soy';
import {TARGET_TYPES} from '../../../utils/constants';
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
import {
	FloatingToolbarMappingPanel,
	SOURCE_TYPE_IDS
} from '../mapping/FloatingToolbarMappingPanel.es';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {encodeAssetId} from '../../../utils/FragmentsEditorIdUtils.es';
import {openAssetBrowser} from '../../../utils/FragmentsEditorDialogUtils';

/**
 * FloatingToolbarLinkPanel
 */
class FloatingToolbarLinkPanel extends Component {
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

		nextState = setIn(nextState, ['_sourceTypeIds'], SOURCE_TYPE_IDS);

		if (
			nextState.mappingFieldsURL &&
			nextState.selectedMappingTypes &&
			nextState.selectedMappingTypes.type
		) {
			nextState = setIn(
				nextState,
				['_sourceTypes'],
				FloatingToolbarMappingPanel.getSourceTypes(
					nextState.selectedMappingTypes.subtype
						? nextState.selectedMappingTypes.subtype.label
						: nextState.selectedMappingTypes.type.label
				)
			);
		}

		if (
			nextState.mappedAssetEntries &&
			nextState.item.editableValues.config &&
			nextState.item.editableValues.config.classNameId &&
			nextState.item.editableValues.config.classPK
		) {
			const mappedAssetEntry = nextState.mappedAssetEntries.find(
				assetEntry =>
					nextState.item.editableValues.config.classNameId ===
						assetEntry.classNameId &&
					nextState.item.editableValues.config.classPK ===
						assetEntry.classPK
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
			this._selectedSourceTypeId = SOURCE_TYPE_IDS.content;

			if (
				this.item &&
				this.mappingFieldsURL &&
				(!this.item.editableValues.config ||
					!this.item.editableValues.config.classNameId)
			) {
				this._selectedSourceTypeId = SOURCE_TYPE_IDS.structure;
			}
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

				requestAnimationFrame(() => {
					this.refs.panel.focus();
				});
			},
			modalTitle: assetBrowserWindowTitle,
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

		requestAnimationFrame(() => {
			this.refs.panel.focus();
		});
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
			href: hrefElement.value
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
	 * @param {object} assetEntry
	 * @param {string} assetEntry.classNameId
	 * @param {string} assetEntry.classPK
	 * @private
	 * @review
	 */
	_selectAssetEntry(assetEntry) {
		const config = {
			classNameId: assetEntry.classNameId,
			classPK: assetEntry.classPK
		};

		this._updateRowConfig(config);
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
			type
		};

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
	store: Config.object().value(null),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {string}
	 */
	_selectedSourceTypeId: Config.oneOf(
		Object.values(SOURCE_TYPE_IDS)
	).internal()
};

const ConnectedFloatingToolbarLinkPanel = getConnectedComponent(
	FloatingToolbarLinkPanel,
	[
		'assetBrowserLinks',
		'getAssetMappingFieldsURL',
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
