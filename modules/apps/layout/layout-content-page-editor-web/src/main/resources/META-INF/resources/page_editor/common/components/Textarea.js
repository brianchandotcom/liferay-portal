/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

export default function Textarea({autoFocus = false, value = '', ...props}) {
	return (
		<textarea
			className={classNames('form-control', 'page-editor__textarea', {
				'page-editor__textarea--empty': !value,
			})}
			ref={(textarea) => autoFocus && textarea && textarea.focus()}
			{...props}
		/>
	);
}

Textarea.propTypes = {
	autoFocus: PropTypes.bool,
	value: PropTypes.string,
};
