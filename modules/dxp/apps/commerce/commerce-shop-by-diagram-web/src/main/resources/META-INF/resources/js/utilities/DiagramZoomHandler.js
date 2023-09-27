/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			!event.target.closest('.diagram-tooltip-wrapper') &&
			!event.target.closest('.dropdown-menu')
		) {
			this.resetActivePinsState();
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
			this._updateZoomState(this._currentScale);
		}

		this._d3zoomWrapper.attr('transform', d3event.transform);
	}

	cleanUp() {
		window.removeEventListener('click', this._handleClickOutside);
	}

	updateZoom(scale, duration = 800) {
		this._currentScale = scale;

		const transition = this._d3diagramWrapper
			.transition()
			.duration(duration)
			.tween(
				'resize',
				window.ResizeObserver
					? null
					: () => this._d3diagramWrapper.dispatch('toggle')
			);

		return new Promise((resolve) => {
			this._d3diagramWrapper
				.transition(transition)
				.call(this._zoom.scaleTo, this._currentScale)
				.on('end', resolve);
		});
	}

	_recenterViewport(x, y, duration, k) {
		if (k) {
			this._currentScale = k;
		}

		this._updateZoomState(this._currentScale);

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
