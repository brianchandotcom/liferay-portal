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

import {Liferay} from '..';
import useSWR from 'swr';

import {LiferayPicklistName} from '../../../enums/liferayPicklistName';
import ListTypeDefinition from '../../../interfaces/listTypeDefinition';
import {LiferayAPIs} from '../common/enums/apis';
import LiferayItems from '../common/interfaces/liferayItems';
import liferayFetcher from '../common/utils/fetcher';

export default function useGetListTypeDefinitions(
	names: LiferayPicklistName[]
) {
	return useSWR(
		[
			`/o/${
				LiferayAPIs.HEADERLESS_ADMIN_LIST_TYPE
			}/list-type-definitions?filter=name in ('${names.join("', '")}')`,
			Liferay.authToken,
		],
		(url, token) =>
			liferayFetcher<LiferayItems<ListTypeDefinition[]>>(url, token)
	);
}
