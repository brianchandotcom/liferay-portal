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

import {select as d3select} from 'd3';
import {fetch} from 'frontend-js-web';

import DiagramZoomHandler from '../utilities/DiagramZoomHandler';

class D3Handler extends DiagramZoomHandler {
	constructor(
		diagramWrapper,
		imageURL,
		pinsCSSSelectors,
		updateLabels,
		updateZoomState,
		zoomWrapper
	) {
		super();

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
			this._texts = Array.from(
				this._diagramWrapper.querySelectorAll(
					pinsCSSSelectors.join(',')
				)
			);

			this._updatePinsState();
			this._addZoom();
			this._updateLabels(this._texts);
		});
	}

	_printSVGImage() {
		return fetch(this._imageURL)
			.then((response) => response.text())
			.then((svgContent) => {
				this._d3zoomWrapper.html(svgContent);

				this._image = this._d3zoomWrapper.select('svg');

				this._diagramWrapper.classList.add('rendered');
			});
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

	updatePins(pins) {
		this._pins = pins;

		if (this.rendered) {
			this._updatePinsState();
		}
	}

	updateZoom(scale) {
		this._currentScale = scale;

		this._animateZoom();
	}

	recenterOnPin(node) {
		const {
			height: imageHeight,
			width: imageWidth,
			x: imageX,
			y: imageY,
		} = this._image.node().getBoundingClientRect();

		const k = this._currentScale;

		const {
			height: nodeHeight,
			width: nodeWidth,
			x: nodeX,
			y: nodeY,
		} = node.getBoundingClientRect();

		const positionX = nodeX - imageX + nodeWidth / 2;
		const positionY = nodeY - imageY + nodeHeight / 2;

		const x = -positionX * k + imageWidth / 2;
		const y = -positionY * k + imageHeight / 2;

		return super._recenterViewport(x, y, 1000);
	}
}

export default D3Handler;
