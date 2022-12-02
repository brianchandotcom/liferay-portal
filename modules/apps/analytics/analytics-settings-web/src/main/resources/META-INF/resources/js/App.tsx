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

import {ClayIconSpriteContext} from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useContext, useReducer} from 'react';

import DefaultPage from './pages/default/DefaultPage';
import WizardPage from './pages/wizard/WizardPage';
import {SPRITEMAP} from './utils/constants';

type TData = {
	connected: boolean;
	liferayAnalyticsURL: string;
	pageView: EPageView;
	token: string;
};

type TView = {
	[key in EPageView]: React.FC;
};

export enum EPageView {
	Wizard = 'VIEW_WIZARD_MODE',
	Default = 'VIEW_DEFAULT_MODE',
}

export const View: TView = {
	[EPageView.Wizard]: WizardPage,
	[EPageView.Default]: DefaultPage,
};

const initialState = {
	connected: false,
	liferayAnalyticsURL: '',
	pageView: EPageView.Wizard,
	token: '',
};

const AppContextData = React.createContext<TData>(initialState);
const AppContextDispatch = React.createContext<any>(null);

const useData = () => useContext(AppContextData);
const useDispatch = () => useContext(AppContextDispatch);

export enum Events {
	Connect = 'CONNECT',
	ChangePageView = 'CHANGE_PAGE_VIEW',
}

interface IAppProps extends React.HTMLAttributes<HTMLElement> {
	connected: boolean;
	liferayAnalyticsURL: string;
	token: string;
}

const AppContent = () => {
	const {pageView} = useData();

	const PageView = View[pageView];

	return (
		<div data-testid={pageView}>
			<PageView />
		</div>
	);
};

const App: React.FC<IAppProps> = ({connected, liferayAnalyticsURL, token}) => {
	const [state, dispatch] = useReducer(reducer, {
		connected,
		liferayAnalyticsURL,
		pageView: connected ? EPageView.Default : EPageView.Wizard,
		token,
	});

	return (
		<ClayTooltipProvider>
			<ClayIconSpriteContext.Provider value={SPRITEMAP}>
				<AppContextData.Provider value={state}>
					<AppContextDispatch.Provider value={dispatch}>
						<div className="analytics-settings-web mt-5">
							<AppContent />
						</div>
					</AppContextDispatch.Provider>
				</AppContextData.Provider>
			</ClayIconSpriteContext.Provider>
		</ClayTooltipProvider>
	);
};

function reducer(state: TData, action: {payload: any; type: Events}) {
	switch (action.type) {
		case Events.Connect: {
			return {
				...state,
				...action.payload,
			};
		}
		case Events.ChangePageView: {
			return {
				...state,
				pageView: action.payload,
			};
		}
		default:
			throw new Error();
	}
}

export {useData, useDispatch};
export default App;
