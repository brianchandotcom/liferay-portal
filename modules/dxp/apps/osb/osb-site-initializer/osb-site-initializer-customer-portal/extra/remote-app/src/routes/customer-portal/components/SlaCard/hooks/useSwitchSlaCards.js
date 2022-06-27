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

import {useState} from 'react';

export default function useSwitchSlaCards(setSlaSelected, slaData) {
	const [slaPosition, setSlaPosition] = useState(0);

	const handleSlaCardClick = () => {
		const nextPosition = slaPosition + 1;

		if (slaData[nextPosition]) {
			setSlaSelected(slaData[nextPosition].label);
			setSlaPosition(nextPosition);
		} else {
			setSlaSelected(slaData[0].label);
			setSlaPosition(0);
		}
	};

	return handleSlaCardClick;
}
