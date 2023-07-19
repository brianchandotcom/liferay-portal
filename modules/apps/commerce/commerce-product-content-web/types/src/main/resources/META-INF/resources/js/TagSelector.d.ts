/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

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
export declare function TagSelector({
	groupIds,
	index,
	namespace,
	onChange,
	rule,
	tagSelectorURL,
}: Props): JSX.Element;
export {};
