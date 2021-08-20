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

import {ACCOUNTS_DND_ENABLED} from './flags';

export default class MultiSelectHandler {
	constructor() {
		this.items = null;
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

		this.items = nodesGroup.selectAll('.chart-item');

		this.items.each((d, index, nodeList) => {
			if (
				unselectableItemIds.has(d.data.chartNodeId) ||
				d.data.type === 'user' ||
				(d.data.type === 'account' && !ACCOUNTS_DND_ENABLED)
			) {
				nodeList[index].classList.add('unselectable');

				d.data.selectable = false;
			}
			else {
				nodeList[index].classList.add('selectable');

				d.data.selectable = true;
			}
		});
	}

	resetSelectableItems() {
		if (this.items) {
			this.items.each((d, index, nodeList) => {
				nodeList[index].classList.remove('unselectable');

				d.data.selectable = undefined;
			});
		}

		this.items = null;
	}
}
