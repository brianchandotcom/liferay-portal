/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React from 'react';
import ReactDOM from 'react-dom';

export default function launcher(
	Component,
	componentId,
	containerId,
	props = {}
) {
	const {portletId} = props;
	const container = window.document.getElementById(containerId);
	const destroyOnNavigate = !portletId;

	if (Liferay.component) {
		Liferay.component(
			componentId,
			{
				destroy: () => {
					ReactDOM.unmountComponentAtNode(container);
				},
			},
			{
				destroyOnNavigate,
				portletId,
			}
		);
	}

	// eslint-disable-next-line liferay-portal/no-react-dom-render
	ReactDOM.render(<Component {...props} />, container);
}
