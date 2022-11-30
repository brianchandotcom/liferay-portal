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
import classNames from 'classnames';
import React from 'react';

export default function QuestionsBadge({
	className,
	isActivityBadge,
	symbol,
	symbolClassName,
	tooltip,
	value,
}) {
	return (
		<div
			className={classNames(
				'c-py-2 c-px-3 rounded stretched-link-layer',
				className
			)}
			data-tooltip-align="top"
			title={tooltip}
		>
			{symbol && (
				<ClayIcon
					className={classNames(symbolClassName, 'mr-2 mt-0')}
					fontSize={16}
					symbol={symbol}
				/>
			)}

			<span
				className={classNames(
					'label-badge-activity questions-labels-limit',
					{
						'label-badge-activity': isActivityBadge,
					}
				)}
			>
				{value || 0}
			</span>
		</div>
	);
}
