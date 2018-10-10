import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './LayoutBreadcrumbs.es';
import './LayoutColumn.es';
import {DRAG_POSITIONS, LayoutDragDrop} from './utils/LayoutDragDrop.es';
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

		this._deleteEmptyColumns(this.layoutColumns);

		this.layoutColumns = this.layoutColumns.map(
			layoutColumn => [...layoutColumn]
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
			}
		);

		if (firstRendered) {
			this._initializeLayoutDragDrop();
		}
	}

	/**
	 * Removes extra empty columns when there are more than three.
	 * @param {Array} layoutColumns
	 * @private
	 * @review
	 */

	_deleteEmptyColumns(layoutColumns) {
		for (let i = 3; (i < layoutColumns.length) && (layoutColumns[i].length === 0); i++) {
			layoutColumns.splice(i, 1);
		}
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

	_getLayoutColumnActiveItem(layoutColumn) {
		let activeItemPlid = null;

		for (let i = 0; i < layoutColumn.length; i++) {
			if (layoutColumn[i].active) {
				activeItemPlid = layoutColumn[i].plid;
			}
		}

		return activeItemPlid;
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

		for (let i = 0; i < layoutColumns.length; i++) {
			for (let j = 0; j < layoutColumns[i].length; j++) {
				if (layoutColumns[i][j].plid === plid) {
					item = layoutColumns[i][j];
				}
			}
		}

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

		for (let i = 0; i < layoutColumns.length; i++) {
			for (let j = 0; j < layoutColumns[i].length; j++) {
				if (layoutColumns[i][j].plid === plid) {
					column = layoutColumns[i];
				}
			}
		}

		return column;
	}

	/**
	 * Handle dragLayoutColumnItem event
	 * @param {!object} eventData
	 * @param {!string} eventData.position
	 * @param {!string} eventData.targetItemPlid
	 * @private
	 * @review
	 */

	_handleDragLayoutColumnItem(eventData) {
		const targetItemPlid = eventData.targetItemPlid;

		const targetItem = this._getLayoutColumnItemByPlid(this.layoutColumns, targetItemPlid);

		if (targetItem) {
			const targetColumn = this._getParentColumnByPlid(this.layoutColumns, targetItemPlid);
		const targetColumnIndex = this.layoutColumns.indexOf(targetColumn);

		const targetInFirstColumn = this.layoutColumns.indexOf(targetColumn) === 0;
		const targetIsSource = this._draggingItem === targetItem;

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
				this._currentPathItemPlid != targetItemPlid
			) {
				this._updatePath(targetColumnIndex, targetItemPlid);
			}
		}
	}

	/**
	 * Method executed when a column is left empty after dragging.
	 * Updates target item's status and removes empty columns if any.
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
		if (this._draggingItem.active && (this._draggingItemColumnIndex != targetColumnIndex)) {
			this._draggingItem.active = false;
			this._removeFollowingColumns(layoutColumns, this._draggingItemColumnIndex);
		}

		const previousColumn = layoutColumns[this._draggingItemColumnIndex - 1];

		const activeItemPlid = this._getLayoutColumnActiveItem(previousColumn);

		const activeItem = this._getLayoutColumnItemByPlid(
			layoutColumns,
			activeItemPlid
		);

		activeItem.hasChild = false;
	}

	/**
	 * Handle layout column item check event
	 * @param {!object} eventData
	 * @param {string} eventData.delegateTarget.value
	 * @private
	 * @review
	 */

	_handleLayoutColumnItemCheck(eventData) {
		this._setLayoutColumnItemChecked(eventData.delegateTarget.value);
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
		if (this._draggingItemPosition) {
			const layoutColumns = this.layoutColumns.map(
				layoutColumn => [...layoutColumn]
			);

			const sourceItemPlid = eventData.sourceItemPlid;
			const targetItemPlid = eventData.targetItemPlid;

			const targetItem = this._getLayoutColumnItemByPlid(
				layoutColumns,
				targetItemPlid
			);

			const sourceColumn = layoutColumns[this._draggingItemColumnIndex];

			const targetColumn = this._getParentColumnByPlid(
				layoutColumns,
				targetItemPlid
			);

			const targetColumnIndex = layoutColumns.indexOf(targetColumn);

			sourceColumn.splice(sourceColumn.indexOf(this._draggingItem), 1);

			let parentPlid = null;
			let priority = null;

			if (this._draggingItemPosition === DRAG_POSITIONS.inside) {
				this._moveItemInside(
					layoutColumns,
					targetItem,
					targetColumnIndex
				);

				parentPlid = targetItemPlid;
			}
			else {
				priority = targetColumn.indexOf(targetItem);

				if (this._draggingItemPosition === DRAG_POSITIONS.bottom) {
					priority++;
				}

				targetColumn.splice(priority, 0, this._draggingItem);

				parentPlid = this._getLayoutColumnActiveItem(
					layoutColumns[targetColumnIndex - 1]
				);
			}

			if (sourceColumn.length === 0 && !this._currentPathItemPlid) {
				this._handleEmptyColumn(
					layoutColumns,
					targetColumnIndex
				);

				this._deleteEmptyColumns(layoutColumns);
			}

			if (this._draggingItem.active && (this._draggingItemColumnIndex != targetColumnIndex)) {
				this._draggingItem.active = false;

				this._removeFollowingColumns(layoutColumns, this._draggingItemColumnIndex);

				this._deleteEmptyColumns(layoutColumns);
			}

			this._moveLayoutColumnItemOnServer(
				parentPlid,
				sourceItemPlid,
				priority
			)
				.then(
					() => {
						this.layoutColumns = layoutColumns;

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
		const sourceItemColumn = this._getParentColumnByPlid(this.layoutColumns, eventData.sourceItemPlid);
		const sourceItemColumnIndex = this.layoutColumns.indexOf(sourceItemColumn);

		this._draggingItem = this._getLayoutColumnItemByPlid(this.layoutColumns, eventData.sourceItemPlid);
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
	 * @param {!Array} layoutColumns
	 * @param {!Array} targetItem
	 * @param {!number} targetColumnIndex
	 * @private
	 * @review
	 */

	_moveItemInside(layoutColumns, targetItem, targetColumnIndex) {
		if (targetItem.active) {
			let nextColumn = null;

			if (layoutColumns[targetColumnIndex + 1]) {
				nextColumn = layoutColumns[targetColumnIndex + 1];
			}
			else {
				nextColumn = [];
			}

			nextColumn.splice(nextColumn.length, 0, this._draggingItem);
		}

		if (this._draggingItem.active && !this._currentPathItemPlid) {
			this._removeFollowingColumns(layoutColumns, this._draggingItemColumnIndex);

			this._deleteEmptyColumns(layoutColumns);
		}

		targetItem.hasChild = true;
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

		if (priority != null) {
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
	 * @param {!Array} layoutColumns
	 * @param {!number} startColumnIndex
	 * @private
	 * @review
	 */

	_removeFollowingColumns(layoutColumns, startColumnIndex) {
		for (let i = startColumnIndex + 1; i < layoutColumns.length; i++) {
			layoutColumns[i] = [];
		}
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
	 * @param {string} plid
	 * @private
	 * @return {object|null}
	 * @review
	 */

	_setLayoutColumnItemChecked(plid) {
		for (let i = 0; i < this.layoutColumns.length; i++) {
			for (let j = 0; j < this.layoutColumns[i].length; j++) {
				if (this.layoutColumns[i][j].plid === plid) {
					this.layoutColumns[i][j].checked = true;
				}
			}
		}
	}

	/**
	 * @param {string} targetColumnIndex
	 * @param {string} targetItemPlid
	 * @private
	 * @review
	 */

	_updatePath(targetColumnIndex, targetItemPlid) {
		this._removeFollowingColumns(this.layoutColumns, targetColumnIndex);

		const targetColumn = this.layoutColumns[targetColumnIndex];
		const targetItem = this._getLayoutColumnItemByPlid(this.layoutColumns, targetItemPlid);

		const activeItemPlid = this._getLayoutColumnActiveItem(targetColumn);

		const activeItem = this._getLayoutColumnItemByPlid(this.layoutColumns, activeItemPlid);

		if (activeItem && (activeItem != targetItem)) {
			activeItem.active = false;
		}

		targetItem.active = true;

		this._currentPathItemPlid = targetItemPlid;

		this._deleteEmptyColumns(this.layoutColumns);

		this._getItemChildren(targetItemPlid)
			.then(
				response => {
					const children = response.children;
					const lastElementIndex = this.layoutColumns.length - 1;

					if (this.layoutColumns[lastElementIndex].length === 0) {
						this.layoutColumns[lastElementIndex] = children;
					}
					else {
						this.layoutColumns.push(children);
					}

					this.layoutColumns = this.layoutColumns.map(
						layoutColumn => [...layoutColumn]
					);
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
	 * @default undefined
	 * @instance
	 * @review
	 * @type {!string}
	 */

	_currentPathItemPlid: Config.string().internal(),

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

	_layoutDragDrop: Config.internal().value(null)
};

Soy.register(Layout, templates);

export {Layout};
export default Layout;