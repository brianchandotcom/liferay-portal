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

import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import React from 'react';

const TokenGroup = ({children, className, group, title}) => {
	return (
		<div
			className={classNames(
				'my-5 token-group token-group-' + group,
				className
			)}
		>
			{title && (
				<ClayLayout.Row>
					<ClayLayout.Col>
						<h2>{title}</h2>
					</ClayLayout.Col>
				</ClayLayout.Row>
			)}

			{children && (
				<ClayLayout.Row className="token-items">
					{children}
				</ClayLayout.Row>
			)}
		</div>
	);
};

export default TokenGroup;
