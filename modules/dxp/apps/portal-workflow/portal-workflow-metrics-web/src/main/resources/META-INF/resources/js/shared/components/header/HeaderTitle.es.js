/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {usePrevious} from '@liferay/frontend-js-react-web';
import React, {useEffect, useState} from 'react';

import Portal from '../portal/Portal.es';

const HeaderTitle = ({container, title}) => {
	const [currentTitle, setCurrentTitle] = useState(title);
	const prevTitle = usePrevious(currentTitle);

	useEffect(() => {
		if (title) {
			setCurrentTitle(title);

			if (prevTitle !== title) {
				document.title = document.title.replace(prevTitle, title);
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [title]);

	return (
		<Portal container={container} elementId="headerTitle" replace>
			{currentTitle}
		</Portal>
	);
};

export default HeaderTitle;
