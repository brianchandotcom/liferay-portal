/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Rule} from './Rule';
export interface Props {
	categorySelectorURL: string;
	groupIds: string;
	id: string;
	namespace: string;
	rules: Rule[];
	tagSelectorURL: string;
	vocabularyIds: string;
}
export default function AutoField({
	categorySelectorURL,
	groupIds,
	id,
	namespace,
	rules: initialRules,
	tagSelectorURL,
	vocabularyIds,
}: Props): JSX.Element;
