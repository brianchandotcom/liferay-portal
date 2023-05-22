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

import Rest from '~/core/Rest';
import yupSchema from '~/schema/yup';

import {UserAccount} from './types';

type UserForm = typeof yupSchema.userWithPassword.__outputType;

class LiferayUserAccountsImpl extends Rest<UserForm, UserAccount> {
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

	public async getPagePermission() {
		const response = await this.getAll();

		const actions = response?.actions ?? {};

		return !!(actions as any)['post-user-account'];
	}
}

const liferayUserAccountsImpl = new LiferayUserAccountsImpl();

export {liferayUserAccountsImpl};
