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

import {useContext} from 'react';
import {Outlet, useParams} from 'react-router-dom';

import {AccountContext} from '../../../context/AccountContext';
import {useFetch} from '../../../hooks/useFetch';
import {liferayUserAccountsRest} from '../../../services/rest';

const UserOutlet = () => {
	const {userId} = useParams();

	const [{myUserAccount}, , mutateMyUserAccount] = useContext(AccountContext);
	const {data, mutate} = useFetch(
		userId ? liferayUserAccountsRest.getResource(userId as string) : null
	);

	const context = {
		mutateUser: userId ? mutate : mutateMyUserAccount,
		userAccount: userId ? data : myUserAccount,
	};

	if (!context.userAccount) {
		return null;
	}

	return <Outlet context={context} />;
};
export default UserOutlet;
