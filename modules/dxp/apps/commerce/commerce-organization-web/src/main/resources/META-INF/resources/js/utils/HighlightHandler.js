/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
