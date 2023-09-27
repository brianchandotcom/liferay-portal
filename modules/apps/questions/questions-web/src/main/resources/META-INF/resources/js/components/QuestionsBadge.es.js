/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			<span
				className={classNames(
					'label-badge-activity questions-labels-limit',
					{
						'label-badge-activity': isActivityBadge,
					}
				)}
			>
				{symbol && (
					<ClayIcon
						className={classNames(symbolClassName, 'mr-2 mt-0')}
						fontSize={16}
						symbol={symbol}
					/>
				)}

				{value || 0}
			</span>
		</div>
	);
}
