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

import yupSchema from '../../schema/yup';
import fetcher from '../fetcher';

type User = typeof yupSchema.user.__outputType;

const adapter = ({
	alternateName,
	emailAddress,
	familyName,
	givenName,
	password,
	repassword,
}: User) => ({
	alternateName,
	emailAddress,
	familyName,
	givenName,
	password,
	repassword,
});

const getUserAccountQuery = (userId: number | string) =>
	`/user-accounts/${userId}`;

const createUserAccount = (user: User) =>
	fetcher.post('/user-accounts', adapter(user));

const updateUserAccount = (id: number, user: User) =>
	fetcher.patch(`/user-accounts/${id}`, adapter(user as User));

export {createUserAccount, updateUserAccount, getUserAccountQuery};
