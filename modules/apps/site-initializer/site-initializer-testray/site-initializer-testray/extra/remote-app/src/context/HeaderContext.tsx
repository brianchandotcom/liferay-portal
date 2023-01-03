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

import {ReactNode, createContext, useEffect, useReducer} from 'react';

import {useFetch} from '../hooks/useFetch';
import i18n from '../i18n';
import {APIResponse, TestrayDispatchTrigger} from '../services/rest';
import {testrayDispatchTriggerImpl} from '../services/rest/TestrayDispatchTrigger';
import {Action, ActionMap} from '../types';

export type HeaderActions = {
	actions: Action[];
	item?: any;
	mutate?: any;
};

type DropdownItem = {
	divider?: boolean;
	icon?: string;
	label: string;
	onClick?: () => void;
	path?: string;
};

type DropdownSection = {
	items: DropdownItem[];
	title?: string;
};

export type Dropdown = DropdownSection[];

export type HeaderTabs = {
	active: boolean;
	path: string;
	title: string;
};

export type HeaderTitle = {
	category?: string;
	path?: string;
	title: string;
};

type InitialState = {
	dropdown: Dropdown;
	headerActions: HeaderActions;
	heading: HeaderTitle[];
	symbol: string;
	tabs: HeaderTabs[];
	testrayDispatchTriggers: APIResponse<TestrayDispatchTrigger>;
};

export const initialState: InitialState = {
	dropdown: [],
	headerActions: {actions: []},
	heading: [
		{
			category: i18n.translate('project'),
			title: i18n.translate('project-directory'),
		},
	],
	symbol: '',
	tabs: [],
	testrayDispatchTriggers: {
		actions: {},
		facets: [],
		items: [],
		lastPage: 1,
		page: 1,
		pageSize: 1,
		totalCount: 1,
	},
};

export const HeaderContext = createContext<
	[InitialState, (param: AppActions) => void]
>([initialState, () => null]);

export enum HeaderTypes {
	SET_DROPDOWN = 'SET_DROPDOWN',
	SET_HEADER_ACTIONS = 'SET_HEADER_ACTIONS',
	SET_HEADING = 'SET_HEADING',
	SET_RESET_HEADER = 'SET_RESET_HEADER',
	SET_SYMBOL = 'SET_SYMBOL',
	SET_TABS = 'SET_TABS',
}

export type HeaderActionsPayload = {
	[HeaderTypes.SET_DROPDOWN]: Dropdown;
	[HeaderTypes.SET_HEADER_ACTIONS]: HeaderActions;
	[HeaderTypes.SET_HEADING]: {append?: boolean; heading: HeaderTitle[]};
	[HeaderTypes.SET_RESET_HEADER]: null;
	[HeaderTypes.SET_SYMBOL]: string;
	[HeaderTypes.SET_TABS]: HeaderTabs[];
};

export type AppActions = ActionMap<HeaderActionsPayload>[keyof ActionMap<
	HeaderActionsPayload
>];

const reducer = (state: InitialState, action: AppActions): InitialState => {
	switch (action.type) {
		case HeaderTypes.SET_HEADER_ACTIONS: {
			return {
				...state,
				headerActions: action.payload,
			};
		}

		case HeaderTypes.SET_DROPDOWN: {
			return {
				...state,
				dropdown: action.payload,
			};
		}

		case HeaderTypes.SET_TABS: {
			return {
				...state,
				tabs: action.payload,
			};
		}

		case HeaderTypes.SET_HEADING: {
			const {append, heading} = action.payload;

			return {
				...state,
				heading: append ? [...state.heading, ...heading] : heading,
			};
		}

		case HeaderTypes.SET_RESET_HEADER: {
			return initialState;
		}

		case HeaderTypes.SET_SYMBOL: {
			return {
				...state,
				symbol: action.payload,
			};
		}

		default:
			return state;
	}
};

const HeaderContextProvider: React.FC<{children: ReactNode}> = ({children}) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	const {data: testrayDispatchTriggers} = useFetch<
		APIResponse<TestrayDispatchTrigger>
	>(testrayDispatchTriggerImpl.resource, {
		aggregationTerms: 'dueStatus',
		pageSize: 10,
		sort: 'dateCreated:asc',
	});

	useEffect(() => {
		const {title} = state.heading[state.heading.length - 1];

		document.title = title;
	}, [state.heading]);

	return (
		<HeaderContext.Provider
			value={[
				{
					...state,
					testrayDispatchTriggers: testrayDispatchTriggers as APIResponse<
						TestrayDispatchTrigger
					>,
				},
				dispatch,
			]}
		>
			{children}
		</HeaderContext.Provider>
	);
};

export default HeaderContextProvider;
