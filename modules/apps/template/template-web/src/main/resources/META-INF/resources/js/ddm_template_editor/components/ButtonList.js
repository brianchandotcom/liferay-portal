/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import {Button} from './Button';

export function ButtonList({items, onButtonClick}) {
	return items.map((item) => (
		<Button
			key={item.label}
			label={item.label}
			onClick={() => onButtonClick(item)}
			tooltip={item.tooltip}
		/>
	));
}

ButtonList.propTypes = {
	items: PropTypes.arrayOf(PropTypes.object),
	onButtonClick: PropTypes.func.isRequired,
};
