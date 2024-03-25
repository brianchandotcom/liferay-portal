/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface SearchResultsProps {
	commerceAccountId: string;
	groupId: string;
	plid: string;
	searchURL: string;
}
export default function SearchResults({
	commerceAccountId,
	groupId,
	plid,
	searchURL,
}: SearchResultsProps): JSX.Element | null;
export {};
