/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React from 'react';

import {CategorizationToolbar} from '../CategorizationToolbar';

export default function TagsView() {
	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('new'),
			},
		],
	};

	const views = [
		{
			contentRenderer: 'table',
			default: true,
			label: Liferay.Language.get('table'),
			name: 'table',
			thumbnail: 'table',
		},
	];

	const emptyState = {
		description: Liferay.Language.get('click-new-to-create-your-first-tag'),
		image: '/states/cms_empty_state.svg',
		title: Liferay.Language.get('no-tags-yet'),
	};

	return (
		<>
			<CategorizationToolbar
				activeTab="tags"
			/>

			<FrontendDataSet
				creationMenu={creationMenu}
				emptyState={emptyState}
				id="TagsView"
				showManagementBar={false}
				showSearch={false}
				views={views}
			/>
		</>
	);
}
