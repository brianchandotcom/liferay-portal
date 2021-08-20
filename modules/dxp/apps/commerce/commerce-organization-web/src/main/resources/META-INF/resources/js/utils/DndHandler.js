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

import {DRAGGING_THRESHOLD, ZOOM_EXTENT} from './constants';

export function hasPositionChanged(start, end) {
	if (!end) {
		return false;
	}

	const diff = Math.max(Math.abs(start.x - end.x), Math.abs(start.y - end.y));

	return diff > DRAGGING_THRESHOLD;
}

export default class DndHandler {
	constructor() {
		this._mouseStartPositions = null;
		this._dragging = false;
		this._chartItems = null;
		this._dragHandle = null;
	}

	_handleMouseDown(event) {
		this._mouseStartPositions = {
			x: event.offsetX,
			y: event.offsetY,
		};
	}

	_createDragHandle(startingNode, itemsTotal, nodesWrapper) {
		this._dragHandle = nodesWrapper
			.append('g')
			.attr('transform', startingNode.attributes.transform.value)
			.attr('class', 'dnd-handle');

		const container = this._dragHandle
			.append('g')
			.attr('class', 'dnd-handle-content');

		const startingNodeClone = startingNode.cloneNode(true);

		startingNodeClone.classList.remove('highlighted');
		startingNodeClone.removeAttribute('transform');

		const itemsToBeAppended = Math.min(3, itemsTotal - 1);
		const rectPlaceholder = startingNodeClone.querySelector('rect');

		for (let i = itemsToBeAppended; i > 0; i--) {
			container
				.append(() => rectPlaceholder.cloneNode())
				.attr('x', i * 3)
				.attr('y', i * 3);
		}

		container.append(() => startingNodeClone);
	}

	_handleMouseMove(
		event,
		d,
		startingNode,
		selectedNodeIds,
		nodesGroup,
		currentScale,
		svgRef
	) {
		if (!this._dragging) {
			if (
				hasPositionChanged(this._mouseStartPositions, {
					x: event.offsetX,
					y: event.offsetY,
				})
			) {
				this._dragging = true;

				const targetsNotAllowed = new Map();

				/**
				 * Elements to be disabled while dragging:
				 * - dragged nodes
				 * - descendants of dragged nodes
				 * - all users and accounts nodes
				 * - all add buttons
				 * - direct parents
				 */

				this._chartItems = nodesGroup.selectAll('.chart-item');

				this._chartItems.each((d) => {
					if (['user', 'account', 'add'].includes(d.data.type)) {
						targetsNotAllowed.set(d.data.chartNodeId, d);
					}

					if (selectedNodeIds.has(d.data.chartNodeId)) {
						targetsNotAllowed.set(d.data.chartNodeId, d);

						if (d.parent) {
							targetsNotAllowed.set(
								d.parent.data.chartNodeId,
								d.parent
							);
						}

						const descendants = d.descendants();

						descendants.forEach((descendant) => {
							targetsNotAllowed.set(
								descendant.data.chartNodeId,
								descendant
							);
						});
					}
				});

				this._createDragHandle(
					startingNode,
					selectedNodeIds.size,
					nodesGroup
				);

				this._chartItems.each((d, index, nodeInstance) => {
					if (targetsNotAllowed.has(d.data.chartNodeId)) {
						nodeInstance[index].classList.add('drop-not-allowed');
					}
					else {
						nodeInstance[index].classList.add('drop-allowed');
					}
				});

				svgRef.classList.add('dragging');
			}
		}

		if (this._dragHandle) {
			const handlerPosition = {
				x:
					(this._mouseStartPositions.x - event.offsetX) *
						(ZOOM_EXTENT[1] / currentScale) *
						-1 +
					d.y,
				y:
					(this._mouseStartPositions.y - event.offsetY) *
						(ZOOM_EXTENT[1] / currentScale) *
						-1 +
					d.x,
			};

			this._dragHandle
				.attr(
					'transform',
					`translate(${handlerPosition.x}, ${handlerPosition.y})`
				)
				.classed('dragging', true);
		}
	}

	_handleMouseUp(mouseDownEvent, mouseUpEvent, d, svgRef, resolve) {
		if (!this._dragging) {
			return resolve({
				d,
				event: mouseDownEvent,
				type: 'click',
			});
		}

		this._mouseStartPositions = null;
		this._dragging = false;
		svgRef.classList.remove('dragging');

		const target = mouseUpEvent.target.closest('.drop-allowed');

		this._chartItems.each((_d, index, nodeInstance) => {
			nodeInstance[index].classList.remove('drop-not-allowed');
			nodeInstance[index].classList.remove('drop-allowed');
		});

		this._dragHandle.remove();

		return resolve({
			d,
			event: mouseDownEvent,
			target: target?.__data__,
			type: 'drop',
		});
	}

	handleMouseEvent(
		initialEvent,
		d,
		selectedNodeIds,
		svgRef,
		nodesGroup,
		currentScale
	) {
		return new Promise((resolve) => {
			this._handleMouseDown(initialEvent);

			const nodesToBeDragged = selectedNodeIds.has(d.data.chartNodeId)
				? selectedNodeIds
				: new Set([d.data.chartNodeId]);
			const startingNodeInstance = initialEvent.currentTarget;

			const _handleMouseMove = (event) =>
				this._handleMouseMove(
					event,
					d,
					startingNodeInstance,
					nodesToBeDragged,
					nodesGroup,
					currentScale,
					svgRef
				);

			svgRef.addEventListener('mousemove', _handleMouseMove, this);

			window.addEventListener(
				'mouseup',
				(event) => {
					svgRef.removeEventListener('mousemove', _handleMouseMove);

					this._handleMouseUp(
						initialEvent,
						event,
						d,
						svgRef,
						resolve
					);
				},
				{once: true}
			);
		});
	}
}
