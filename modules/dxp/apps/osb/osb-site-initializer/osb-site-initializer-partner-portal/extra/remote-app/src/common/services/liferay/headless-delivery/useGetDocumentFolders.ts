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

import LiferayDocumentFolder from '../../../interfaces/liferayDocumentFolder';
import {LiferayAPIs} from '../common/enums/apis';
import LiferayItems from '../common/interfaces/liferayItems';
import liferayFetcher from '../common/utils/fetcher';

export default function useGetDocumentFolder(siteId: number, name?: string) {
	return useSWR(
		[
			`/o/${
				LiferayAPIs.HEADERLESS_DELIVERY
			}/sites/${siteId}/document-folders/${
				name ? `?filter=name eq '${name}'` : ''
			}`,
			Liferay.authToken,
		],
		(url, token) =>
			liferayFetcher<LiferayItems<LiferayDocumentFolder[]>>(url, token)
	);
}
