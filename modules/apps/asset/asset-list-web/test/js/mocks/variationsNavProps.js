/* eslint-disable sort-keys */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

export const emptyStateNoSegments = {
	updateVariationsPriorityURL:
		'http://localhost:8080/group/guest/~/control_panel/manage?p_p_id=com_liferay_asset_list_web_portlet_AssetListPortlet&p_p_lifecycle=0&p_p_state=maximized&_com_liferay_asset_list_web_portlet_AssetListPortlet_mvcRenderCommandName=%2Fasset_list%2Fupdate_variations_priority&p_p_auth=V5P1LU54',
	componentId: null,
	assetEntryListSegmentsEntryRels: [
		{
			active: true,
			assetListEntrySegmentsEntryRelId: 0,
			deleteAssetListEntryVariationURL: '',
			label: 'Anyone',
			editAssetListEntryURL: 'edit-asset-list-entry-url',
		},
	],
	openSelectSegmentsEntryDialogMethod: 'openSelectSegmentsEntryDialogMethod',
	portletId: 'com_liferay_asset_list_web_portlet_AssetListPortlet',
	validAssetListEntry: false,
	availableSegmentsEntries: false,
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
	availableSegmentsEntries: true,
};

export const listWithTwoVariations = {
	...emptyStateNoSegments,
	...emptyStateOneAvailableSegments,
	assetEntryListSegmentsEntryRels: [
		{
			active: true,
			assetListEntrySegmentsEntryRelId: 0,
			deleteAssetListEntryVariationURL: '',
			label: 'Anyone',
			editAssetListEntryURL: 'edit-asset-list-entry-url-0',
		},
		{
			active: false,
			assetListEntrySegmentsEntryRelId: 1,
			deleteAssetListEntryVariationURL: 'delete-asset-list-entry-url-1',
			label: 'Liferayers',
			editAssetListEntryURL: 'edit-asset-list-entry-url-1',
		},
	],
	validAssetListEntry: true,
};

export const listWithFourVariationsAndNoMoreSegmentsEntries = {
	...emptyStateNoSegments,
	...emptyStateOneAvailableSegments,
	...listWithTwoVariations,
	assetEntryListSegmentsEntryRels: [
		...listWithTwoVariations.assetEntryListSegmentsEntryRels,
		{
			active: false,
			assetListEntrySegmentsEntryRelId: 2,
			deleteAssetListEntryVariationURL: 'delete-asset-list-entry-url-2',
			label: 'Admins',
			editAssetListEntryURL: 'edit-asset-list-entry-url-2',
		},
		{
			active: false,
			assetListEntrySegmentsEntryRelId: 3,
			deleteAssetListEntryVariationURL: 'delete-asset-list-entry-url-3',
			label: 'Random',
			editAssetListEntryURL: 'edit-asset-list-entry-url-3',
		},
	],
	availableSegmentsEntries: false,
};
