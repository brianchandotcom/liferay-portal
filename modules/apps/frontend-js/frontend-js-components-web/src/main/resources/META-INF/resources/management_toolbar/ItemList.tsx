/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

export default function ItemList({children, expand, ...otherProps}: IProps) {
	return (
		<ul
			{...otherProps}
			className={classNames('navbar-nav', {
				'navbar-nav-expand': expand,
			})}
		>
			{children}
		</ul>
	);
}

interface IProps {
	children?: React.ReactNode;
	expand?: boolean;
}
