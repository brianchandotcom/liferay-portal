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

import {ProgressRing} from '../ProgressRing';

export function StepItem({
	children,
	isMobileDevice = false,
	onClick,
	percentage = 0,
	selected = false,
}) {
	const completed = percentage === 100;
	const partially = percentage !== 0;

	return (
		<div
			className={classNames(
				'align-items-center d-flex font-weight-bolder step-item text-paragraph-lg',
				{
					completed,
					partially,
					selected,
					'text-brand-primary': selected || partially,
				}
			)}
			onClick={partially && !isMobileDevice ? onClick : undefined}
		>
			<i className="align-items-center justify-content-center position-relative">
				{partially && (
					<ProgressRing
						className="position-absolute progress-ring"
						diameter={32}
						percent={percentage}
						strokeWidth={3}
					/>
				)}
			</i>

			{completed && (
				<div className="align-items-center bg-brand-primary d-flex justify-content-center">
					<ClayIcon className="m-0 text-neutral-0" symbol="check" />
				</div>
			)}

			{children}
		</div>
	);
}
