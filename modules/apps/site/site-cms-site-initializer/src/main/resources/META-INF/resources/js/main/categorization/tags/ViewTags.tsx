/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-components-web';
import React from 'react';

import CategorizationToolbar from '../CategorizationToolbar';
import CreateTagsModal from './CreateTagsModal';

export default function ViewTags({
	assetLibraryId,
	tagsURL,
	vocabulariesURL,
}: {
	assetLibraryId: string;
	tagsURL: string;
	vocabulariesURL: string;
}) {
	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('new'),
				onClick: () => {
					openModal({
						contentComponent: () =>
							CreateTagsModal({
								assetLibraryId,
								tagsURL,
							}),
						size: 'md',
					});
				},
			},
		],
	};

	const filters = [
		{
			items: [{label: assetLibraryId, value: assetLibraryId}],
			label: 'Space',
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
						fieldName: 'assetLibraryKey',
						label: Liferay.Language.get('space'),
						sortable: false,
					},
					{
						fieldName: 'dateModified',
						label: Liferay.Language.get('modified'),
						sortable: false,
					},
				],
			},
			thumbnail: 'table',
		},
	];

	const emptyState = {
		description: Liferay.Language.get('click-new-to-create-your-first-tag'),
		image: '/states/cms_empty_state.svg',
		title: Liferay.Language.get('no-tags-yet'),
	};

	return (
		<div className="categorization-section">
			<CategorizationToolbar
				activeTab="tags"
				tagsURL={tagsURL}
				vocabulariesURL={vocabulariesURL}
			/>

			<FrontendDataSet
				apiURL={`/o/headless-admin-taxonomy/v1.0/asset-libraries/${assetLibraryId}/keywords`}
				creationMenu={creationMenu}
				emptyState={emptyState}
				filters={filters}
				id="ViewTags"
				itemsActions={[
					{
						icon: 'pencil',
						label: Liferay.Language.get('edit'),
					},
					{
						icon: 'trash',
						label: Liferay.Language.get('delete'),
					},
				]}
				showManagementBar={true}
				showSearch={true}
				views={views}
			/>
		</div>
	);
}
