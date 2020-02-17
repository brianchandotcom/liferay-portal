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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

export default function SidebarPanelHeader({padded = true, ...props}) {
	return (
		<h1
			{...props}
			className={classNames(
				'page-editor__sidebar__panel-header',
				'align-items-center',
				'd-flex',
				{
					[props.className]: !!props.className,
					'pt-2': padded,
					'px-3': padded
				}
			)}
		/>
	);
}

SidebarPanelHeader.propTypes = {
	padded: PropTypes.bool
};
