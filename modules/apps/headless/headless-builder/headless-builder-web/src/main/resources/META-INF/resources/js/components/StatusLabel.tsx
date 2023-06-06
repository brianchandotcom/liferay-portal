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

import ClayLabel from '@clayui/label';
import React from 'react';

interface StatusLabelProps {
	statusKey: string;
}

export default function StatusLabel({statusKey}: StatusLabelProps) {
	let displayType:
		| 'danger'
		| 'info'
		| 'secondary'
		| 'success'
		| 'unstyled'
		| 'warning';
	let statusLabel;

	switch (statusKey) {
		case 'published':
			displayType = 'success';
			statusLabel = Liferay.Language.get('published');
			break;
		case 'unpublished':
			displayType = 'secondary';
			statusLabel = Liferay.Language.get('unpublished');
			break;
		default:
			displayType = 'unstyled';
			statusLabel = Liferay.Language.get(statusKey);
	}

	return <ClayLabel displayType={displayType}>{statusLabel}</ClayLabel>;
}
