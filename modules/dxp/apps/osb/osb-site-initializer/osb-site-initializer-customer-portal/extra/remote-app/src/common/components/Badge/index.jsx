/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

const Badge = ({badgeClassName, children, ...props}) => {
	return (
		<div
			{...props}
			className={classNames(
				'alert alert-danger p-sm-2 text-danger text-paragraph-sm',
				{
					[badgeClassName]: badgeClassName,
				}
			)}
		>
			<ClayIcon symbol="exclamation-full" />

			{children}
		</div>
	);
};

export default Badge;
