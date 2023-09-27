/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {AssetTagsSelector} from 'asset-taglib';
import React from 'react';

import {BaseRule} from './Rule';

export interface Tag {
	label: string;
	value: string;
}

export interface TagRule extends BaseRule {
	queryValues?: string;
	type: 'assetTags';
}

interface Props {
	groupIds: string;
	index: number;
	namespace: string;
	onChange: (tags: Tag[]) => void;
	rule: TagRule;
	tagSelectorURL: string;
}

export function TagSelector({
	groupIds,
	index = 0,
	namespace,
	onChange,
	rule,
	tagSelectorURL,
}: Props) {
	const groupIdsList =
		groupIds && !!groupIds.length ? groupIds.split(',') : [];

	const selectedItems =
		rule.queryValues && !!rule.queryValues.length
			? rule.queryValues.split(',').map((tagName) => ({
					label: tagName,
					value: tagName,
			  }))
			: [];

	return (
		<div className="d-inline-block tag-selector">
			<input
				name={`${namespace}queryTagNames${index}`}
				type="hidden"
				value={rule.queryValues}
			/>

			<AssetTagsSelector
				groupIds={groupIdsList}
				onSelectedItemsChange={onChange}
				portletURL={tagSelectorURL}
				selectedItems={selectedItems}
				showLabel={false}
				showSelectButton
				showSubtitle={false}
				subtitle=""
			/>
		</div>
	);
}
