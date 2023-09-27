/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './MetalFieldAdapter.soy';

class MetalFieldAdapter extends Component {
	getChildContext() {
		return {
			dispatch: this._handleDispatch.bind(this),
			store: {
				editingLanguageId: this.editingLanguageId,
			},
		};
	}

	_handleDispatch(name, event) {
		switch (name) {
			case 'fieldRemoved':
				this.onRemoved(name, event);
				break;
			case 'fieldRepeated':
				this.onRepeated(name, event);
				break;
			default:
				new TypeError(`${name} event is not supported.`);
				break;
		}
	}

	_handleFieldBlurred(event) {

		// Sends the empty value, the `blur` event does not need to
		// know this information only that the event was triggered.

		this.onBlur(event, '');
	}

	_handleFieldEdited(event) {
		const {originalEvent, value} = event;
		this.onChange(originalEvent, value);
	}

	_handleFieldFocused(event) {

		// Sends the empty value, the `focus` event does not need to
		// know this information only that the event was triggered.

		this.onFocus(event, '');
	}
}

MetalFieldAdapter.STATE = {
	activePage: Config.number(),
	editable: Config.bool(),
	editingLanguageId: Config.string(),
	field: Config.any(),
	onBlur: Config.any(),
	onChange: Config.any(),
	onFocus: Config.any(),
	onRemoved: Config.any(),
	onRepeated: Config.any(),
	pageIndex: Config.number(),
	spritemap: Config.string(),
	type: Config.string(),
};

Soy.register(MetalFieldAdapter, templates);

export default MetalFieldAdapter;
