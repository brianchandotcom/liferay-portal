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

import {MOVE_FRAGMENT_ENTRY_LINK} from '../../actions/actions.es';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import {shouldUpdatePureComponent} from '../../utils/FragmentsEditorComponentUtils.es';
import {
	getFragmentColumn,
	getFragmentRowIndex,
	getItemMoveDirection,
	getItemPath,
	getTargetBorder,
	itemIsInPath
} from '../../utils/FragmentsEditorGetUtils.es';
import {moveItem, moveRow} from '../../utils/FragmentsEditorUpdateUtils.es';
import {
	FRAGMENTS_EDITOR_ITEM_TYPES,
	FRAGMENTS_EDITOR_ROW_TYPES
} from '../../utils/constants';
import templates from './DropZoneFragmentEntryLink.soy';

import '../master_layout/ManageAllowedFragment.es';

/**
 * FragmentEntryLink
 * @review
 */
class DropZoneFragmentEntryLink extends Component {
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
			)
		};
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
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
DropZoneFragmentEntryLink.STATE = {
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
	 * Row type
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {string}
	 */
	rowType: Config.string()
};

const ConnectedFragmentEntryLink = getConnectedComponent(
	DropZoneFragmentEntryLink,
	[
		'activeItemId',
		'activeItemType',
		'dropTargetItemId',
		'dropTargetItemType',
		'dropTargetBorder',
		'hoveredItemId',
		'hoveredItemType',
		'layoutData',
		'spritemap'
	]
);

Soy.register(ConnectedFragmentEntryLink, templates);

export {ConnectedFragmentEntryLink, DropZoneFragmentEntryLink};

export default ConnectedFragmentEntryLink;
