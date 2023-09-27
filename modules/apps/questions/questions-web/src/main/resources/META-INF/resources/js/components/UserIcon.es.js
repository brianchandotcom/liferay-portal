/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

const UserIcon = ({fullName = '', portraitURL = '', size = 'lg', userId}) => {
	const stickerColor = parseInt(userId, 10) % 10;

	return (
		<ClaySticker
			className={classnames('sticker-use-icon', {
				[`user-icon-color-${stickerColor}`]: !portraitURL,
			})}
			displayType="secondary"
			shape="circle"
			size={size}
		>
			{portraitURL ? (
				<div className="sticker-overlay">
					<img
						alt={`${fullName}.`}
						className="sticker-img"
						src={portraitURL}
					/>
				</div>
			) : (
				<ClayIcon symbol="user" />
			)}
		</ClaySticker>
	);
};

UserIcon.propTypes = {
	fullName: PropTypes.string,
	portraitURL: PropTypes.string,
	userId: PropTypes.string.isRequired,
};

export default UserIcon;
