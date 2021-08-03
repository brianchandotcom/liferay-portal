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

import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import React from 'react';

import '../../css/main.scss';

export default function TaskNode({current = false, done = false, title}) {
	return (
		<div
			className={classnames(
				'node task-node',
				current ? 'current-task text-white' : 'text-secondary'
			)}
		>
			<span>{title}</span>

			{current && (
				<ClayIcon className="current-icon ml-2" symbol="live" />
			)}

			{done && <ClayIcon className="done-icon ml-2" symbol="check" />}
		</div>
	);
}
