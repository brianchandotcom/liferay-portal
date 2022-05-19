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

import {useContext, useEffect} from 'react';
import {Outlet} from 'react-router-dom';

import {AccountContext} from '../../../context/AccountContext';
import {useHeader} from '../../../hooks';
import i18n from '../../../i18n';

const UserOutlet = () => {
	const [{myUserAccount}] = useContext(AccountContext);

	const {setHeading, setTabs} = useHeader();

	useEffect(() => {
		if (myUserAccount) {
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
	}, [setHeading, myUserAccount]);

	useEffect(() => {
		setTabs([]);
	}, [setTabs]);

	if (!myUserAccount) {
		return null;
	}

	return <Outlet context={myUserAccount} />;
};
export default UserOutlet;
