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

import getCN from 'classnames';
import React, {Component} from 'react';
import {PropTypes} from 'prop-types';

class ClaySpinner extends Component {
	static propTypes = {
		className: PropTypes.string,
		light: PropTypes.bool,
		loading: PropTypes.bool,
		size: PropTypes.oneOf(['sm'])
	};

	static defaultProps = {
		loading: false
	};

	render() {
		const {className, light, loading, size} = this.props;

		const classes = getCN(
			'loading-animation',
			{
				'loading-animation-light': light,
				'loading-animation-sm': size === 'sm'
			},
			className
		);

		return loading && <span className={classes} data-testid='spinner' />;
	}
}

export default ClaySpinner;
