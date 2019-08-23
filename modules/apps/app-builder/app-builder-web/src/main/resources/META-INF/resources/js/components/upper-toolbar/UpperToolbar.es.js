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

const UpperToolbarItem = ({children, expand}) => {
	return (
		<li className={`tbar-item ${expand ? 'tbar-item-expand' : ''}`}>
			<div className="tbar-section">{children}</div>
		</li>
	);
};

const UpperToolbar = ({children}) => {
	return (
		<nav className="component-tbar subnav-tbar-light tbar tbar-article">
			<div className="container-fluid container-fluid-max-xl">
				<ul className="tbar-nav">{children}</ul>
			</div>
		</nav>
	);
};

export default UpperToolbar;
export {UpperToolbarItem};
