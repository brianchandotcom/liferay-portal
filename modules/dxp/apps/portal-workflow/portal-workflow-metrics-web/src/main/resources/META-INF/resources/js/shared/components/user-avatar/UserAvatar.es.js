/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React from 'react';

const UserAvatar = ({className, image}) => {
	const avatarClassName = getCN(
		className,
		'rounded-circle sticker sticker-light sticker-user-icon'
	);

	return (
		<span className={avatarClassName}>
			<span className="sticker-overlay">
				{image ? (
					<img className="img-fluid" src={image} />
				) : (
					<ClayIcon symbol="user" />
				)}
			</span>
		</span>
	);
};

export default UserAvatar;
