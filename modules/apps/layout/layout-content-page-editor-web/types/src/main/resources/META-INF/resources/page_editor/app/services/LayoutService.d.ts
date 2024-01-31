/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CollectionItemLayoutDataItem} from '../../types/layout_data/CollectionItemLayoutDataItem';
import {LayoutData, LayoutDataItem} from '../../types/layout_data/LayoutData';
import {
	FragmentEntryLink,
	FragmentEntryLinkMap,
} from '../actions/addFragmentEntryLinks';
import {LayoutDataItemType} from '../config/constants/layoutDataItemTypes';
import {PageContent} from '../utils/usePageContents';
import {OnNetworkStatus} from './draftServiceFetch';
export interface StyleBookTokenValue {
	cssVariable: string;
	editorType: string;
	label: string;
	name: string;
	tokenCategoryLabel: string;
	tokenSetLabel: string;
	value: string;
}
export declare type StyleBookTokenValueMap = Record<
	string,
	StyleBookTokenValue
>;
export interface Layout {
	groupId: string;
	layoutId: string;
	layoutUuid: string;
	privateLayout: boolean;
	title: string;
}
declare const _default: {
	addItem({
		itemType,
		onNetworkStatus,
		parentItemId,
		position,
		segmentsExperienceId,
	}: {
		itemType: LayoutDataItemType;
		onNetworkStatus: OnNetworkStatus;
		parentItemId: string;
		position: number;
		segmentsExperienceId: string;
	}): Promise<{
		addedItemId: string;
		layoutData: LayoutData;
	}>;
	changeMasterLayout({
		masterLayoutPlid,
		onNetworkStatus,
	}: {
		masterLayoutPlid: string;
		onNetworkStatus: OnNetworkStatus;
	}): Promise<
		| {
				fragmentEntryLinks: FragmentEntryLinkMap;
				masterLayoutData: LayoutData;
		  }
		| undefined
	>;
	changeStyleBookEntry({
		onNetworkStatus,
		styleBookEntryId,
	}: {
		onNetworkStatus: OnNetworkStatus;
		styleBookEntryId: string;
	}): Promise<{
		tokenValues: StyleBookTokenValueMap;
	}>;
	getLayoutFriendlyURL(
		layout: Layout
	): Promise<{
		friendlyURL: string;
	}>;
	markItemForDeletion({
		itemId,
		onNetworkStatus,
		portletIds,
		segmentsExperienceId,
	}: {
		itemId: string;
		onNetworkStatus: OnNetworkStatus;
		portletIds?: string[] | undefined;
		segmentsExperienceId: string;
	}): Promise<{
		layoutData: LayoutData;
		pageContents: PageContent[];
	}>;
	moveItem({
		itemId,
		onNetworkStatus,
		parentItemId,
		position,
		segmentsExperienceId,
	}: {
		itemId: string;
		onNetworkStatus: OnNetworkStatus;
		parentItemId: string;
		position: number;
		segmentsExperienceId: string;
	}): Promise<LayoutData>;
	restoreCollectionDisplayConfig({
		filterFragmentEntryLinks,
		itemConfig,
		itemId,
		onNetworkStatus,
		segmentsExperienceId,
	}: {
		filterFragmentEntryLinks: Array<{
			editableValues: FragmentEntryLink['editableValues'];
			fragmentEntryLinkId: string;
		}>;
		itemConfig: CollectionItemLayoutDataItem['config'];
		itemId: string;
		onNetworkStatus: OnNetworkStatus;
		segmentsExperienceId: string;
	}): Promise<void>;
	unmarkItemsForDeletion({
		itemIds,
		onNetworkStatus,
		segmentsExperienceId,
	}: {
		itemIds: string[];
		onNetworkStatus: OnNetworkStatus;
		segmentsExperienceId: string;
	}): Promise<{
		layoutData: LayoutData;
		pageContents: PageContent[];
	}>;
	updateCollectionDisplayConfig({
		itemConfig,
		itemId,
		languageId,
		onNetworkStatus,
		segmentsExperienceId,
	}: {
		itemConfig: CollectionItemLayoutDataItem['config'];
		itemId: string;
		languageId: Liferay.Language.Locale;
		onNetworkStatus: OnNetworkStatus;
		segmentsExperienceId: string;
	}): Promise<{
		fragmentEntryLinks: FragmentEntryLink[];
		layoutData: LayoutData;
		pageContents: PageContent[];
	}>;
	updateItemConfig({
		itemConfig,
		itemId,
		onNetworkStatus,
		segmentsExperienceId,
	}: {
		itemConfig: LayoutDataItem['config'];
		itemId: string;
		onNetworkStatus: OnNetworkStatus;
		segmentsExperienceId: string;
	}): Promise<{
		layoutData: LayoutData;
		pageContents: PageContent[];
	}>;
	updateLayoutData({
		layoutData,
		onNetworkStatus,
		segmentsExperienceId,
	}: {
		layoutData: LayoutData;
		onNetworkStatus: OnNetworkStatus;
		segmentsExperienceId: string;
	}): Promise<void>;
	updateRowColumns({
		itemId,
		numberOfColumns,
		onNetworkStatus,
		segmentsExperienceId,
	}: {
		itemId: string;
		numberOfColumns: number;
		onNetworkStatus: OnNetworkStatus;
		segmentsExperienceId: string;
	}): Promise<{
		layoutData: LayoutData;
		pageContents: PageContent[];
	}>;
};
export default _default;
