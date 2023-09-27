/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import React from 'react';

export default function LocalesContainer({children, ...otherProps}) {
	return (
		<ClayTabs {...otherProps} className="lfr-translationmanager" modern>
			{children}
		</ClayTabs>
	);
}
