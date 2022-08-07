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
import AdditionalOption from '../../../../utils/types/additionalOption';
import LiferayItems from '../../../../utils/types/liferayItems';
import {liferayAPIs} from '../../common/apis';
import liferayFetcher from '../../common/fetcher';
import {liferayObjectResourceName} from '../constants/liferayObjectResourceName';

export default function useGetAdditionalOptions() {
	return useSWR(
		[
			`/o/${liferayAPIs.OBJECT}/${liferayObjectResourceName.ADDITIONAL_OPTIONS}`,
			Liferay.authToken,
		],
		(url, token) =>
			liferayFetcher<LiferayItems<AdditionalOption[]>>(url, token)
	);
}
