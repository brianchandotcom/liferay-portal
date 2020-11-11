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
import selectEditableValue from './selectEditableValue';

export default function selectEditableValueContent(
	{fragmentEntryLinks, languageId},
	fragmentEntryLinkId,
	editableId,
	processorType
) {
	const data = selectEditableValue(
		{fragmentEntryLinks},
		fragmentEntryLinkId,
		editableId,
		processorType
	);

	let content = data;

	if (content[languageId]) {
		content = content[languageId];
	}
	else if (content[config.defaultLanguageId]) {
		content = content[config.defaultLanguageId];
	}

	if (content == null || content.defaultValue) {
		content = data.defaultValue;
	}

	return content;
}
