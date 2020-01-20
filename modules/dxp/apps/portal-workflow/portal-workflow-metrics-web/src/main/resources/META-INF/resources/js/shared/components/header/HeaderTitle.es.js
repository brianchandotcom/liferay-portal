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

import {usePrevious} from 'frontend-js-react-web';
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
		<Portal container={container} replace>
			{currentTitle}
		</Portal>
	);
};

export default HeaderTitle;
