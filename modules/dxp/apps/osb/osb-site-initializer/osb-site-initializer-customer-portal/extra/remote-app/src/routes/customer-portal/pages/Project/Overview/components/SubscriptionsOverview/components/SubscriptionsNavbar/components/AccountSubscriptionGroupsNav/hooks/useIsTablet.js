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
import {Liferay} from '../../../../../../../../../../../../common/services/liferay';

export default function useIsTablet() {
	const [isTablet, setIsTablet] = useState(Liferay.Util.isTablet());

	useEffect(() => {
		const handleResize = () => setIsTablet(Liferay.Util.isTablet());

		window.addEventListener('resize', handleResize);

		return () => window.removeEventListener('resize', handleResize);
	}, []);

	return isTablet;
}
