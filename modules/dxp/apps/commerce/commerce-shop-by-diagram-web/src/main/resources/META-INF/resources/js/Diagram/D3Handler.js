/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {drag as d3drag, event as d3event, select as d3select} from 'd3';
import {openToast} from 'frontend-js-web';

import DiagramZoomHandler from '../utilities/DiagramZoomHandler';
import {
	PINS_CIRCLE_RADIUS,
	PINS_RADIUS,
	ZOOM_DISABLED,
} from '../utilities/constants';
import {savePin} from '../utilities/data';
import {
	getAbsolutePositions,
	getPercentagePositions,
	isPinMoving,
} from '../utilities/index';

class D3Handler extends DiagramZoomHandler {
	constructor(
		allowPinsUpdate,
		closeDropdowns,
		diagramWrapper,
		imageURL,
		setTooltipData,
		updateZoomState,
		zoomWrapper
	) {
		super();

		this._allowPinsUpdate = allowPinsUpdate;
		this._closeDropdowns = closeDropdowns;
		this._currentScale = 1;
		this._d3diagramWrapper = d3select(diagramWrapper);
		this._d3zoomWrapper = d3select(zoomWrapper);
		this._diagramWrapper = diagramWrapper;
		this._imageURL = imageURL;
		this._pinBackground = null;
		this._setTooltipData = setTooltipData;
		this._updateZoomState = updateZoomState;
		this._zoomWrapper = zoomWrapper;

		this._handleDragEnded = this._handleDragEnded.bind(this);
		this._handleDragStarted = this._handleDragStarted.bind(this);
		this._handleDragging = this._handleDragging.bind(this);
		this._handleEnter = this._handleEnter.bind(this);
		this._handleExit = this._handleExit.bind(this);
		this._handleImageClick = this._handleImageClick.bind(this);
		this._handleUpdate = this._handleUpdate.bind(this);
		this._handleZoom = this._handleZoom.bind(this);
		this._pinsRadius = PINS_RADIUS.DEFAULT;

		this._addListeners();
		this._printImage();
		this._addZoom();
	}

	_addListeners() {
		window.addEventListener('click', this._handleClickOutside);

		this._diagramWrapper.addEventListener('click', () => {
			this._closeDropdowns();
		});
	}

	_printImage() {
		const wrappperBoundingClientRect = this._diagramWrapper.getBoundingClientRect();

		this._image = this._d3zoomWrapper
			.append('image')
			.attr('href', this._imageURL)
			.attr('height', wrappperBoundingClientRect.height)
			.attr('x', 0)
			.attr('y', 0)
			.on('load', (_d, index, nodes) => {
				const imageWidth = nodes[index].getBoundingClientRect().width;

				const panX =
					(wrappperBoundingClientRect.width - imageWidth) / 2;

				if (!ZOOM_DISABLED) {
					this._d3diagramWrapper.call(
						this._zoom.translateBy,
						panX,
						0
					);
				}

				this.imageRendered = true;

				if (this._pins) {
					this._updatePrintedPins();
				}

				this._diagramWrapper.classList.add('rendered');
			})
			.on('click', this._handleImageClick);
	}

	_handleZoom() {
		this.resetActivePinsState();
		this._setTooltipData(null);

		super._handleZoom();
	}

	_recenterViewport(node, duration) {
		const {height, width} = this._diagramWrapper.getBoundingClientRect();
		const k = this._currentScale;
		const d = node.__data__;

		const [pinPositionX, pinPositionY] = getAbsolutePositions(
			d.positionX,
			d.positionY,
			this._image.node(),
			this._currentScale
		);

		const x = -pinPositionX * k + width / 2;
		const y = -pinPositionY * k + height / 2;

		return super._recenterViewport(x, y, duration, k);
	}

	resetActivePinsState() {
		if (this._activePin) {
			this._activePin.classList.remove('active');
		}

		if (this._newPinPlaceholder) {
			this._newPinPlaceholder.remove();
			this._newPinPlaceholder = null;
		}
	}

	_handleImageClick() {
		this.resetActivePinsState();

		if (!this._allowPinsUpdate) {
			this._setTooltipData(null);

			return;
		}

		const [x, y] = getPercentagePositions(
			d3event.x,
			d3event.y,
			d3event.target
		);

		this._newPinPlaceholder = this._d3zoomWrapper
			.append('g')
			.attr('class', 'empty-pin-node')
			.attr(
				'transform',
				`translate(${getAbsolutePositions(
					x,
					y,
					d3event.target,
					this._currentScale
				)})`
			)
			.append('g')
			.attr('class', 'pin-radius-handler')
			.attr('transform', `scale(${this._pinsRadius})`)
			.append('circle')
			.attr('class', 'pin-node-background')
			.attr('r', PINS_CIRCLE_RADIUS);

		const target = this._newPinPlaceholder.node();

		this._setTooltipData({target, x, y});
	}

	highlight(sequence) {
		this._d3zoomWrapper
			.selectAll('.pin-node')
			.filter((d) => d.sequence === sequence)
			.classed('highlighted', true);
	}

	removeHighlight(sequence) {
		this._d3zoomWrapper
			.selectAll('.pin-node')
			.filter((d) => d.sequence === sequence)
			.classed('highlighted', false);
	}

	updatePins(pins) {
		this._pins = pins;
		this.resetActivePinsState();

		if (this.imageRendered) {
			this._updatePrintedPins();
		}
	}

	updatePinsRadius(pinsRadius) {
		this._pinsRadius = pinsRadius;

		if (this.imageRendered) {
			this._d3zoomWrapper
				.selectAll('.pin-node')
				.select('.pin-radius-handler')
				.attr('transform', `scale(${this._pinsRadius})`);
		}
	}

	selectPinByProduct(product) {
		const found = this._d3zoomWrapper
			.selectAll('.pin-node')
			.filter((d) => d.mappedProduct.id === product.id);

		const foundNodes = found.nodes();

		if (foundNodes.length) {
			this._recenterViewport(foundNodes[0], 500).then(() => {
				this._selectPinNode(foundNodes[0]);
			});
		}
	}

	_selectPinNode(target) {
		this.resetActivePinsState();

		target.classList.add('active');

		this._activePin = target;

		this._setTooltipData({
			selectedPin: target.__data__,
			target,
		});
	}

	_updatePrintedPins() {
		this._d3zoomWrapper
			.selectAll('.pin-node')
			.data(this._pins, (d) => d.id)
			.join(this._handleEnter, this._handleUpdate, this._handleExit);
	}

	_handleEnter(enter) {
		const pinsWrapper = enter
			.append('g')
			.attr('class', 'pin-node')
			.attr(
				'transform',
				(d) =>
					`translate(${getAbsolutePositions(
						d.positionX,
						d.positionY,
						this._image.node(),
						this._currentScale
					)})`
			)
			.attr('role', 'pin')
			.on('click', (_d, index, nodes) => {
				this._selectPinNode(nodes[index]);
			});

		if (this._allowPinsUpdate) {
			pinsWrapper.call(
				d3drag()
					.on('start', this._handleDragStarted)
					.on('drag', this._handleDragging)
					.on('end', this._handleDragEnded)
			);
		}

		const radiusHandlers = pinsWrapper
			.append('g')
			.attr('class', 'pin-radius-handler')
			.call((enter) => enter.transition(30).attr('y', 0))
			.attr('transform', `scale(${this._pinsRadius})`);

		radiusHandlers
			.append('circle')
			.attr('class', 'pin-node-background')
			.attr('r', PINS_CIRCLE_RADIUS);

		radiusHandlers
			.append('text')
			.attr('y', 5)
			.attr('text-anchor', 'middle')
			.attr('class', 'pin-node-text')
			.text((d) => d.sequence);

		return pinsWrapper;
	}

	_handleUpdate(update) {
		update.select('.pin-node-text').text((d) => d.sequence);

		return update;
	}

	_handleExit(exit) {
		exit.remove();

		return exit;
	}

	_handleDragStarted(_d, index, nodes) {
		const selectedPin = nodes[index];

		this._dragDetails = {
			startTransform: selectedPin.getAttribute('transform'),
			startX: d3event.x,
			startY: d3event.y,
		};

		selectedPin.classList.add('drag-started');

		this.resetActivePinsState();
		this._setTooltipData(null);
	}

	_handleDragging(_d, index, nodes) {
		const selectedPin = nodes[index];

		if (
			isPinMoving(
				d3event.x,
				d3event.y,
				this._dragDetails.startX,
				this._dragDetails.startY
			)
		) {
			selectedPin.classList.add('dragging');
			this._dragDetails.moved = true;

			d3select(selectedPin).attr(
				'transform',
				`translate(${d3event.x},${d3event.y})`
			);
		}
		else {
			selectedPin.classList.remove('dragging');
			this._dragDetails.moved = false;

			d3select(selectedPin).attr(
				'transform',
				this._dragDetails.startTransform
			);
		}
	}

	_handleDragEnded(d, index, nodes) {
		const selectedPin = nodes[index];

		selectedPin.classList.remove('dragging', 'drag-started');

		if (this._dragDetails.moved) {
			const [x, y] = getPercentagePositions(
				d3event.sourceEvent.x,
				d3event.sourceEvent.y,
				this._image.node()
			);

			savePin(d.id, null, null, x, y)
				.then(() => {
					openToast({
						message: Liferay.Language.get('pin-position-updated'),
						type: 'success',
					});
				})
				.catch(() => {
					openToast({
						message: Liferay.Language.get(
							'pin-position-failed-to-be-updated'
						),
						type: 'danger',
					});
				});
		}
	}
}

export default D3Handler;
