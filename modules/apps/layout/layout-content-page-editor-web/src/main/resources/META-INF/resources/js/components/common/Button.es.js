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

/* eslint no-unused-vars: "warn" */

import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React from 'react';

const Button = ({children, loading, ...props}) => (
	<ClayButton {...props}>
		<span className="d-inline-flex fragments-editor__button">
			{loading && (
				<span
					aria-hidden="true"
					className="loading-animation loading-animation-sm m-0 mr-1"
				/>
			)}

			{children}
		</span>
	</ClayButton>
);

Button.defaultProps = {
	loading: false
};

Button.propTypes = {
	children: PropTypes.node.isRequired,
	loading: PropTypes.bool
};

export {Button};
export default Button;
