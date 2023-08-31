/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import React from 'react';

export default function Container({
	active = false,
	children,
	className,
	...otherProps
}: IProps) {
	return (
		<nav
			{...otherProps}
			className={classNames(
				'management-bar navbar navbar-expand-md',
				className,
				{
					'management-bar-light': !active,
					'management-bar-primary navbar-nowrap': active,
				}
			)}
		>
			<ClayLayout.ContainerFluid
				size={Liferay?.FeatureFlags?.['LPS-184404'] ? false : 'xl'}
			>
				{children}
			</ClayLayout.ContainerFluid>
		</nav>
	);
}

interface IProps extends React.HTMLAttributes<HTMLElement> {
	active?: boolean;
}
