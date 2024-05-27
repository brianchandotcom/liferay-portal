/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-nocheck

import {
	getCollectionFilterValue,
	setCollectionFilterValue,
} from '@liferay/fragment-renderer-collection-filter-impl';
import {AssetTagsSelector} from 'asset-taglib';
import React, {useCallback, useState} from 'react';

interface IProps {
	disabled: boolean;
	fragmentEntryLinkId: string;
	groupIds: Array<string>;
	helpText: string;
	label: string;
	showLabel: boolean;
	targetCollections: Array<string>;
}

export function SelectTags({
	disabled,
	fragmentEntryLinkId,
	groupIds,
	helpText,
	label,
	showLabel,
	targetCollections,
}: IProps) {
	const [inputValue, setInputValue] = useState('');

	const [selectedItems, setSelectedItems] = useState(() => {
		const value = getCollectionFilterValue('tags', fragmentEntryLinkId);

		if (Array.isArray(value)) {
			return value.map((tagId) => ({label: tagId, value: tagId}));
		}
		else if (value) {
			return [{label: value, value}];
		}

		return [];
	});

	const updateSelectedItems = useCallback(
		(nextItems: Array<{label: string; value: string}>) => {
			setSelectedItems(nextItems);

			setCollectionFilterValue(
				'tags',
				fragmentEntryLinkId,
				nextItems.map((tag) => tag.value),
				targetCollections
			);
		},
		[fragmentEntryLinkId, targetCollections]
	);

	return (
		<AssetTagsSelector
			formGroupClassName="mb-0"
			groupIds={groupIds}
			helpText={helpText}
			inputValue={inputValue}
			label={label}
			onInputValueChange={disabled ? () => {} : setInputValue}
			onSelectedItemsChange={updateSelectedItems}
			selectedItems={selectedItems}
			showLabel={showLabel}
			showSelectButton={false}
		/>
	);
}
