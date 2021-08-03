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

import {config} from '../config/index';
import isNullOrUndefined from './isNullOrUndefined';

export const getEditableLocalizedValue = (
	editableValue,
	languageId = null,
	defaultValue = ''
) => {
	let content;

	if (languageId && !isNullOrUndefined(editableValue?.[languageId])) {
		content = editableValue[languageId];
	}
	else if (!isNullOrUndefined(editableValue?.[config.defaultLanguageId])) {
		content = editableValue[config.defaultLanguageId];
	}
	else if (!isNullOrUndefined(editableValue?.defaultValue)) {
		content = editableValue.defaultValue;
	}
	else if (typeof editableValue === typeof defaultValue) {
		content = editableValue;
	}
	else {
		content = defaultValue;
	}

	return content;
};
