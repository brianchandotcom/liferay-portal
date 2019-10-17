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

import 'frontend-taglib/cards_treeview/CardsTreeview.es';

import 'metal';

import 'metal-component';
import {PortletBase} from 'frontend-js-web';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './SelectCategory.soy';

/**
 * KeyBoardEvent enter key
 * @review
 * @type {!string}
 */

const ENTER_KEY = 'Enter';

/**
 * SelectCategory
 *
 * This component shows a list of available categories to move content in and
 * allows to filter them by searching.
 */

class SelectCategory extends PortletBase {
	/**
	 * Filters deep nested nodes based on a filtering value
	 *
	 * @type {Array.<Object>} nodes
	 * @type {String} filterVAlue
	 * @protected
	 */

	filterSiblingNodes_(nodes, filterValue) {
		let filteredNodes = [];

		nodes.forEach(node => {
			if (node.name.toLowerCase().indexOf(filterValue) !== -1) {
				filteredNodes.push(node);
			}

			if (node.children) {
				filteredNodes = filteredNodes.concat(
					this.filterSiblingNodes_(node.children, filterValue)
				);
			}
		});

		return filteredNodes;
	}

	/**
	 * When the search form is submitted, nothing should happend,
	 * as filtering is performed on keypress.
	 * @param {KeyboardEvent} event
	 * @private
	 * @review
	 */

	_handleSearchFormKeyDown(event) {
		if (event.key === ENTER_KEY) {
			event.preventDefault();
			event.stopImmediatePropagation();
		}
	}

	/**
	 * Searchs for nodes by name based on a filtering value
	 *
	 * @param {!Event} event
	 * @protected
	 */

	_searchNodes(event) {
		if (!this.originalNodes) {
			this.originalNodes = this.nodes;
		} else {
			this.nodes = this.originalNodes;
		}

		const filterValue = event.delegateTarget.value.toLowerCase();

		if (filterValue !== '') {
			this.viewType = 'flat';
			this.nodes = this.filterSiblingNodes_(this.nodes, filterValue);
		} else {
			this.viewType = 'tree';
		}
	}

	/**
	 * Fires item selector save event on selected node change
	 *
	 * @param {!Event} event
	 * @protected
	 */

	_selectedNodeChange(event) {
		const newVal = event.newVal;
		let selectedNodes = this.selectedNodes_;

		if (!selectedNodes) {
			selectedNodes = [];
		}

		if (newVal) {
			const data = {};

			newVal.forEach(node => {
				data[node.id] = {
					categoryId: node.vocabulary ? 0 : node.id,
					value: node.name,
					vocabularyId: node.vocabulary ? node.id : 0
				};
			});

			selectedNodes.forEach(node => {
				if (newVal.indexOf(node) === -1) {
					data[node.id] = {
						categoryId: node.vocabulary ? 0 : node.id,
						unchecked: true,
						value: node.name,
						vocabularyId: node.vocabulary ? node.id : 0
					};
				}
			});

			selectedNodes = [];

			newVal.forEach(node => {
				selectedNodes.push(node);
			});

			this.selectedNodes_ = selectedNodes;

			Liferay.Util.getOpener().Liferay.fire(this.itemSelectorSaveEvent, {
				data
			});
		}
	}
}

SelectCategory.STATE = {
	/**
	 * Event name to fire on node selection
	 * @type {String}
	 */

	itemSelectorSaveEvent: Config.string(),

	/**
	 * Enables multiple selection of tree elements
	 * @type {boolean}
	 */

	multiSelection: Config.bool().value(false),

	/**
	 * List of nodes
	 * @type {Array.<Object>}
	 */

	nodes: Config.array().required(),

	/**
	 * Theme images root path
	 * @type {String}
	 */

	pathThemeImages: Config.string().required(),

	/**
	 * Type of view to render. Accepted values are 'tree' and 'flat'
	 * @type {String}
	 */

	viewType: Config.string().value('tree')
};

Soy.register(SelectCategory, templates);

export default SelectCategory;
