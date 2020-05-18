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

import 'dynamic-data-mapping-form-renderer/js/components/PageRenderer/PageRenderer.es';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './PageRendererRows.soy';

class PageRendererRows extends Component {
	getChildContext() {
		return this.parentContext;
	}

	_handleFieldBlurred(event) {
		this.emit('fieldBlurred', event);
	}

	_handleFieldEdited(event) {
		this.emit('fieldEdited', event);
	}

	_handleFieldFocused(event) {
		this.emit('fieldFocused', event);
	}
}

PageRendererRows.STATE = {
	activePage: Config.number(),
	editable: Config.bool(),
	pageIndex: Config.number(),
	parentContext: Config.any(),
	rows: Config.any(),
	spritemap: Config.string(),
};

Soy.register(PageRendererRows, templates);

export default PageRendererRows;
