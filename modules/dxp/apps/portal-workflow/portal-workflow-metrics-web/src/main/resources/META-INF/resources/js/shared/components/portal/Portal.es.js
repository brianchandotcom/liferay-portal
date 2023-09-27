/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	const portalElement = useMemo(() => document.createElement('div'), []);

	useEffect(() => {
		if (container) {
			if (className) {
				portalElement.classList.add(className);
			}

			if (elementId) {
				portalElement.id = elementId;
			}

			if (replace) {
				container.innerHTML = '';
			}

			container[position](portalElement);

			return () => {
				const currentElement = document.getElementById(elementId);

				if (currentElement && currentElement.parentNode) {
					currentElement.parentNode.removeChild(currentElement);
				}
			};
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	if (!container) {
		return null;
	}

	// eslint-disable-next-line @liferay/portal/no-react-dom-create-portal
	return <>{ReactDOM.createPortal(children, portalElement)}</>;
};

export default Portal;
