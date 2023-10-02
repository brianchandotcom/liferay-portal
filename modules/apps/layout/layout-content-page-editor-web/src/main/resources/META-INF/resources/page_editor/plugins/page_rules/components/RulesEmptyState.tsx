/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import React from 'react';

export default function RulesEmptyState() {
	return (
		<div className="align-items-center d-flex flex-column justify-content-between">
			<ClayEmptyState
				className="mb-0"
				description={Liferay.Language.get(
					'fortunately-it-is-very-easy-to-add-new-ones'
				)}
				imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state.gif`}
				small
				title={Liferay.Language.get('no-rules-yet')}
			/>

			<ClayButton className="mt-2" displayType="secondary" size="sm">
				{Liferay.Language.get('new-rule')}
			</ClayButton>
		</div>
	);
}
