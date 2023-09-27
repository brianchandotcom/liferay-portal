/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				<ClayLayout.Row className="align-items-start token-items">
					{children}
				</ClayLayout.Row>
			)}
		</div>
	);
};

export default TokenGroup;
