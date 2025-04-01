/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {openModal, openToast} from 'frontend-js-components-web';
import {fetch, navigate, sub} from 'frontend-js-web';
import React from 'react';

import SpaceSticker from '../../components/SpaceSticker';
import CategorizationToolbar from '../CategorizationToolbar';
import CreateTagsModal from './CreateTagsModal';
import EditTagsModal from './EditTagsModal';

export default function ViewTags({
	assetLibraryId,
	tagsURL,
	vocabulariesURL,
}: {
	assetLibraryId: string;
	tagsURL: string;
	vocabulariesURL: string;
}) {
	const VIEWS_SPACE_TABLE_CELL_RENDERER_NAME = 'ViewsSpaceTableCellRenderer';

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
			apiURL: '/o/headless-asset-library/v1.0/asset-libraries',
			itemKey: 'id',
			itemLabel: 'name',
			label: 'Space',
			multiple: true,
			type: 'selection',
		},
	];

	const ViewsSpaceTableCell = ({itemData}: {itemData: any}) => {
		return (
			<span className="align-items-center d-flex space-renderer-sticker">
				<SpaceSticker name={itemData.assetLibraryKey} size="sm" />
			</span>
		);
	};

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
						contentRenderer: VIEWS_SPACE_TABLE_CELL_RENDERER_NAME,
						fieldName: 'assetLibraryKey',
						label: Liferay.Language.get('space'),
						sortable: false,
					},
					{
						contentRenderer: 'dateTime',
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

	const onDeleteClick = (itemData: {
		itemData: {actions: {delete: {href: string; method: string}}};
	}) => {
		openModal({
			bodyHTML: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this-tag'
			),
			buttons: [
				{
					autoFocus: true,
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					displayType: 'danger',
					label: Liferay.Language.get('delete'),
					onClick: ({processClose}: {processClose: Function}) => {
						processClose();

						const deleteURL = itemData.itemData.actions.delete.href;

						fetch(deleteURL, {
							headers: {
								'Accept': 'application/json',
								'Content-Type': 'application/json',
								'x-csrf-token': Liferay.authToken,
							},
							method: itemData.itemData.actions.delete.method,
						})
							.then((response) => {
								if (response.ok) {
									openToast({
										message: Liferay.Language.get(
											'your-request-completed-successfully'
										),
										title: Liferay.Language.get('success'),
										type: 'success',
									});
								}
								else {
									openToast({
										message: Liferay.Language.get(
											'an-unexpected-error-occurred'
										),
										title: Liferay.Language.get('error'),
										type: 'danger',
									});
								}
							})
							.catch(() => {
								openToast({
									message: Liferay.Language.get(
										'an-unexpected-error-occurred'
									),
									title: Liferay.Language.get('error'),
									type: 'danger',
								});
							});
						navigate(tagsURL);
					},
				},
			],
			status: 'danger',
			title: sub(
				Liferay.Language.get('delete-x'),
				Liferay.Language.get('tag')
			),
		});
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
				customRenderers={{
					tableCell: [
						{
							component: ViewsSpaceTableCell,
							name: VIEWS_SPACE_TABLE_CELL_RENDERER_NAME,
							type: 'internal',
						},
					],
				}}
				emptyState={emptyState}
				filters={filters}
				id="ViewTags"
				itemsActions={[
					{
						icon: 'pencil',
						label: Liferay.Language.get('edit'),
						onClick: (itemData: {itemData: any}) => {
							openModal({
								contentComponent: () =>
									EditTagsModal({
										tagId: itemData.itemData.id,
										tagName: itemData.itemData.name,
										tagsURL,
									}),
								size: 'md',
							});
						},
					},
					{
						data: {
							permissionKey: 'delete',
						},
						icon: 'trash',
						label: Liferay.Language.get('delete'),
						onClick: onDeleteClick,
					},
				]}
				showManagementBar={true}
				showSearch={true}
				views={views}
			/>
		</div>
	);
}
