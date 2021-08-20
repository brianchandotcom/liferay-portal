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

import React, {useMemo} from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../../app/config/constants/editableTypes';
import {useSelector} from '../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../app/selectors/selectLanguageId';
import {selectPageContents} from '../../../../../app/selectors/selectPageContents';
import isMapped from '../../../../../app/utils/editable-value/isMapped';
import {getEditableLocalizedValue} from '../../../../../app/utils/getEditableLocalizedValue';
import SidebarPanelContent from '../../../../../common/components/SidebarPanelContent';
import NoPageContents from './NoPageContents';
import PageContents from './PageContents';

const getEditableTitle = (editable, languageId) => {
	const div = document.createElement('div');

	div.innerHTML = getEditableLocalizedValue(editable, languageId);

	return div.textContent.trim();
};

const getEditableValues = (fragmentEntryLinks, segmentsExperienceId) =>
	Object.values(fragmentEntryLinks)
		.filter(
			(fragmentEntryLink) =>
				!fragmentEntryLink.masterLayout &&
				fragmentEntryLink.editableValues &&
				!fragmentEntryLink.removed &&
				fragmentEntryLink.segmentsExperienceId === segmentsExperienceId
		)
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
	const pageContents = useSelector(selectPageContents);
	const segmentsExperienceId = useSelector(
		(state) => state.segmentsExperienceId
	);

	const inlineTextContents = useMemo(
		() =>
			getEditableValues(fragmentEntryLinks, segmentsExperienceId)
				.map((editable) =>
					normalizeEditableValues(editable, languageId)
				)
				.filter((editable) => editable.title),
		[fragmentEntryLinks, languageId, segmentsExperienceId]
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
			<SidebarPanelContent
				className="page-editor__page-contents"
				padded={false}
			>
				{view}
			</SidebarPanelContent>
		</>
	);
}
