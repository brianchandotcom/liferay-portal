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

export default function useSLACardPosition(slaCardsCount) {
	const [currentPosition, setCurrentPosition] = useState(0);
	const [lastPosition, setLastPosition] = useState();

	useEffect(() => {
		if (slaCardsCount) {
			setLastPosition(slaCardsCount - 1);
		}
	}, [slaCardsCount]);

	const changePosition = () => {
		const nextPosition = currentPosition + 1;

		if (nextPosition < slaCardsCount) {
			setCurrentPosition(nextPosition);
			setLastPosition(currentPosition);

			return;
		}

		setLastPosition(slaCardsCount - 1);
		setCurrentPosition(0);
	};

	return {changePosition, currentPosition, lastPosition};
}
