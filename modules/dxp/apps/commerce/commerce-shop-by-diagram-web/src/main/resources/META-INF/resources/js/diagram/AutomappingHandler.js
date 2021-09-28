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

import {event as d3event, select as d3select, zoom as d3zoom} from 'd3';
import {fetch} from 'frontend-js-web';

import {ZOOM_VALUES} from './utilities/constants';

class AutomappingHandler {
	constructor(
		diagramWrapper,
		zoomWrapper,
		imageURL,
		pinsCSSSelectors,
		updateLabels,
		updateZoomState
	) {
		this._currentScale = 1;
		this._diagramWrapper = diagramWrapper;
		this._d3diagramWrapper = d3select(diagramWrapper);
		this._d3zoomWrapper = d3select(zoomWrapper);
		this._imageURL = imageURL;
		this._updateLabels = updateLabels;
		this._pinBackground = null;
		this._updateZoomState = updateZoomState;
		this._zoomWrapper = zoomWrapper;
		this._handleZoom = this._handleZoom.bind(this);
		this.rendered = false;

		this._printSVGImage().then(() => {
			this.rendered = true;
			this._texts = this._diagramWrapper.querySelectorAll(
				pinsCSSSelectors.join(',')
			);

			this._updatePinsState();
			this._addZoom();
			this._updateLabels(this._texts);
		});
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

	_printSVGImage() {
		return fetch(this._imageURL)
			.then((response) => response.text())
			.then((svgContent) => {
				this._d3zoomWrapper.html(svgContent);
				this._diagramWrapper.classList.add('rendered');
			});
	}

	updatePins(pins) {
		this._pins = pins;

		if (this.rendered) {
			this._updatePinsState();
		}
	}

	_updatePinsState() {
		if (this._pins) {
			const sequences = new Set(this._pins.map((pin) => pin.sequence));

			this._texts.forEach((text) => {
				text.classList.add('pin');

				if (sequences.has(text.textContent)) {
					text.classList.add('mapped');
					text._mapped = true;
				}
				else {
					text.classList.remove('mapped');
					text._mapped = false;
				}
			});
		}
	}
}

export default AutomappingHandler;
