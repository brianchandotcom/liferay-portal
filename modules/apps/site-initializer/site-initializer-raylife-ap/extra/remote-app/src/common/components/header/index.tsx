/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
