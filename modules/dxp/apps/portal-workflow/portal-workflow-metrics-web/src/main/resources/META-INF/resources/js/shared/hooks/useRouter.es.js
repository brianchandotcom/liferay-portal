/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {matchRoutes, useLocation, useNavigate, useParams} from 'react-router';

import {appDataRoutes} from '../../components/App.es';

const useRouter = () => {
	const location = useLocation();
	const navigate = useNavigate();
	const params = useParams();

	const routeMatches = matchRoutes(appDataRoutes, location.pathname);

	const path = routeMatches?.map(({route}) => route.path).join('/') ?? '';

	return {
		location,
		navigate,
		path,
		routeParams: params,
	};
};

export {useRouter};
