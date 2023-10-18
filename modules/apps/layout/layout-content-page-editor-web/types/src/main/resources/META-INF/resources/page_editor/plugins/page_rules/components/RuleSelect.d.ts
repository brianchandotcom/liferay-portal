/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface RuleSelectProps<T> {
	items: ReadonlyArray<{
		label: string;
		value: T;
	}>;
	onSelectionChange: (selection: T) => void;
	selectedKey?: string;
}
export default function RuleSelect<T extends string>({
	items,
	onSelectionChange,
	selectedKey,
}: RuleSelectProps<T>): JSX.Element;
export {};
