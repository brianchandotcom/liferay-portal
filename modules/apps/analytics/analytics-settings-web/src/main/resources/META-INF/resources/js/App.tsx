/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useContext, useReducer} from 'react';

import DefaultPage from './pages/default/DefaultPage';
import WizardPage from './pages/wizard/WizardPage';
import {SPRITEMAP} from './utils/constants';

export type TData = {
	connected: boolean;
	liferayAnalyticsURL: string;
	pageView: EPageView;
	token: string;
	wizardMode: boolean;
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

export const initialState = {
	connected: false,
	liferayAnalyticsURL: '',
	pageView: EPageView.Wizard,
	token: '',
	wizardMode: true,
};

export const AppContextData = React.createContext<TData>(initialState);
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
	wizardMode: boolean;
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

const AppContextProvider: React.FC<IAppProps> = ({
	children,
	connected,
	liferayAnalyticsURL,
	token,
	wizardMode,
}) => {
	const [state, dispatch] = useReducer(reducer, {
		connected,
		liferayAnalyticsURL,
		pageView: wizardMode ? EPageView.Wizard : EPageView.Default,
		token,
	});

	return (
		<ClayTooltipProvider>
			<ClayIconSpriteContext.Provider value={SPRITEMAP}>
				<AppContextData.Provider value={state}>
					<AppContextDispatch.Provider value={dispatch}>
						{children}
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

const App: React.FC<IAppProps> = (props) => {
	return (
		<AppContextProvider {...props}>
			<div className="analytics-settings-web mt-5">
				<AppContent />
			</div>
		</AppContextProvider>
	);
};

export {AppContextProvider, useData, useDispatch};
export default App;
