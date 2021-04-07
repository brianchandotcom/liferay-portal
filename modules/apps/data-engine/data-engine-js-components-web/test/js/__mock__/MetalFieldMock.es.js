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

import './MetalFieldMockRegister.soy';

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './MetalFieldMock.soy';

class MetalFieldMock extends Component {
	dispatch(...args) {
		const {dispatch} = this.context;

		(dispatch || this.emit).apply(this, args);
	}

	_handleDuplicateClick() {
		this.dispatch('fieldRepeated', this.name);
	}

	_handleRemoveClick() {
		this.dispatch('fieldRemoved', this.name);
	}

	_handleInputBlur(event) {
		this.emit('fieldBlurred', {
			fieldInstance: this,
			originalEvent: event,
			value: event.target.value,
		});
	}

	_handleInputFocus(event) {
		this.emit('fieldFocused', {
			fieldInstance: this,
			originalEvent: event,
			value: event.target.value,
		});
	}

	_handleInputChange(event) {
		this.emit('fieldEdited', {
			fieldInstance: this,
			originalEvent: event,
			value: event.target.value,
		});
	}
}

MetalFieldMock.STATE = {
	name: Config.string(),
};

Soy.register(MetalFieldMock, templates);

export default MetalFieldMock;
