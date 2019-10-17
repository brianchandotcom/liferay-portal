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

import {Treeview} from 'frontend-js-web';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './CardsTreeview.soy';

/**
 * CardsTreeview
 *
 * This is an extension of the default TreeView component that adds
 * the following features:
 *
 * - Node selection management, both single and multiple
 * - Custom tree node template using Lexicon Horizontal Cards
 * - Improved accessibility for keyboard navigation following common tree widget patterns
 * @review
 */
class CardsTreeview extends Treeview {
	/**
	 * @inheritDoc
	 * @review
	 */
	created() {
		this.expandSelectedNodesParentNodes_(this.nodes);
		this.addSelectedNodes_(this.nodes);
	}

	/**
	 * Adds nodes with selected attribute to selectedNodes list in case when
	 * they are still not there.
	 * @param nodes Nodes to check and add to selectedNodes list.
	 * @protected
	 * @review
	 */
	addSelectedNodes_(nodes) {
		nodes.forEach(node => {
			if (node.children) {
				this.addSelectedNodes_(node.children);
			}

			if (node.selected) {
				this.selectNode_(node);
			}
		});
	}

	/**
	 * Deselects all selected tree nodes
	 * @protected
	 * @review
	 */
	deselectAll_() {
		for (let i = this.selectedNodes.length - 1; i >= 0; i--) {
			this.selectedNodes[i].selected = false;
			this.selectedNodes.pop();
		}
	}

	/**
	 * Selects specific nodes
	 * @param node to deselect.
	 * @protected
	 * @review
	 */
	deselectNode_(node) {
		node.selected = false;

		this.selectedNodes.splice(this.selectedNodes.indexOf(node), 1);

		this.selectedNodes = this.selectedNodes;
	}

	/**
	 * Expands all parent nodes of expanded children.
	 * @param nodes List of nodes to expand all parent nodes of expanded children.
	 * @protected
	 * @review
	 */
	expandSelectedNodesParentNodes_(nodes) {
		let expanded;
		let expandedParent;

		nodes.forEach(node => {
			expanded = node.expanded;

			if (node.selected) {
				expandedParent = true;
			}

			if (node.children) {
				expanded =
					this.expandSelectedNodesParentNodes_(node.children) ||
					expanded;
			}

			node.expanded = expanded;
		}, this);

		return expandedParent;
	}

	/**
	 * Focus the given tree node.
	 * @param {!Object} nodeObj
	 * @protected
	 * @review
	 */
	focus_(nodeObj) {
		if (nodeObj) {
			this.element
				.querySelector('[data-treeitemid="' + nodeObj.id + '"] .card')
				.focus();
		}
	}

	/**
	 * Focus the next tree node of given tree node.
	 * @param {!Element} node
	 * @protected
	 * @review
	 */
	focusNextNode_(node) {
		const path = node.getAttribute('data-treeview-path').split('-');

		const nodeObj = this.getNodeObj(path);

		let nextNodeObj;

		if (nodeObj.children && nodeObj.expanded) {
			path.push(0);

			nextNodeObj = this.getNodeObj(path);
		} else {
			while (!nextNodeObj && path.length > 0) {
				path[path.length - 1]++;

				nextNodeObj = this.getNodeObj(path);

				path.pop();
			}
		}

		this.focus_(nextNodeObj);
	}

	/**
	 * Focus the previous tree node of given tree node.
	 * @param {!Element} node
	 * @protected
	 * @review
	 */
	focusPrevNode_(node) {
		const path = node.getAttribute('data-treeview-path').split('-');

		let prevNodeObj;

		if (path[path.length - 1] === '0') {
			path.pop();

			prevNodeObj = this.getNodeObj(path);
		} else {
			path[path.length - 1]--;

			prevNodeObj = this.getNodeObj(path);

			while (prevNodeObj.children && prevNodeObj.expanded) {
				prevNodeObj =
					prevNodeObj.children[prevNodeObj.children.length - 1];
			}
		}

		this.focus_(prevNodeObj);
	}

	/**
	 * This is called when one of this tree view's nodes is clicked.
	 * @param {!Event} event
	 * @protected
	 * @review
	 */
	handleNodeClicked_(event) {
		const path = event.delegateTarget.parentNode.parentNode.parentNode
			.getAttribute('data-treeview-path')
			.split('-');

		const node = this.getNodeObj(path);

		if (node.disabled) {
			return;
		}

		if (this.multiSelection) {
			if (node.selected) {
				this.deselectNode_(node);
			} else {
				this.selectNode_(node);
			}
		} else if (!node.selected) {
			this.deselectAll_();
			this.selectNode_(node);
		}

		this.nodes = this.nodes;
	}

	/**
	 * This is called when one of this tree view's nodes receives a keypress.
	 * Depending on the pressed key, the tree will:
	 * - ENTER or SPACE: Select the current node
	 * - DOWN ARROW: Focus the next node
	 * - UP ARROW: Focus the previous node
	 * - LEFT ARROW: Collapse the current node
	 * - RIGHT ARROW: Expand the current node
	 * @param {!Event} event
	 * @protected
	 * @review
	 */
	handleNodeKeyUp_(event) {
		const node = event.delegateTarget.parentNode.parentNode.parentNode;

		if (event.keyCode === 37) {
			this.setNodeExpandedState_(node, {
				expanded: false
			});
		} else if (event.keyCode === 38) {
			this.focusPrevNode_(node);
		} else if (event.keyCode === 39) {
			this.setNodeExpandedState_(node, {
				expanded: true
			});
		} else if (event.keyCode === 40) {
			this.focusNextNode_(node);
		} else if (event.keyCode === 13 || event.keyCode === 32) {
			this.handleNodeClicked_(event);
		}
	}

	/**
	 * This is called when one of this tree view's nodes toggler is clicked.
	 * @param {!Event} event
	 * @protected
	 * @review
	 */
	handleNodeTogglerClicked_(event) {
		this.toggleExpandedState_(
			event.delegateTarget.parentNode.parentNode.parentNode
		);
	}

	/**
	 * Selects specific node.
	 * @param node to select.
	 * @protected
	 * @review
	 */
	selectNode_(node) {
		node.selected = true;

		this.selectedNodes.push(node);

		this.selectedNodes = this.selectedNodes;
	}

	/**
	 * Sets the expanded state of a node
	 * @param {!Element} node The tree node we want to change the expanded state to
	 * @param {!Object} state A state object with the new value of the expanded state
	 * @protected
	 * @review
	 */
	setNodeExpandedState_(node, state) {
		const path = node.getAttribute('data-treeview-path').split('-');

		const nodeObj = this.getNodeObj(path);

		nodeObj.expanded = state.expanded;

		this.nodes = this.nodes;
	}
}

/**
 * CardsTreeview state definition.
 * @type {!Object}
 * @review
 * @static
 */
CardsTreeview.STATE = {
	/**
	 * Enables multiple selection of tree elements
	 * @review
	 * @type {boolean}
	 */
	multiSelection: Config.bool().value(false),

	/**
	 * List of selected nodes
	 * @review
	 * @type {Array.<Object>}
	 */
	selectedNodes: Config.array().value([]),

	/**
	 * Type of view to render. Accepted values are 'tree' and 'flat'
	 * @review
	 * @type {String}
	 */
	viewType: Config.string().value('tree')
};

Soy.register(CardsTreeview, templates);

export default CardsTreeview;
