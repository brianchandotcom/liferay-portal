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

import {gql} from '@apollo/client';

import {liferayUserAccountFragment} from '../fragments';

export type UserAccount = {
	additionalName: string;
	alternateName: string;
	emailAddress: string;
	familyName: string;
	givenName: string;
	id: number;
	image: string;
};

export const getLiferayMyUserAccount = gql`
	${liferayUserAccountFragment}

	query getLiferayMyUserAccount {
		myUserAccount {
			...LiferayUserAccountFragment
		}
	}
`;

export const getLiferayUserAccounts = gql`
	${liferayUserAccountFragment}

	query getLiferayUserAccount(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		userAccounts(filter: $filter, page: $page, pageSize: $pageSize) {
			items {
				...LiferayUserAccountFragment
			}
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;

export const getLiferayUserAccount = gql`
	${liferayUserAccountFragment}

	query getLiferayUserAccount($userAccountId: Long) {
		userAccount(userAccountId: $userAccountId) {
			...LiferayUserAccountFragment
		}
	}
`;
