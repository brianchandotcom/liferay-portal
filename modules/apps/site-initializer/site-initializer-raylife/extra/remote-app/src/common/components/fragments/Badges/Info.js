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

import ClayIcon from '@clayui/icon';
import React from 'react';

export function InfoBadge({children, ...props}) {
	return (
		<div
			{...props}
			className="align-items-center badge badge-info d-flex rounded"
		>
			<ClayIcon
				className="c-ml-4 c-mr-2 flex-shrink-0"
				symbol="info-circle"
			/>

			{children}
		</div>
	);
}
