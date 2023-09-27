/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../app/config/constants/editableTypes';
import {useSelector} from '../../../app/contexts/StoreContext';
import selectLanguageId from '../../../app/selectors/selectLanguageId';
import {selectPageContents} from '../../../app/selectors/selectPageContents';
import isMapped from '../../../app/utils/editable_value/isMapped';
import {getEditableLocalizedValue} from '../../../app/utils/getEditableLocalizedValue';
import getFragmentItem from '../../../app/utils/getFragmentItem';
import {hasRestrictedParent} from '../../../app/utils/hasRestrictedParent';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import NoPageContents from './NoPageContents';
import PageContents from './PageContents';

const getEditableTitle = (editable, languageId) => {
	const div = document.createElement('div');

	div.innerHTML = getEditableLocalizedValue(editable, languageId);

	return div.textContent.trim();
};

const getEditableValues = (
	fragmentEntryLinks,
	segmentsExperienceId,
	layoutData,
	restrictedItemIds
) =>
	Object.values(fragmentEntryLinks)
		.filter((fragmentEntryLink) => {
			if (Liferay.FeatureFlags['LPS-169923']) {
				const item = getFragmentItem(
					layoutData,
					fragmentEntryLink.fragmentEntryLinkId
				);

				if (
					item &&
					hasRestrictedParent(item, layoutData, restrictedItemIds)
				) {
					return;
				}
			}

			return (
				!fragmentEntryLink.masterLayout &&
				fragmentEntryLink.editableValues &&
				!fragmentEntryLink.removed &&
				fragmentEntryLink.segmentsExperienceId === segmentsExperienceId
			);
		})
		.map((fragmentEntryLink) => {
			const editableValues = Object.entries(
				fragmentEntryLink.editableValues[
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				] ?? {}
			);

			return editableValues
				.filter(
					([key, value]) =>
						(fragmentEntryLink.editableTypes[key] ===
							EDITABLE_TYPES.text ||
							fragmentEntryLink.editableTypes[key] ===
								EDITABLE_TYPES['rich-text']) &&
						!isMapped(value)
				)
				.map(([key, value]) => ({
					...value,
					editableId: `${fragmentEntryLink.fragmentEntryLinkId}-${key}`,
					type: fragmentEntryLink.editableTypes[key],
				}));
		})
		.reduce(
			(editableValuesA, editableValuesB) => [
				...editableValuesA,
				...editableValuesB,
			],
			[]
		);

const normalizeEditableValues = (editable, languageId) => {
	return {
		...editable,
		icon: 'align-left',
		title: getEditableTitle(editable, languageId),
	};
};

const normalizePageContents = (pageContents) =>
	pageContents.reduce(
		(acc, content) =>
			acc[content.type]
				? {...acc, [content.type]: [...acc[content.type], content]}
				: {...acc, [content.type]: [content]},
		{}
	);

export default function ContentsSidebar() {
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const languageId = useSelector(selectLanguageId);
	const layoutData = useSelector((state) => state.layoutData);
	const pageContents = useSelector(selectPageContents);
	const restrictedItemIds = useSelector((state) => state.restrictedItemIds);
	const segmentsExperienceId = useSelector(
		(state) => state.segmentsExperienceId
	);

	const inlineTextContents = useMemo(
		() =>
			getEditableValues(
				fragmentEntryLinks,
				segmentsExperienceId,
				layoutData,
				restrictedItemIds
			)
				.map((editable) =>
					normalizeEditableValues(editable, languageId)
				)
				.filter((editable) => editable.title),
		[
			fragmentEntryLinks,
			languageId,
			restrictedItemIds,
			segmentsExperienceId,
			layoutData,
		]
	);

	const contents = normalizePageContents(pageContents);

	const contentsWithInlineText = {
		...contents,
		...(inlineTextContents.length && {
			[Liferay.Language.get('inline-text')]: inlineTextContents,
		}),
	};

	const view = Object.keys(contentsWithInlineText).length ? (
		<PageContents pageContents={contentsWithInlineText} />
	) : (
		<NoPageContents />
	);

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('page-content')}
			</SidebarPanelHeader>

			<div className="d-flex flex-column page-editor__page-contents">
				{view}
			</div>
		</>
	);
}
