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

import PropTypes from 'prop-types';
import React from 'react';

const SidebarHeader = props => (
	<h1 className="fragments-editor-sidebar-section__title align-items-center d-flex">
		{props.children}
	</h1>
);

SidebarHeader.propTypes = {
	children: PropTypes.node
};

export {SidebarHeader};
export default SidebarHeader;
