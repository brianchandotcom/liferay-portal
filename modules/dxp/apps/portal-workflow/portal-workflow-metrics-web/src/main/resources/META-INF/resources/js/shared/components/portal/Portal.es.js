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

import React, {useEffect, useMemo} from 'react';
import ReactDOM from 'react-dom';

const Portal = ({children, container, replace}) => {
	const element = useMemo(() => document.createElement('div'), []);

	useEffect(() => {
		if (container) {
			if (replace) {
				container.innerHTML = '';
			}

			container.appendChild(element);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	if (!container) {
		return null;
	}

	return <>{ReactDOM.createPortal(children, element)}</>;
};

export default Portal;
