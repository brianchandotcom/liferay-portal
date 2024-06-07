/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import SearchBuilder from '~/common/core/SearchBuilder';
import {useGetListTypeDefinitions} from '~/common/services/liferay/graphql/list-type-definitions';

const listTypePurposeComplimentaryKey = 'Purpose of Complimentary Key';

export default function useGetPurposeComplimentaryKeyList() {
	const {data} = useGetListTypeDefinitions({
		filter: SearchBuilder.eq('name', listTypePurposeComplimentaryKey),
	});

	const purposeComplimentaryKeyList = useMemo(
		() =>
			(
				(data?.listTypeDefinitions?.items[0].listTypeEntries ?? []) as {
					name: string;
				}[]
			).map(({name}) => ({label: name, value: name})),
		[data?.listTypeDefinitions?.items]
	);

	return purposeComplimentaryKeyList;
}
