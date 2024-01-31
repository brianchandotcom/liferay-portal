/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData} from '../../types/layout_data/LayoutData';
import {FragmentComposition} from '../actions/addFragmentComposition';
import {FragmentEntryLinkComment} from '../actions/addFragmentEntryLinkComment';
import {
	FragmentEntryLink,
	FragmentEntryLinkMap,
} from '../actions/addFragmentEntryLinks';
import {FragmentEntry} from '../actions/updateFragments';
import {PageContent} from '../utils/usePageContents';
import {OnNetworkStatus} from './draftServiceFetch';
declare const _default: {
	addComment({
		body,
		fragmentEntryLinkId,
		onNetworkStatus,
		parentCommentId,
	}: {
		body: string;
		fragmentEntryLinkId: string;
		onNetworkStatus: OnNetworkStatus;
		parentCommentId?: string | undefined;
	}): Promise<FragmentEntryLinkComment>;
	addFragmentComposition({
		description,
		fragmentCollectionId,
		itemId,
		name,
		onNetworkStatus,
		previewImageURL,
		saveInlineContent,
		saveMappingConfiguration,
		segmentsExperienceId,
	}: {
		description: string;
		fragmentCollectionId: string;
		itemId: string;
		name: string;
		onNetworkStatus: OnNetworkStatus;
		previewImageURL?: string | undefined;
		saveInlineContent: boolean;
		saveMappingConfiguration: boolean;
		segmentsExperienceId: string;
	}): Promise<{
		fragmentComposition: FragmentComposition;
		url: string;
	}>;
	addFragmentEntryLink({
		fragmentEntryKey,
		groupId,
		onNetworkStatus,
		parentItemId,
		position,
		segmentsExperienceId,
	}: {
		fragmentEntryKey: string;
		groupId: string;
		onNetworkStatus: OnNetworkStatus;
		parentItemId: string;
		position: number;
		segmentsExperienceId: string;
	}): Promise<{
		addedItemId: string;
		fragmentEntryLink: FragmentEntryLink;
		layoutData: LayoutData;
	}>;
	addFragmentEntryLinks({
		fragmentEntryKey,
		groupId,
		onNetworkStatus,
		parentItemId,
		position,
		segmentsExperienceId,
	}: {
		fragmentEntryKey: string;
		groupId: string;
		onNetworkStatus: OnNetworkStatus;
		parentItemId: string;
		position: number;
		segmentsExperienceId: string;
	}): Promise<{
		addedItemId: string;
		fragmentEntryLinks: FragmentEntryLinkMap;
		layoutData: LayoutData;
	}>;
	deleteComment({
		commentId,
		onNetworkStatus,
	}: {
		commentId: string;
		onNetworkStatus: OnNetworkStatus;
	}): Promise<void>;
	duplicateItem({
		itemId,
		onNetworkStatus,
		segmentsExperienceId,
	}: {
		itemId: string;
		onNetworkStatus: OnNetworkStatus;
		segmentsExperienceId: string;
	}): Promise<{
		duplicatedFragmentEntryLinks: FragmentEntryLink[];
		duplicatedItemId: string;
		layoutData: LayoutData;
		restrictedItemIds: string[];
	}>;
	editComment({
		body,
		commentId,
		onNetworkStatus,
		resolved,
	}: {
		body: string;
		commentId: string;
		onNetworkStatus: OnNetworkStatus;
		resolved: boolean;
	}): Promise<FragmentEntryLinkComment>;
	renderFragmentEntryLinkContent({
		fragmentEntryLinkId,
		itemClassName,
		itemClassPK,
		itemExternalReferenceCode,
		languageId,
		segmentsExperienceId,
	}: {
		fragmentEntryLinkId: string;
		itemClassName: string;
		itemClassPK?: string | null | undefined;
		itemExternalReferenceCode?: string | null | undefined;
		languageId: Liferay.Language.Locale;
		segmentsExperienceId: string;
	}): Promise<{
		content: string;
	}>;
	toggleFragmentHighlighted({
		fragmentEntryKey,
		groupId,
		highlighted,
		onNetworkStatus,
	}: {
		fragmentEntryKey: string;
		groupId?: string | undefined;
		highlighted: boolean;
		onNetworkStatus: OnNetworkStatus;
	}): Promise<{
		highlightedFragments: FragmentEntry[];
	}>;
	updateConfigurationValues({
		editableValues,
		fragmentEntryLinkId,
		languageId,
		onNetworkStatus,
		segmentsExperienceId,
	}: {
		editableValues: FragmentEntryLink['editableValues'];
		fragmentEntryLinkId: string;
		languageId: Liferay.Language.Locale;
		onNetworkStatus: OnNetworkStatus;
		segmentsExperienceId: string;
	}): Promise<{
		fragmentEntryLink: FragmentEntryLink;
		layoutData: LayoutData;
		pageContents: PageContent[];
	}>;
	updateEditableValues({
		editableValues,
		fragmentEntryLinkId,
		languageId,
		onNetworkStatus,
		segmentsExperienceId,
	}: {
		editableValues: FragmentEntryLink['editableValues'];
		fragmentEntryLinkId: string;
		languageId: Liferay.Language.Locale;
		onNetworkStatus: OnNetworkStatus;
		segmentsExperienceId: string;
	}): Promise<{
		fragmentEntryLink: FragmentEntryLink;
		pageContents: PageContent[];
	}>;
	updateSetsOrder({
		fragmentCollectionKeys,
		onNetworkStatus,
		portletCategoryKeys,
	}: {
		fragmentCollectionKeys: string[] | null;
		onNetworkStatus: OnNetworkStatus;
		portletCategoryKeys: string[] | null;
	}): Promise<unknown>;
};
export default _default;
