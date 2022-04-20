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
import React from 'react';

import './Card.scss';

export default function Card({
	children,
	className,
	title,
	...otherProps
}: IProps) {
	return (
		<div
			{...otherProps}
			className={classNames(className, 'lfr-objects__card')}
		>
			<div className="lfr-objects__card-header">
				<h3 className="lfr-objects__card-title">{title}</h3>
			</div>

			<div className={classNames(className, 'lfr-objects__card-body')}>
				{children}
			</div>
		</div>
	);
}

interface IProps extends React.HTMLAttributes<HTMLDivElement> {
	title: string;
}
