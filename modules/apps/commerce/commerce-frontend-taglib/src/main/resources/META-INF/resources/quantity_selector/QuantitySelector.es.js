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

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './QuantitySelector.soy';

class QuantitySelector extends Component {
	attached() {
		if (!this.quantity) {
			this.quantity = this.allowedQuantities
				? this.allowedQuantities[0]
				: this.minQuantity;
			this._updateQuantity(this.quantity);
		}

		return !!this.quantity;
	}

	syncQuantity() {
		this.checkButtonsAvailability(this.quantity);
	}

	prepareStateForRender(state) {
		this.checkButtonsAvailability(state.quantity);
	}

	_handleSelectOption(e) {
		const quantity = parseInt(e.target.value, 10);

		return this.emit('updateQuantity', quantity);
	}

	checkButtonsAvailability(quantity) {
		this._prevAvailable = this._isPrevButtonAvailable(quantity);
		this._nextAvailable = this._isNextButtonAvailable(quantity);
	}

	_isPrevButtonAvailable(quantity) {
		const tempValue = this.multipleQuantity
			? quantity - this.multipleQuantity
			: quantity - 1;

		return tempValue >= this.minQuantity;
	}

	_isNextButtonAvailable(quantity) {
		const tempValue = this.multipleQuantity
			? quantity + this.multipleQuantity
			: quantity + 1;

		return tempValue <= this.maxQuantity;
	}

	_handlePrevQuantityButtonPressed(e) {
		e.preventDefault();
		if (!this._prevAvailable) {
			this.showError = true;

			return false;
		}

		let quantity = this.quantity;

		if (this.multipleQuantity) {
			quantity -= this.multipleQuantity;
		}
		else {
			quantity -= 1;
		}

		if (quantity < this.minQuantity) {
			this.inputError = 'MaxAvailableReached';

			return false;
		}

		return this._updateQuantity(quantity);
	}

	_handleNextQuantityButtonPressed(e) {
		e.preventDefault();
		if (!this._nextAvailable) {
			this.showError = true;

			return false;
		}

		let quantity = this.quantity;

		if (this.multipleQuantity) {
			quantity += this.multipleQuantity;
		}
		else {
			quantity += 1;
		}

		if (quantity > this.maxQuantity) {
			this.inputError = 'MaxAvailableReached';

			return false;
		}

		return this._updateQuantity(quantity);
	}

	_handleArrowKeys(e) {
		if (e.keyCode == 38) {
			return this._handleNextQuantityButtonPressed(e);
		}
		if (e.keyCode == 40) {
			return this._handlePrevQuantityButtonPressed(e);
		}

		return e;
	}

	_handleInputKeyUp(e) {
		if (!e.target.value) {
			return null;
		}
		const quantity = parseInt(e.target.value, 10);

		return this._submitQuantity(quantity);
	}

	_handleFormSubmit(e) {
		e.preventDefault();
		this.showError = true;

		return this.emit('submitQuantity', this.quantity);
	}

	_submitQuantity(quantity) {
		if (this.multipleQuantity) {
			if (quantity % this.multipleQuantity) {
				this.inputError = 'NotMultipleThan';

				return this.inputError;
			}
		}

		if (quantity < this.minQuantity) {
			this.inputError = 'MinAvailableReached';

			return this.inputError;
		}

		if (quantity > this.maxQuantity) {
			this.inputError = 'MaxAvailableReached';

			return this.inputError;
		}

		return this._updateQuantity(quantity);
	}

	_updateQuantity(quantity) {
		this.showError = false;

		return this.emit('updateQuantity', quantity);
	}
}

QuantitySelector.STATE = {
	_nextAvailable: Config.bool().value(true),
	_prevAvailable: Config.bool().value(true),
	allowedQuantities: Config.array(Config.number()),
	disabled: Config.bool().value(false),
	inputError: Config.string(),
	maxQuantity: Config.number().value(99999999),
	minQuantity: Config.number().value(1),
	multipleQuantity: Config.number(),
	quantity: Config.number(),
	showError: Config.bool().value(false),
};

Soy.register(QuantitySelector, template);

export {QuantitySelector};
export default QuantitySelector;
