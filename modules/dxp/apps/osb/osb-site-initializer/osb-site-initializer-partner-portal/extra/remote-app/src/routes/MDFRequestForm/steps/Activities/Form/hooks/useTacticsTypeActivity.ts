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

import useGetTacticsByTypeActivityId from '../../../../../../common/services/liferay/object/type-activities/useGetTacticsByTypeActivityId';
import Tactic from '../interfaces/tactic';

export default function useTacticsTypeActivity(
	updateTacticOptions: (tactics: Tactic[]) => void
) {
	const [selectedTypeActivityId, setSelectedTypeActivityId] = useState<
		string
	>();

	const {data: tactics} = useGetTacticsByTypeActivityId(
		selectedTypeActivityId
	);

	useEffect(() => {
		if (tactics) {
			updateTacticOptions(tactics.items);
		}
	}, [tactics, updateTacticOptions]);

	return {setSelectedTypeActivityId};
}
