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

import PropTypes from 'prop-types';
import React from 'react';

const InvisibleFieldset = props => (
	<fieldset disabled={props.disabled}>{props.children}</fieldset>
);

InvisibleFieldset.defaultProps = {
	disabled: false
};

InvisibleFieldset.propTypes = {
	children: PropTypes.node.isRequired,
	disabled: PropTypes.bool
};

export {InvisibleFieldset};
export default InvisibleFieldset;
