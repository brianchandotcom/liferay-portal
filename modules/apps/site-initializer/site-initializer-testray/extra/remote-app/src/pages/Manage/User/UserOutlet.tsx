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

import {useQuery} from '@apollo/client';
import {useEffect} from 'react';
import {Outlet, useParams} from 'react-router-dom';

import {UserAccount, getLiferayUserAccount} from '../../../graphql/queries';
import {useHeader} from '../../../hooks';
import i18n from '../../../i18n';

const UserOutlet = () => {
	const {userId} = useParams();
	const {data} = useQuery<{userAccount: UserAccount}>(getLiferayUserAccount, {
		variables: {
			userAccountId: userId,
		},
	});

	const userAccount = data?.userAccount;
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
