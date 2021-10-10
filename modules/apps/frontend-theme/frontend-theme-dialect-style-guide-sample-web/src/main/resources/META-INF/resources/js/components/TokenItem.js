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

const SIZES_MAP = {
	large: {
		colLg: '12',
		colMd: '12',
		colSm: '12',
	},
	medium: {
		colLg: '4',
		colMd: '6',
		colSm: '12',
	},
	small: {
		colLg: '3',
		colMd: '4',
		colSm: '6',
	},
	smaller: {
		colLg: '2',
		colMd: '4',
		colSm: '6',
	},
};

const TokenItem = (props) => {
	const {children, className, label, size = 'smaller'} = props;

	return (
		<ClayLayout.Col
			className="my-2 token-item"
			lg={SIZES_MAP[size]['colLg']}
			md={SIZES_MAP[size]['colMd']}
			sm={SIZES_MAP[size]['colSm']}
		>
			<div className={classNames('token-sample', className)}>
				{children}
			</div>

			{label && <div className="token-label">{label}</div>}
		</ClayLayout.Col>
	);
};

export default TokenItem;
