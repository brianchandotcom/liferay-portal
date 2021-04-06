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
import {useSelector} from '../../../../../app/store/index';
import SidebarPanelContent from '../../../../../common/components/SidebarPanelContent';
import NoPageContents from './NoPageContents';
import PageContents from './PageContents';

const getEditableValues = (fragmentEntryLinks) =>
	Object.values(fragmentEntryLinks)
		.filter(
			(fragmentEntryLink) =>
				!fragmentEntryLink.masterLayout &&
				fragmentEntryLink.editableValues
		)
		.map((fragmentEntryLink) => {
			const editableValues = Object.entries(
				fragmentEntryLink.editableValues[
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				]
			);

			return editableValues.map(([key, value]) => ({
				...value,
				editableId: `${fragmentEntryLink.fragmentEntryLinkId}-${key}`,
			}));
		})
		.reduce(
			(editableValuesA, editableValuesB) => [
				...editableValuesA,
				...editableValuesB,
			],
			[]
		);

const normalizeEditableValues = (editable) => {
	return {
		...editable,
		icon: 'align-left',
		title: editable.title || editable.defaultValue,
	};
};

export default function ContentsSidebar() {
	const contents = [];
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const pageContents = useSelector((state) => state.pageContents);
	let view = <NoPageContents />;

	const inlineTextContents = useMemo(
		() =>
			getEditableValues(fragmentEntryLinks).map(normalizeEditableValues),
		[fragmentEntryLinks]
	);

	if (pageContents.length) {
		contents.push({
			items: pageContents,
			label: Liferay.Language.get('documents'),
		});
	}

	if (inlineTextContents.length) {
		contents.push({
			items: inlineTextContents,
			label: Liferay.Language.get('inline-text'),
		});
	}

	if (contents.length) {
		view = <PageContents pageContents={contents} />;
	}

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
