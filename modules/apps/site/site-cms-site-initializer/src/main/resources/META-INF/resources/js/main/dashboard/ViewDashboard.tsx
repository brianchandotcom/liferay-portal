/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import React from 'react';

export default function ViewDashboard() {
	return (
		<ClayLayout.Container fluid>
			<Text size={1} weight="bold">
				{Liferay.Language.get('dashboard')}
			</Text>
		</ClayLayout.Container>
	);
}
