import Component from 'metal-component';
import {Config} from 'metal-state';
import navigate from 'frontend-js-web/liferay/util/navigate.es';
import Soy from 'metal-soy';

import './LayoutBreadcrumbs.es';
import './LayoutColumn.es';
import {DRAG_POSITIONS, LayoutDragDrop} from './utils/LayoutDragDrop.es';
import {
	clearFollowingColumns,
	columnIsItemChild,
	deleteEmptyColumns,
	dropIsValid,
	getColumnActiveItem,
	getColumnLastItem,
	getItem,
	getItemColumn,
	getItemColumnIndex,
	itemIsParent,
	removeItem,
	setHomePage
} from './utils/LayoutUtils.es';
import {dropItemInsideColumn, dropItemInsideItem} from './utils/LayoutDropUtils.es';
import {setIn} from '../utils/utils.es';
import templates from './Layout.soy';

const UPDATE_PATH_TIMEOUT = 1000;

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
		const A = new AUI();

		A.use(
			'liferay-search-container',
			'liferay-search-container-select',
			(A) => {
				const plugins = [];

				plugins.push(
					{
						cfg: {
							rowSelector: '.layout-column'
						},
						fn: A.Plugin.SearchContainerSelect
					}
				);

				const searchContainer = new Liferay.SearchContainer(
					{
						contentBox: A.one(this.refs.layout),
						id: this.getInitialConfig().portletNamespace +
							this.getInitialConfig().searchContainerId,
						plugins
					}
				);

				this.searchContainer_ = searchContainer;
			}
		);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	dispose() {
		this._layoutDragDrop.dispose();
	}

	/**
	 * @inheritDoc
	 */
	rendered(firstRendered) {
		requestAnimationFrame(
			() => {
				this.refs.layoutColumns.scrollLeft = this.refs.layoutColumns.scrollWidth;

				if (this._newPathItems) {
					this._addLayoutDragDropTargets(this._newPathItems);
					this._newPathItems = null;
				}
			}
		);

		if (firstRendered) {
			this._initializeLayoutDragDrop();
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

		items.forEach(
			(item) => {
				query = `[data-layout-column-item-plid="${item.plid}"]`;
				element = document.querySelector(query);
				this._layoutDragDrop.addTarget(element);
			}
		);
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

		return fetch(
			this.getItemChildrenURL,
			{
				body: formData,
				credentials: 'include',
				method: 'POST'
			}
		).then(
			(response) => response.json()
		);
	}

	/**
	 * Handle dragLayoutColumnItem event
	 * @param {!object} eventData
	 * @param {!string} eventData.position
	 * @param {!string} eventData.sourceItemPlid
	 * @param {!number} eventData.targetColumnIndex
	 * @param {!string} eventData.targetItemPlid
	 * @private
	 * @review
	 */
	_handleDragLayoutColumnItem(eventData) {
		clearTimeout(this._updatePathTimeout);

		const {
			position,
			sourceItemPlid,
			targetColumnIndex,
			targetItemPlid
		} = eventData;

		if (targetColumnIndex) {
			this._setColumnHoveredData(sourceItemPlid, targetColumnIndex);
		}
		else if (targetItemPlid) {
			this._setItemHoveredData(
				position,
				sourceItemPlid,
				targetItemPlid
			);

			if (
				this._draggingItemPosition === DRAG_POSITIONS.inside &&
				this._currentPathItemPlid !== targetItemPlid
			) {
				this._updatePathTimeout = setTimeout(
					() => {
						this._updatePath(targetItemPlid);
					},
					UPDATE_PATH_TIMEOUT
				);
			}
		}
	}

	/**
	 * @param {!object} eventData
	 * @param {!string} eventData.sourceItemPlid
	 * @param {!string} eventData.targetItemPlid
	 * @private
	 * @review
	 */
	_handleDropLayoutColumnItem(eventData) {
		let layoutColumns = this.layoutColumns.map(
			(layoutColumn) => [...layoutColumn]
		);
		const {sourceItemPlid, targetColumnIndex, targetItemPlid} = eventData;

		const itemDropIsValid = dropIsValid(
			layoutColumns,
			sourceItemPlid,
			targetItemPlid,
			targetColumnIndex
		);

		if (itemDropIsValid) {
			let parentPlid = null;
			let priority = null;
			let targetColumn = null;
			let targetItem = null;

			layoutColumns = removeItem(sourceItemPlid, layoutColumns);

			if (targetColumnIndex) {
				const dropData = dropItemInsideColumn(
					layoutColumns,
					this._draggingItem,
					targetColumnIndex
				);

				layoutColumns = dropData.layoutColumns;
				parentPlid = dropData.newParentPlid;
				priority = dropData.priority;
			}
			else if (targetItemPlid) {
				targetItem = getItem(layoutColumns, targetItemPlid);

				if (this._draggingItemPosition === DRAG_POSITIONS.inside) {
					const pathUpdated = !!this._currentPathItemPlid;

					const dropData = dropItemInsideItem(
						layoutColumns,
						this._draggingItem,
						pathUpdated,
						targetItemPlid
					);

					layoutColumns = dropData.layoutColumns;
					parentPlid = dropData.newParentPlid;
					priority = dropData.priority;
				}
				else {
					priority = targetColumn.indexOf(targetItem);

					if (this._draggingItemPosition === DRAG_POSITIONS.bottom) {
						priority++;
					}

					targetColumn.splice(priority, 0, this._draggingItem);

					parentPlid = getColumnActiveItem(
						layoutColumns,
						targetColumnIndex - 1
					).plid;
				}
			}

			if (sourceColumn.length === 0 && !this._currentPathItemPlid) {
				layoutColumns = this._handleEmptyColumn(
					layoutColumns,
					targetColumnIndex
				);

				layoutColumns = deleteEmptyColumns(layoutColumns);
			}

			if (
				this._draggingItem.active &&
				(this._draggingItemColumnIndex !== targetColumnIndex)
			) {
				this._draggingItem.active = false;

				layoutColumns = clearFollowingColumns(
					layoutColumns,
					this._draggingItemColumnIndex
				);

				layoutColumns = deleteEmptyColumns(layoutColumns);
			}

			if (this._draggingItem.homePage) {
				layoutColumns = setHomePage(
					layoutColumns,
					this._draggingItem.plid
				);
			}

			this._moveLayoutColumnItemOnServer(
				parentPlid,
				this._draggingItem.plid,
				priority
			)
				.then(
					response => {
						let nextPromise = response;

						if (this._draggingItemParentPlid !== '0') {
							nextPromise = this._getItemChildren(this._draggingItemParentPlid).then(
								response => {
									if (response.children && response.children.length === 0) {
										layoutColumns = this._removeHasChildArrow(
											layoutColumns,
											this._draggingItemParentPlid
										);
									}
								}
							);
						}

						return nextPromise;
					}
				).then(
					() => {
						this.layoutColumns = layoutColumns;

						clearTimeout(this._updatePathTimeout);

						requestAnimationFrame(
							() => {
								this._initializeLayoutDragDrop();
							}
						);
					}
				);
		}

		this._resetHoveredData();
		this._currentPathItemPlid = null;
		this._draggingItem = null;
		this._draggingItemColumnIndex = null;
	}

	/**
	 * Method executed when a column is left empty after dragging.
	 * Updates target item's status, removes empty columns if any
	 * and returns a new columns array.
	 *
	 * @param {object} layoutColumns
	 * @param {number} targetColumnIndex
	 * @private
	 * @return {object}
	 * @review
	 */
	_handleEmptyColumn(
		layoutColumns,
		targetColumnIndex
	) {
		let nextLayoutColumns = layoutColumns;

		if (
			this._draggingItem.active &&
			(this._draggingItemColumnIndex !== targetColumnIndex)
		) {
			this._draggingItem.active = false;

			nextLayoutColumns = clearFollowingColumns(
				nextLayoutColumns,
				this._draggingItemColumnIndex
			);
		}

		const previousColumn = nextLayoutColumns[this._draggingItemColumnIndex - 1];

		const activeItem = getColumnActiveItem(
			nextLayoutColumns,
			this._draggingItemColumnIndex - 1
		);

		return setIn(
			nextLayoutColumns,
			[
				this._draggingItemColumnIndex - 1,
				previousColumn.indexOf(activeItem),
				'hasChild'
			],
			false
		);
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
		const targetIsA = event.target.tagName === 'A';
		const targetIsButton = event.target.tagName === 'BUTTON';

		if (!targetIsA && !targetIsButton) {
			const itemUrl = event.delegateTarget.dataset.layoutColumnItemUrl;

			if (itemUrl) {
				navigate(itemUrl);
			}
			else {
				const itemPlid = event.delegateTarget.dataset.layoutColumnItemPlid;

				const item = this._getLayoutColumnItemByPlid(this.layoutColumns, itemPlid);

				navigate(item.url);
			}
		}
	}

	/**
	 * @private
	 * @review
	 */

	_handleLeaveLayoutColumnItem() {
		this._resetHoveredData();
	}

	/**
	 * @param {!object} eventData
	 * @param {!string} eventData.sourceItemPlid
	 * @private
	 * @review
	 */
	_handleStartMovingLayoutColumnItem(eventData) {
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

		return fetch(
			this.moveLayoutColumnItemURL,
			{
				body: formData,
				credentials: 'include',
				method: 'POST'
			}
		).catch(
			() => {
				this._resetHoveredData();
			}
		);
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

		const column = getItemColumn(
			layoutColumns,
			itemPlid
		);
		const item = getItem(
			nextLayoutColumns,
			itemPlid
		);

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
			this.layoutColumns,
			targetColumnIndex,
			draggingItemPlid
		);
		const targetColumnLastItem = getColumnLastItem(
			this.layoutColumns,
			targetColumnIndex
		);
		const targetEqualsSource = targetColumnLastItem &&
			(draggingItemPlid === targetColumnLastItem.plid);

		if (
			targetColumnLastItem &&
			!targetColumnIsChild &&
			!targetEqualsSource
		) {
			this._draggingItemPosition = DRAG_POSITIONS.bottom;
			this._hoveredLayoutColumnItemPlid = targetColumnLastItem.plid;
		}
	}

	/**
	 * Set hovered data with an item
	 * @param {string} position Position where item is being dragged
	 * @param {string} sourceItemPlid
	 * @param {string} targetItemPlid
	 * @private
	 * @review
	 * @see DRAG_POSITIONS
	 */
	_setItemHoveredData(position, sourceItemPlid, targetItemPlid) {
		const targetColumnIndex = getItemColumnIndex(
			this.layoutColumns,
			targetItemPlid
		);

		const targetIsChild = columnIsItemChild(
			this.layoutColumns,
			targetColumnIndex,
			sourceItemPlid
		);

		const targetEqualsSource = (sourceItemPlid === targetItemPlid);

		const draggingInsideParent = (
			(position === DRAG_POSITIONS.inside) &&
			itemIsParent(this.layoutColumns, sourceItemPlid, targetItemPlid)
		);

		if (!targetEqualsSource && !targetIsChild && !draggingInsideParent) {
			this._draggingItemPosition = position;
			this._hoveredLayoutColumnItemPlid = targetItemPlid;
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
			[
				columnIndex,
				column.indexOf(item),
				'checked'
			],
			checked
		);
	}

	/**
	 * @param {string} targetColumnIndex
	 * @param {string} targetItemPlid
	 * @private
	 * @review
	 */
	_updatePath(targetColumnIndex, targetItemPlid) {
		let nextLayoutColumns = this.layoutColumns;

		nextLayoutColumns = clearFollowingColumns(
			nextLayoutColumns,
			targetColumnIndex
		);

		const targetColumn = nextLayoutColumns[targetColumnIndex];

		const targetItem = getItem(
			nextLayoutColumns,
			targetItemPlid
		);

		const activeItem = getColumnActiveItem(
			nextLayoutColumns,
			targetColumnIndex
		);

		if (activeItem && (activeItem !== targetItem)) {
			nextLayoutColumns = setIn(
				nextLayoutColumns,
				[
					targetColumnIndex,
					targetColumn.indexOf(activeItem),
					'active'
				],
				false
			);
		}

		nextLayoutColumns = setIn(
			nextLayoutColumns,
			[
				targetColumnIndex,
				targetColumn.indexOf(targetItem),
				'active'
			],
			true
		);

		this._currentPathItemPlid = targetItemPlid;

		nextLayoutColumns = deleteEmptyColumns(nextLayoutColumns);

		this._getItemChildren(targetItemPlid).
			then(
				(response) => {
					const {children} = response;
					const lastColumnIndex = nextLayoutColumns.length - 1;

					if (nextLayoutColumns[lastColumnIndex].length === 0) {
						nextLayoutColumns = setIn(
							nextLayoutColumns,
							[lastColumnIndex],
							children
						);
					}
					else {
						nextLayoutColumns = setIn(
							nextLayoutColumns,
							[nextLayoutColumns.length],
							children
						);
					}

					this._newPathItems = children;

					this.layoutColumns = nextLayoutColumns;
				}
			);
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
	 * Breadcrumb Entries
	 * @instance
	 * @memberof Layout
	 * @type {!Array}
	 */

	breadcrumbEntries: Config.arrayOf(
		Config.shapeOf(
			{
				title: Config.string().required(),
				url: Config.string().required()
			}
		)
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
			Config.shapeOf(
				{
					actionURLs: Config.object().required(),
					active: Config.bool().required(),
					checked: Config.bool().required(),
					hasChild: Config.bool().required(),
					homePage: Config.bool().required(),
					homePageTitle: Config.string().required(),
					plid: Config.string().required(),
					title: Config.string().required(),
					url: Config.string().required()
				}
			)
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

	siteNavigationMenuNames: Config.string().required(),

	/**
	 * Wether the path is refreshing or not
	 * @default null
	 * @instance
	 * @review
	 * @type {!string}
	 */

	_currentPathItemPlid: Config.string().internal().
		value(null),

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

	_updatePathTimeout: Config.number().internal()
};

Soy.register(Layout, templates);

export {Layout};
export default Layout;