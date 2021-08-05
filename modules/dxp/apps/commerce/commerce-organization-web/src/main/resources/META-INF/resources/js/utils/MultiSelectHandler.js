/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ACTION_KEYS} from './constants';
import {hasPermission} from './index';

export default class MultiSelectHandler {
	constructor() {
		this.selectable = [];
		this.unselectable = [];
	}

	updateSelectableItems(selectedNodes, nodesGroup) {
		const unselectableItemIds = new Set();

		selectedNodes.forEach((element) => {
			const descendants = element.descendants();

			descendants.shift();

			const ancestors = element.ancestors();

			ancestors.shift();

			[...ancestors, ...descendants].forEach((descendant) => {
				unselectableItemIds.add(descendant.data.chartNodeId);
			});
		});

		const items = nodesGroup.selectAll('.chart-item');

		items.each((d, index, nodeList) => {
			if (
				!unselectableItemIds.has(d.data.chartNodeId) &&
				d.data.type !== 'user' &&
				d.data.type !== 'add' &&
				hasPermission(d.data, ACTION_KEYS[d.data.type].MOVE)
			) {
				nodeList[index].classList.add('selectable');

				d.data.selectable = true;

				this.selectable.push({
					data: d.data,
					node: nodeList[index],
				});
			}
			else {
				nodeList[index].classList.add('unselectable');

				d.data.selectable = false;

				this.unselectable.push({
					data: d.data,
					node: nodeList[index],
				});
			}
		});
	}

	resetSelectableItems() {
		if (this.selectable.length) {
			this.selectable.forEach((selectableNode) => {
				selectableNode.node.classList.remove('selectable');

				selectableNode.data.selectable = undefined;
			});

			this.selectable = [];
		}

		if (this.unselectable.length) {
			this.unselectable.forEach((unselectableNode) => {
				unselectableNode.node.classList.remove('unselectable');

				unselectableNode.data.selectable = undefined;
			});

			this.unselectable = [];
		}
	}
}
