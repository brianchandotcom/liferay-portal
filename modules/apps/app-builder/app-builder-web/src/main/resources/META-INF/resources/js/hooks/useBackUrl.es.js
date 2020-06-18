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

import {useCallback, useContext} from 'react';
import {__RouterContext as RouterContext} from 'react-router-dom';

export default function useBackUrl() {
	const {
		location: {pathname, search},
	} = useContext(RouterContext);

	return useCallback(
		(url) => ({
			pathname: url,
			search: `?backUrl=${pathname}${search}`,
		}),
		[pathname, search]
	);
}
