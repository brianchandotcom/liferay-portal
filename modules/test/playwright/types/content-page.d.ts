/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type FragmentField = {
	value: {
		text: {
			mapping: {
				fieldKey: string;
				itemReference: {
					contextSource: string;
				};
			};
		};
	};
};

type Layout = {
	friendlyUrlPath: string;
};

type PageDefinition = {
	pageElement: PageElement;
};

type PageElement = {
	definition?: {
		collectionConfig?: {
			collectionReference: {
				classPK: string;
			};
			collectionType: 'Collection';
		};
		fragment?: {
			key: string;
		};
		fragmentConfig?: Record<string, string>;
		fragmentFields?: FragmentField[];
		layout?: {};
		numberOfColumns?: 1;
		numberOfItems?: 5;
		widgetInstance?: {
			widgetName: string;
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

type PagePermission = {
	actionKeys: PermissionActionKeys[];
	roleKey: 'Owner' | 'Site Member' | 'Guest';
};

type PermissionActionKeys =
	| 'ADD_DISCUSSION'
	| 'ADD_LAYOUT'
	| 'CONFIGURE_PORTLETS'
	| 'CUSTOMIZE'
	| 'DELETE'
	| 'DELETE_DISCUSSION'
	| 'LAYOUT_RULE_BUILDER'
	| 'PERMISSIONS'
	| 'PREVIEW_DRAFT'
	| 'UPDATE'
	| 'UPDATE_DISCUSSION'
	| 'UPDATE_LAYOUT_ADVANCED_OPTIONS'
	| 'UPDATE_LAYOUT_BASIC'
	| 'UPDATE_LAYOUT_CONTENT'
	| 'UPDATE_LAYOUT_LIMITED'
	| 'VIEW';

type SpacingType =
	| 'Margin Bottom'
	| 'Margin Left'
	| 'Margin Right'
	| 'Margin Top'
	| 'Padding Bottom'
	| 'Padding Left'
	| 'Padding Right'
	| 'Padding Top';

type StyleUnit = 'px' | '%' | 'em' | 'rem' | 'vw' | 'vh' | 'custom';
