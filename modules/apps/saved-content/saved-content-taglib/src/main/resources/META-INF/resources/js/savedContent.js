/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React from 'react';

export default function savedContent({saved = true}) {
	return (
		<ClayButtonWithIcon
			displayType="secondary"
			monospaced
			size="sm"
			symbol={saved ? 'bookmarks-full' : 'bookmarks'}
		/>
	);
}
