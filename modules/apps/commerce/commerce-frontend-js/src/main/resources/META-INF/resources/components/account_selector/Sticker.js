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

import ClaySticker from '@clayui/sticker';
import PropTypes from 'prop-types';
import React from 'react';

import {getInitials} from './util/index';

function AccountSticker({logoURL, name, size}) {
	return (
		<ClaySticker
			className="current-account-thumbnail"
			shape="user-icon"
			size={size}
		>
			{logoURL ? (
				<ClaySticker.Image alt={name} src={logoURL} />
			) : (
				getInitials(name)
			)}
		</ClaySticker>
	);
}

AccountSticker.propTypes = {
	className: PropTypes.string,
	logoURL: PropTypes.string,
	name: PropTypes.string,
	size: PropTypes.string,
};

export default AccountSticker;
