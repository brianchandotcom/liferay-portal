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
	drag as d3drag,
	event as d3event,
	select as d3select,
	zoom as d3zoom,
} from 'd3';
import {openToast} from 'frontend-js-web';

import {
	DEFAULT_PINS_RADIUS,
	PINS_CIRCLE_RADIUS,
	ZOOM_VALUES,
} from './utilities/constants';
import {savePin} from './utilities/data';
import {
	getAbsolutePositions,
	getPercentagePositions,
	isPinMoving,
} from './utilities/index';

class DiagramHandler {
	constructor(
		diagramWrapper,
		zoomWrapper,
		imageURL,
		updateZoomState,
		setTooltipData
	) {
		this._currentScale = 1;
		this._diagramWrapper = diagramWrapper;
		this._d3diagramWrapper = d3select(diagramWrapper);
		this._d3zoomWrapper = d3select(zoomWrapper);
		this._imageURL = imageURL;
		this._pinBackground = null;
		this._setTooltipData = setTooltipData;
		this._updateZoomState = updateZoomState;
		this._zoomWrapper = zoomWrapper;
		this._handleZoom = this._handleZoom.bind(this);
		this._handleDragStarted = this._handleDragStarted.bind(this);
		this._handleDragging = this._handleDragging.bind(this);
		this._handleDragEnded = this._handleDragEnded.bind(this);
		this._handleImageClick = this._handleImageClick.bind(this);
		this._handleEnter = this._handleEnter.bind(this);
		this._handleExit = this._handleExit.bind(this);
		this._handleUpdate = this._handleUpdate.bind(this);
		this._pinsRadius = DEFAULT_PINS_RADIUS;

		this._printImage();
		this._addZoom();
	}

	_addZoom() {
		this._zoom = d3zoom()
			.scaleExtent([ZOOM_VALUES[0], ZOOM_VALUES[ZOOM_VALUES.length - 1]])
			.on('zoom', this._handleZoom);

		this._svg = this._d3diagramWrapper.call(this._zoom);
	}

	_handleZoom() {
		this._resetActivePinsState();

		this._currentScale = d3event.transform.k;

		this._setTooltipData(null);

		if (d3event.sourceEvent) {
			this._updateZoomState(
				this._currentScale,
				d3event.transform,
				d3event
			);
		}

		this._d3zoomWrapper.attr('transform', d3event.transform);
	}

	cleanUp() {}

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

				this._d3diagramWrapper.call(this._zoom.translateBy, panX, 0);

				this.imageRendered = true;

				if (this._pins) {
					this._updatePrintedPins();
				}

				this._diagramWrapper.classList.add('rendered');
			})
			.on('click', this._handleImageClick);
	}

	_resetActivePinsState() {
		if (this._activePin) {
			this._activePin.classList.remove('active');
		}

		if (this._newPinPlaceholder) {
			this._newPinPlaceholder.remove();
			this._newPinPlaceholder = null;
		}
	}

	_handleImageClick() {
		this._resetActivePinsState();

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
				`translate(${getAbsolutePositions(x, y, d3event.target)})`
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

	updatePins(pins) {
		this._pins = pins;
		this._resetActivePinsState();

		if (this.imageRendered) {
			this._updatePrintedPins();
		}
	}

	updatePinsRadius(pinsRadius) {
		this._pinsRadius = pinsRadius;

		if (this._radiusHandlers) {
			this._radiusHandlers.attr(
				'transform',
				`scale(${this._pinsRadius})`
			);
		}
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
						this._image.node()
					)})`
			)
			.call(
				d3drag()
					.on('start', this._handleDragStarted)
					.on('drag', this._handleDragging)
					.on('end', this._handleDragEnded)
			)
			.on('click', (d, index, nodes) => {
				this._resetActivePinsState();

				const target = nodes[index];

				target.classList.add('active');
				this._activePin = target;

				this._setTooltipData({
					selectedPin: d,
					target,
				});
			});

		this._radiusHandlers = pinsWrapper
			.append('g')
			.attr('class', 'pin-radius-handler')

			.call((enter) => enter.transition(30).attr('y', 0))

			.attr('transform', `scale(${this._pinsRadius})`);

		this._radiusHandlers
			.append('circle')
			.attr('class', 'pin-node-background')
			.attr('r', PINS_CIRCLE_RADIUS);

		this._radiusHandlers
			.append('text')
			.attr('y', 5)
			.attr('text-anchor', 'middle')
			.attr('class', 'pin-node-text')
			.text((d) => d.sequence);

		return pinsWrapper;
	}

	_handleUpdate(update) {
		update.selectAll('pin-node-text').text((d) => d.sequence);

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

		this._resetActivePinsState();
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

export default DiagramHandler;
