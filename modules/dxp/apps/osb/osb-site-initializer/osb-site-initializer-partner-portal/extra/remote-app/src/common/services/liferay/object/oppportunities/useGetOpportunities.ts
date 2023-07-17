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
import Opportunity from '../../../../interfaces/opportunity';
import {LiferayAPIs} from '../../common/enums/apis';
import LiferayItems from '../../common/interfaces/liferayItems';
import liferayFetcher from '../../common/utils/fetcher';

export default function useGetOpportunities(pageSize: number) {
	return useSWR(
		[
			`/o/${LiferayAPIs.OBJECT}/opportunitysfs?pageSize=${pageSize}`,
			Liferay.authToken,
		],
		(url, token) => liferayFetcher<LiferayItems<Opportunity[]>>(url, token)
	);
}
