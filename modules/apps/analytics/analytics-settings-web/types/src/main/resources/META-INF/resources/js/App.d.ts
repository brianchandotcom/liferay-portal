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

import React from 'react';
declare type TData = {
	connected: boolean;
	liferayAnalyticsURL: string;
	pageView: EPageView;
	token: string;
};
declare type TView = {
	[key in EPageView]: React.FC;
};
export declare enum EPageView {
	Wizard = 'VIEW_WIZARD_MODE',
	Default = 'VIEW_DEFAULT_MODE',
}
export declare const View: TView;
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
}
declare const App: React.FC<IAppProps>;
export {useData, useDispatch};
export default App;
