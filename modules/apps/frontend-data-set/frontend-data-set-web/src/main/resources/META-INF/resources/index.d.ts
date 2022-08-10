/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

export function FrontendDataSet({
	actionParameterName,
	activeViewSettings,
	apiURL,
	appURL,
	bulkActions,
	creationMenu,
	currentURL,
	customDataRenderers,
	customViewsEnabled,
	filters,
	formId,
	formName,
	id,
	initialSelectedItemsValues,
	inlineAddingSettings,
	inlineEditingSettings,
	items,
	itemsActions,
	namespace,
	nestedItemsKey,
	nestedItemsReferenceKey,
	onActionDropdownItemClick,
	onBulkActionItemClick,
	overrideEmptyResultView,
	pagination,
	portletId,
	selectedItemsKey,
	selectionType,
	showManagementBar,
	showPagination,
	showSearch,
	sidePanelId,
	sorting,
	style,
	views,
}: IFrontendDataSetProps): JSX.Element;

interface IFrontendDataSetProps {
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
	customViewsEnabled?: boolean;
	enableInlineAddModeSetting?: {
		defaultBodyContent?: object;
	};
	filters?: (
		| IAutocompleteFilter
		| ICustomFilter
		| ICheckboxFilter
		| IDateRangeFilter
		| IRadioFilter
	)[];
	formId?: string;
	formName?: string;
	id: string;
	initialSelectedItemsValues?: any[];
	inlineAddingSettings?: {
		apiURL: string;
		defaultBodyContent: object;
	};
	inlineEditingSettings?: [
		boolean,
		{
			alwaysOn: boolean;
			defaultBodyContent: object;
		}
	];
	items?: any[];
	itemsActions?: TItemsActions[];
	namespace?: string;
	nestedItemsKey?: string;
	nestedItemsReferenceKey?: string;
	onActionDropdownItemClick?: any;
	onBulkActionItemClick?: any;
	overrideEmptyResultView?: boolean;
	pagination?: {
		deltas?: [
			{
				href?: string;
				label: number;
			}
		];
		initialDelta: number;
	};
	portletId?: string;
	selectedItemsKey?: string;
	selectionType?: ['single', 'multiple'];
	showManagementBar?: boolean;
	showPagination?: boolean;
	showSearch?: boolean;
	sidePanelId?: string;
	sorting?: [
		{
			direction?: ['asc', 'desc'];
			key?: string;
		}
	];
	style?: ['default', 'fluid', 'stacked'];
	views: [
		{
			component?: any;
			contentRenderer?: string;
			contentRendererModuleURL?: string;
			label?: string;
			name?: string;
			schema?: object;
			thumbnail?: string;
		}
	];
}

type TItemsActions = {
	data?: {
		confirmationMessage?: string;
		id?: string;
		method?: 'delete' | 'get';
		permissionKey?: string;
	};
	href?: string;
	icon?: string;
	label?: string;
	target?: 'async' | 'headless' | 'link' | 'modal' | 'sidePanel';
};

type TFilters = {
	id: string;
	label: string;
	type:
		| 'autocomplete'
		| 'checkbox'
		| 'dateRange'
		| 'number'
		| 'radio'
		| 'text';
};

interface IAutocompleteFilter extends TFilters {
	apiURL: string;
	inputPlaceholder: string;
	itemKey: string;
	itemLabel: string;
	preloadedData: {
		exclude?: boolean;
		items?: object[];
	};
	type: 'autocomplete';
}

interface ICheckboxFilter extends TFilters {
	items: object[];
	preloadedData?: {
		exclude?: boolean;
		items?: object[];
	};
	type: 'checkbox';
}

type TDate = {
	day: number;
	month: number;
	year: number;
};

interface IDateRangeFilter extends TFilters {
	max?: TDate;
	min?: TDate;
	preloadedData?: {
		from: TDate;
		to: TDate;
	};
	type: 'dateRange';
}

interface IRadioFilter extends TFilters {
	items: object[];
	preloadedData?: {
		exclude?: boolean;
		itemsValues?: string[] | number[];
	};
	type: 'radio';
}

interface ICustomFilter extends Omit<TFilters, 'type'> {
	updateFilterState: (
		value?: any,
		formattedValue?: any,
		dataFilter?: string
	) => void;
	value?: string;
}
