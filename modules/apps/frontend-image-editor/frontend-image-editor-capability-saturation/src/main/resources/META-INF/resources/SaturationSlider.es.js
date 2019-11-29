/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import core from 'metal';
import Component from 'metal-component';
import dom from 'metal-dom';
import {Drag} from 'metal-drag-drop';
import Position from 'metal-position';
import Soy from 'metal-soy';

import templates from './SaturationSlider.soy';

class SaturationSlider extends Component {
	/**
	 * @inheritDoc
	 */

	attached() {
		this.drag_ = new Drag({
			axis: 'x',
			constrain: this.constrainToRail_.bind(this),
			container: this.element,
			handles: '.handle',
			sources: '.rail-handle'
		});
		this.on('elementChanged', this.handleElementChanged_);

		this.attachDragEvents_();
	}

	/**
	 * Attaches the drag events to handle value updates when dragging the rail handle.
	 * protected
	 */

	attachDragEvents_() {
		this.drag_.on(
			Drag.Events.DRAG,
			this.updateValueFromDragData_.bind(this)
		);
		this.drag_.on(
			Drag.Events.END,
			this.updateValueFromDragData_.bind(this)
		);
	}

	/**
	 * Constrains the given region to be inside the rail. This is being used
	 * instead of `Drag`'s default behavior, because `Drag` would require the
	 * whole handle to be inside the rail element, while we just want to make sure
	 * that the left side of the handle is inside it.
	 * @param {!Object} region
	 * @protected
	 */

	constrainToRail_(region) {
		const constrain = Position.getRegion(this.refs.rail, true);
		if (region.left < constrain.left) {
			region.left = constrain.left;
		}
		else if (region.left > constrain.right) {
			region.left -= region.left - constrain.right;
		}
		region.right = region.left + region.width;
	}

	/**
	 * @inheritDoc
	 */

	disposeInternal() {
		super.disposeInternal();
		this.drag_.dispose();
	}

	/**
	 * Returns the `Drag` instance being used.
	 * @return {!Drag}
	 */

	getDrag() {
		return this.drag_;
	}

	/**
	 * Handles the `elementChanged` event. Updates the drag container to the new
	 * element, and also updates the constrain element.
	 * @param {!Object} data
	 * @protected
	 */

	handleElementChanged_(data) {
		if (data.newVal) {
			this.drag_.container = data.newVal;
		}
	}

	/**
	 * Handles mouse down actions on the SaturationSlider rail and updates the SaturationSlider value accordingly.
	 * @param {!Event} event
	 * @protected
	 */

	onRailClick_(event) {
		if (
			dom.hasClass(event.target, 'rail') ||
			dom.hasClass(event.target, 'rail-active')
		) {
			const prevValue = this.value;
			this.updateValue_(event.offsetX, 0, true);
			if (prevValue === this.value) {
				const handleRegion = Position.getRegion(this.refs.handle);
				if (event.offsetX < handleRegion.left) {
					this.value -= 1;
				}
				else {
					this.value += 1;
				}
			}
		}
	}

	/**
	 * Synchronizes the SaturationSlider UI with the `max` state key.
	 * @param {number} newVal The new value of the state key.
	 */

	syncMax(newVal) {
		if (newVal < this.value) {
			this.value = newVal;
		}
	}

	/**
	 * Synchronizes the SaturationSlider UI with the `min` state key.
	 * @param {number} newVal The new value of the state key.
	 */

	syncMin(newVal) {
		if (newVal > this.value) {
			this.value = newVal;
		}
	}

	/**
	 * Updates the SaturationSlider value based on the UI state of the handle element.
	 * @param {number} handlePosition Position of the handle in px.
	 * @param {number} offset Offset to be added to normalize relative inputs.
	 * @param {boolean=} opt_relative If the given position is relative to the
	 *     rail or not.
	 * @protected
	 */

	updateValue_(handlePosition, offset, opt_relative) {
		var region = Position.getRegion(this.element);
		if (!opt_relative) {
			handlePosition -= region.left;
		}
		this.value = Math.round(
			offset + (handlePosition / region.width) * (this.max - this.min)
		);
	}

	/**
	 * Handles Drag events from the rail handle and updates the SaturationSlider value accordingly.
	 * @param {!Object} data
	 * @protected
	 */

	updateValueFromDragData_(data, event) {
		this.updateValue_(data.x, this.min);
		event.preventDefault();
	}
}
Soy.register(SaturationSlider, templates);

/**
 * `SaturationSlider`'s state definition.
 */

SaturationSlider.STATE = {
	/**
	 * Name of the hidden input field that holds the SaturationSlider value. Useful when SaturationSlider is embedded
	 * inside a form so it can automatically send its value.
	 * @type {string}
	 */

	inputName: {
		validator: core.isString
	},

	/**
	 * Defines the maximum value handled by the SaturationSlider.
	 * @type {number}
	 * @default 100
	 */

	max: {
		value: 100
	},

	/**
	 * Defines the minimum value handled by the SaturationSlider.
	 * @type {number}
	 * @default 0
	 */

	min: {
		value: 0
	},

	/**
	 * Defines the currently selected value on the SaturationSlider.
	 * @type {number}
	 * @default 0
	 */

	value: {
		validator(val) {
			return core.isNumber(val) && this.min <= val && val <= this.max;
		},
		value: 0
	}
};

export {SaturationSlider};
export default SaturationSlider;
