/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import EmptyState from '@clayui/empty-state';
import React from 'react';

const EmptyStateCard = () => {
	return (
		<EmptyState
			className="cms-dashboard__empty-state"
			description={Liferay.Language.get(
				'no-content-has-been-created-in-the-cms-spaces'
			)}
			imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/cms_empty_state.svg`}
			imgSrcReducedMotion={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/cms_empty_state.svg`}
			title={Liferay.Language.get('no-assets-yet')}
		/>
	);
};

export default EmptyStateCard;
