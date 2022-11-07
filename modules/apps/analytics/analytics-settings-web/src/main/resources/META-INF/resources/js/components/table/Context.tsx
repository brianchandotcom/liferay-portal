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
import {TItem} from './Table';

export enum Events {
	ChangeFilter = 'CHANGE_FILTER',
	ChangeItems = 'CHANGE_ITEMS',
	ChangeKeywords = 'CHANGE_KEYWORDS',
	ChangePagination = 'CHANGE_PAGINATION',
	FormatData = 'FORMAT_DATA',
	ToggleCheckbox = 'TOGGLE_CHECKBOX',
}

const initialState = {
	checked: false,
	filter: DEFAULT_FILTER,
	internalKeywords: '',
	items: [],
	keywords: '',
	pagination: DEFAULT_PAGINATION,
};

type TState = {
	checked: boolean;
	filter: TFilter;
	internalKeywords: string;
	items: TItem[];
	keywords: string;
	pagination: TPagination;
};

const TableContextData = createContext<TState>(initialState);
const TableContextDispatch = createContext<any>(null);

const useData = () => useContext(TableContextData);
const useDispatch = () => useContext(TableContextDispatch);

function reducer(state: TState, action: {payload: any; type: Events}) {
	switch (action.type) {
		case Events.ChangeFilter:
			return {
				...state,
				checked: false,
				filter: {
					...state.filter,
					...action.payload,
				},
			};
		case Events.ChangeItems:
			return {
				...state,
				checked: state.items.every(({checked}) => checked),
				items: action.payload,
			};
		case Events.ChangeKeywords:
			return {
				...state,
				checked: false,
				keywords: action.payload,
			};
		case Events.FormatData: {
			return {
				...state,
				...action.payload,
			};
		}
		case Events.ChangePagination:
			return {
				...state,
				checked: false,
				pagination: {
					...state.pagination,
					...action.payload,
				},
			};
		case Events.ToggleCheckbox:
			return {
				...state,
				checked: action.payload,
				items: state.items.map((item) => {
					if (!item.disabled) {
						return {
							...item,
							checked: action.payload,
						};
					}

					return item;
				}),
			};
		default:
			throw new Error();
	}
}

const TableContext: React.FC = ({children}) => {
	const [state, dispatch] = useReducer(reducer, initialState);

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
