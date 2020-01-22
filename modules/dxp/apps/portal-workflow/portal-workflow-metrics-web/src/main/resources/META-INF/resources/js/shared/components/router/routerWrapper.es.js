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
import {Link, Redirect} from 'react-router-dom';

import {useRouter} from '../../hooks/useRouter.es';
import {parse, stringify} from './queryString.es';

const BackLink = ({children, ...otherProps}) => {
	const {backPath} = useParams();

	return (
		<Link {...otherProps} to={backPath}>
			{children}
		</Link>
	);
};

const BackRedirect = () => {
	const {backPath} = useParams();

	return <Redirect to={backPath} />;
};

const ChildLink = ({children, query, to, ...otherProps}) => {
	const {currentPath} = useParams();

	return (
		<Link
			{...otherProps}
			to={{
				pathname: to,
				search: stringify({backPath: currentPath, ...query})
			}}
		>
			{children}
		</Link>
	);
};

const useParams = () => {
	const {
		location: {pathname, search}
	} = useRouter();

	const {backPath} = parse(search);

	return {backPath, currentPath: `${pathname}${search}`};
};

export {BackLink, BackRedirect, ChildLink};
