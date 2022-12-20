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

import {LiferayPicklistName} from '../../../../../../../common/enums/liferayPicklistName';
import useGetListTypeDefinitions from '../../../../../../../common/services/liferay/list-type-definitions/useGetListTypeDefinitions';
import getEntriesByListTypeDefinitions from '../../../../../../../common/utils/getEntriesByListTypeDefinitions';

export default function useDynamicFieldEntries() {
	const {data: listTypeDefinitions} = useGetListTypeDefinitions([
		LiferayPicklistName.LEAD_FOLLOW_UP_STRATEGIES,
		LiferayPicklistName.BUDGET_EXPENSES,
		LiferayPicklistName.TYPE_OF_ACTIVITY,
		LiferayPicklistName.TACTIC,
	]);

	const fieldEntries = useMemo(
		() => getEntriesByListTypeDefinitions(listTypeDefinitions?.items),
		[listTypeDefinitions?.items]
	);

	return {
		fieldEntries,
	};
}
