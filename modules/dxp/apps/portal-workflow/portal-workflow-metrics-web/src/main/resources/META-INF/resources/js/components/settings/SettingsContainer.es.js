/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Route, Switch} from 'react-router-dom';

import {usePageTitle} from '../../shared/hooks/usePageTitle.es';
import IndexesPage from './indexes-page/IndexesPage.es';

export default function SettingsContainer() {
	usePageTitle(Liferay.Language.get('settings'));

	return (
		<Switch>
			<Route component={IndexesPage} exact path="/settings/indexes" />
		</Switch>
	);
}
