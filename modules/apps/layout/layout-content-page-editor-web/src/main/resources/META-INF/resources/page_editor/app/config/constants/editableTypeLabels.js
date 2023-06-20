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

import {EDITABLE_TYPES} from './editableTypes';

export const EDITABLE_TYPE_LABELS = {
	[EDITABLE_TYPES.action]: Liferay.Language.get('action'),
	[EDITABLE_TYPES.backgroundImage]: Liferay.Language.get('background-image'),
	[EDITABLE_TYPES.html]: Liferay.Language.get('html'),
	[EDITABLE_TYPES.image]: Liferay.Language.get('image'),
	[EDITABLE_TYPES.link]: Liferay.Language.get('link'),
	[EDITABLE_TYPES['rich-text']]: Liferay.Language.get('rich-text'),
	[EDITABLE_TYPES.text]: Liferay.Language.get('text'),
};
