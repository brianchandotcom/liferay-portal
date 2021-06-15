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
