/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Link, useLocation} from 'react-router';

import {stringify} from './queryString.es';

const ChildLink = ({children, query, to, ...otherProps}) => {
	const {pathname, search} = useLocation();

	return (
		<Link
			{...otherProps}
			to={{
				pathname: to,
				search: stringify({backPath: `${pathname}${search}`, ...query}),
			}}
		>
			{children}
		</Link>
	);
};

export default ChildLink;
