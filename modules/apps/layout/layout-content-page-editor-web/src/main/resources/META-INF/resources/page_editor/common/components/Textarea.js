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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

export default function Textarea({autoFocus = false, value = '', ...props}) {
	return (
		<textarea
			className={classNames('form-control', 'page-editor__textarea', {
				'page-editor__textarea--empty': !value
			})}
			ref={textarea => autoFocus && textarea && textarea.focus()}
			{...props}
		/>
	);
}

Textarea.propTypes = {
	autoFocus: PropTypes.bool,
	value: PropTypes.string
};
