import Component from 'metal-component';
import core from 'metal';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy from 'metal-soy';
import { Config } from 'metal-state';

import CardsTreeView from 'journal-web/js/CardsTreeView.es';
import templates from './SelectLayout.soy';

/**
 * SelectLayout
 *
 * This component shows a list of available layouts to select in expanded tree
 * and allows to filter them by searching.
 */
class SelectLayout extends CardsTreeView {

	attached() {
		this.on('selectedNodesChanged', this.selectedNodeChange_);
	}

	/**
	 * Filters deep nested nodes based on a filtering value
	 *
	 * @type {Array.<Object>} nodes
	 * @type {String} filterVAlue
	 * @protected
	 */
	filterSiblingNodes_(nodes, filterValue) {
		let filteredNodes = [];

		nodes.forEach(
			(node) => {
				if (node.name.toLowerCase().indexOf(filterValue) !== -1) {
					filteredNodes.push(node);
				}

				if (node.children) {
					filteredNodes = filteredNodes.concat(this.filterSiblingNodes_(node.children, filterValue));
				}
			}
		);

		return filteredNodes;
	}

	/**
	 * This is called when one of this tree view's nodes title is clicked.
	 * @param {!Event} event
	 * @protected
	 */
	handleNodeTitleClicked_(event) {
		let path = event.delegateTarget.getAttribute('data-treeview-path').split('-');

		let node = this.getNodeObj(path);

		if (this.followURLOnTitleClick) {
			Liferay.Util.getOpener().document.location.href = node.url;
		} else {
			this.selectedNodeChange_(event, node);
		}
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
		}
		else {
			this.nodes = this.originalNodes;
		}

		let filterValue = event.delegateTarget.value.toLowerCase();

		if (filterValue !== '') {
			this.viewType = 'flat';
			this.nodes = this.filterSiblingNodes_(this.nodes, filterValue);
		}
		else {
			this.viewType = 'tree';
		}
	}

	/**
	 * Fires item selector save event on selected node change
	 *
	 * @param {!Event} event
	 * @protected
	 */
	selectedNodeChange_(event, selectedNode) {
		let node = event.newVal[0] ? event.newVal[0] :selectedNode;

		if (node) {
			let data = {
				groupId: node.groupId,
				id: node.id,
				layoutId: node.layoutId,
				name: node.value,
				privateLayout: node.privateLayout,
				value: node.url
			};

			Liferay.Util.getOpener().Liferay.fire(
				this.itemSelectorSaveEvent,
				{
					data: data
				}
			);
		}
	}
}

SelectLayout.STATE = {

	/**
	 * Enables URL following on the title click
	 * @type {String}
	 */
	followURLOnTitleClick: Config.bool().value(false),

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
	 * Enables multiple selection of tree elements
	 * @type {boolean}
	 */
	multiSelection: Config.bool().value(false),

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

Soy.register(SelectLayout, templates);

export { SelectLayout }
export default SelectLayout;