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

'use strict';

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './CompareCheckbox.soy';

class CompareCheckbox extends Component {
	attached() {
		window.Liferay.on('compareIsAvailable', this._enableCompare, this);
		window.Liferay.on('compareIsUnavailable', this._disableCompare, this);
		window.Liferay.on(
			'itemRemovedFromCompare',
			this._removeFromCompare,
			this
		);
	}

	detached() {
		window.Liferay.detach('compareIsAvailable', this._enableCompare, this);
		window.Liferay.detach(
			'compareIsUnavailable',
			this._disableCompare,
			this
		);
		window.Liferay.detach(
			'itemRemovedFromCompare',
			this._removeFromCompare,
			this
		);
	}

	_enableCompare() {
		this.compareAvailable = true;

		return this._emitUpdates();
	}

	_disableCompare() {
		this.compareAvailable = false;

		return this._emitUpdates();
	}

	_removeFromCompare(data) {
		if (data.id === this.productId) {
			this.inCompare = false;
		}

		return this._emitUpdates();
	}

	_emitUpdates() {
		this.emit('checkboxCompareUpdated', {
			compareAvailable: this.compareAvailable,
			inCompare: this.inCompare,
		});
	}

	_handleCompareCheckbox(evt) {
		evt.preventDefault();
		this.inCompare = !this.inCompare;
		Liferay.fire(
			this.inCompare ? 'addItemToCompare' : 'removeItemFromCompare',
			{
				id: this.productId,
				thumbnail: this.pictureUrl || null
			}
		);
	}
}

Soy.register(CompareCheckbox, template);

CompareCheckbox.STATE = {
	checkboxVisible: Config.bool(),
	compareAvailable: Config.bool(),
	inCompare: Config.bool(),
	labelVisible: Config.bool(),
	pictureUrl: Config.string(),
	productId: Config.oneOfType([Config.number(), Config.string()]).required(),
};

export {CompareCheckbox};
export default CompareCheckbox;
