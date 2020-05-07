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

import ClayBadge from '@clayui/badge';
import React from 'react';

const METHOD_DISPLAY_MAP = {
	delete: 'danger',
	get: 'primary',
	patch: 'info',
	post: 'success',
	put: 'warning',
};

const MethodBadge = (props) => {
	const {displayType, method, ...otherProps} = props;

	return (
		<ClayBadge
			displayType={displayType || METHOD_DISPLAY_MAP[method]}
			label={method.toUpperCase()}
			{...otherProps}
		/>
	);
};

export default MethodBadge;
