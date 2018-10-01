import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './LayoutBreadcrumbs.es';
import './LayoutColumn.es';
import {DRAG_BORDERS, LayoutDragDrop} from './utils/LayoutDragDrop.es';
import templates from './Layout.soy';

/**
 * Component that allows to show layouts tree in form of three dependent
 * columns. It integrates three <LayoutColumn /> components for N-th, N-th + 2
 * and N-th + 3 levels of layouts tree.
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
	 * @param {Array} layoutColumns
	 * @param {string} plid
	 * @private
	 * @review
	 */

	_getLayoutColumnItemByPlid(layoutColumns, plid) {
		let item;

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
	 * @review
	 */

	_getParentColumnByPlid(layoutColumns, plid) {
		let column;

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
	 * @inheritDoc
	 * @review
	 */

	_handleDragLayoutColumnItem(data) {
		const sourceColumn = this._getParentColumnByPlid(this.layoutColumns, data.sourceItemPlid);
		const targetColumn = this._getParentColumnByPlid(this.layoutColumns, data.targetItemPlid);

		if (sourceColumn === targetColumn) {
			this._hoveredLayoutColumnItemBorder = data.border;
			this._hoveredLayoutColumnItemPlid = data.targetItemPlid;
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	_handleLeaveLayoutColumnItem(data) {
		this._hoveredLayoutColumnItemBorder = undefined;
		this._hoveredLayoutColumnItemPlid = undefined;
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	_handleMoveLayoutColumnItem(data) {
		const sourceItemPlid = data.sourceItemPlid;

		let layoutColumns = this.layoutColumns.map(
			layoutColumn => [...layoutColumn]
		);

		const sourceItem = this._getLayoutColumnItemByPlid(layoutColumns, sourceItemPlid);
		const targetItem = this._getLayoutColumnItemByPlid(layoutColumns, this._hoveredLayoutColumnItemPlid);

		const sourceColumn = this._getParentColumnByPlid(layoutColumns, sourceItemPlid);
		const targetColumn = this._getParentColumnByPlid(layoutColumns, this._hoveredLayoutColumnItemPlid);

		if ((sourceItem != targetItem) && (sourceColumn === targetColumn)) {
			sourceColumn.splice(sourceColumn.indexOf(sourceItem), 1);

			let position = sourceColumn.indexOf(targetItem);

			if (this._hoveredLayoutColumnItemBorder === DRAG_BORDERS.bottom) {
				position = sourceColumn.indexOf(targetItem) + 1;
			}

			sourceColumn.splice(position, 0, sourceItem);

			this.layoutColumns = layoutColumns;
		}

		this._hoveredLayoutColumnItemBorder = undefined;
		this._hoveredLayoutColumnItemPlid = undefined;
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	_initializeLayoutDragDrop() {
		this._layoutDragDrop = new LayoutDragDrop();

		this._layoutDragDrop.on('dragLayoutColumnItem', this._handleDragLayoutColumnItem.bind(this));
		this._layoutDragDrop.on('leaveLayoutColumnItem', this._handleLeaveLayoutColumnItem.bind(this));
		this._layoutDragDrop.on('moveLayoutColumnItem', this._handleMoveLayoutColumnItem.bind(this));
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
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

	_hoveredLayoutColumnItemBorder: Config.string().internal(),

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