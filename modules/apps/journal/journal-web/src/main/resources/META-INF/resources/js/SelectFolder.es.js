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

import templates from './SelectFolder.soy';

/**
 * SelectFolder
 *
 * This component shows a list of available folders to move content in and
 * allows to filter them by searching.
 */

class SelectFolder extends PortletBase {
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
	 * Searchs for nodes by name based on a filtering value
	 *
	 * @param {!Event} event
	 * @protected
	 */

	searchNodes_(event) {
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

	selectedNodeChange_(event) {
		var node = event.newVal[0];

		if (node) {
			var data = {
				folderId: node.id,
				folderName: node.name
			};

			Liferay.Util.getOpener().Liferay.fire(this.itemSelectorSaveEvent, {
				data
			});
		}
	}
}

SelectFolder.STATE = {
	/**
	 * Event name to fire on node selection
	 * @type {String}
	 */

	itemSelectorSaveEvent: Config.string(),

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

Soy.register(SelectFolder, templates);

export default SelectFolder;
