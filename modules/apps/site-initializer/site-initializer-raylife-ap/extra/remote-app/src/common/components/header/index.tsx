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

import ClayIconProvider from '../../context/ClayIconProvider';

type HeaderProps = {
	children?: React.ReactNode;
	className?: string;
	title: string;
};

const Header: React.FC<HeaderProps> = ({children, className = '', title}) => {
	return (
		<ClayIconProvider>
			<div
				className={`${className} align-items-center d-flex justify-content-between ray-header-container`}
			>
				<div className="font-weight-bolder h4 header-title mb-0">
					{title}
				</div>

				<div className="action-area">{children}</div>
			</div>
		</ClayIconProvider>
	);
};

export default Header;
