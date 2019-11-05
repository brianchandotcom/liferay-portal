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
import ClaySticker from '@clayui/sticker';
import PropTypes from 'prop-types';
import React from 'react';

export default function UserIcon({fullName = '', portraitURL = '', userId}) {
	const stickerColor = parseInt(userId, 10) % 10;

	return (
		<ClaySticker
			className={`flex-shrink-0 sticker-use-icon user-icon-color-${stickerColor}`}
			displayType="secondary"
			shape="circle"
			size="lg"
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
}

UserIcon.propTypes = {
	fullName: PropTypes.string,
	portraitURL: PropTypes.string,
	userId: PropTypes.string.isRequired
};
