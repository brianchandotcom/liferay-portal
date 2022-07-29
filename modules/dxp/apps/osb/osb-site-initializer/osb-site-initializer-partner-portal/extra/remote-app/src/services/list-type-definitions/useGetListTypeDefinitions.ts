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

import {useEffect, useState} from 'react';

import LIFERAY_APIS from '../../constants/liferayAPIs';
import {Liferay} from '../liferay';

const useGetListTypeDefinitions = (filter: string) => {
	const [listTypeDefinitions, setTypeDefinitions] = useState<any[]>([]);

	useEffect(() => {
		const getListTypeDefinitions = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch(
				`/o/${
					LIFERAY_APIS.headlessAdminListType
				}/list-type-definitions${
					filter ? `?filter=name eq '${filter}'` : ``
				}`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			);

			const data = await response.json();

			if (data) {
				setTypeDefinitions(data.items[0].listTypeEntries);
			}
		};

		getListTypeDefinitions();
	}, [filter]);

	return listTypeDefinitions;
};
export {useGetListTypeDefinitions};
