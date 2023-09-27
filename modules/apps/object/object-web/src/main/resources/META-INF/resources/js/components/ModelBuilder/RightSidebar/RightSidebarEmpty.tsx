/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import React from 'react';

import './RightSidebarEmpty.scss';

export function RightSidebarEmpty() {
	return (
		<div className="lfr-objects__model-builder-right-sidebar-empty-state">
			<ClayEmptyState
				description={Liferay.Language.get(
					'select-an-object-or-relationship-to-activate-this-panel'
				)}
				imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/empty_state.gif`}
				small
				title={Liferay.Language.get('select-an-object-or-relationship')}
			/>
		</div>
	);
}
