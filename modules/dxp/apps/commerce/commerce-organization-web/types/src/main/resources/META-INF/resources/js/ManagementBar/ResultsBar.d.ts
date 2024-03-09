/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

declare const ResultsBar: ({
	searchResult,
	searchResultCount,
	setSearchResult,
}: IResultsBar) => JSX.Element;
interface IResultsBar {
	searchResult: {
		id: number | null;
		name: string;
		type: string;
	};
	searchResultCount: number;
	setSearchResult: (p: {
		id: number | null;
		name: string;
		type: string;
	}) => void;
}
export default ResultsBar;
