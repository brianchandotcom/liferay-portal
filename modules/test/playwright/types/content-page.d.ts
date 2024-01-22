/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
type ConfigurationSection =
	| 'Background'
	| 'Borders'
	| 'CSS'
	| 'Effects'
	| 'Frame'
	| 'Hide from Site Search Results'
	| 'Options'
	| 'Spacing'
	| 'Text';

type ConfigurationTab = 'Advanced' | 'General' | 'Styles';

type PageDefinition = {
	pageElement: PageElement;
};

type PageElement = {
	definition?: {
		fragment: {
			key: string;
		};
	};
	id: string;
	pageElements?: PageElement[];
	type:
		| 'Collection'
		| 'CollectionItem'
		| 'Column'
		| 'DropZone'
		| 'Form'
		| 'Fragment'
		| 'FragmentDropZone'
		| 'Root'
		| 'Row'
		| 'Section'
		| 'Widget';
};
