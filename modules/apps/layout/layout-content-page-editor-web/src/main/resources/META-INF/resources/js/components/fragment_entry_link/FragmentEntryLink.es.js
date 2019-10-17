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

import '../floating_toolbar/fragment_configuration/FloatingToolbarFragmentConfigurationPanel.es';

import './FragmentEntryLinkContent.es';
import {
	MOVE_FRAGMENT_ENTRY_LINK,
	UPDATE_SELECTED_SIDEBAR_PANEL_ID
} from '../../actions/actions.es';
import {duplicateFragmentEntryLinkAction} from '../../actions/duplicateFragmentEntryLink.es';
import {removeFragmentEntryLinkAction} from '../../actions/removeFragmentEntryLinks.es';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from '../../actions/saveChanges.es';
import {updateActiveItemAction} from '../../actions/updateActiveItem.es';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import {shouldUpdatePureComponent} from '../../utils/FragmentsEditorComponentUtils.es';
import {
	getFragmentColumn,
	getFragmentRowIndex,
	getItemMoveDirection,
	getItemPath,
	getTargetBorder,
	getWidget,
	itemIsInPath
} from '../../utils/FragmentsEditorGetUtils.es';
import {
	moveItem,
	moveRow,
	removeItem
} from '../../utils/FragmentsEditorUpdateUtils.es';
import {
	FLOATING_TOOLBAR_BUTTONS,
	FRAGMENTS_EDITOR_ITEM_TYPES,
	FRAGMENTS_EDITOR_ROW_TYPES,
	FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
} from '../../utils/constants';
import {prefixSegmentsExperienceId} from '../../utils/prefixSegmentsExperienceId.es';
import FloatingToolbar from '../floating_toolbar/FloatingToolbar.es';
import templates from './FragmentEntryLink.soy';

/**
 * FragmentEntryLink
 * @review
 */
class FragmentEntryLink extends Component {
	/**
	 * @inheritdoc
	 */
	created() {
		this._handleFloatingToolbarButtonClicked = this._handleFloatingToolbarButtonClicked.bind(
			this
		);
	}

	/**
	 * @inheritdoc
	 */
	disposed() {
		this._disposeFloatingToolbar();
	}

	/**
	 * @inheritdoc
	 * @param {object} state
	 * @return {object}
	 * @review
	 */
	prepareStateForRender(state) {
		const hoveredPath = getItemPath(
			state.hoveredItemId,
			state.hoveredItemType,
			state.layoutData.structure
		);

		return {
			...state,

			_fragmentEntryLinkRowType: state.rowType,
			_fragmentsEditorItemTypes: FRAGMENTS_EDITOR_ITEM_TYPES,
			_fragmentsEditorRowTypes: FRAGMENTS_EDITOR_ROW_TYPES,

			_hovered: itemIsInPath(
				hoveredPath,
				state.fragmentEntryLinkId,
				FRAGMENTS_EDITOR_ITEM_TYPES.fragment
			),

			_showComments: state.sidebarPanels.some(
				sidebarPanel => sidebarPanel.sidebarPanelId === 'comments'
			)
		};
	}

	/**
	 * @inheritdoc
	 */
	rendered() {
		if (
			this.fragmentEntryLinkId === this.activeItemId &&
			this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment
		) {
			this._createFloatingToolbar();
		} else {
			this._disposeFloatingToolbar();
		}
	}

	/**
	 * @inheritdoc
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		return shouldUpdatePureComponent(changes);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	syncFragmentEntryLinks() {
		if (this.fragmentEntryLinks[this.fragmentEntryLinkId]) {
			const defaultSegmentsExperienceId = prefixSegmentsExperienceId(
				this.defaultSegmentsExperienceId
			);
			const segmentsExperienceId = prefixSegmentsExperienceId(
				this.segmentsExperienceId
			);

			const configurationValues = this.fragmentEntryLinks[
				this.fragmentEntryLinkId
			].editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR];

			this._configuration = this.fragmentEntryLinks[
				this.fragmentEntryLinkId
			].configuration;

			this._defaultConfigurationValues = this.fragmentEntryLinks[
				this.fragmentEntryLinkId
			].defaultConfigurationValues;

			if (configurationValues) {
				const segmentedConfigurationValues =
					configurationValues[segmentsExperienceId] ||
					configurationValues[defaultSegmentsExperienceId] ||
					configurationValues;

				this._configurationValues = {
					...this._defaultConfigurationValues,
					...segmentedConfigurationValues
				};
			}
		}
	}

	/**
	 * Creates a new instance of the floating toolbar.
	 * @private
	 */
	_createFloatingToolbar() {
		const config = {
			anchorElement: this.element,
			buttons: this._getFloatingToolbarButtons(),
			events: {
				buttonClicked: this._handleFloatingToolbarButtonClicked
			},
			item: {
				configuration: this._configuration,
				configurationValues: this._configurationValues,
				defaultConfigurationValues: this._defaultConfigurationValues,
				fragmentEntryLinkId: this.fragmentEntryLinkId
			},
			itemId: this.fragmentEntryLinkId,
			itemType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment,
			portalElement: document.body,
			store: this.store
		};

		if (this._floatingToolbar) {
			this._floatingToolbar.setState(config);
		} else {
			this._floatingToolbar = new FloatingToolbar(config);
		}
	}

	/**
	 * Duplicate this fragmentEntryLink
	 * @private
	 */
	_duplicateFragmentEntryLink() {
		this.store
			.dispatch(enableSavingChangesStatusAction())
			.dispatch(
				duplicateFragmentEntryLinkAction(
					this.fragmentEntryLinkId,
					this.rowType
				)
			)
			.dispatch(updateLastSaveDateAction())
			.dispatch(disableSavingChangesStatusAction());
	}

	/**
	 * Disposes of an existing floating toolbar instance.
	 * @private
	 */
	_disposeFloatingToolbar() {
		if (this._floatingToolbar) {
			this._floatingToolbar.dispose();

			this._floatingToolbar = null;
		}
	}

	/**
	 * @private
	 * @return {object[]} Floating toolbar buttons
	 * @review
	 */
	_getFloatingToolbarButtons() {
		const buttons = [];

		const fragmentEntryLink = this.fragmentEntryLinks[
			this.fragmentEntryLinkId
		];

		const widget =
			fragmentEntryLink.portletId &&
			getWidget(this.widgets, fragmentEntryLink.portletId);

		if (!widget || widget.instanceable) {
			buttons.push(FLOATING_TOOLBAR_BUTTONS.duplicateFragment);
		}

		if (this._shouldShowConfigPanel()) {
			buttons.push(FLOATING_TOOLBAR_BUTTONS.fragmentConfiguration);
		}

		return buttons;
	}

	/**
	 * Callback executed when an floating toolbar button is clicked
	 * @param {Event} event
	 * @param {Object} data
	 * @private
	 */
	_handleFloatingToolbarButtonClicked(event, data) {
		const {panelId} = data;

		if (panelId === FLOATING_TOOLBAR_BUTTONS.duplicateFragment.panelId) {
			event.preventDefault();

			this._duplicateFragmentEntryLink();
		}
	}

	/**
	 * Handle fragment keyup event so it can emit when it
	 * should be moved or selected.
	 * @param {KeyboardEvent} event
	 * @private
	 * @review
	 */
	_handleFragmentKeyUp(event) {
		event.stopPropagation();

		const direction = getItemMoveDirection(event.keyCode);
		const {fragmentEntryLinkRowType} = event.delegateTarget.dataset;

		if (direction) {
			if (
				fragmentEntryLinkRowType ===
				FRAGMENTS_EDITOR_ROW_TYPES.sectionRow
			) {
				moveRow(
					direction,
					getFragmentRowIndex(
						this.layoutData.structure,
						this.fragmentEntryLinkId
					),
					this.store,
					this.layoutData.structure
				);
			} else {
				const column = getFragmentColumn(
					this.layoutData.structure,
					this.fragmentEntryLinkId
				);
				const fragmentIndex = column.fragmentEntryLinkIds.indexOf(
					this.fragmentEntryLinkId
				);
				const targetFragmentEntryLinkId =
					column.fragmentEntryLinkIds[fragmentIndex + direction];

				if (direction && targetFragmentEntryLinkId) {
					const moveItemPayload = {
						fragmentEntryLinkId: this.fragmentEntryLinkId,
						targetBorder: getTargetBorder(direction),
						targetItemId: targetFragmentEntryLinkId,
						targetItemType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment
					};

					moveItem(
						this.store,
						MOVE_FRAGMENT_ENTRY_LINK,
						moveItemPayload
					);
				}
			}
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleFragmentCommentsButtonClick() {
		this.store.dispatch(
			updateActiveItemAction(
				this.fragmentEntryLinkId,
				FRAGMENTS_EDITOR_ITEM_TYPES.fragment
			)
		);

		this.store.dispatch({
			type: UPDATE_SELECTED_SIDEBAR_PANEL_ID,
			value: 'comments'
		});
	}

	/**
	 * Callback executed when the fragment remove button is clicked.
	 * @param {Object} event
	 * @private
	 */
	_handleFragmentRemoveButtonClick(event) {
		event.stopPropagation();

		removeItem(
			this.store,
			removeFragmentEntryLinkAction(this.fragmentEntryLinkId)
		);
	}

	/**
	 * Returns wether the config panel should be shown or not
	 * @private
	 * @review
	 */
	_shouldShowConfigPanel() {
		const fieldSetsExist =
			this._configuration &&
			Array.isArray(this._configuration.fieldSets) &&
			this._configuration.fieldSets.length > 0;

		const fragmentIsActive =
			this.fragmentEntryLinkId === this.activeItemId &&
			this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment;

		return fieldSetsExist && fragmentIsActive;
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FragmentEntryLink.STATE = {
	/**
	 * Fragment Entry Configuration
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @type {object}
	 */
	_configuration: Config.object().internal(),

	/**
	 * Fragment Entry Link Configuration values
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @type {object}
	 */
	_configurationValues: Config.object().internal(),

	/**
	 * Fragment Entry Link Default configuration values
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @type {object}
	 */
	_defaultConfigurationValues: Config.object().internal(),

	/**
	 * Floating toolbar instance for internal use.
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @type {object|null}
	 */
	_floatingToolbar: Config.internal().value(null),

	/**
	 * FragmentEntryLink id
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!string}
	 */
	fragmentEntryLinkId: Config.string().required(),

	/**
	 * Fragment name
	 * @default ''
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {string}
	 */
	name: Config.string().value(''),

	/**
	 * Row type
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {string}
	 */
	rowType: Config.string(),

	/**
	 * Shows FragmentEntryLink control toolbar
	 * @default true
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!bool}
	 */
	showControlBar: Config.bool().value(true),

	/**
	 * CSS class to modify style
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!string}
	 */
	styleModifier: Config.string()
};

const ConnectedFragmentEntryLink = getConnectedComponent(FragmentEntryLink, [
	'activeItemId',
	'activeItemType',
	'defaultLanguageId',
	'defaultSegmentsExperienceId',
	'dropTargetItemId',
	'dropTargetItemType',
	'dropTargetBorder',
	'duplicateFragmentEntryLinkURL',
	'fragmentEntryLinks',
	'hoveredItemId',
	'hoveredItemType',
	'imageSelectorURL',
	'languageId',
	'layoutData',
	'portletNamespace',
	'segmentsExperienceId',
	'selectedMappingTypes',
	'selectedSidebarPanelId',
	'sidebarPanels',
	'spritemap',
	'widgets'
]);

Soy.register(ConnectedFragmentEntryLink, templates);

export {ConnectedFragmentEntryLink, FragmentEntryLink};

export default ConnectedFragmentEntryLink;
