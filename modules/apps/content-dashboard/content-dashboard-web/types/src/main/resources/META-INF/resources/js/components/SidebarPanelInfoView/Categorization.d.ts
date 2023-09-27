/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

declare const Categorization: ({
	tags,
	vocabularies,
}: IProps) => JSX.Element | null;
export interface Vocabulary {
	categories: string[];
	groupName: string;
	isPublic: boolean;
	vocabularyName: string;
}
interface Vocabularies {
	[id: string]: Vocabulary;
}
interface IProps {
	tags: string[];
	vocabularies: Vocabularies;
}
export default Categorization;
