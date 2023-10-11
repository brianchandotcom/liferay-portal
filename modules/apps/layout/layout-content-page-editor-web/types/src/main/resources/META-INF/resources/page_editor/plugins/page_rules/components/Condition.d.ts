/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Fetcher} from '../../../app/utils/useCache';
export interface Condition {
	condition?: 'user' | 'role' | 'segment';
	id: string;
	type: 'user' | undefined;
	value?: string;
}
interface ConditionProps {
	condition: Condition;
	fetcher: Fetcher;
	onConditionChange: (condition: Condition) => void;
	onDeleteCondition: () => void;
}
export default function Condition({
	condition,
	fetcher,
	onConditionChange,
	onDeleteCondition,
}: ConditionProps): JSX.Element;
export {};
