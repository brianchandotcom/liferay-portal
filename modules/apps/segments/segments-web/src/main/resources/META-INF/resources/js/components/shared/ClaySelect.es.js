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

class ClaySelect extends Component {
	static propTypes = {
		className: PropTypes.string,
		onChange: PropTypes.func.isRequired,
		options: PropTypes.arrayOf(
			PropTypes.shape({
				label: PropTypes.string,
				value: PropTypes.string.isRequired
			})
		).isRequired,
		selected: PropTypes.string
	};

	render() {
		const {className, options, selected, ...otherProps} = this.props;

		const classes = getCN('form-control', className);

		return (
			<select className={classes} {...otherProps} value={selected}>
				{options.map(({label, value}, index) => (
					<option key={index} value={value}>
						{label ? label : value}
					</option>
				))}
			</select>
		);
	}
}

export default ClaySelect;
