/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
