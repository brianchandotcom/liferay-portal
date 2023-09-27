/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {select as d3select} from 'd3';
import {fetch} from 'frontend-js-web';

import DiagramZoomHandler from '../utilities/DiagramZoomHandler';

class D3Handler extends DiagramZoomHandler {
	constructor(
		diagramWrapper,
		imageURL,
		isAdmin,
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
		this._isAdmin = isAdmin;
		this._updateLabels = updateLabels;
		this._pinBackground = null;
		this._pinsCSSSelectors = pinsCSSSelectors;
		this._updateZoomState = updateZoomState;
		this._zoomWrapper = zoomWrapper;
		this._handleZoom = this._handleZoom.bind(this);
		this.rendered = false;

		this._printSVGImage().then(() => {
			this.rendered = true;

			this._addZoom();
			this._updatePinsState();
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
		const sequences = new Set(
			this._pins ? this._pins.map((pin) => pin.sequence) : []
		);

		const imagePosition = this._image.node().getBoundingClientRect();

		const labels = Array.from(
			this._diagramWrapper.querySelectorAll(
				this._pinsCSSSelectors.join(',')
			)
		).filter((text) => {
			const pinSaved = sequences.has(text.textContent);

			const isPin = this._isAdmin || pinSaved;

			if (this._isAdmin || pinSaved) {
				text.classList.add('pin');

				const textPosition = text.getBoundingClientRect();

				text.__data__ = {
					distanceFromCenterX:
						(textPosition.x +
							textPosition.width / 2 -
							(imagePosition.x + imagePosition.width / 2)) *
						-1,
					distanceFromCenterY:
						(textPosition.y +
							textPosition.height / 2 -
							(imagePosition.y + imagePosition.height / 2)) *
						-1,
				};
			}

			if (pinSaved) {
				text.classList.add('mapped');
				text._mapped = true;
			}
			else {
				text.classList.remove('mapped');
				text._mapped = false;
			}

			return isPin;
		});

		this._updateLabels(labels);
	}

	updatePins(pins) {
		this._pins = pins;

		if (this.rendered) {
			this._updatePinsState();
		}
	}

	async recenterOnPin(node) {
		return super._recenterViewport(
			node.__data__.distanceFromCenterX,
			node.__data__.distanceFromCenterY,
			800,
			1
		);
	}
}

export default D3Handler;
