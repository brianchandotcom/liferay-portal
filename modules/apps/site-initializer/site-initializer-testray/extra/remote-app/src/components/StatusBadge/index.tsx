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
const taskbarClassNames: any = {
	blocked: 'label-inverse-secondary',
	failed: 'label-inverse-danger',
	incomplete: 'label-inverse-light',
	other: 'label-inverse-primary',
	passed: 'label-inverse-success',
	self: 'label-inverse-info',
	test_fix: 'label-tonal-success',
};

export type StatusBadgeProps = {
	type: string;
};

const StatusBadge: React.FC<StatusBadgeProps> = ({type}) => (
	<span className={`label ${taskbarClassNames[type]}`}>
		{type.toUpperCase()}
	</span>
);

export default StatusBadge;
