/* eslint-disable sort-keys */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const emptyStateNoSegments = {
	updateVariationsPriorityURL:
		'http://localhost:8080/group/guest/~/control_panel/manage?p_p_id=com_liferay_asset_list_web_portlet_AssetListPortlet&p_p_lifecycle=0&p_p_state=maximized&_com_liferay_asset_list_web_portlet_AssetListPortlet_mvcRenderCommandName=%2Fasset_list%2Fupdate_variations_priority&p_p_auth=V5P1LU54',
	componentId: null,
	assetListEntrySegmentsEntryRels: [
		{
			active: true,
			assetListEntrySegmentsEntryRelId: 0,
			deleteAssetListEntryVariationURL: '',
			name: 'Anyone',
			editAssetListEntryURL: 'edit-asset-list-entry-url',
		},
	],
	openSelectSegmentsEntryDialogMethod: 'openSelectSegmentsEntryDialogMethod',
	portletId: 'com_liferay_asset_list_web_portlet_AssetListPortlet',
	assetListEntryValid: false,
	segmentsEntriesAvailables: false,
	locale: {
		ISO3Country: 'USA',
		ISO3Language: 'eng',
		country: 'US',
		displayCountry: 'United States',
		displayLanguage: 'English',
		displayName: 'English (United States)',
		displayScript: '',
		displayVariant: '',
		extensionKeys: '[]',
		language: 'en',
		script: '',
		unicodeLocaleAttributes: '[]',
		unicodeLocaleKeys: '[]',
		variant: '',
	},
	portletNamespace: '_com_liferay_asset_list_web_portlet_AssetListPortlet_',
	createNewSegmentURL: 'http://localhost:8080/create-new-segment-demo-url',
};

export const emptyStateOneAvailableSegments = {
	...emptyStateNoSegments,
	segmentsEntriesAvailables: true,
};

export const emptyStateOneAvailableSegmentsWithEntryValid = {
	...emptyStateOneAvailableSegments,
	assetListEntryValid: true,
};

export const listWithTwoVariations = {
	...emptyStateNoSegments,
	...emptyStateOneAvailableSegments,
	assetListEntrySegmentsEntryRels: [
		{
			active: true,
			assetListEntrySegmentsEntryRelId: 0,
			deleteAssetListEntryVariationURL: '',
			name: 'Anyone',
			editAssetListEntryURL: 'edit-asset-list-entry-url-0',
		},
		{
			active: false,
			assetListEntrySegmentsEntryRelId: 1,
			deleteAssetListEntryVariationURL: 'delete-asset-list-entry-url-1',
			name: 'Liferayers',
			editAssetListEntryURL: 'edit-asset-list-entry-url-1',
		},
	],
	assetListEntryValid: true,
};

export const listWithFourVariationsAndNoMoreSegmentsEntries = {
	...emptyStateNoSegments,
	...emptyStateOneAvailableSegments,
	...listWithTwoVariations,
	assetListEntrySegmentsEntryRels: [
		...listWithTwoVariations.assetListEntrySegmentsEntryRels,
		{
			active: false,
			assetListEntrySegmentsEntryRelId: 2,
			deleteAssetListEntryVariationURL: 'delete-asset-list-entry-url-2',
			name: 'Admins',
			editAssetListEntryURL: 'edit-asset-list-entry-url-2',
		},
		{
			active: false,
			assetListEntrySegmentsEntryRelId: 3,
			deleteAssetListEntryVariationURL: 'delete-asset-list-entry-url-3',
			name: 'Random',
			editAssetListEntryURL: 'edit-asset-list-entry-url-3',
		},
	],
	segmentsEntriesAvailables: false,
};
