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

import InfoItemService from '../services/InfoItemService';

export default function loadBackgroundImage(backgroundImage) {
	if (!backgroundImage) {
		return Promise.resolve('');
	}
	else if (typeof backgroundImage.url === 'string') {
		return Promise.resolve(backgroundImage.url);
	}
	else if (backgroundImage.fieldId) {
		return InfoItemService.getInfoItemFieldValue({
			classNameId: backgroundImage.classNameId,
			classPK: backgroundImage.classPK,
			fieldId: backgroundImage.fieldId,
			onNetworkStatus: () => {},
		}).then((response) => {
			if (response.fieldValue && response.fieldValue.url) {
				return response.fieldValue.url;
			}

			return '';
		});
	}

	return Promise.resolve('');
}
