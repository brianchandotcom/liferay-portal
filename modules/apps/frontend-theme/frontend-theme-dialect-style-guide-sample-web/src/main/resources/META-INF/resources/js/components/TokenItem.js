/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	const {
		border = false,
		children,
		className,
		label,
		size = 'smaller',
	} = props;

	return (
		<ClayLayout.Col
			className="my-2 token-item"
			lg={SIZES_MAP[size]['colLg']}
			md={SIZES_MAP[size]['colMd']}
			sm={SIZES_MAP[size]['colSm']}
		>
			<div
				className={classNames('token-sample', className, {
					'token-border': border,
				})}
			>
				{children}
			</div>

			{label && <div className="token-label">{label}</div>}
		</ClayLayout.Col>
	);
};

export default TokenItem;
