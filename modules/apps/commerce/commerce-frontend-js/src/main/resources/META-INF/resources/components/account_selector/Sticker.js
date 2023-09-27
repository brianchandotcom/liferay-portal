/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
