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
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

const FormField = ({children, error, id, name}) => {
	const hasError = error != null;

	return (
		<div
			className={classNames({'form-group': true, 'has-error': hasError})}
		>
			<label htmlFor={id}>
				{name}
				<span className="reference-mark">
					<ClayIcon symbol="asterisk" />
				</span>
			</label>

			{children}

			{hasError && (
				<div className="form-feedback-item">
					<span className="form-feedback-indicator mr-1">
						<ClayIcon symbol="exclamation-full" />
					</span>
					{error}
				</div>
			)}
		</div>
	);
};

FormField.propTypes = {
	error: PropTypes.string,
	id: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired
};

export {FormField};
export default FormField;
