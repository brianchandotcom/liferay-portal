/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

export default function Step() {
	return (
		<>
			<ClayLayout.Sheet>
				{Liferay.Language.get('Filters')}
			</ClayLayout.Sheet>

			<ClayLayout.Sheet>
				{Liferay.Language.get('Portlets')}
			</ClayLayout.Sheet>
		</>
	);
}
