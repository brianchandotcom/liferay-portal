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
import {
	shouldUpdatePureComponent,
	onPropertiesChanged
} from '../../utils/FragmentsEditorComponentUtils.es';
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
import {computeConfigurationEditableValue} from '../../utils/computeValues.es';
import {
	FLOATING_TOOLBAR_BUTTONS,
	FRAGMENTS_EDITOR_ITEM_TYPES,
	FRAGMENTS_EDITOR_ROW_TYPES,
	FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
} from '../../utils/constants';
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
		onPropertiesChanged(
			this,
			['_active', '_fragmentEntryLink', '_hasUpdatePermissions'],
			() => {
				if (this._hasUpdatePermissions && this._active) {
					this._createFloatingToolbar();
				}
				else {
					this._disposeFloatingToolbar();
				}
			}
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
		return {
			...state,
			_fragmentEntryLinkRowType: state.rowType,
			_fragmentsEditorRowTypes: FRAGMENTS_EDITOR_ROW_TYPES
		};
	}

	/**
	 * @inheritdoc
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		return shouldUpdatePureComponent(changes, [
			'events',
			'_floatingToolbar'
		]);
	}

	/**
	 * Creates a new instance of the floating toolbar.
	 * @private
	 */
	_createFloatingToolbar() {
		const buttons = [];
		const {
			configuration,
			defaultConfigurationValues,
			editableValues,
			fragmentEntryLinkId,
			portletId
		} = this._fragmentEntryLink;
		const widget = portletId && getWidget(this.widgets, portletId);

		let configurationValues =
			editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR];

		if (!widget || widget.instanceable) {
			buttons.push(FLOATING_TOOLBAR_BUTTONS.duplicateFragment);
		}

		if (
			configuration &&
			Array.isArray(configuration.fieldSets) &&
			configuration.fieldSets.length
		) {
			buttons.push(FLOATING_TOOLBAR_BUTTONS.fragmentConfiguration);

			if (configurationValues) {
				configurationValues = {
					...defaultConfigurationValues,
					...computeConfigurationEditableValue(
						editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR],
						{selectedExperienceId: this.segmentsExperienceId}
					)
				};
			}
		}

		const config = {
			anchorElement: this.element,
			buttons,
			events: {
				buttonClicked: (event, data) => {
					if (
						data.panelId ===
						FLOATING_TOOLBAR_BUTTONS.duplicateFragment.panelId
					) {
						event.preventDefault();

						this._duplicateFragmentEntryLink();

						this._disposeFloatingToolbar();
					}
				}
			},
			item: {
				configuration,
				configurationValues,
				defaultConfigurationValues,
				fragmentEntryLinkId
			},
			itemId: fragmentEntryLinkId,
			itemType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment,
			portalElement: document.body,
			store: this.store
		};

		if (this._floatingToolbar) {
			this._floatingToolbar.setState(config);
		}
		else {
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
			}
			else {
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
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FragmentEntryLink.STATE = {
	_active: Config.internal()
		.bool()
		.value(false),
	_defaultConfigurationValues: Config.object().internal(),
	_dropBorder: Config.internal()
		.string()
		.value(''),
	_floatingToolbar: Config.internal().value(null),
	_fragmentEntryLink: Config.internal()
		.object()
		.value(null),
	_hasUpdatePermissions: Config.internal()
		.bool()
		.value(true),
	_hovered: Config.internal()
		.bool()
		.value(false),
	_itemType: Config.internal()
		.string()
		.value(''),
	_showComments: Config.internal()
		.bool()
		.value(false),
	_spritemap: Config.internal()
		.string()
		.value(''),
	fragmentEntryLinkId: Config.string().required(),
	layoutData: Config.internal().object(),
	name: Config.string().value(''),
	rowType: Config.string(),
	segmentsExperienceId: Config.internal()
		.string()
		.value(),
	showControlBar: Config.bool().value(true),
	styleModifier: Config.string(),
	widgets: Config.internal().array()
};

const ConnectedFragmentEntryLink = getConnectedComponent(
	FragmentEntryLink,
	[],
	(state, props) => {
		const _hovered = itemIsInPath(
			getItemPath(
				state.hoveredItemId,
				state.hoveredItemType,
				state.layoutData.structure
			),
			props.fragmentEntryLinkId,
			FRAGMENTS_EDITOR_ITEM_TYPES.fragment
		);

		const _showComments = state.sidebarPanels.some(
			sidebarPanel => sidebarPanel.sidebarPanelId === 'comments'
		);

		return {
			_active:
				state.activeItemId === props.fragmentEntryLinkId &&
				state.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment,
			_dropBorder:
				state.dropTargetItemId === props.fragmentEntryLinkId &&
				state.dropTargetItemType ===
					FRAGMENTS_EDITOR_ITEM_TYPES.fragment
					? state.dropTargetBorder
					: '',
			_fragmentEntryLink:
				state.fragmentEntryLinks[props.fragmentEntryLinkId],
			_hasUpdatePermissions: state.hasUpdatePermissions,
			_hovered,
			_itemType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment,
			_showComments,
			_spritemap: state.spritemap,
			layoutData: state.layoutData,
			segmentsExperienceId: state.segmentsExperienceId,
			widgets: state.widgets
		};
	}
);

Soy.register(ConnectedFragmentEntryLink, templates);

export {ConnectedFragmentEntryLink, FragmentEntryLink};

export default ConnectedFragmentEntryLink;
