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
	tag,
}: {
	additionCount?: number;
	deletionCount?: number;
	tag?: string;
}) {
	const hasItems = !!additionCount;
	const hasDeletions = !!deletionCount;
	const hasTag = !!tag;

	if (!hasItems && !hasDeletions && !hasTag) {
		return null;
	}

	return (
		<div className="align-items-center c-gap-2 d-inline-flex ml-2">
			{hasTag && <ClayLabel displayType="info">{tag}</ClayLabel>}

			{hasItems && (
				<ClayLabel displayType="secondary">
					{additionCount === 1
						? sub(Liferay.Language.get('x-item'), additionCount)
						: sub(Liferay.Language.get('x-items'), additionCount)}
				</ClayLabel>
			)}

			{hasDeletions && (
				<ClayLabel displayType="warning">
					{deletionCount === 1
						? sub(Liferay.Language.get('x-deletion'), deletionCount)
						: sub(
								Liferay.Language.get('x-deletions'),
								deletionCount
							)}
				</ClayLabel>
			)}
		</div>
	);
}
