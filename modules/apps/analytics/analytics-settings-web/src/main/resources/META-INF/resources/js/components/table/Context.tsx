/* eslint-disable lines-around-comment */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useContext, useReducer} from 'react';

import {DEFAULT_FILTER, TFilter} from '../../utils/filter';
import {DEFAULT_PAGINATION, TPagination} from '../../utils/pagination';
import {TColumnItem, TFormattedItems, TItem} from './types';
import {
	formattingItems,
	getGlobalChecked,
	selectFormattedItems,
	updateFormattedItems,
} from './utils';

export enum Events {
	ChangeFilter = 'CHANGE_FILTER',
	ChangeItem = 'CHANGE_ITEM',
	ChangeItems = 'CHANGE_ITEMS',
	ChangeKeywords = 'CHANGE_KEYWORDS',
	ChangePagination = 'CHANGE_PAGINATION',
	FormatData = 'FORMAT_DATA',
	Reload = 'RELOAD',
	ToggleGlobalCheckbox = 'TOGGLE_CHECKBOX',
}

const INITIAL_STATE: TState = {
	filter: DEFAULT_FILTER,
	formattedItems: {},
	globalChecked: false,
	internalKeywords: '',
	keywords: '',
	pagination: DEFAULT_PAGINATION,
	reload: () => {},
	rows: [],
};

type TState = {
	filter: TFilter;
	formattedItems: TFormattedItems;
	globalChecked: boolean;
	internalKeywords: string;
	keywords: string;
	pagination: TPagination;
	reload: () => void;
	rows: string[];
};

type TAction = {payload: any; type: Events};

const TableContextData = createContext<TState>(INITIAL_STATE);
const TableContextDispatch = createContext<any>(null);

const useData = () => useContext(TableContextData);
const useDispatch = () => useContext(TableContextDispatch);

function reducer(state: TState, action: TAction) {
	switch (action.type) {
		case Events.ChangeFilter: {
			return {
				...state,
				filter: {
					...state.filter,
					...action.payload,
				},
			};
		}
		case Events.ChangeItem: {
			const {
				columns,
				id,
			}: {
				columns: {
					column: TColumnItem;
					index: number;
				}[];
				id: string;
			} = action.payload;

			const newColumns = [...state.formattedItems[id].columns];

			columns.forEach(({column, index}) => {
				newColumns[index] = {
					...newColumns[index],
					...column,
				};
			});

			return {
				...state,
				formattedItems: {
					...state.formattedItems,
					[id]: {
						...state.formattedItems[id],
						columns: newColumns,
					},
				},
			};
		}
		case Events.ChangeItems: {
			const {formattedItems, rows} = state;

			const newFormattedItems = {
				...formattedItems,
				[action.payload]: {
					...formattedItems[action.payload],
					checked: !formattedItems[action.payload].checked,
				},
			};

			return {
				...state,
				formattedItems: newFormattedItems,
				globalChecked: getGlobalChecked(
					selectFormattedItems(newFormattedItems, rows)
				),
			};
		}
		case Events.ChangeKeywords: {
			return {
				...state,
				keywords: action.payload,
				pagination: {
					...state.pagination,
					page: 1,
				},
			};
		}
		case Events.FormatData: {
			const {formattedItems, pagination} = state;
			const {items, page, pageSize, totalCount} = action.payload;
			const rows = items.map(({id}: TItem) => id);

			// It is necessary to maintain the formatting order of
			// items so that state items override request items

			const newFormattedItems = {
				...formattingItems(items),
				...formattedItems,
			};

			return {
				...state,
				formattedItems: newFormattedItems,
				globalChecked: getGlobalChecked(
					selectFormattedItems(newFormattedItems, rows)
				),
				pagination: {
					maxCount: pagination.maxCount || totalCount,
					page,
					pageSize,
					totalCount,
				},
				rows,
			};
		}
		case Events.ChangePagination: {
			return {
				...state,
				pagination: {
					...state.pagination,
					...action.payload,
				},
			};
		}
		case Events.Reload: {
			return {
				...state,
				reload: action.payload,
			};
		}
		case Events.ToggleGlobalCheckbox: {
			const {formattedItems, rows} = state;
			const newValue = !state.globalChecked;

			return {
				...state,
				formattedItems: {
					...formattedItems,
					...updateFormattedItems(
						selectFormattedItems(formattedItems, rows),
						newValue
					),
				},
				globalChecked: newValue,
			};
		}
		default:
			throw new Error();
	}
}

interface ITableContextProps {
	initialFilter?: TFilter;
	initialKeywords?: string;
	initialPagination?: TPagination;
}

const TableContext: React.FC<ITableContextProps> = ({
	children,
	initialFilter,
	initialKeywords,
	initialPagination,
}) => {
	const [state, dispatch] = useReducer<React.Reducer<TState, TAction>>(
		reducer,
		{
			...INITIAL_STATE,
			...(initialFilter && {filter: initialFilter}),
			...(initialKeywords && {keywords: initialKeywords}),
			...(initialPagination && {pagination: initialPagination}),
		}
	);

	return (
		<TableContextData.Provider value={state}>
			<TableContextDispatch.Provider value={dispatch}>
				{children}
			</TableContextDispatch.Provider>
		</TableContextData.Provider>
	);
};

export {useData, useDispatch};
export default TableContext;
