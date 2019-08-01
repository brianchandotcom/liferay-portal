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

import {ClayButtonWithIcon} from '@clayui/button';
import React from 'react';
import {NavLink} from 'react-router-dom';

const withLink = (button, href) => <NavLink to={href}>{button}</NavLink>;

export default ({href, onClick, tooltip}) => {
	let className = 'nav-btn nav-btn-monospaced navbar-breakpoint-down-d-none';

	if (tooltip) {
		className += ' lfr-portal-tooltip';
	}

	let button = (
		<ClayButtonWithIcon
			className={className}
			data-title={tooltip}
			onClick={onClick}
			symbol="plus"
		/>
	);

	if (href) {
		button = withLink(button, href);
	}

	return button;
};
