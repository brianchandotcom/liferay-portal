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

import {useEffect} from 'react';
import {Outlet, useParams} from 'react-router-dom';

import {useHeader} from '../../../hooks';
import {useFetch} from '../../../hooks/useFetch';
import i18n from '../../../i18n';
import {getUserAccountQuery} from '../../../services/rest';

const UserOutlet = () => {
	const {userId} = useParams();
	const {data} = useFetch(getUserAccountQuery(userId as string));

	const userAccount = data;
	const {setHeading, setTabs} = useHeader();

	useEffect(() => {
		if (userAccount) {
			setTimeout(() => {
				setHeading(
					[
						{
							category: i18n.translate('manage').toUpperCase(),
							title: i18n.translate('manage-user'),
						},
					],
					true
				);
			}, 0);
		}
	}, [setHeading, userAccount]);

	useEffect(() => {
		setTabs([]);
	}, [setTabs]);

	if (!userAccount) {
		return null;
	}

	return <Outlet context={{userAccount}} />;
};
export default UserOutlet;
