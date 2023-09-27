/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ManagementToolbarSearch.scss';
interface IProps {
	query: string;
	setQuery: (value: string) => void;
}
export declare function ManagementToolbarSearch({
	query,
	setQuery,
}: IProps): JSX.Element;
export {};
