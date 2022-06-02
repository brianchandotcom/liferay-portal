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

const THRESHOLD_COUNT = 4;

export default function useHasManyProjects(koroneikiAccounts) {
	const [maxTotalCount, setMaxTotalCount] = useState(0);

	useEffect(() => {
		const totalCount = koroneikiAccounts?.totalCount;

		if (totalCount > maxTotalCount) {
			setMaxTotalCount(totalCount);
		}
	}, [koroneikiAccounts?.totalCount, maxTotalCount]);

	return maxTotalCount > THRESHOLD_COUNT;
}
