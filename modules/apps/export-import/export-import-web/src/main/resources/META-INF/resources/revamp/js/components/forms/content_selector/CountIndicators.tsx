/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {sub} from 'frontend-js-web';
import React from 'react';

interface CountIndicatorsProps {
	additionCount?: number;
	deletionCount?: number;
}

export default function CountIndicators({
	additionCount,
	deletionCount,
}: CountIndicatorsProps) {
	if (!additionCount && !deletionCount) {
		return null;
	}

	return (
		<>
			{additionCount ? (
				<span className="font-weight-normal ml-2 small text-secondary">
					{sub(Liferay.Language.get('x-items'), additionCount)}
				</span>
			) : null}

			{deletionCount ? (
				<ClayLabel className="ml-2" displayType="warning">
					{sub(Liferay.Language.get('x-deletions'), deletionCount)}
				</ClayLabel>
			) : null}
		</>
	);
}
