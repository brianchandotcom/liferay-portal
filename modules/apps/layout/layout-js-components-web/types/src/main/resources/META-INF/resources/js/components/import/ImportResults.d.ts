/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ImportResult {
	message: string;
	name: string;
	type: 'fragment' | 'composition';
}
export interface ImportResultsData {
	'imported': ImportResult[];
	'imported-draft': ImportResult[];
	'invalid': ImportResult[];
}
interface Props {
	fileName: string | null;
	importResults: ImportResultsData;
}
declare function ImportResults({fileName, importResults}: Props): JSX.Element;
export default ImportResults;
