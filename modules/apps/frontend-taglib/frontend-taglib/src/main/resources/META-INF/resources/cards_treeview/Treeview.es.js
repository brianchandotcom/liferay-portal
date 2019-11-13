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

import core from 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';

import KeyboardFocusManager from './KeyboardFocusManager.es';
import templates from './Treeview.soy';

/**
 * Treeview component.
 * @deprecated since 7.2
 */

class Treeview extends Component {
	/**
	 * @inheritDoc
	 */

	attached() {
		this.keyboardFocusManager_ = new KeyboardFocusManager(this, 'li')
			.setFocusHandler(this.handleNextFocus_.bind(this))
			.start();
		this.keyboardFocusManager_.on(
			KeyboardFocusManager.EVENT_FOCUSED,
			this.handleKeyboardFocused_.bind(this)
		);
	}

	/**
	 * @inheritDoc
	 */

	disposed() {
		this.keyboardFocusManager_.dispose();
		this.keyboardFocusManager_ = null;
	}

	/**
	 * Gets the node object from the `nodes` state that is located at the given
	 * index path.
	 * @param {!Array<number>} path An array of indexes indicating where the
	 *   searched node is located inside the `nodes` state.
	 * @return {!Object}
	 */

	getNodeObj(path) {
		var obj = this.nodes[path[0]];
		for (var i = 1; i < path.length; i++) {
			obj = obj.children[path[i]];
		}
		return obj;
	}

	/**
	 * Gets the treeview path for a given node.
	 * @param {!Element} node
	 * @return {!Array<string>}
	 * @protected
	 */

	getPath_(node) {
		return node.getAttribute('data-treeview-path').split('-');
	}

	/**
	 * Handles the `focused` event from `KeyboardFocusManager`. Stores the ref
	 * of the last focused tree item so that we can retain it in the tab order
	 * when the user leaves the tree.
	 * @param {!Object} data
	 * @protected
	 */

	handleKeyboardFocused_(data) {
		this.lastFocusedRef_ = data.ref;
	}

	/**
	 * Handles the left arrow being pressed. If the node is expanded, it will be
	 * closed. If it's closed, its parent's ref will be returned so it can be
	 * focused by `KeyboardFocusManager`.
	 * @param {!Array<string>} path
	 * @param {!Object} obj
	 * @return {?string}
	 * @protected
	 */

	handleLeftArrow_(path, obj) {
		if (obj.expanded) {
			obj.expanded = false;
			this.nodes = this.nodes;
		} else if (path.length > 1) {
			path.pop();
			return Treeview.NODE_REF_PREFIX + path.join('-');
		}
	}

	/**
	 * Handles focus through keyboard.
	 * @param {!Event} event
	 * @return {boolean|string|Element}
	 * @protected
	 */

	handleNextFocus_(event) {
		event.stopPropagation();

		const path = this.getPath_(event.delegateTarget);
		const obj = this.getNodeObj(path);
		switch (event.keyCode) {
			case 37:
				return this.handleLeftArrow_(path, obj);
			case 39:
				return this.handleRightArrow_(path, obj);
			default:
				// Use default behavior for other keys (like up/down arrows).

				return true;
		}
	}

	/**
	 * This is called when one of this tree view's nodes is clicked.
	 * @param {!Event} event
	 * @protected
	 */

	handleNodeClicked_(event) {
		this.toggleExpandedState_(event.delegateTarget.parentNode.parentNode);
	}

	/**
	 * This is called when one of this tree view's nodes receives a keypress.
	 * If the pressed key is ENTER or SPACE, the node's expanded state will be toggled.
	 * @param {!Event} event
	 * @protected
	 */

	handleNodeKeyUp_(event) {
		if (event.keyCode === 13 || event.keyCode === 32) {
			this.toggleExpandedState_(event.delegateTarget);
			event.stopPropagation();
		}
	}

	/**
	 * Handles the right arrow being pressed. If the node is closed, it will be
	 * expanded. If it's already expanded, the ref of its first child will be
	 * returned so it can be focused by `KeyboardFocusManager`.
	 * @param {!Array<string>} path
	 * @param {!Object} obj
	 * @return {?string}
	 * @protected
	 */

	handleRightArrow_(path, obj) {
		if (obj.expanded) {
			path.push(0);
			return Treeview.NODE_REF_PREFIX + path.join('-');
		} else if (obj.children) {
			obj.expanded = true;
			this.nodes = this.nodes;
		}
	}

	/**
	 * Toggles the expanded state for the given tree node.
	 * @param {!Element} node
	 * @protected
	 */

	toggleExpandedState_(node) {
		var nodeObj = this.getNodeObj(this.getPath_(node));
		nodeObj.expanded = !nodeObj.expanded;
		this.nodes = this.nodes;
	}
}
Soy.register(Treeview, templates);

// The prefix used for tree item nodes' refs.

Treeview.NODE_REF_PREFIX = 'node-';

/**
 * Treeview state definition.
 * @type {!Object}
 * @static
 */

Treeview.STATE = {
	/**
	 * The ref of the last item that has been focused, so that we can retain only
	 * that node in the tab order.
	 * @type {string}
	 */

	lastFocusedRef_: {
		internal: true,
		validator: core.isString
	},

	/**
	 * This tree view's nodes. Each node should have a name, and can optionally
	 * have nested children nodes. It should also indicate if its children are
	 * expanded or not.
	 * @type {Array<!{children: Array, expanded: boolean?, name: string}>}
	 * @default []
	 */

	nodes: {
		validator: Array.isArray,
		valueFn() {
			return [];
		}
	}
};

export {Treeview};
export default Treeview;
