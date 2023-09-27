/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
