import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './LayoutBreadcrumbs.es';
import './LayoutColumn.es';
import {DRAG_POSITIONS, LayoutDragDrop} from './utils/LayoutDragDrop.es';
import {setIn} from '../utils/utils.es';
import templates from './Layout.soy';

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
			A => {
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
						id: this.getInitialConfig().portletNamespace + this.getInitialConfig().searchContainerId,
						plugins: plugins
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
			item => {
				query = `[data-layout-column-item-plid="${item.plid}"]`;
				element = document.querySelector(query);
				this._layoutDragDrop.addTarget(element);
			}
		);
	}

	/**
	 * Removes extra empty columns when there are more than three and returns
	 * a new Array with the removed columns.
	 * @param {Array} layoutColumns
	 * @private
	 * @return {Array}
	 * @review
	 */

	_deleteEmptyColumns(layoutColumns) {
		const columns = [...layoutColumns];

		for (let i = 3; (i < columns.length) && (columns[i].length === 0); i++) {
			columns.splice(i, 1);
		}

		return columns;
	}

	/**
	 * @private
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
			response => response.json()
		);
	}

	/**
	 * @param {Array} layoutColumn
	 * @private
	 * @return {string}
	 * @review
	 */

	_getLayoutColumnActiveItemPlid(layoutColumn) {
		const layoutColumnItem = layoutColumn.find(
			layoutColumnItem => layoutColumnItem.active
		);

		return (
			layoutColumnItem ?
				layoutColumnItem.plid :
				null
		);
	}

	/**
	 * @param {Array} layoutColumns
	 * @param {string} plid
	 * @private
	 * @return {object|null}
	 * @review
	 */

	_getLayoutColumnItemByPlid(layoutColumns, plid) {
		let item = null;

		layoutColumns.forEach(
			layoutColumn => {
				item = item || layoutColumn.find(
					_item => _item.plid === plid
				);
			}
		);

		return item;
	}

	/**
	 * @param {Array} layoutColumns
	 * @param {string} plid
	 * @private
	 * @return {object|null}
	 * @review
	 */

	_getParentColumnByPlid(layoutColumns, plid) {
		let column = null;

		layoutColumns.forEach(
			layoutColumn => {
				const item = layoutColumn.find(
					_item => _item.plid === plid
				);

				if (item) {
					column = layoutColumn;
				}
			}
		);

		return column;
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

		if (eventData.targetColumnIndex) {
			const sourceColumn = this._getParentColumnByPlid(
				this.layoutColumns,
				eventData.sourceItemPlid
			);
			const sourceItem = this._getLayoutColumnItemByPlid(
				this.layoutColumns,
				eventData.sourceItemPlid
			);

			const targetColumn = this.layoutColumns[eventData.targetColumnIndex];
			const targetColumnLastItem = targetColumn[targetColumn.length - 1];

			const targetColumnIsChild = this.layoutColumns.indexOf(targetColumn) >
				this.layoutColumns.indexOf(sourceColumn);

			if (targetColumnLastItem &&
				!(sourceItem.active && targetColumnIsChild) &&
				!(sourceItem === targetColumnLastItem)
			) {
				this._draggingItemPosition = DRAG_POSITIONS.bottom;
				this._hoveredLayoutColumnItemPlid = targetColumnLastItem.plid;
			}

		}
		else {
			const targetItemPlid = eventData.targetItemPlid;

			const targetItem = this._getLayoutColumnItemByPlid(
				this.layoutColumns,
				targetItemPlid
			);

			const targetColumn = this._getParentColumnByPlid(
				this.layoutColumns,
				targetItemPlid
			);

			if (targetItem && targetColumn) {
				const targetColumnIndex = this.layoutColumns.indexOf(targetColumn);
				const targetInFirstColumn = (this.layoutColumns.indexOf(targetColumn) === 0);
				const targetIsSource = (this._draggingItem === targetItem);

				const targetIsChild = (
					this._draggingItem.active &&
					(this._draggingItemColumnIndex < targetColumnIndex)
				);

				const targetIsParent = (
					targetItem.active &&
					(eventData.position === DRAG_POSITIONS.inside) &&
						(targetColumnIndex === (this._draggingItemColumnIndex - 1)) &&
						!this._currentPathItemPlid
				);

				if (
					!targetInFirstColumn &&
					!targetIsSource &&
					!targetIsChild &&
					!targetIsParent
				) {
					this._draggingItemPosition = eventData.position;
					this._hoveredLayoutColumnItemPlid = targetItemPlid;
				}

				if (
					this._draggingItemPosition === DRAG_POSITIONS.inside &&
					this._currentPathItemPlid !== targetItemPlid
				) {
					this._updatePathTimeout = setTimeout(
						() => {
							this._updatePath(targetColumnIndex, targetItemPlid);
						},
						1000
					);
				}
			}
		}
	}

	/**
	 * Method executed when a column is left empty after dragging.
	 * Updates target item's status, removes empty columns if any
	 * and returns a new columns array.
	 *
	 * @param {!Array} layoutColumns
	 * @param {!number} targetColumnIndex
	 * @private
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

			nextLayoutColumns = this._clearFollowingColumns(
				nextLayoutColumns,
				this._draggingItemColumnIndex
			);
		}

		const previousColumn = nextLayoutColumns[this._draggingItemColumnIndex - 1];

		const activeItemPlid = this._getLayoutColumnActiveItemPlid(
			previousColumn
		);

		const activeItem = this._getLayoutColumnItemByPlid(
			nextLayoutColumns,
			activeItemPlid
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
			const itemPlid = event.delegateTarget.dataset.layoutColumnItemPlid;

			const item = this._getLayoutColumnItemByPlid(this.layoutColumns, itemPlid);

			window.location.href = item.url;
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
	 * @param {!string} eventData.targetItemPlid
	 * @private
	 * @review
	 */

	_handleMoveLayoutColumnItem(eventData) {
		const targetExists = eventData.targetItemPlid ||
			eventData.targetColumnIndex;

		const targetIsSource = eventData.sourceItemPlid === eventData.targetItemPlid;

		if (this._draggingItem && targetExists && !targetIsSource) {
			let layoutColumns = this.layoutColumns.map(
				layoutColumn => [...layoutColumn]
			);

			const sourceColumn = layoutColumns[this._draggingItemColumnIndex];

			let parentPlid = null;
			let priority = null;
			let targetColumn = null;
			let targetColumnIndex = null;
			let targetItem = null;

			let targetItemPlid = eventData.targetItemPlid;

			layoutColumns = this._removeDraggingItem(
				layoutColumns,
				eventData.targetItemPlid
			);

			if (eventData.targetColumnIndex) {
				targetColumnIndex = eventData.targetColumnIndex;

				targetColumn = layoutColumns[targetColumnIndex];

				parentPlid = this._getLayoutColumnActiveItemPlid(
					layoutColumns[targetColumnIndex - 1]
				);

				priority = targetColumn.length;

				targetColumn.splice(priority, 0, this._draggingItem);
			}
			else {
				targetColumn = this._getParentColumnByPlid(
					layoutColumns,
					targetItemPlid
				);

				targetColumnIndex = layoutColumns.indexOf(targetColumn);

				targetItem = this._getLayoutColumnItemByPlid(
					layoutColumns,
					targetItemPlid
				);

				if (this._draggingItemPosition === DRAG_POSITIONS.inside) {
					layoutColumns = this._moveItemInside(
						layoutColumns,
						this._draggingItem,
						targetItem
					);

					parentPlid = targetItemPlid;

					if (this._currentPathItemPlid) {
						const nextColumn = layoutColumns[targetColumnIndex + 1];

						priority = nextColumn.indexOf(this._draggingItem);
					}
				}
				else {
					priority = targetColumn.indexOf(targetItem);

					if (this._draggingItemPosition === DRAG_POSITIONS.bottom) {
						priority++;
					}

					targetColumn.splice(priority, 0, this._draggingItem);

					parentPlid = this._getLayoutColumnActiveItemPlid(
						layoutColumns[targetColumnIndex - 1]
					);
				}
			}

			if (sourceColumn.length === 0 && !this._currentPathItemPlid) {
				layoutColumns = this._handleEmptyColumn(
					layoutColumns,
					targetColumnIndex
				);

				layoutColumns = this._deleteEmptyColumns(layoutColumns);
			}

			if (
				this._draggingItem.active &&
				(this._draggingItemColumnIndex !== targetColumnIndex)
			) {
				this._draggingItem.active = false;

				layoutColumns = this._clearFollowingColumns(
					layoutColumns,
					this._draggingItemColumnIndex
				);

				layoutColumns = this._deleteEmptyColumns(layoutColumns);
			}

			if (this._draggingItem.homePage) {
				layoutColumns = this._setHomePage(
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
	 * @param {!object} eventData
	 * @param {!string} eventData.sourceItemPlid
	 * @private
	 * @review
	 */

	_handleStartMovingLayoutColumnItem(eventData) {
		const sourceItemColumn = this._getParentColumnByPlid(
			this.layoutColumns,
			eventData.sourceItemPlid
		);

		const sourceItemColumnIndex = this.layoutColumns.indexOf(
			sourceItemColumn
		);

		this._draggingItem = this._getLayoutColumnItemByPlid(
			this.layoutColumns,
			eventData.sourceItemPlid
		);

		this._draggingItemColumnIndex = sourceItemColumnIndex;
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
			'moveLayoutColumnItem',
			this._handleMoveLayoutColumnItem.bind(this)
		);

		this._layoutDragDrop.on(
			'startMovingLayoutColumnItem',
			this._handleStartMovingLayoutColumnItem.bind(this)
		);
	}

	/**
	 * Moves sourceItem inside targetItem and refresh targetItem children
	 * @param {!Array} layoutColumns
	 * @param {!Array} sourceItem
	 * @param {!Array} targetItem
	 * @param {!number} targetColumnIndex
	 * @private
	 * @review
	 */

	_moveItemInside(layoutColumns, sourceItem, targetItem) {
		let nextLayoutColumns = layoutColumns;

		const targetColumn = this._getParentColumnByPlid(
			nextLayoutColumns,
			targetItem.plid
		);

		const targetColumnIndex = nextLayoutColumns.indexOf(targetColumn);

		if (targetItem.active) {
			const nextColumn = nextLayoutColumns[targetColumnIndex + 1];

			if (nextColumn) {
				nextLayoutColumns = setIn(
					nextLayoutColumns,
					[
						targetColumnIndex + 1,
						nextColumn.length
					],
					sourceItem
				);
			}
			else {
				nextLayoutColumns = setIn(
					nextLayoutColumns,
					[targetColumnIndex + 1],
					[]
				);

				nextLayoutColumns = setIn(
					nextLayoutColumns,
					[targetColumnIndex + 1, 0],
					sourceItem
				);
			}
		}

		if (sourceItem.active && !this._currentPathItemPlid) {
			nextLayoutColumns = this._clearFollowingColumns(
				nextLayoutColumns,
				this._draggingItemColumnIndex
			);

			nextLayoutColumns = this._deleteEmptyColumns(nextLayoutColumns);
		}

		return setIn(
			nextLayoutColumns,
			[targetColumnIndex, targetColumn.indexOf(targetItem), 'hasChild'],
			true
		);
	}

	/**
	 * Sends the movement of an item to the server.
	 * @param {string} parentPlid
	 * @param {string} plid
	 * @param {string} priority
	 * @private
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
	 * Removes following columns starting at position indicated
	 * by startColumnIndex
	 * @param {!Array} layoutColumns
	 * @param {!number} startColumnIndex
	 * @private
	 * @return {Array}
	 * @review
	 */

	_clearFollowingColumns(layoutColumns, startColumnIndex) {
		const columns = [...layoutColumns];

		for (let i = startColumnIndex + 1; i < columns.length; i++) {
			columns[i] = [];
		}

		return columns;
	}

	/**
	 * Remove dragging item if necessary
	 * @param {object} layoutColumns
	 * @param {string} targetItemPlid
	 * @private
	 * @review
	 */

	_removeDraggingItem(layoutColumns, targetItemPlid) {
		const nextLayoutColumns = layoutColumns;

		const sourceColumn = nextLayoutColumns[this._draggingItemColumnIndex];
		const sourceColumnIndex = nextLayoutColumns.indexOf(sourceColumn);

		const targetColumn = this._getParentColumnByPlid(
			nextLayoutColumns,
			targetItemPlid
		);
		const targetColumnIndex = nextLayoutColumns.indexOf(targetColumn);

		const pathUpdated = (this._currentPathItemPlid !== null);
		const sameColumn = (sourceColumn === targetColumn);
		const targetBelongsPreviousColumn = (this._draggingItemPosition === DRAG_POSITIONS.inside) &&
			(targetColumnIndex === sourceColumnIndex - 1);

		if (sameColumn || !(pathUpdated && targetBelongsPreviousColumn)) {
			const draggingItemPlid = this._draggingItem.plid;
			
			const draggingItem = this._getLayoutColumnItemByPlid(
				nextLayoutColumns,
				draggingItemPlid
			);

			sourceColumn.splice(sourceColumn.indexOf(draggingItem), 1);
		}

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

	/** Set an item active property to true
	 * @param {!string} plid
	 * @param {!boolean} checked
	 * @private
	 * @return {object|null}
	 * @review
	 */

	_setLayoutColumnItemChecked(plid, checked) {
		const column = this._getParentColumnByPlid(this.layoutColumns, plid);
		const columnIndex = this.layoutColumns.indexOf(column);
		const item = this._getLayoutColumnItemByPlid(this.layoutColumns, plid);

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

	/** Set the first page as Home page
	 * @param {!Array} layoutColumns
	 * @private
	 * @return {object|null}
	 * @review
	 */

	_setHomePage(layoutColumns, currentHomeItemPlid) {
		let nextLayoutColumns = layoutColumns;

		const currentHomeItem = this._getLayoutColumnItemByPlid(
			nextLayoutColumns,
			currentHomeItemPlid
		);
		const currentHomeItemColumn = this._getParentColumnByPlid(
			nextLayoutColumns,
			currentHomeItemPlid
		);
		const currentHomeItemColumnIndex = nextLayoutColumns.indexOf(
			currentHomeItemColumn
		);

		nextLayoutColumns = setIn(
			nextLayoutColumns,
			[
				currentHomeItemColumnIndex,
				currentHomeItemColumn.indexOf(currentHomeItem),
				'homePage'
			],
			false
		);

		nextLayoutColumns = setIn(
			nextLayoutColumns,
			[
				currentHomeItemColumnIndex,
				0,
				'homePage'
			],
			true
		);

		return nextLayoutColumns;
	}

	/**
	 * @param {string} targetColumnIndex
	 * @param {string} targetItemPlid
	 * @private
	 * @review
	 */

	_updatePath(targetColumnIndex, targetItemPlid) {
		let nextLayoutColumns = this.layoutColumns;

		nextLayoutColumns = this._clearFollowingColumns(
			nextLayoutColumns,
			targetColumnIndex
		);

		const targetColumn = nextLayoutColumns[targetColumnIndex];

		const targetItem = this._getLayoutColumnItemByPlid(
			nextLayoutColumns,
			targetItemPlid
		);

		const activeItemPlid = this._getLayoutColumnActiveItemPlid(
			targetColumn
		);

		const activeItem = this._getLayoutColumnItemByPlid(
			nextLayoutColumns,
			activeItemPlid
		);

		if (activeItem && (activeItem !== targetItem)) {
			nextLayoutColumns = setIn(
				nextLayoutColumns,
				[targetColumnIndex, targetColumn.indexOf(activeItem), 'active'],
				false
			);
		}

		nextLayoutColumns = setIn(
			nextLayoutColumns,
			[targetColumnIndex, targetColumn.indexOf(targetItem), 'active'],
			true
		);

		this._currentPathItemPlid = targetItemPlid;

		nextLayoutColumns = this._deleteEmptyColumns(nextLayoutColumns);

		this._getItemChildren(targetItemPlid)
			.then(
				response => {
					const children = response.children;
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

	_currentPathItemPlid: Config.string().internal().value(null),

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