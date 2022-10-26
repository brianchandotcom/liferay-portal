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
import {ReactNode} from 'react';

const statusBarClassNames = {
	blocked: 'blocked',
	failed: 'failed',
	incomplete: 'light',
	inprogress: 'in-progress',
	other: 'primary',
	passed: 'passed',
	self: 'info',
	testfix: 'test-fix',
	untested: 'untested',
};

export type StatusBadgeType = keyof typeof statusBarClassNames;

export type StatusBadgeProps = {
	children: ReactNode;
	type: StatusBadgeType;
};

const StatusBadge: React.FC<StatusBadgeProps> = ({children, type}) => (
	<span
		className={classNames(
			'label label-chart text-uppercase text-nowrap',
			statusBarClassNames[type] || type?.toLowerCase().replace(' ', '-')
		)}
	>
		{children}
	</span>
);

export default StatusBadge;
