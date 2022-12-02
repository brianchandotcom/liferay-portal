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

import React, {createContext, useContext, useReducer} from 'react';

import {DEFAULT_FILTER, TFilter} from '../../utils/filter';
import {DEFAULT_PAGINATION, TPagination} from '../../utils/pagination';
import {TFormattedItems, TItem} from './types';
import {
	getFormattedItems,
	getGlobalChecked,
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
				id,
				values,
			}: {
				id: string;
				values: {
					id: string;
					value: number | boolean | string;
				}[];
			} = action.payload;

			return {
				...state,
				formattedItems: {
					...state.formattedItems,
					[id]: {
						...state.formattedItems[id],
						columns: state.formattedItems[id].columns.map(
							(column) => {
								for (const {id, value} of values) {
									if (column.id === id) {
										return {
											...column,
											value,
										};
									}
								}

								return column;
							}
						),
					},
				},
			};
		}
		case Events.ChangeItems: {
			const formattedItems = {
				...state.formattedItems,
				[action.payload]: {
					...state.formattedItems[action.payload],
					checked: !state.formattedItems[action.payload].checked,
				},
			};

			return {
				...state,
				formattedItems,
				globalChecked: getGlobalChecked(formattedItems),
			};
		}
		case Events.ChangeKeywords: {
			return {
				...state,
				keywords: action.payload,
			};
		}
		case Events.FormatData: {
			const {items, page, pageSize, totalCount} = action.payload;

			// It is necessary to maintain the formatting order of
			// items so that state items override request items

			const formattedItems = {
				...getFormattedItems(items),
				...state.formattedItems,
			};

			return {
				...state,
				formattedItems,
				globalChecked: getGlobalChecked(formattedItems),
				pagination: {
					maxCount: state.pagination.maxCount || totalCount,
					page,
					pageSize,
					totalCount,
				},
				rows: items.map(({id}: TItem) => id),
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
			const {globalChecked, items} = action.payload;

			return {
				...state,
				formattedItems: updateFormattedItems(
					{
						...getFormattedItems(items),
						...state.formattedItems,
					},
					globalChecked
				),
				globalChecked,
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
