/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

declare type Props = {
	fields: Array<{
		label: string;
		name: string;
		value: string;
	}>;
	url: string;
};
export default function SeparatorFields({fields, url}: Props): JSX.Element;
export {};
