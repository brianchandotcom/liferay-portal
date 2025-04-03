/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {IInlineEditingSettings, IItemsActions, ISchema} from '.';

export interface IFrontendDataSetContext {
	actionParameterName?: string | null;
	allItemsSelectedActive: boolean;
	apiURL?: string;
	appURL?: string;
	applyItemInlineUpdates: Function;
	createInlineItem: Function;
	customDataRenderers?: Array<any>;
	customRenderers?: {
		tableCell?: Array<TRenderer>;
		views?: Array<TRenderer>;
	};
	executeAsyncItemAction: Function;
	formId?: string;
	formName?: string;
	highlightItems: Function;
	highlightedItemsValue?: string;
	id?: string;
	inlineAddingSettings?: {
		apiURL?: string;
		defaultBodyContent?: object;
	};
	inlineEditingSettings?: IInlineEditingSettings;
	itemsActions?: Array<IItemsActions>;
	itemsChanges?: Array<any>;
	loadData: Function;
	modalId?: string;
	namespace?: string;
	nestedItemsKey?: string;
	nestedItemsReferenceKey?: string;
	onActionDropdownItemClick: Function;
	onBulkActionItemClick: Function;
	onItemsChange: ({
		itemKey,
		items,
	}: {
		itemKey?: string;
		items: Array<any>;
	}) => void;
	onSearch: ({query}: {query: string}) => void;
	onSelect: Function;
	openModal: Function;
	openSidePanel: Function;
	portletId?: string;
	searchParam?: string;
	selectItems: Function;
	selectable?: boolean;
	selectedItemsKey?: string;
	selectedItemsValue?: Array<any>;
	selectionType?: string;
	sidePanelId?: string;
	sorts?: Array<TRenderer>;
	style?: string;
	toggleItemInlineEdit: Function;
	uniformActionsDisplay?: boolean;
	updateDataSetItems: Function;
	updateItem: Function;
}

export interface IHTMLElementBuilder {
	(args: any): HTMLElement;
}

export interface IClientExtensionRenderer {
	externalReferenceCode?: string;
	htmlElementBuilder?: IHTMLElementBuilder;
	name?: string;
	type: 'clientExtension';
	url?: string;
}

export interface IInternalRenderer {
	component: React.ComponentType<any>;
	default?: boolean;
	label?: string;
	name?: string;
	schema?: ISchema;
	symbol?: string;
	type: 'internal';
	url?: string;
}

export type TRenderer = IClientExtensionRenderer | IInternalRenderer;

const FrontendDataSetContext = React.createContext({
	allItemsSelectedActive: false,
	applyItemInlineUpdates: () => {},
	createInlineItem: () => {},
	executeAsyncItemAction: () => {},
	highlightItems: () => {},
	loadData: () => {},
	onActionDropdownItemClick: () => {},
	onBulkActionItemClick: () => {},
	onItemsChange: () => {},
	onSearch: () => {},
	onSelect: () => {},
	openModal: () => {},
	openSidePanel: () => {},
	selectItems: () => {},
	selectable: false,
	selectedItemsValue: [],
	toggleItemInlineEdit: () => {},
	updateDataSetItems: () => {},
	updateItem: () => {},
} as IFrontendDataSetContext);

export default FrontendDataSetContext;
