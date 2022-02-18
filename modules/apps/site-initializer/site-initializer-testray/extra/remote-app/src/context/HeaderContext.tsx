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

import {useQuery} from '@apollo/client';
import {createContext, useEffect, useReducer} from 'react';

import {getTestrayProjects} from '../graphql/queries';
import {Liferay} from '../services/liferay/liferay';
import {ActionMap} from '../types';

export type HeaderTabs = {
	active: boolean;
	path: string;
	title: string;
};

export type HeaderTitle = {
	category: string;
	path?: string;
	title: string;
};

type InitialState = {
	dropdown: Dropdown;
	heading: HeaderTitle[];
	tabs: HeaderTabs[];
};

export type Dropdown = {
	sections: [
		{
			items: [
				{
					icon: string;
					label: string;
					path: string;
				}
			];
			title: string;
		}
	];
};

export const initialState: InitialState = {
	dropdown: {
		sections: [
			{
				items: [
					{
						icon: 'user',
						label: 'Manage Accounts',
						path: '/manage/user',
					},
				],
				title: '',
			},
		],
	},

	heading: [
		{
			category: 'PROJECT',
			title: 'Project Directory',
		},
	],
	tabs: [],
};

export const HeaderContext = createContext<
	[InitialState, (param: AppActions) => void]
>([initialState, () => null]);

export enum HeaderTypes {
	SET_DROPDOWN = 'SET_DROPDOWN',
	SET_HEADING = 'SET_HEADING',
	SET_RESET_HEADER = 'SET_RESET_HEADER',
	SET_TABS = 'SET_TABS',
}

export type HeaderActionsPayload = {
	[HeaderTypes.SET_DROPDOWN]: Dropdown;
	[HeaderTypes.SET_HEADING]: {append?: boolean; heading: HeaderTitle[]};
	[HeaderTypes.SET_RESET_HEADER]: null;
	[HeaderTypes.SET_TABS]: HeaderTabs[];
};

export type AppActions = ActionMap<HeaderActionsPayload>[keyof ActionMap<
	HeaderActionsPayload
>];

const reducer = (state: InitialState, action: AppActions): InitialState => {
	switch (action.type) {
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

		default:
			return state;
	}
};

const HeaderContextProvider: React.FC = ({children}) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	const variables = {scopeKey: Liferay.ThemeDisplay.getSiteGroupId()};

	const {data} = useQuery(getTestrayProjects, {
		variables,
	});

	const testrayProjects = data?.c?.testrayProjects?.items;

	useEffect(() => {
		if (testrayProjects) {
			const createObject = (elements: any): Dropdown => {
				return {
					sections: [
						{
							items: elements.map((element: any) => {
								return {
									icon: '',
									label: element.name,
									path: `${element.name}`,
								};
							}),
							title: '',
						},
					],
				};
			};

			dispatch({
				payload: createObject(testrayProjects),
				type: HeaderTypes.SET_DROPDOWN,
			});
		}
	}, [testrayProjects]);

	return (
		<HeaderContext.Provider value={[state, dispatch]}>
			{children}
		</HeaderContext.Provider>
	);
};

export default HeaderContextProvider;
