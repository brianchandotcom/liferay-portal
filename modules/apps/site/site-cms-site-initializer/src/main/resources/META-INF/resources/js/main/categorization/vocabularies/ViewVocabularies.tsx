/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {navigate} from 'frontend-js-web';
import React from 'react';

import CategorizationToolbar from '../CategorizationToolbar';

export default function ViewVocabularies({
	addVocabularyURL,
	tagsURL,
	vocabulariesURL,
}: {
	addVocabularyURL: string;
	tagsURL: string;
	vocabulariesURL: string;
}) {
	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('add-vocabulary'),
				onClick: () => navigate(addVocabularyURL),
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
		description: Liferay.Language.get(
			'vocabularies-are-needed-to-create-categories'
		),
		image: '/states/cms_empty_state.svg',
		title: Liferay.Language.get('no-vocabularies-yet'),
	};

	return (
		<div className="categorization-section">
			<CategorizationToolbar
				activeTab="vocabularies"
				tagsURL={tagsURL}
				vocabulariesURL={vocabulariesURL}
			/>

			<FrontendDataSet
				creationMenu={creationMenu}
				emptyState={emptyState}
				id="ViewVocabularies"
				showManagementBar={false}
				showSearch={false}
				views={views}
			/>
		</div>
	);
}
