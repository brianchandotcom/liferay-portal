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

export const UPDATE_AFTER = 500;

export default class QuantityControls {
	constructor({maxQuantity, minQuantity, multipleQuantity}) {
		this.max = maxQuantity;
		this.step = multipleQuantity;

		this.min =
			this.step > 1
				? this._getUpperBound(minQuantity, this.step)
				: minQuantity;
	}

	getConfiguration() {
		return {...this};
	}

	/**
	 * If this.step > 1,
	 * value will always be floored to the
	 * closest lower multiple value.
	 */
	getLowerBound(value = NaN) {
		return value > this.min ? value - (value % this.step) : this.min;
	}

	_getUpperBound(min) {
		if (min > this.step) {
			const remainder = min % this.step;

			return remainder
				? /**
				   * min is *not* a multiple of this.step
				   */
				  min + this.step - remainder
				: /**
				   * and min *is* a multiple of this.step
				   */
				  min;
		}

		return this.step;
	}
}
