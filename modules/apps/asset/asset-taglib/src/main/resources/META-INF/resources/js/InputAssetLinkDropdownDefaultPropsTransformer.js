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

function buildAPIURL(groupId, classNameIds, excludedAssetEntry) {
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

	if (excludedAssetEntry) {
		filter += ` and (classNameId ne ${excludedAssetEntry.classNameId} or classPK ne ${excludedAssetEntry.classPK})`;
	}

	url.searchParams.set('filter', filter);

	return url.toString();
}

export default function propsTransformer({
	additionalProps,
	portletNamespace,
	...props
}) {
	const {
		assetEntryTypes = [],
		groupId,
		refererClassNameId,
		refererClassPK,
		removeIcon,
	} = additionalProps;

	return {
		...props,
		onClick(event) {
			event.preventDefault();

			const searchContainer = Liferay.SearchContainer.get(
				`${portletNamespace}assetLinksSearchContainer`
			);

			const searchContainerData = searchContainer.getData();

			const existingEntryIds = searchContainerData
				? searchContainerData.split(',')
				: [];

			const classNameIds = assetEntryTypes.map(
				(assetEntryType) => assetEntryType.classNameId
			);

			const singleAssetEntryType = assetEntryTypes.length === 1;

			const excludedAssetEntry = refererClassPK
				? {classNameId: refererClassNameId, classPK: refererClassPK}
				: null;

			openItemSelectorModal({
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
				items: existingEntryIds.map((entryId) => ({
					assetEntryId: Number(entryId),
				})),
				locator: {
					id: 'assetEntryId',
					label: 'title',
					value: 'assetEntryId',
				},
				multiSelect: true,
				onItemsChange(selectedAssetEntries) {
					if (!selectedAssetEntries) {
						return;
					}

					selectedAssetEntries.forEach((selectedAssetEntry) => {
						const entryId = selectedAssetEntry.assetEntryId;

						if (existingEntryIds.indexOf(String(entryId)) !== -1) {
							return;
						}

						searchContainer.addRow(
							[
								`<div class="list-group-title">${Liferay.Util.escapeHTML(
									selectedAssetEntry.title
								)}</div><p class="list-group-subtitle">${Liferay.Util.escapeHTML(
									selectedAssetEntry.assetType
								)}</p><p class="list-group-subtitle">${Liferay.Language.get(
									'scope'
								)}: ${Liferay.Util.escapeHTML(
									selectedAssetEntry.groupDescriptiveName
								)}</p>`,
								`<button aria-label="${Liferay.Language.get(
									'remove'
								)}" class="btn btn-monospaced btn-outline-borderless btn-outline-secondary float-right lfr-portal-tooltip modify-link" data-rowId="${entryId}" title="${Liferay.Language.get(
									'remove'
								)}" type="button">${removeIcon}</button>`,
							],
							entryId
						);

						searchContainer.updateDataStore();

						existingEntryIds.push(String(entryId));
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
