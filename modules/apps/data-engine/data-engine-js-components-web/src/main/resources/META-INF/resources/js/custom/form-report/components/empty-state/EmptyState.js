/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import React from 'react';

export default function EmptyState({
	description = Liferay.Language.get(
		'the-report-will-show-up-once-the-first-entry-is-submitted'
	),
	title = Liferay.Language.get('there-are-no-entries'),
}) {
	return (
		<ClayEmptyState
			description={description}
			imgSrc={`${themeDisplay.getPathThemeImages()}/states/empty_state.gif`}
			title={title}
		/>
	);
}
