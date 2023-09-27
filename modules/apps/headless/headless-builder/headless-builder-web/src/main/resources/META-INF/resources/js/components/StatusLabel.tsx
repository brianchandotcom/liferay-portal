/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
