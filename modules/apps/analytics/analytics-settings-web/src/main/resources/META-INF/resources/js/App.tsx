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
import React from 'react';

import DefaultPage from './pages/default/DefaultPage';
import WizardPage from './pages/wizard/WizardPage';

export const AppContext = React.createContext({
	connected: false,
	liferayAnalyticsURL: '',
	token: '',
});

export enum EPageView {
	Wizard = 'VIEW_WIZARD_MODE',
	Default = 'VIEW_DEFAULT_MODE',
}

type TView = {
	[key in EPageView]: React.FC;
};

const View: TView = {
	[EPageView.Wizard]: WizardPage,
	[EPageView.Default]: DefaultPage,
};

interface IAppProps extends React.HTMLAttributes<HTMLElement> {
	connected: boolean;
	liferayAnalyticsURL: string;
	token: string;
}

const App: React.FC<IAppProps> = ({connected, liferayAnalyticsURL, token}) => {
	const PageView: React.FC =
		View[connected ? EPageView.Default : EPageView.Wizard];

	const spritemap =
		Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';

	return (
		<ClayIconSpriteContext.Provider value={spritemap}>
			<AppContext.Provider
				value={{connected, liferayAnalyticsURL, token}}
			>
				<div className="analytics-settings-web mt-5">
					<PageView />
				</div>
			</AppContext.Provider>
		</ClayIconSpriteContext.Provider>
	);
};

export default App;
