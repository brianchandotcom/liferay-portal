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

import 'clay-badge';

import 'clay-dropdown';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './LayoutColumn.soy';
import LAYOUT_COLUMN_ITEM_DROPDOWN_ITEMS from './utils/LayoutColumnItemDropdownItems.es';

/**
 * LayoutColumn
 */
class LayoutColumn extends Component {
	/**
	 * Get layout column item dropdown options
	 * @param {object} layoutColumnItem
	 * @return {object[]} Dropdown options
	 * @review
	 */
	static _getLayoutColumnItemDropDownItems(layoutColumnItem, namespace) {
		const {actionURLs = {}} = layoutColumnItem;

		const dropdownItems = LAYOUT_COLUMN_ITEM_DROPDOWN_ITEMS.filter(
			dropdownItem => actionURLs[dropdownItem.name]
		).map(dropdownItem => ({
			handleClick: dropdownItem.handleClick || null,
			href: actionURLs[dropdownItem.name],
			label: dropdownItem.label,
			layoutColumnItem,
			namespace
		}));

		return dropdownItems;
	}

	/**
	 * @param {object} state
	 * @inheritdoc
	 */
	prepareStateForRender(state) {
		const layoutColumn = this.layoutColumn.map(layoutColumnItem => ({
			...layoutColumnItem,
			dropdownItems: LayoutColumn._getLayoutColumnItemDropDownItems(
				layoutColumnItem,
				this.portletNamespace
			)
		}));

		return Object.assign(state, {
			layoutColumn
		});
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	rendered() {
		if (this.refs.active) {
			this.refs.active.scrollIntoView();
		}
	}

	/**
	 * Handle column item dropdown item click event.
	 * @param {Event} event
	 */
	_handleLayoutColumnItemDropdownItemClick(event) {
		if (event.data && event.data.item && event.data.item.handleClick) {
			event.data.item.handleClick(event, this);
		}
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

LayoutColumn.STATE = {
	/**
	 * List of layouts in the current column
	 * @default undefined
	 * @instance
	 * @memberof LayoutColumn
	 * @type {!Array}
	 */

	layoutColumn: Config.arrayOf(
		Config.shapeOf({
			actionURLs: Config.object().required(),
			actions: Config.string().required(),
			active: Config.bool().required(),
			description: Config.string().required(),
			hasChild: Config.bool().required(),
			pending: Config.bool().value(false),
			plid: Config.string().required(),
			title: Config.string().required(),
			url: Config.string().required()
		})
	).required(),

	/**
	 * URL for using icons
	 * @default undefined
	 * @instance
	 * @memberof LayoutColumn
	 * @type {!string}
	 */

	pathThemeImages: Config.string().required(),

	/**
	 * Namespace of portlet to prefix parameters names
	 * @default undefined
	 * @instance
	 * @memberof LayoutColumn
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
	 * CSS class to modify style
	 * @default undefined
	 * @instance
	 * @review
	 * @type {!string}
	 */

	styleModifier: Config.string()
};

Soy.register(LayoutColumn, templates);

export {LayoutColumn};
export default LayoutColumn;
