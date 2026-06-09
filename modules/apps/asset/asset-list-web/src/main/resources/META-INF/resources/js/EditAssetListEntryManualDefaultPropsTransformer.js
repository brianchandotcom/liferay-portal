/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {openItemSelectorModal} from '@liferay/frontend-js-item-selector-web';
import {sub} from 'frontend-js-web';
import React from 'react';

const ASSET_ENTRIES_API_URL = '/o/headless-delivery/v1.0/asset-entries';

const STATUS_FILTER = "(status eq 'approved' or status eq 'scheduled')";

const STATUSES = {
	0: {displayType: 'success', label: Liferay.Language.get('approved')},
	1: {displayType: 'info', label: Liferay.Language.get('pending')},
	2: {displayType: 'secondary', label: Liferay.Language.get('draft')},
	3: {displayType: 'warning', label: Liferay.Language.get('expired')},
	4: {displayType: 'danger', label: Liferay.Language.get('denied')},
	8: {displayType: 'info', label: Liferay.Language.get('scheduled')},
};

function StatusCell({value}) {
	const status = STATUSES[value];

	if (!status) {
		return null;
	}

	return (
		<ClayLabel displayType={status.displayType}>{status.label}</ClayLabel>
	);
}

function buildAPIURL(groupId, classNameIds) {
	const url = new URL(ASSET_ENTRIES_API_URL, window.location.origin);

	url.searchParams.set('groupIds', groupId);
	url.searchParams.set('showNonindexable', 'true');

	let filter = STATUS_FILTER;

	if (classNameIds.length === 1) {
		filter = `classNameId eq ${classNameIds[0]} and ${STATUS_FILTER}`;
	}
	else if (classNameIds.length > 1) {
		filter = `classNameId in (${classNameIds.join(',')}) and ${STATUS_FILTER}`;
	}

	url.searchParams.set('filter', filter);

	return url.toString();
}

export default function propsTransformer({
	additionalProps,
	portletNamespace,
	...props
}) {
	const {assetEntryTypes = [], groupId} = additionalProps;

	return {
		...props,
		onClick(event) {
			event.preventDefault();

			const classNameIds = assetEntryTypes.map(
				(assetEntryType) => assetEntryType.classNameId
			);

			const singleAssetEntryType = assetEntryTypes.length === 1;

			openItemSelectorModal({
				apiURL: buildAPIURL(groupId, classNameIds),
				fdsProps: {
					configInURLBehavior: 'OFF',
					customRenderers: {
						tableCell: [
							{
								component: StatusCell,
								name: 'assetEntryStatus',
								type: 'internal',
							},
						],
					},
					filters: singleAssetEntryType
						? []
						: [
								{
									entityFieldType: 'integer',
									id: 'classNameId',
									items: assetEntryTypes.map(
										(assetEntryType) => ({
											label: assetEntryType.label,
											value: String(
												assetEntryType.classNameId
											),
										})
									),
									label: Liferay.Language.get('type'),
									multiple: true,
									type: 'selection',
								},
							],
					id: `${portletNamespace}assetEntriesItemSelector`,
					pagination: {
						deltas: [{label: 20}, {label: 40}, {label: 60}],
						initialDelta: 20,
					},
					views: [
						{
							contentRenderer: 'table',
							label: '',
							name: 'table',
							schema: {
								fields: [
									{
										fieldName: 'title',
										label: Liferay.Language.get('title'),
									},
									{
										fieldName: 'assetType',
										label: Liferay.Language.get('type'),
									},
									{
										fieldName: 'creator.name',
										label: Liferay.Language.get('author'),
									},
									{
										contentRenderer: 'dateTime',
										fieldName: 'dateModified',
										label: Liferay.Language.get(
											'modified-date'
										),
									},
									{
										contentRenderer: 'assetEntryStatus',
										fieldName: 'status',
										label: Liferay.Language.get('status'),
									},
								],
							},
						},
					],
				},
				itemTypeLabel: singleAssetEntryType
					? assetEntryTypes[0].label
					: Liferay.Language.get('asset'),
				items: [],
				locator: {
					id: 'assetEntryId',
					label: 'title',
					value: 'assetEntryId',
				},
				multiSelect: true,
				onItemsChange(selectedAssetEntries) {
					if (!selectedAssetEntries || !selectedAssetEntries.length) {
						return;
					}

					Liferay.Util.postForm(document[`${portletNamespace}fm`], {
						data: {
							assetEntryIds: selectedAssetEntries
								.map(
									(selectedAssetEntry) =>
										selectedAssetEntry.assetEntryId
								)
								.join(','),
						},
					});
				},
				title: singleAssetEntryType
					? sub(
							Liferay.Language.get('select-x'),
							assetEntryTypes[0].label
						)
					: Liferay.Language.get('select-asset'),
			});
		},
	};
}
