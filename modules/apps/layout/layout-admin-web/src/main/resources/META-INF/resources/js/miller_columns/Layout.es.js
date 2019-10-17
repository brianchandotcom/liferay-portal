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

import {fetch, navigate} from 'frontend-js-web';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import './LayoutBreadcrumbs.es';

import './LayoutColumn.es';
import {setIn} from '../utils/utils.es';
import templates from './Layout.soy';
import {
	DROP_TARGET_BORDERS,
	DROP_TARGET_ITEM_TYPES,
	LayoutDragDrop
} from './utils/LayoutDragDrop.es';
import {
	dropIsValid,
	dropItemInsideColumn,
	dropItemInsideItem,
	dropItemNextToItem
} from './utils/LayoutDropUtils.es';
import {
	columnIsItemChild,
	getColumnActiveItem,
	getColumnLastItem,
	getItem,
	getItemColumn,
	getItemColumnIndex,
	itemIsParent
} from './utils/LayoutGetUtils.es';
import {
	clearFollowingColumns,
	clearPath,
	deleteEmptyColumns,
	setActiveItem
} from './utils/LayoutUpdateUtils.es';

/**
 * Metal drag
 * @param {number}
 */
const DRAG_SPEED = 20;

const UPDATE_PATH_TIMEOUT = 500;

/**
 * Component that allows to show layouts tree in form of three dependent
 * columns. It integrates three <LayoutColumn /> components for N-th, N-th + 2
 * and N-th + 3 levels of layouts tree.
 * @review
 */
class Layout extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this._handleLayoutColumnsScroll = this._handleLayoutColumnsScroll.bind(
			this
		);

		const A = new AUI();

		A.use(
			'liferay-search-container',
			'liferay-search-container-select',
			A => {
				const plugins = [];

				plugins.push({
					cfg: {
						rowSelector: '.layout-item'
					},
					fn: A.Plugin.SearchContainerSelect
				});

				const searchContainer = new Liferay.SearchContainer({
					contentBox: A.one(this.refs.layout),
					id:
						this.getInitialConfig().portletNamespace +
						this.getInitialConfig().searchContainerId,
					plugins
				});

				this.searchContainer_ = searchContainer;
			}
		);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	disposed() {
		this._layoutDragDrop.dispose();
		this._removeLayoutColumnsScrollListener();
	}

	/**
	 * @inheritDoc
	 */
	rendered(firstRendered) {
		requestAnimationFrame(() => {
			const {layoutColumns} = this.refs;

			if (typeof this._layoutColumnsScrollLeft === 'number') {
				layoutColumns.scrollLeft = this._layoutColumnsScrollLeft;
			} else {
				layoutColumns.scrollLeft = layoutColumns.scrollWidth;
			}

			if (this._newPathItems) {
				this._addLayoutDragDropTargets(this._newPathItems);
				this._newPathItems = null;
			}
		});

		if (firstRendered) {
			this._initializeLayoutDragDrop();
		}
	}

	/**
	 * Adds scroll listener to layout columns.
	 * @private
	 * @review
	 */
	_addLayoutColumnsScrollListener() {
		const {layoutColumns} = this.refs;

		if (layoutColumns) {
			this._layoutColumnsScrollLeft = layoutColumns.scrollLeft;

			layoutColumns.addEventListener(
				'scroll',
				this._handleLayoutColumnsScroll
			);
		}
	}

	/**
	 * Receives an array of items and add them as drag drop targets
	 * @param {!Array} items
	 * @private
	 * @review
	 */
	_addLayoutDragDropTargets(items) {
		let element = null;
		let query = null;

		items.forEach(item => {
			query = `[data-layout-column-item-plid="${item.plid}"]`;
			element = document.querySelector(query);
			this._layoutDragDrop.addTarget(element);
		});
	}

	/**
	 * @param {string} plid
	 * @private
	 * @return {Promise<object>}
	 * @review
	 */
	_getItemChildren(plid) {
		const formData = new FormData();

		formData.append(`${this.portletNamespace}plid`, plid);

		return fetch(this.getItemChildrenURL, {
			body: formData,
			method: 'POST'
		}).then(response => response.json());
	}

	/**
	 * Handle dragLayoutColumnItem event
	 * @param {!object} eventData
	 * @param {!string} eventData.position
	 * @param {!string} eventData.sourceItemPlid
	 * @param {!string} eventData.targetId
	 * @param {!string} eventData.targetType
	 * @private
	 * @review
	 */
	_handleDragLayoutColumnItem(eventData) {
		clearTimeout(this._updatePathTimeout);

		const {position, sourceItemPlid, targetId, targetType} = eventData;

		if (targetType === DROP_TARGET_ITEM_TYPES.column) {
			this._setColumnHoveredData(sourceItemPlid, targetId);
		} else if (targetType === DROP_TARGET_ITEM_TYPES.item) {
			this._setItemHoveredData(position, sourceItemPlid, targetId);

			if (
				this._draggingItemPosition === DROP_TARGET_BORDERS.inside &&
				this._currentPathItemPlid !== targetId
			) {
				this._updatePathTimeout = setTimeout(() => {
					this._updatePath(targetId);
				}, UPDATE_PATH_TIMEOUT);
			}
		}
	}

	/**
	 * @param {!object} eventData
	 * @param {!string} eventData.targetId
	 * @param {!string} eventData.targetType
	 * @private
	 * @review
	 */
	_handleDropLayoutColumnItem(eventData) {
		this._removeLayoutColumnsScrollListener();
		this._resetDragDropClasses();

		let layoutColumns = this.layoutColumns.map(layoutColumn => [
			...layoutColumn
		]);
		const {sourceItemPlid, targetId, targetType} = eventData;

		const itemDropIsValid = dropIsValid(
			layoutColumns,
			this._draggingItem,
			this._draggingItemColumnIndex,
			targetId,
			targetType
		);

		if (itemDropIsValid) {
			let parentPlid = null;
			let priority = null;

			if (targetType === DROP_TARGET_ITEM_TYPES.column) {
				layoutColumns = clearPath(
					layoutColumns,
					this._draggingItem,
					this._draggingItemColumnIndex,
					targetId,
					targetType
				);

				const dropData = dropItemInsideColumn(
					layoutColumns,
					this._draggingItem,
					targetId
				);

				layoutColumns = dropData.layoutColumns;
				parentPlid = dropData.newParentPlid;
				priority = dropData.priority;
			} else if (targetType === DROP_TARGET_ITEM_TYPES.item) {
				const targetItem = getItem(layoutColumns, targetId);

				layoutColumns = clearPath(
					layoutColumns,
					this._draggingItem,
					this._draggingItemColumnIndex,
					targetId,
					targetType
				);

				if (this._draggingItemPosition === DROP_TARGET_BORDERS.inside) {
					const pathUpdated = !!this._currentPathItemPlid;

					const dropData = dropItemInsideItem(
						layoutColumns,
						this._draggingItem,
						this._draggingItemColumnIndex,
						pathUpdated,
						targetItem
					);

					layoutColumns = dropData.layoutColumns;
					parentPlid = dropData.newParentPlid;
					priority = dropData.priority;
				} else {
					const dropData = dropItemNextToItem(
						layoutColumns,
						this._draggingItem,
						this._draggingItemPosition,
						targetItem
					);

					layoutColumns = dropData.layoutColumns;
					parentPlid = dropData.newParentPlid;
					priority = dropData.priority;
				}
			}

			this._moveLayoutColumnItemOnServer(
				parentPlid,
				sourceItemPlid,
				priority
			)
				.then(response => {
					let nextPromise = response;

					if (this._draggingItemParentPlid !== '0') {
						nextPromise = this._getItemChildren(
							this._draggingItemParentPlid
						).then(response => {
							if (
								response.children &&
								response.children.length === 0
							) {
								layoutColumns = this._removeHasChildArrow(
									layoutColumns,
									this._draggingItemParentPlid
								);
							}
						});
					}

					return nextPromise;
				})
				.then(() => {
					this.layoutColumns = layoutColumns;

					clearTimeout(this._updatePathTimeout);

					requestAnimationFrame(() => {
						this._initializeLayoutDragDrop();
					});
				});
		}

		this._resetHoveredData();
		this._currentPathItemPlid = null;
		this._draggingItem = null;
		this._draggingItemColumnIndex = null;
	}

	/**
	 * Handle layout column item check event
	 * @param {!object} event
	 * @param {string} event.delegateTarget.value
	 * @private
	 * @review
	 */
	_handleLayoutColumnItemCheck(event) {
		this._setLayoutColumnItemChecked(
			event.delegateTarget.value,
			event.delegateTarget.checked
		);
	}

	/**
	 * Handle click event on layout column item checkbox
	 * @param {!object} event
	 * @private
	 * @review
	 */
	_handleLayoutColumnItemCheckboxClick(event) {
		event.stopPropagation();
	}

	/**
	 * Handle layout column item click event
	 * @param {!object} event
	 * @param {string} event.delegateTarget.dataset.layoutColumnItemPlid
	 * @param {object} event.target.classList
	 * @param {string} event.target.type
	 * @private
	 * @review
	 */
	_handleLayoutColumnItemClick(event) {
		const itemUrl = event.delegateTarget.dataset.layoutColumnItemUrl;

		if (itemUrl) {
			navigate(itemUrl);
		} else {
			const itemPlid = event.delegateTarget.dataset.layoutColumnItemPlid;

			const item = getItem(this.layoutColumns, itemPlid);

			this.layoutColumns = setActiveItem(this.layoutColumns, itemPlid);

			navigate(item.url);
		}
	}

	/**
	 * Stores scroll distances when they are below DRAG_SPEED and
	 * a drag and drop is being done.
	 * @private
	 * @review
	 * @see DRAG_SPEED
	 */
	_handleLayoutColumnsScroll() {
		const {layoutColumns} = this.refs;

		if (layoutColumns) {
			const delta = Math.abs(
				this._layoutColumnsScrollLeft - layoutColumns.scrollLeft
			);

			if (delta <= DRAG_SPEED) {
				this._layoutColumnsScrollLeft = layoutColumns.scrollLeft;
			}
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleLeaveLayoutColumnItem() {
		this._resetDragDropClasses();
		this._resetHoveredData();
	}

	/**
	 * @param {!object} eventData
	 * @param {!string} eventData.sourceItemPlid
	 * @private
	 * @review
	 */
	_handleStartMovingLayoutColumnItem(eventData) {
		this._addLayoutColumnsScrollListener();

		const sourceItemColumn = getItemColumn(
			this.layoutColumns,
			eventData.sourceItemPlid
		);

		const sourceItemColumnIndex = this.layoutColumns.indexOf(
			sourceItemColumn
		);

		this._draggingItem = getItem(
			this.layoutColumns,
			eventData.sourceItemPlid
		);

		this._draggingItemColumnIndex = sourceItemColumnIndex;

		this._draggingItemParentPlid = getColumnActiveItem(
			this.layoutColumns,
			sourceItemColumnIndex - 1
		).plid;
	}

	/**
	 * @private
	 * @review
	 */
	_initializeLayoutDragDrop() {
		if (this._layoutDragDrop) {
			this._layoutDragDrop.dispose();
		}

		this._layoutDragDrop = new LayoutDragDrop();

		this._layoutDragDrop.on(
			'dragLayoutColumnItem',
			this._handleDragLayoutColumnItem.bind(this)
		);

		this._layoutDragDrop.on(
			'leaveLayoutColumnItem',
			this._handleLeaveLayoutColumnItem.bind(this)
		);

		this._layoutDragDrop.on(
			'dropLayoutColumnItem',
			this._handleDropLayoutColumnItem.bind(this)
		);

		this._layoutDragDrop.on(
			'startMovingLayoutColumnItem',
			this._handleStartMovingLayoutColumnItem.bind(this)
		);
	}

	/**
	 * Sends the movement of an item to the server.
	 * @param {string} parentPlid
	 * @param {string} plid
	 * @param {string} priority
	 * @private
	 * @return {Promise<object>}
	 * @review
	 */
	_moveLayoutColumnItemOnServer(parentPlid, plid, priority) {
		const formData = new FormData();

		formData.append(`${this.portletNamespace}plid`, plid);
		formData.append(`${this.portletNamespace}parentPlid`, parentPlid);

		if (priority) {
			formData.append(`${this.portletNamespace}priority`, priority);
		}

		return fetch(this.moveLayoutColumnItemURL, {
			body: formData,
			method: 'POST'
		}).catch(() => {
			this._resetHoveredData();
		});
	}

	/**
	 * Remove arrow if the item has no children
	 * @param {Array} layoutColumns
	 * @param {string} itemPlid
	 * @private
	 * @return {Array}
	 * @review
	 */
	_removeHasChildArrow(layoutColumns, itemPlid) {
		let nextLayoutColumns = layoutColumns;

		const column = getItemColumn(layoutColumns, itemPlid);
		const item = getItem(nextLayoutColumns, itemPlid);

		nextLayoutColumns = setIn(
			nextLayoutColumns,
			[
				nextLayoutColumns.indexOf(column),
				column.indexOf(item),
				'hasChild'
			],
			false
		);

		this._draggingItemParentPlid = null;

		return nextLayoutColumns;
	}

	/**
	 * Removes scroll listener from layout columns.
	 * @private
	 * @review
	 */
	_removeLayoutColumnsScrollListener() {
		const {layoutColumns} = this.refs;

		if (layoutColumns) {
			layoutColumns.removeEventListener(
				'scroll',
				this._handleLayoutColumnsScroll
			);
		}

		this._layoutColumnsScrollLeft = null;
	}

	/**
	 * Removes all drag-drop related classes from all items.
	 * @private
	 * @review
	 */
	_resetDragDropClasses() {
		this.element
			.querySelectorAll(
				`
				.layout-column-item-drag-bottom,
				.layout-column-item-drag-inside,
				.layout-column-item-drag-top
			`
			)
			.forEach(item => {
				item.classList.remove(
					'layout-column-item-drag-bottom',
					'layout-column-item-drag-inside',
					'layout-column-item-drag-top'
				);
			});
	}

	/**
	 * Resets dragging information to null
	 * @private
	 */
	_resetHoveredData() {
		this._draggingItemPosition = null;
		this._hoveredLayoutColumnItemPlid = null;
	}

	/**
	 * Set hovered data with the last item of a column
	 * @param {string} draggingItemPlid
	 * @param {number} targetColumnIndex Index of the column whose last item
	 * will be stored inside hoveredLayoutColumnItemPlid
	 * @private
	 * @review
	 */
	_setColumnHoveredData(draggingItemPlid, targetColumnIndex) {
		const targetColumnIsChild = columnIsItemChild(
			targetColumnIndex,
			this._draggingItem,
			this._draggingItemColumnIndex
		);
		const targetColumnLastItem = getColumnLastItem(
			this.layoutColumns,
			targetColumnIndex
		);
		const targetEqualsSource =
			targetColumnLastItem &&
			draggingItemPlid === targetColumnLastItem.plid;

		if (
			targetColumnLastItem &&
			!targetColumnIsChild &&
			!targetEqualsSource
		) {
			this._draggingItemPosition = DROP_TARGET_BORDERS.bottom;
			this._hoveredLayoutColumnItemPlid = targetColumnLastItem.plid;

			this._resetDragDropClasses();

			this._setElementDragDropCssClass(
				targetColumnLastItem.plid,
				DROP_TARGET_BORDERS.bottom
			);
		}
	}

	/**
	 * Adds the given CSS class to the given itemPlid if it is found inside
	 * the document.
	 * @param {string} itemPlid
	 * @param {string} cssClass
	 * @private
	 * @review
	 */
	_setElementDragDropCssClass(itemPlid, cssClass) {
		const itemElement = this.element.querySelector(
			`li[data-layout-column-item-plid="${itemPlid}"]`
		);

		if (itemElement) {
			itemElement.classList.add(cssClass);
		}
	}

	/**
	 * Set hovered data with an item
	 * @param {string} position Position where item is being dragged
	 * @param {string} sourceItemPlid
	 * @param {string} targetItemPlid
	 * @private
	 * @review
	 * @see DROP_TARGET_BORDERS
	 */
	_setItemHoveredData(position, sourceItemPlid, targetItemPlid) {
		const targetColumnIndex = getItemColumnIndex(
			this.layoutColumns,
			targetItemPlid
		);

		const targetIsChild = columnIsItemChild(
			targetColumnIndex,
			this._draggingItem,
			this._draggingItemColumnIndex
		);

		const targetEqualsSource = sourceItemPlid === targetItemPlid;

		const draggingInsideParent =
			position === DROP_TARGET_BORDERS.inside &&
			itemIsParent(this.layoutColumns, sourceItemPlid, targetItemPlid);

		if (!targetEqualsSource && !targetIsChild && !draggingInsideParent) {
			this._draggingItemPosition = position;
			this._hoveredLayoutColumnItemPlid = targetItemPlid;

			this._resetDragDropClasses();
			this._setElementDragDropCssClass(targetItemPlid, position);
		}
	}

	/**
	 * Set an item active property to true
	 * @param {!string} plid
	 * @param {!boolean} checked
	 * @private
	 * @review
	 */
	_setLayoutColumnItemChecked(plid, checked) {
		const column = getItemColumn(this.layoutColumns, plid);
		const columnIndex = this.layoutColumns.indexOf(column);
		const item = getItem(this.layoutColumns, plid);

		this.layoutColumns = setIn(
			this.layoutColumns,
			[columnIndex, column.indexOf(item), 'checked'],
			checked
		);
	}

	/**
	 * @param {string} targetItemPlid
	 * @private
	 * @review
	 */
	_updatePath(targetItemPlid) {
		let nextLayoutColumns = this.layoutColumns;

		const targetColumn = getItemColumn(nextLayoutColumns, targetItemPlid);
		const targetColumnIndex = nextLayoutColumns.indexOf(targetColumn);

		nextLayoutColumns = clearFollowingColumns(
			nextLayoutColumns,
			targetColumnIndex
		);

		nextLayoutColumns = setActiveItem(nextLayoutColumns, targetItemPlid);

		this._draggingItem.active = false;

		this._currentPathItemPlid = targetItemPlid;

		nextLayoutColumns = deleteEmptyColumns(nextLayoutColumns);

		this._getItemChildren(targetItemPlid).then(response => {
			const {children} = response;
			const lastColumnIndex = nextLayoutColumns.length - 1;

			if (nextLayoutColumns[lastColumnIndex].length === 0) {
				nextLayoutColumns = setIn(
					nextLayoutColumns,
					[lastColumnIndex],
					children
				);
			} else {
				nextLayoutColumns = setIn(
					nextLayoutColumns,
					[nextLayoutColumns.length],
					children
				);
			}

			this._newPathItems = children;

			this.layoutColumns = nextLayoutColumns;
		});
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

Layout.STATE = {
	/**
	 * Wether the path is refreshing or not
	 * @default null
	 * @instance
	 * @review
	 * @type {!string}
	 */

	_currentPathItemPlid: Config.string()
		.internal()
		.value(null),

	/**
	 * Item that is being dragged.
	 * @default undefined
	 * @instance
	 * @review
	 * @type {!object}
	 */

	_draggingItem: Config.internal(),

	/**
	 * Index of the dragging item column in layoutColumns array.
	 * @default undefined
	 * @instance
	 * @review
	 * @type {!number}
	 */

	_draggingItemColumnIndex: Config.number().internal(),

	/**
	 * Plid of the dragging item parent
	 * @default undefined
	 * @instance
	 * @review
	 * @type {!string}
	 */

	_draggingItemParentPlid: Config.string().internal(),

	/**
	 * Nearest border of the hovered layout column item when dragging.
	 * @default undefined
	 * @instance
	 * @review
	 * @type {!string}
	 */

	_draggingItemPosition: Config.string().internal(),

	/**
	 * Id of the hovered layout column item when dragging.
	 * @default undefined
	 * @instance
	 * @review
	 * @type {!string}
	 */

	_hoveredLayoutColumnItemPlid: Config.string().internal(),

	/**
	 * Scroll left position stored while dragging elements
	 * @default null
	 * @instance
	 * @memberOf Layout
	 * @private
	 * @review
	 * @type {number}
	 */
	_layoutColumnsScrollLeft: Config.internal().value(null),

	/**
	 * Internal LayoutDragDrop instance
	 * @default null
	 * @instance
	 * @memberOf Layout
	 * @review
	 * @type {object|null}
	 */

	_layoutDragDrop: Config.internal().value(null),

	/**
	 * Stores new items that are shown when path is updated
	 * @default null
	 * @instance
	 * @memberOf Layout
	 * @review
	 * @type {Array}
	 */

	_newPathItems: Config.internal().value(null),

	/**
	 * Id of the timeout to update the path
	 * @default undefined
	 * @instance
	 * @memberOf Layout
	 * @review
	 * @type {number}
	 */

	_updatePathTimeout: Config.number().internal(),

	/**
	 * Breadcrumb Entries
	 * @instance
	 * @memberof Layout
	 * @type {!Array}
	 */

	breadcrumbEntries: Config.arrayOf(
		Config.shapeOf({
			title: Config.string().required(),
			url: Config.string().required()
		})
	).required(),

	/**
	 * URL for get the children of an item
	 * @default undefined
	 * @instance
	 * @review
	 * @type {!string}
	 */

	getItemChildrenURL: Config.string().required(),

	/**
	 * Layout blocks
	 * @instance
	 * @memberof Layout
	 * @type {!Array}
	 */

	layoutColumns: Config.arrayOf(
		Config.arrayOf(
			Config.shapeOf({
				actionURLs: Config.object().required(),
				actions: Config.string().required(),
				active: Config.bool().required(),
				checked: Config.bool().required(),
				hasChild: Config.bool().required(),
				parentable: Config.bool().required(),
				plid: Config.string().required(),
				title: Config.string().required(),
				url: Config.string().required()
			})
		)
	).required(),

	/**
	 * URL for moving a layout column item through its column.
	 * @default undefined
	 * @instance
	 * @review
	 * @type {!string}
	 */

	moveLayoutColumnItemURL: Config.string().required(),

	/**
	 * URL for using icons
	 * @instance
	 * @memberof Layout
	 * @type {!string}
	 */

	pathThemeImages: Config.string().required(),

	/**
	 * Namespace of portlet to prefix parameters names
	 * @instance
	 * @memberof Layout
	 * @type {!string}
	 */

	portletNamespace: Config.string().required(),

	/**
	 * Site navigation menu names, to add layouts by default
	 * @instance
	 * @memberof Layout
	 * @type {!string}
	 */

	siteNavigationMenuNames: Config.string().required()
};

Soy.register(Layout, templates);

export {Layout};
export default Layout;
