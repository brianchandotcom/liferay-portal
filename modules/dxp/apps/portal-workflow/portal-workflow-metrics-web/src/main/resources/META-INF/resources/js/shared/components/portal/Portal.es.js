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

const Portal = ({
	children,
	className,
	container,
	elementId,
	position = 'appendChild',
	replace,
}) => {
	const portalEl = useMemo(() => document.createElement('div'), []);

	useEffect(() => {
		if (container) {
			if (className) {
				portalEl.classList.add(className);
			}

			if (elementId) {
				portalEl.id = elementId;
			}

			if (replace) {
				container.innerHTML = '';
			}

			container[position](portalEl);

			return () => {
				const currentEl = document.getElementById(elementId);

				if (currentEl && currentEl.parentNode) {
					currentEl.parentNode.removeChild(currentEl);
				}
			};
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	if (!container) {
		return null;
	}

	return <>{ReactDOM.createPortal(children, portalEl)}</>;
};

export default Portal;
