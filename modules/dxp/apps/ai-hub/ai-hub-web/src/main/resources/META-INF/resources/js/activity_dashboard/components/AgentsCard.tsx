/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

import MetricCard from './MetricCard';

export default function AgentsCard({value}: {value: number}) {
	return (
		<MetricCard
			icon={<ClayIcon symbol="organizations" />}
			title={Liferay.Language.get('agents')}
			value={value}
		/>
	);
}
