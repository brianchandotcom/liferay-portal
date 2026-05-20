/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {sub} from 'frontend-js-web';
import React from 'react';

export default function SectionTags({
	additionCount,
	deletionCount,
}: {
	additionCount?: number;
	deletionCount?: number;
}) {
	const hasItems = !!additionCount;
	const hasDeletions = !!deletionCount;

	if (!hasItems && !hasDeletions) {
		return null;
	}

	return (
		<div className="align-items-center c-gap-2 d-inline-flex ml-2">
			{hasItems && (
				<ClayLabel displayType="secondary">
					{sub(Liferay.Language.get('x-items'), additionCount)}
				</ClayLabel>
			)}

			{hasDeletions && (
				<ClayLabel displayType="warning">
					{sub(Liferay.Language.get('x-deletions'), deletionCount)}
				</ClayLabel>
			)}
		</div>
	);
}
