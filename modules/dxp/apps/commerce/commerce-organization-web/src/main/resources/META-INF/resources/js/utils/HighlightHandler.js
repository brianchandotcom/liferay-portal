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

export default class HighlightHandler {
	constructor() {
		this._highlightedLinks = null;
		this._highlightedNodes = null;
		this._enabled = true;
	}

	disableHighlight() {
		this._enabled = false;
	}

	enableHighlight() {
		this._enabled = true;
	}

	highlight(selectedNode, root, nodesGroup, linksGroup) {
		if (!this._enabled) {
			return;
		}

		const nodeInstances = [];

		root.each((d) => {
			if (d.data.chartNodeId === selectedNode.data.chartNodeId) {
				nodeInstances.push(d);
			}
		});

		const chartIdsToBeHighlighted = nodeInstances.reduce(
			(chartIdsToBeHighlighted, nodeInstance) => {
				const ancestorsId = nodeInstance
					.ancestors()
					.map((d) => d.data.chartNodeId);

				return chartIdsToBeHighlighted.concat(ancestorsId);
			},
			[]
		);

		this._highlightedNodes = nodesGroup
			.selectAll('.chart-item')
			.filter((d) =>
				chartIdsToBeHighlighted.includes(d.data.chartNodeId)
			);

		this._highlightedLinks = linksGroup
			.selectAll('.chart-link')
			.filter((d) =>
				chartIdsToBeHighlighted.includes(d.target.data.chartNodeId)
			);

		this._highlightedLinks.raise();

		this._highlightedLinks.classed('highlighted', true);
		this._highlightedNodes.classed('highlighted', true);
	}

	removeHighlight() {
		if (this._highlightedLinks && this._highlightedNodes) {
			this._highlightedLinks.classed('highlighted', false);
			this._highlightedNodes.classed('highlighted', false);

			this._highlightedLinks = null;
			this._highlightedNodes = null;
		}
	}
}
