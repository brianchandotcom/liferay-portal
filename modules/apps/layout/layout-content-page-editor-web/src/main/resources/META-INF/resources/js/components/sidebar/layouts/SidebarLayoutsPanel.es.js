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

import {
	ADD_ROW,
	CLEAR_DROP_TARGET,
	UPDATE_DROP_TARGET
} from '../../../actions/actions.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import SidebarLayoutsDragDrop from './utils/SidebarLayoutsDragDrop.es';
import {Store} from '../../../store/store.es';
import templates from './SidebarLayoutsPanel.soy';

/**
 * SidebarLayoutsPanel
 */
class SidebarLayoutsPanel extends Component {
	/**
	 * @inheritDoc
	 */
	rendered(firstRendered) {
		if (firstRendered) {
			this._initializeSidebarLayoutsDragDrop();
		}
	}

	/**
	 * @inheritdoc
	 */
	disposed() {
		this._sidebarLayoutsDragDrop.dispose();
	}

	/**
	 * Handles dragLayout event and dispatches action to update drag target
	 * @param {object} eventData
	 * @param {string} eventData.hoveredRowBorder
	 * @param {string} eventData.hoveredRowId
	 */
	_handleDragLayout(eventData) {
		const {hoveredRowBorder, hoveredRowId} = eventData;

		this.store.dispatch({
			dropTargetBorder: hoveredRowBorder,
			dropTargetItemId: hoveredRowId,
			dropTargetItemType: FRAGMENTS_EDITOR_ITEM_TYPES.row,
			type: UPDATE_DROP_TARGET
		});
	}

	/**
	 * Handles dropLayout event and dispatches action to add a row
	 * @param {!object} eventData
	 * @param {!number} eventData.layoutIndex
	 * @private
	 * @review
	 */
	_handleDropLayout(eventData) {
		const layoutColumns = this._layouts[eventData.layoutIndex].columns;

		this.store
			.dispatch({
				layoutColumns,
				type: ADD_ROW
			})
			.dispatch({
				type: CLEAR_DROP_TARGET
			});

		requestAnimationFrame(() => {
			this._initializeSidebarLayoutsDragDrop();
		});
	}

	/**
	 * Handles leaveLayoutTarget event and dispatches
	 * action to clear drag target
	 * @private
	 * @review
	 */
	_handleLeaveLayoutTarget() {
		this.store.dispatch({
			type: CLEAR_DROP_TARGET
		});
	}

	/**
	 * Initializes sidebarLayoutsDragDrop instance
	 * @private
	 * @review
	 */
	_initializeSidebarLayoutsDragDrop() {
		if (this._sidebarLayoutsDragDrop) {
			this._sidebarLayoutsDragDrop.dispose();
		}

		this._sidebarLayoutsDragDrop = new SidebarLayoutsDragDrop();

		this._sidebarLayoutsDragDrop.on(
			'dragLayout',
			this._handleDragLayout.bind(this)
		);

		this._sidebarLayoutsDragDrop.on(
			'dropLayout',
			this._handleDropLayout.bind(this)
		);

		this._sidebarLayoutsDragDrop.on(
			'leaveLayoutTarget',
			this._handleLeaveLayoutTarget.bind(this)
		);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
SidebarLayoutsPanel.STATE = {
	/**
	 * List of layouts to be shown
	 * @default []
	 * @memberOf SidebarLayoutsPanel
	 * @private
	 * @review
	 * @type {Array}
	 */
	_layouts: Config.arrayOf(
		Config.shapeOf({
			columns: Config.arrayOf(Config.string())
		})
	).value([
		{
			columns: ['12']
		},
		{
			columns: ['6', '6']
		},
		{
			columns: ['8', '4']
		},
		{
			columns: ['4', '8']
		},
		{
			columns: ['4', '4', '4']
		},
		{
			columns: ['3', '3', '3', '3']
		}
	]),

	/**
	 * Internal SidebarLayoutsDragDrop instance
	 * @default null
	 * @instance
	 * @memberOf SidebarLayoutsPanel
	 * @review
	 * @type {object|null}
	 */
	_sidebarLayoutsDragDrop: Config.internal().value(null),

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf SidebarLayoutsPanel
	 * @review
	 * @type {Store}
	 */
	store: Config.instanceOf(Store)
};

Soy.register(SidebarLayoutsPanel, templates);

export {SidebarLayoutsPanel};
export default SidebarLayoutsPanel;
