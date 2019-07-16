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

import propTypes from 'prop-types';
import React from 'react';

class IntegerInput extends React.Component {
	static propTypes = {
		disabled: propTypes.bool,
		onChange: propTypes.func.isRequired,
		value: propTypes.oneOfType([propTypes.string, propTypes.number])
	};

	_handleIntegerChange = event => {
		const value = parseInt(event.target.value, 10);

		if (!isNaN(value)) {
			this.props.onChange({value: value.toString()});
		}
	};

	render() {
		const {disabled, value} = this.props;

		return (
			<input
				className="criterion-input form-control"
				data-testid="integer-number"
				disabled={disabled}
				onChange={this._handleIntegerChange}
				type="number"
				value={value}
			/>
		);
	}
}

export default IntegerInput;
