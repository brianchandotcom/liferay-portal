/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

export default function ResultsBarItem({
	children,
	className,
	expand = false,
	...otherProps
}: IProps) {
	return (
		<li
			{...otherProps}
			className={classNames('tbar-item', className, {
				'tbar-item-expand': expand,
			})}
		>
			<div className="tbar-section">{children}</div>
		</li>
	);
}

interface IProps extends React.LiHTMLAttributes<HTMLLIElement> {
	expand?: boolean;
}
