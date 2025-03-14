/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {navigate} from 'frontend-js-web';
import React from 'react';

import CategorizationToolbar from '../CategorizationToolbar';
import {AssetType} from '../types/AssetType';

export default function ViewVocabularies({
	addVocabularyURL,
	assetTypes,
	siteId,
	tagsURL,
	vocabulariesURL,
}: {
	addVocabularyURL: string;
	assetTypes: AssetType[];
	siteId: number;
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

	const filters = [
		{
			id: 'assetTypes',
			items: assetTypes,
			label: 'Asset Types',
			multiple: true,
			type: 'selection',
		},
	];

	const views = [
		{
			contentRenderer: 'table',
			default: true,
			label: Liferay.Language.get('table'),
			name: 'table',
			schema: {
				fields: [
					{
						fieldName: 'name',
						label: Liferay.Language.get('title'),
						sortable: true,
					},
					{
						fieldName: 'numberOfTaxonomyCategories',
						label: Liferay.Language.get('categories'),
						sortable: true,
					},
					{
						fieldName: 'assetTypes.type',
						label: Liferay.Language.get('type'),
						sortable: true,
					},
					{
						fieldName: 'dateModified',
						label: Liferay.Language.get('modified'),
						sortable: true,
					},
				],
			},
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
				apiURL={`/o/headless-admin-taxonomy/v1.0/sites/${siteId}/taxonomy-vocabularies`}
				creationMenu={creationMenu}
				emptyState={emptyState}
				filters={filters}
				id="ViewVocabularies"
				showManagementBar={true}
				showSearch={true}
				views={views}
			/>
		</div>
	);
}
