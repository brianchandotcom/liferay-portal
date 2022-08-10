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

import {useEffect} from 'react';

import {TypeActivity} from '../../../../../../common/interfaces/typeActivity';
import useGetTacticsByTypeActivityId from '../../../../../../common/services/liferay/object/type-activities/useGetTacticsByTypeActivityId';
import Tactic from '../interfaces/tactic';

export default function useTacticsTypeActivity(
	selectedTypeActivity: TypeActivity | undefined,
	updateTacticOptions: (tactics: Tactic[]) => void
) {
	const {data: tactics} = useGetTacticsByTypeActivityId(
		selectedTypeActivity?.id
	);

	useEffect(() => {
		if (tactics) {
			updateTacticOptions(tactics.items);
		}
	}, [tactics, updateTacticOptions]);
}
