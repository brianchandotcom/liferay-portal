/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useState} from 'react';
import {Outlet} from 'react-router';

import {withParams} from '../../shared/components/router/routerUtil.es';
import SLAFormPage from './form-page/SLAFormPage.es';
import SLAListPage from './list-page/SLAListPage.es';

const SLAContext = createContext();

export default function SLAContainer() {
	const [SLAUpdated, setSLAUpdated] = useState(false);

	return (
		<SLAContext.Provider value={{SLAUpdated, setSLAUpdated}}>
			<Outlet />
		</SLAContext.Provider>
	);
}

export {SLAContext};
