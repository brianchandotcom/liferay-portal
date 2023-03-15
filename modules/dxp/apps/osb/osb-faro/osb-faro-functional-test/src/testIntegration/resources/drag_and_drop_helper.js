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

/* !
 * \class DndSimulatorDataTransfer
 *
 * \brief Re-implementation of the native \see DataTransfer object.
 *
 * \param data Optional: The data to initialize the data transfer object with.
 *
 * \see https://developer.mozilla.org/en-US/docs/Web/API/DataTransfer
 */
const DndSimulatorDataTransfer = function () {
	this.data = {};
};

/* !
 * \brief Controls the feedback currently given to the user.
 *
 * Must be any of the following strings:
 *
 * - "move"
 * - "copy"
 * - "link"
 * - "none"
 *
 * The default is "move".
 *
 * \see https://developer.mozilla.org/en-US/docs/Web/API/DataTransfer/dropEffect
 */
DndSimulatorDataTransfer.prototype.dropEffect = 'move';

/* !
 * \brief Controls which kind of drag/drop operatins are allowed.
 *
 * Must be any of the following strings:
 *
 * - "none"
 * - "copy"
 * - "copyLink"
 * - "copyMove"
 * - "link"
 * - "linkMove"
 * - "move"
 * - "all"
 * - "uninitialized"
 *
 * The default is "all".
 *
 * \see https://developer.mozilla.org/en-US/docs/Web/API/DataTransfer/effectAllowed
 */
DndSimulatorDataTransfer.prototype.effectAllowed = 'all';

/* !
 * \brief List of files being dragged.
 *
 * This property will remain an empty list when the drag and drop operation
 * does not involve any files.
 *
 * \see https://developer.mozilla.org/en-US/docs/Web/API/DataTransfer/files
 */
DndSimulatorDataTransfer.prototype.files = [];

/* !
 * \brief Read-only list of items being dragged.
 *
 * This is actually a list of \see DataTransferItem
 * \see https://developer.mozilla.org/en-US/docs/Web/API/DataTransferItem
 *
 * This property will remain an empty list when the drag and drop
 * operation does not involve any files.
 */
DndSimulatorDataTransfer.prototype.items = [];

/* !
 * \brief Read-only list of data formats that were set in
 *           the "dragstart" event.
 *
 * The order of the formats is the same order as the data
 * included in the drag operation.
 *
 * \see https://developer.mozilla.org/en-US/docs/Web/API/DataTransfer/types
 */
DndSimulatorDataTransfer.prototype.types = [];

/* !
 * \brief Removes all data.
 *
 * \param format Optional: Only remove the data associated with this format.
 *
 * \see https://developer.mozilla.org/en-US/docs/Web/API/DataTransfer/clearData
 */
DndSimulatorDataTransfer.prototype.clearData = function (format) {
	if (format) {
		delete this.data[format];

		const index = this.types.indexOf(format);
		delete this.types[index];
		delete this.data[index];
	}
	else {
		this.data = {};
	}
};

/* !
 * \brief Sets the drag operation"s drag data to the specified data
 *          and type.
 *
 * \param format A string describing the data"s format.
 * \param data   The data to store (formatted according to the
 *                 specified format).
 *
 * \see https://developer.mozilla.org/en-US/docs/Web/API/DataTransfer/setData
 */
DndSimulatorDataTransfer.prototype.setData = function (format, data) {
	this.data[format] = data;
	this.items.push(data);
	this.types.push(format);
};

/* !
 * \brief Retrives drag dta for the specified type.
 *
 * \param format A string describing the type of data to retrieve.
 *
 * \returns The drag data for the specified type, otherwise an empty string.
 *
 * \see https://developer.mozilla.org/en-US/docs/Web/API/DataTransfer/getData
 */
DndSimulatorDataTransfer.prototype.getData = function (format) {
	if (format in this.data) {
		return this.data[format];
	}

	return '';
};

/* !
 * \brief Sets a custom image to be displayed during dragging.
 *
 * \param img         An image elment to use for the drag feedback image.
 * \param xOffset    A long indicating the horizontal offset within the image.
 * \param yOffset   A long indicating the veritcal offset within the image.
 */
DndSimulatorDataTransfer.prototype.setDragImage = function (
	_img,
	_xOffset,
	_yOffset
) {

	/* since simulation doesn"t replicate the visual effects, there is
    no point in implementing this */
};

// eslint-disable-next-line no-undef
DndSimulator = {

	/* !
	 * \brief Creates a new fake event ready to be dispatched.
	 *
	 * \param eventName The type of event to create.
	 *                    For example: "mousedown".
	 * \param options    Dictionary of options for this event.
	 *
	 * \returns An event ready for dispatching.
	 */
	createEvent(eventName, options) {
		const event = document.createEvent('CustomEvent');
		event.initCustomEvent(eventName, true, true, null);

		event.view = window;
		event.detail = 0;
		event.ctlrKey = false;
		event.altKey = false;
		event.shiftKey = false;
		event.metaKey = false;
		event.button = 0;
		event.relatedTarget = null;

		/* if the clientX and clientY options are specified,
        also calculated the desired screenX and screenY values */
		if (options.clientX && options.clientY) {
			event.screenX = window.screenX + options.clientX;
			event.screenY = window.screenY + options.clientY;
		}

		/* copy the rest of the options into
        the event object */
		for (const prop in options) {
			event[prop] = options[prop];
		}

		return event;
	},

	/* !
	 * \brief Simulates dragging one element on top of the other.
	 *
	 * Specified elements can be CSS selectors.
	 *
	 * \param sourceElement The element to drag to the target element.
	 * \param targetElement The element the source element should be
	 *                        dragged to.
	 */
	simulate(sourceElement, targetElement) {

		/* if strings are specified, assume they are CSS selectors */
		if (typeof sourceElement === 'string') {
			sourceElement = document.querySelector(sourceElement);
		}

		if (typeof targetElement === 'string') {
			targetElement = document.querySelector(targetElement);
		}

		/* get the coordinates of both elements, note that
        left refers to X, and top to Y */
		const sourceCoordinates = sourceElement.getBoundingClientRect();
		const targetCoordinates = targetElement.getBoundingClientRect();

		/* simulate a mouse down event on the coordinates
        of the source element */
		const mouseDownEvent = this.createEvent('mousedown', {
			clientX: sourceCoordinates.left,
			clientY: sourceCoordinates.top,
		});

		sourceElement.dispatchEvent(mouseDownEvent);

		/* simulate a drag start event on the source element */
		const dragStartEvent = this.createEvent('dragstart', {
			clientX: sourceCoordinates.left,
			clientY: sourceCoordinates.top,
			dataTransfer: new DndSimulatorDataTransfer(),
		});

		sourceElement.dispatchEvent(dragStartEvent);

		/* simulate a drag event on the source element */
		const dragEvent = this.createEvent('drag', {
			clientX: sourceCoordinates.left,
			clientY: sourceCoordinates.top,
		});

		sourceElement.dispatchEvent(dragEvent);

		/* simulate a drag enter event on the target element */
		const dragEnterEvent = this.createEvent('dragenter', {
			clientX: targetCoordinates.left,
			clientY: targetCoordinates.top,
			dataTransfer: dragStartEvent.dataTransfer,
		});

		targetElement.dispatchEvent(dragEnterEvent);

		/* simulate a drag over event on the target element */
		const dragOverEvent = this.createEvent('dragover', {
			clientX: targetCoordinates.left,
			clientY: targetCoordinates.top,
			dataTransfer: dragStartEvent.dataTransfer,
		});

		targetElement.dispatchEvent(dragOverEvent);

		/* simulate a drop event on the target element */
		const dropEvent = this.createEvent('drop', {
			clientX: targetCoordinates.left,
			clientY: targetCoordinates.top,
			dataTransfer: dragStartEvent.dataTransfer,
		});

		targetElement.dispatchEvent(dropEvent);

		/* simulate a drag end event on the source element */
		const dragEndEvent = this.createEvent('dragend', {
			clientX: targetCoordinates.left,
			clientY: targetCoordinates.top,
			dataTransfer: dragStartEvent.dataTransfer,
		});

		sourceElement.dispatchEvent(dragEndEvent);

		/* simulate a mouseup event on the target element */
		const mouseUpEvent = this.createEvent('mouseup', {
			clientX: targetCoordinates.left,
			clientY: targetCoordinates.top,
		});

		targetElement.dispatchEvent(mouseUpEvent);
	},
};
