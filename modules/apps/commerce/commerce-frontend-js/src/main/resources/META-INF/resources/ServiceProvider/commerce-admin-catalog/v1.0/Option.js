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

import AJAX from '../../../utilities/AJAX/index';

const OPTIONS_PATH = '/options';

const OPTION_VALUES_PATH = '/optionValues';

const VERSION = 'v1.0';

function resolveOptionsPath(basePath = '', optionId = '') {
	return `${basePath}${VERSION}${OPTIONS_PATH}/${optionId}`;
}

function resolveOptionValuesPath(basePath = '', optionId = '') {
	return `${basePath}${VERSION}${OPTIONS_PATH}/${optionId}${OPTION_VALUES_PATH}`;
}

export default (basePath) => ({
	createOption: (json) => AJAX.POST(resolveOptionsPath(basePath), json),
	createOptionValue: (optionId, json) =>
		AJAX.POST(resolveOptionValuesPath(basePath, optionId), json),
});
