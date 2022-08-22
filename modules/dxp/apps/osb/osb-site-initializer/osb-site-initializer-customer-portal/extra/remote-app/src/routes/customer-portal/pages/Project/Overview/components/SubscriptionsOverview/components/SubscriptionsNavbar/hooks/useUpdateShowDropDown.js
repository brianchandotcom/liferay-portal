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

import {useEffect, useRef, useState} from 'react';
import {useCustomerPortal} from '../../../../../../../../context';

export default function useUpdateShowDropDown() {
	const [showDropDown, setShowDropDown] = useState(false);
	const subscriptionNavbarRef = useRef();
	const [{isQuickLinksExpanded}] = useCustomerPortal();

	useEffect(() => {
		const updateShowDropDown = () => {
			setShowDropDown(
				subscriptionNavbarRef?.current &&
					subscriptionNavbarRef.current.offsetWidth <
						(isQuickLinksExpanded ? 500 : 570)
			);
		};

		updateShowDropDown();
		window.addEventListener('resize', updateShowDropDown);

		return () => window.removeEventListener('resize', updateShowDropDown);
	}, [isQuickLinksExpanded]);

	return showDropDown;
}
