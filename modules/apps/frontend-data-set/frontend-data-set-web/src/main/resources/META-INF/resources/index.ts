/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TRenderer} from './FrontendDataSetContext';

export declare function FrontendDataSet({
	actionParameterName,
	activeViewSettings,
	apiURL,
	appURL,
	bulkActions,
	creationMenu,
	currentURL,
	customDataRenderers,
	customViews,
	customViewsEnabled,
	emptyState,
	filters,
	formId,
	formName,
	header,
	id,
	inlineAddingSettings,
	inlineEditingSettings,
	items,
	itemsActions,
	namespace,
	nestedItemsKey,
	nestedItemsReferenceKey,
	onActionDropdownItemClick,
	onBulkActionItemClick,
	onSelect,
	overrideEmptyResultView,
	pagination,
	portletId,
	selectedItems,
	selectedItemsKey,
	selectionType,
	showManagementBar,
	showPagination,
	showSearch,
	sidePanelId,
	sorts,
	style,
	views,
}: IFrontendDataSetProps): JSX.Element;

export declare function DateTimeRenderer({
	options,
	value,
}: DateTimeRendererProps): string;

type DateTimeRendererProps = {
	options?: {
		format: {
			day?: string;
			hour?: string;
			minute?: string;
			month?: string;
			second?: string;
			timeZone?: string;
			year?: string;
		};
	};
	value: string;
};

type TDelta = {
	href?: string;
	label: number;
};

export interface IInlineEditingSettings {
	alwaysOn: boolean;
	defaultBodyContent: object;
}

export interface IActionsDropdown extends IBaseActions {
	loading: boolean;
	menuActive?: boolean;
	onClick: Function;
	onMenuActiveChange?: Function;
	setLoading: Function;
}

export interface IBaseActions {
	actions: IItemsActions[];
	itemData: any;
	itemId: number | string;
}

export interface IItemsActions {
	data?: IItemActionsData;
	href?: string;
	icon?: string;
	id?: string | number;
	items?: IItemsActions[];
	label?: string;
	method?: string;
	onClick?: Function;
	separator?: boolean;
	target?:
		| 'async'
		| 'blank'
		| 'headless'
		| 'inlineEdit'
		| 'link'
		| 'modal'
		| 'modal-permissions'
		| 'sidePanel'
		| 'event';
	type?: string;
}

export interface IItemActionsData {
	confirmationMessage?: string;
	disableHeader?: boolean;
	errorMessage?: string;
	id?: string | number;
	method?: 'delete' | 'get' | 'patch' | 'post';
	permissionKey?: string;
	requestBody?: string;
	size?: 'sm' | 'lg' | 'full-screen';
	status?: string;
	successMessage?: string;
	title?: string;
}

export interface IQuickActions extends IBaseActions {
	onClick: Function;
}

export type TSort = {
	active?: boolean;
	default?: boolean;
	direction?: 'asc' | 'desc';
	key?: string;
	label?: string;
};

type TViews = {
	component?: any;
	contentRenderer?: string;
	contentRendererClientExtension?: boolean;
	contentRendererModuleURL?: string;
	label?: string;
	name?: string;
	schema?: object;
	thumbnail?: string;
};

export interface IFrontendDataSetProps {
	actionParameterName?: string;
	activeViewSettings?: string;
	apiURL?: string;
	appURL?: string;
	bulkActions?: any[];
	creationMenu?: {
		primaryItems?: any[];
		secondaryItems?: any[];
	};
	currentURL?: string;
	customDataRenderers?: any;
	customRenderers?: {tableCell: Array<TRenderer>};
	customViews?: string;
	customViewsEnabled?: boolean;
	emptyState?: {
		description?: string;
		image?: string;
		title?: string;
	};
	enableInlineAddModeSetting?: {
		defaultBodyContent?: object;
	};
	filters?: any;
	formId?: string;
	formName?: string;
	header?: {
		title?: string;
	};
	id: string;
	inlineAddingSettings?: {
		apiURL: string;
		defaultBodyContent: object;
	};
	inlineEditingSettings?: IInlineEditingSettings;
	items?: any[];
	itemsActions?: IItemsActions[];
	namespace?: string;
	nestedItemsKey?: string;
	nestedItemsReferenceKey?: string;
	onActionDropdownItemClick?: any;
	onBulkActionItemClick?: any;
	onSelect?: Function;
	overrideEmptyResultView?: boolean;
	pagination?: {
		deltas?: TDelta[];
		initialDelta?: number;
		initialPageNumber?: number;
	};
	portletId?: string;
	selectedItems?: any[];
	selectedItemsKey?: string;
	selectionType?: 'single' | 'multiple';
	showManagementBar?: boolean;
	showPagination?: boolean;
	showSearch?: boolean;
	sidePanelId?: string;
	sorts?: TSort[];
	style?: 'default' | 'fluid' | 'stacked';
	views: TViews[];
	viewsTitle?: string;
}

export {
	IClientExtensionRenderer,
	IInternalRenderer,
} from './FrontendDataSetContext';
export {INTERNAL_CELL_RENDERERS as FDS_INTERNAL_CELL_RENDERERS} from './cell_renderers/InternalCellRenderer';
export {
	FDS_ARRAY_FIELD_NAME_DELIMITER,
	FDS_ARRAY_FIELD_NAME_PARENT_SUFFIX,
	FDS_NESTED_FIELD_NAME_DELIMITER,
	FDS_NESTED_FIELD_NAME_PARENT_SUFFIX,
} from './constants';
