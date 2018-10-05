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
		const sourceColumn = this._getParentColumnByPlid(this.layoutColumns, eventData.sourceItemPlid);
		const sourceItem = this._getLayoutColumnItemByPlid(this.layoutColumns, eventData.sourceItemPlid);

		const targetColumn = this._getParentColumnByPlid(this.layoutColumns, eventData.targetItemPlid);
		const targetItem = this._getLayoutColumnItemByPlid(this.layoutColumns, eventData.targetItemPlid);

		if (sourceItem != targetItem &&
			this.layoutColumns.indexOf(targetColumn) != 0 &&
			!(sourceItem.active && sourceColumn != targetColumn) &&
			!(sourceItem.active && eventData.position === DRAG_POSITIONS.inside)) {
			this._draggingItemPosition = eventData.position;
			this._hoveredLayoutColumnItemPlid = eventData.targetItemPlid;
		}
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
		const layoutColumns = this.layoutColumns.map(
			layoutColumn => [...layoutColumn]
		);

		const sourceItemPlid = eventData.sourceItemPlid;
		const targetItemPlid = eventData.targetItemPlid;

		const sourceItem = this._getLayoutColumnItemByPlid(layoutColumns, sourceItemPlid);
		const targetItem = this._getLayoutColumnItemByPlid(layoutColumns, targetItemPlid);

		if (this._draggingItemPosition) {

			const sourceColumn = this._getParentColumnByPlid(layoutColumns, sourceItemPlid);
			const targetColumn = this._getParentColumnByPlid(layoutColumns, targetItemPlid);

			sourceColumn.splice(sourceColumn.indexOf(sourceItem), 1);

			if (this._draggingItemPosition === DRAG_POSITIONS.inside) {
				targetItem.hasChild = true;
				this._moveLayoutColumnItem(targetItemPlid, sourceItemPlid)
					.then(
						() => {
							this.layoutColumns = layoutColumns;
							this._initializeLayoutDragDrop();
						}
					);
			}
			else {
				let priority = targetColumn.indexOf(targetItem);

				if (this._draggingItemPosition === DRAG_POSITIONS.bottom) {
					priority++;
				}

				targetColumn.splice(priority, 0, sourceItem);

				const targetColumnIndex = layoutColumns.indexOf(targetColumn);

				const parentPlid = this._getLayoutColumnActiveItem(layoutColumns[targetColumnIndex - 1]);

				this._moveLayoutColumnItem(parentPlid, sourceItemPlid, priority)
					.then(
						() => {
							this.layoutColumns = layoutColumns;
							this._initializeLayoutDragDrop();
						}
					);
			}
		}

		this._resetHoveredData();
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
	}

	/**
	 * Sends the movement of an item to the server.
	 * @param {string} parentPlid
	 * @param {string} plid
	 * @param {string} priority
	 * @private
	 * @review
	 */

	_moveLayoutColumnItem(parentPlid, plid, priority) {
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
	 * Resets hovered information to null
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