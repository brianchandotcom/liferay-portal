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

import {
	event as d3event,
	zoom as d3zoom,
	zoomIdentity as d3ZoomIdentity,
} from 'd3';

import {ZOOM_VALUES} from './constants';

class DiagramZoomHandler {
	constructor() {
		this._handleClickOutside = this._handleClickOutside.bind(this);
	}

	_handleClickOutside(event) {
		if (
			!this._diagramWrapper.parentNode.contains(event.target) &&
			!event.target.closest('.diagram-tooltip-wrapper')
		) {
			this._resetActivePinsState();
		}
	}

	_addZoom() {
		this._zoom = d3zoom()
			.scaleExtent([ZOOM_VALUES[0], ZOOM_VALUES[ZOOM_VALUES.length - 1]])
			.on('zoom', this._handleZoom);

		this._svg = this._d3diagramWrapper.call(this._zoom);
	}

	_handleZoom() {
		this._currentScale = d3event.transform.k;

		if (d3event.sourceEvent) {
			this._updateZoomState(
				this._currentScale,
				d3event.transform,
				d3event
			);
		}

		this._d3zoomWrapper.attr('transform', d3event.transform);
	}

	cleanUp() {
		window.removeEventListener('click', this._handleClickOutside);
	}

	updateZoom(scale) {
		this._currentScale = scale;

		this._animateZoom();
	}

	_animateZoom() {
		const transition = this._d3diagramWrapper
			.transition()
			.duration(800)
			.tween(
				'resize',
				window.ResizeObserver
					? null
					: () => this._d3diagramWrapper.dispatch('toggle')
			);

		this._d3diagramWrapper
			.transition(transition)
			.call(this._zoom.scaleTo, this._currentScale);
	}

	_recenterViewport(x, y, duration) {
		return new Promise((resolve) => {
			this._d3diagramWrapper
				.transition(
					this._d3diagramWrapper.transition().duration(duration)
				)
				.call(
					this._zoom.transform,
					d3ZoomIdentity.translate(x, y).scale(this._currentScale)
				)
				.on('end', resolve);
		});
	}
}

export default DiagramZoomHandler;
