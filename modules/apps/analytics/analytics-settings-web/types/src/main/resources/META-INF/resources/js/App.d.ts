/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
export declare type TData = {
	connected: boolean;
	liferayAnalyticsURL: string;
	pageView: EPageView;
	token: string;
	wizardMode: boolean;
};
declare type TView = {
	[key in EPageView]: React.FC;
};
export declare enum EPageView {
	Wizard = 'VIEW_WIZARD_MODE',
	Default = 'VIEW_DEFAULT_MODE',
}
export declare const View: TView;
export declare const initialState: {
	connected: boolean;
	liferayAnalyticsURL: string;
	pageView: EPageView;
	token: string;
	wizardMode: boolean;
};
export declare const AppContextData: React.Context<TData>;
declare const useData: () => TData;
declare const useDispatch: () => any;
export declare enum Events {
	Connect = 'CONNECT',
	ChangePageView = 'CHANGE_PAGE_VIEW',
}
interface IAppProps extends React.HTMLAttributes<HTMLElement> {
	connected: boolean;
	liferayAnalyticsURL: string;
	token: string;
	wizardMode: boolean;
}
declare const AppContextProvider: React.FC<IAppProps>;
declare const App: React.FC<IAppProps>;
export {AppContextProvider, useData, useDispatch};
export default App;
