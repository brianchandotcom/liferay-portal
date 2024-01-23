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
import {config} from '../config/index';
import {PageContent} from '../utils/usePageContents';
import draftServiceFetch, {OnNetworkStatus} from './draftServiceFetch';
import serviceFetch from './serviceFetch';

export default {
	addComment({
		body,
		fragmentEntryLinkId,
		onNetworkStatus,
		parentCommentId,
	}: {
		body: string;
		fragmentEntryLinkId: string;
		onNetworkStatus: OnNetworkStatus;
		parentCommentId?: string;
	}) {
		const fetchBody: {
			body: string;
			fragmentEntryLinkId: string;
			parentCommentId?: string;
		} = {
			body,
			fragmentEntryLinkId,
		};

		if (parentCommentId) {
			fetchBody.parentCommentId = parentCommentId;
		}

		return draftServiceFetch<FragmentEntryLinkComment>(
			config.addFragmentEntryLinkCommentURL,
			{body: fetchBody},
			onNetworkStatus
		);
	},

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
		previewImageURL?: string;
		saveInlineContent: boolean;
		saveMappingConfiguration: boolean;
		segmentsExperienceId: string;
	}) {
		return draftServiceFetch<{
			fragmentComposition: FragmentComposition;
			url: string;
		}>(
			config.addFragmentCompositionURL,
			{
				body: {
					description,
					fragmentCollectionId,
					itemId,
					name,
					previewImageURL,
					saveInlineContent,
					saveMappingConfiguration,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

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
	}) {
		return draftServiceFetch<{
			addedItemId: string;
			fragmentEntryLink: FragmentEntryLink;
			layoutData: LayoutData;
		}>(
			config.addFragmentEntryLinkURL,
			{
				body: {
					fragmentEntryKey,
					groupId,
					parentItemId,
					position,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

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
	}) {
		return draftServiceFetch<{
			addedItemId: string;
			fragmentEntryLinks: FragmentEntryLinkMap;
			layoutData: LayoutData;
		}>(
			config.addFragmentEntryLinksURL,
			{
				body: {
					fragmentEntryKey,
					groupId,
					parentItemId,
					position,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

	deleteComment({
		commentId,
		onNetworkStatus,
	}: {
		commentId: string;
		onNetworkStatus: OnNetworkStatus;
	}) {
		return draftServiceFetch<void>(
			config.deleteFragmentEntryLinkCommentURL,
			{body: {commentId}},
			onNetworkStatus
		);
	},

	duplicateItem({
		itemId,
		onNetworkStatus,
		segmentsExperienceId,
	}: {
		itemId: string;
		onNetworkStatus: OnNetworkStatus;
		segmentsExperienceId: string;
	}) {
		return draftServiceFetch<{
			duplicatedFragmentEntryLinks: FragmentEntryLink[];
			duplicatedItemId: string;
			layoutData: LayoutData;
			restrictedItemIds: string[];
		}>(
			config.duplicateItemURL,
			{
				body: {
					itemId,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

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
	}) {
		return draftServiceFetch<FragmentEntryLinkComment>(
			config.editFragmentEntryLinkCommentURL,
			{
				body: {
					body,
					commentId,
					resolved,
				},
			},
			onNetworkStatus
		);
	},

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
		itemClassPK?: string | null;
		itemExternalReferenceCode?: string | null;
		languageId: Liferay.Language.Locale;
		segmentsExperienceId: string;
	}) {
		const body: {
			fragmentEntryLinkId: string;
			itemClassName: string;
			itemClassPK?: string;
			itemExternalReferenceCode?: string;
			languageId: Liferay.Language.Locale;
			segmentsExperienceId: string;
		} = {
			fragmentEntryLinkId,
			itemClassName,
			languageId,
			segmentsExperienceId,
		};

		if (itemClassPK) {
			body.itemClassPK = itemClassPK;
		}

		if (itemExternalReferenceCode) {
			body.itemExternalReferenceCode = itemExternalReferenceCode;
		}

		return serviceFetch<{content: string}>(config.renderFragmentEntryURL, {
			body,
		});
	},

	toggleFragmentHighlighted({
		fragmentEntryKey,
		groupId = '0',
		highlighted,
		onNetworkStatus,
	}: {
		fragmentEntryKey: string;
		groupId?: string;
		highlighted: boolean;
		onNetworkStatus: OnNetworkStatus;
	}) {
		return draftServiceFetch<{highlightedFragments: FragmentEntry[]}>(
			config.updateFragmentsHighlightedConfigurationURL,
			{
				body: {
					fragmentEntryKey,
					groupId,
					highlighted,
				},
			},
			onNetworkStatus
		);
	},

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
	}) {
		return draftServiceFetch<{
			fragmentEntryLink: FragmentEntryLink;
			layoutData: LayoutData;
			pageContents: PageContent[];
		}>(
			config.updateConfigurationValuesURL,
			{
				body: {
					editableValues: JSON.stringify(editableValues),
					fragmentEntryLinkId,
					languageId,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

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
	}) {
		return draftServiceFetch<{
			fragmentEntryLink: FragmentEntryLink;
			pageContents: PageContent[];
		}>(
			config.editFragmentEntryLinkURL,
			{
				body: {
					editableValues: JSON.stringify(editableValues),
					fragmentEntryLinkId,
					languageId,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

	updateSetsOrder({
		fragmentCollectionKeys,
		onNetworkStatus,
		portletCategoryKeys,
	}: {
		fragmentCollectionKeys: string[] | null;
		onNetworkStatus: OnNetworkStatus;
		portletCategoryKeys: string[] | null;
	}) {
		return draftServiceFetch(
			config.updateFragmentPortletSetsSortURL,
			{
				body: {
					fragmentCollectionKeys:
						JSON.stringify(fragmentCollectionKeys) || null,
					portletCategoryKeys:
						JSON.stringify(portletCategoryKeys) || null,
				},
			},
			onNetworkStatus
		);
	},
};
