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

import useSWR from 'swr';

import {Liferay} from '../..';
import LiferayItems from '../../../../utils/types/liferayItems';
import LiferayPicklist from '../../../../utils/types/liferayPicklist';
import {liferayAPIs} from '../../common/apis';
import liferayFetcher from '../../common/fetcher';
import {liferayObjectResourceName} from '../constants/liferayObjectResourceName';

interface CompanyExtender {
	country: LiferayPicklist;
	id: number;
	r_company_accountEntryId: number;
}

export default function useCompanyExtenders(currentAccountId?: string) {
	return useSWR(
		currentAccountId
			? [
					`/o/${liferayAPIs.OBJECT}/${liferayObjectResourceName.COMPANY_EXTENDERS}/?filter=r_company_accountEntryId eq ${currentAccountId}`,
					Liferay.authToken,
			  ]
			: null,
		(url, token) =>
			liferayFetcher<LiferayItems<CompanyExtender[]>>(url, token)
	);
}
