/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import React from 'react';

export const LARGE = 20000;
export const MEDIUM = 10000;
export const SMALL = 0;
export function PublicationScoreLabel({publicationScore}) {
	let displayType = null;
	let label = Liferay.Language.get('publication-size') + ': ';

	if (publicationScore > LARGE) {
		displayType = 'warning';
		label += Liferay.Language.get('large');
	}
	else if (publicationScore > MEDIUM) {
		displayType = 'info';
		label += Liferay.Language.get('medium');
	}
	else {
		displayType = 'success';
		label += Liferay.Language.get('small');
	}

	return displayType && label ? (
		<ClayLabel displayType={displayType}>{label}</ClayLabel>
	) : null;
}
