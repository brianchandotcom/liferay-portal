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

import {useMemo} from 'react';
import {useGetListTypeDefinitions} from '../../../../../../../../../../../../common/services/liferay/graphql/list-type-definitions';
import {LIST_TYPES} from '../../../../../../../../../../utils/constants';

const listTypePrimaryRegions = LIST_TYPES.lxcPrimaryRegion;

export default function useGetPrimaryRegionList() {
	const {data} = useGetListTypeDefinitions({
		filter: `name eq '${listTypePrimaryRegions}'`,
	});

	const items = data?.listTypeDefinitions?.items[0].listTypeEntries;

	const primaryRegionList = useMemo(
		() => items?.map(({name}) => ({label: name, value: name})) || [],
		[items]
	);

	return primaryRegionList;
}
