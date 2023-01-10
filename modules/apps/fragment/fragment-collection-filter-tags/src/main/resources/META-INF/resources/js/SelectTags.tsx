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

import {
	getCollectionFilterValue,
	setCollectionFilterValue,
} from '@liferay/fragment-renderer-collection-filter-impl';
import {AssetTagsSelector} from 'asset-taglib';
import React, {useCallback, useState} from 'react';

interface IProps {
	disabled: boolean;
	fragmentEntryLinkId: string;
	helpText: string;
	label: string;
	showLabel: boolean;
}

export default function SelectTags({
	disabled,
	fragmentEntryLinkId,
	helpText,
	label,
	showLabel,
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
				nextItems.map((tag) => tag.value)
			);
		},
		[fragmentEntryLinkId]
	);

	return (
		<AssetTagsSelector
			formGroupClassName="mb-0"
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
