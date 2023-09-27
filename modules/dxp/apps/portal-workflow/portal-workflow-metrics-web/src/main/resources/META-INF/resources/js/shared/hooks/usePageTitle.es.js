/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext, useEffect} from 'react';

import {AppContext} from '../../components/AppContext.es';

const usePageTitle = (pageTitle) => {
	const {setTitle} = useContext(AppContext);

	useEffect(() => {
		setTitle(pageTitle);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [pageTitle]);
};

export {usePageTitle};
