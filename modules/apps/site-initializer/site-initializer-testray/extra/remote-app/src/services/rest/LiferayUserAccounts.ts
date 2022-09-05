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
import Rest from './Rest';
import {UserAccount} from './types';

type UserForm = typeof yupSchema.userWithPassword.__outputType;

class LiferayUserAccountsRest extends Rest<UserForm, UserAccount> {
	constructor() {
		super({
			adapter: ({
				alternateName,
				currentPassword,
				emailAddress,
				familyName,
				givenName,
				password,
			}) => ({
				alternateName,
				currentPassword,
				emailAddress,
				familyName,
				givenName,
				password,
			}),
			uri: 'user-accounts',
		});
	}
}

const liferayUserAccountsRest = new LiferayUserAccountsRest();

export {liferayUserAccountsRest};
