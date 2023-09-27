/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';

const PROPERTY = {
	overflowY: {
		off: 'hidden',
		on: 'scroll',
	},
	touchAction: {
		off: 'none',
		on: 'auto',
	},
};

const useImperativeDisableScroll = ({disabled, element}) => {
	useEffect(() => {
		if (!element) {
			return;
		}

		element.style['touch-action'] = disabled // on/off scroll for Mobile Phone
			? PROPERTY.touchAction.off
			: PROPERTY.touchAction.on;

		element.style.overflowY = disabled // on/off scroll for Desktop Browser
			? PROPERTY.overflowY.off
			: PROPERTY.overflowY.on;

		return () => {
			element.style['touch-action'] = PROPERTY.overflowY.on;
			element.style.overflowY = PROPERTY.overflowY.on;
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [disabled]);
};

export default useImperativeDisableScroll;
