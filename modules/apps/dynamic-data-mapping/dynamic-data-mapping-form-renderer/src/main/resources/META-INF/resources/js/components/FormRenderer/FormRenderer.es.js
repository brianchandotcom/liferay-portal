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

import '../../containers/Pagination/Pagination.es';

import '../../containers/PaginationControls/PaginationControls.es';

import '../../containers/Tabs/Tabs.es';

import '../../containers/Wizard/Wizard.es';

import '../PageRenderer/PageRenderer.es';

import 'clay-button';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './FormRenderer.soy';

/**
 * FormRenderer.
 * @extends Component
 */

class FormRenderer extends Component {
	_defaultLanguageIdValueFn() {
		return themeDisplay.getLanguageId();
	}

	_editingLanguageIdValueFn() {
		const {defaultLanguageId} = this;

		return defaultLanguageId;
	}

	_getDisplayableFn() {
		const {containerId, readOnly, viewMode} = this;

		return (
			readOnly ||
			!viewMode ||
			document.getElementById(containerId) !== null
		);
	}

	_handleFieldBlurred(event) {
		this.emit('fieldBlurred', event);
	}

	_handleFieldClicked(event) {
		this.emit('fieldClicked', event);
	}

	_handleFieldEdited(event) {
		this.emit('fieldEdited', event);
	}

	_handleFieldFocused(event) {
		this.emit('fieldFocused', event);
	}
}

FormRenderer.STATE = {

	/**
	 * @default
	 * @instance
	 * @memberof FormRenderer
	 * @type {?number}
	 */

	activePage: Config.number().value(0),

	/**
	 * @default undefined
	 * @memberof FormRenderer
	 * @type {string}
	 * @required
	 */

	containerId: Config.string().value(''),

	/**
	 * @default undefined
	 * @memberof FormRenderer
	 * @type {string}
	 * @required
	 */

	defaultLanguageId: Config.string().valueFn('_defaultLanguageIdValueFn'),

	/**
	 * @default false
	 * @instance
	 * @memberof FormRenderer
	 * @type {?bool}
	 */

	displayable: Config.bool().valueFn('_getDisplayableFn'),

	/**
	 * @default false
	 * @instance
	 * @memberof FormRenderer
	 * @type {?bool}
	 */

	editable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @memberof FormRenderer
	 * @type {string}
	 * @required
	 */

	editingLanguageId: Config.string().valueFn('_editingLanguageIdValueFn'),

	/**
	 * @default []
	 * @instance
	 * @memberof FormRenderer
	 * @type {?array<object>}
	 */

	pages: Config.array().value([]),

	/**
	 * @instance
	 * @memberof FormRenderer
	 * @type {string}
	 */

	portletNamespace: Config.string().required(),

	/**
	 * @default false
	 * @instance
	 * @memberof FormRenderer
	 * @type {?bool}
	 */

	readOnly: Config.bool().value(false),

	/**
	 * @default []
	 * @instance
	 * @memberof FormRenderer
	 * @type {?array<object>}
	 */

	rules: Config.array().value([]),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormRenderer
	 * @type {!string}
	 */

	spritemap: Config.string().required(),

	/**
	 * @default false
	 * @instance
	 * @memberof FormRenderer
	 * @type {?bool}
	 */

	viewMode: Config.bool().value(false),
};

Soy.register(FormRenderer, templates);

export default FormRenderer;
