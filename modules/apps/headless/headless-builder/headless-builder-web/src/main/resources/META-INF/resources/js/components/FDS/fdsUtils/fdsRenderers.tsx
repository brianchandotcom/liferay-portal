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

import React from 'react';

import StatusLabel from '../../StatusLabel';

export function itemPathRenderer({itemData}: FDSItem) {
	let path = itemData.baseURL;

	if (Array.from(path)[0] !== '/') {
		path = '/' + path;
	}

	if (path.slice(-1) !== '/') {
		path = path + '/';
	}

	return path;
}

export function itemStatusRenderer({itemData}: FDSItem) {
	return <StatusLabel statusKey={itemData.applicationStatus.key} />;
}
