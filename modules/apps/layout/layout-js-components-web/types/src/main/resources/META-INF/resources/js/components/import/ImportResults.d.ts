/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface Result {
	message: string;
	name: string;
	type: 'fragment' | 'composition';
}
export interface Results {
	'imported': Result[];
	'imported-draft': Result[];
	'invalid': Result[];
}
interface ResultsProps {
	fileName: string | null;
	importResults: Results;
}
export default function ImportResults({
	fileName,
	importResults,
}: ResultsProps): JSX.Element;
export declare function getResultsText(importResults: Results): string;
export {};
