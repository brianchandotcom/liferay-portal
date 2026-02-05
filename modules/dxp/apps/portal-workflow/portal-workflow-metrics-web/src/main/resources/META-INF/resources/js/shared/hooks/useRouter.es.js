/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLocation, useMatches, useNavigate, useParams} from 'react-router';

const useRouter = () => {
	const location = useLocation();
	const matches = useMatches();
	const navigate = useNavigate();
	const params = useParams();

	const routeMatch = matches.findLast(({handle}) => handle?.path);
	const path = routeMatch?.handle.path;

	return {
		location,
		navigate,
		path,
		routeParams: params,
	};
};

export {useRouter};
