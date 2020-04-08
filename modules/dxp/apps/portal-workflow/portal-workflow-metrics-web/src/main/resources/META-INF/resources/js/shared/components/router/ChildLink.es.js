/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';
import {Link} from 'react-router-dom';

import {useRouter} from '../../hooks/useRouter.es';
import {stringify} from './queryString.es';

const ChildLink = ({children, query, to, ...otherProps}) => {
	const {
		location: {pathname, search},
	} = useRouter();

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
