/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {sub} from 'frontend-js-web';
import React from 'react';

const ASSET_ENTRIES_API_URL = '/o/headless-delivery/v1.0/asset-entries';

const STATUS_FILTER = "(status eq 'approved' or status eq 'scheduled')";

const STATUS_APPROVED = 0;
const STATUS_DENIED = 4;
const STATUS_DRAFT = 2;
const STATUS_EXPIRED = 3;
const STATUS_PENDING = 1;
const STATUS_SCHEDULED = 7;

const STATUSES = {
	[STATUS_APPROVED]: {
		displayType: 'success',
		label: Liferay.Language.get('approved'),
	},
	[STATUS_DENIED]: {
		displayType: 'danger',
		label: Liferay.Language.get('denied'),
	},
	[STATUS_DRAFT]: {
		displayType: 'secondary',
		label: Liferay.Language.get('draft'),
	},
	[STATUS_EXPIRED]: {
		displayType: 'warning',
		label: Liferay.Language.get('expired'),
	},
	[STATUS_PENDING]: {
		displayType: 'info',
		label: Liferay.Language.get('pending'),
	},
	[STATUS_SCHEDULED]: {
		displayType: 'info',
		label: Liferay.Language.get('scheduled'),
	},
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

function buildAPIURL(groupId, classNameIds, excludedAssetEntry) {
	const url = new URL(ASSET_ENTRIES_API_URL, window.location.origin);

	url.searchParams.set('groupIds', groupId);
	url.searchParams.set('showNonindexable', 'true');

	const filters = [];

	if (classNameIds.length === 1) {
		filters.push(`classNameId eq ${classNameIds[0]}`);
	}
	else if (classNameIds.length > 1) {
		filters.push(`classNameId in (${classNameIds.join(',')})`);
	}

	filters.push(STATUS_FILTER);

	if (excludedAssetEntry) {
		filters.push(
			`(classNameId ne ${excludedAssetEntry.classNameId} or classPK ne ${excludedAssetEntry.classPK})`
		);
	}

	url.searchParams.set('filter', filters.join(' and '));

	return url.toString();
}

/**
 * Builds the props shared by every asset entry item selector caller (Asset
 * Publisher, Asset List, and the Asset Taglib link selector). Callers spread the
 * result into openItemSelectorModal and add their own items and onItemsChange.
 */
export default function getAssetEntriesItemSelectorProps({
	assetEntryTypes = [],
	excludedAssetEntry,
	groupId,
	portletNamespace,
}) {
	const classNameIds = assetEntryTypes.map(
		(assetEntryType) => assetEntryType.classNameId
	);

	const singleAssetEntryType = assetEntryTypes.length === 1;

	return {
		apiURL: buildAPIURL(groupId, classNameIds, excludedAssetEntry),
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
							items: assetEntryTypes.map((assetEntryType) => ({
								label: assetEntryType.label,
								value: String(assetEntryType.classNameId),
							})),
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
								label: Liferay.Language.get('modified-date'),
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
		locator: {
			id: 'assetEntryId',
			label: 'title',
			value: 'assetEntryId',
		},
		multiSelect: true,
		title: singleAssetEntryType
			? sub(Liferay.Language.get('select-x'), assetEntryTypes[0].label)
			: Liferay.Language.get('select-asset'),
	};
}
