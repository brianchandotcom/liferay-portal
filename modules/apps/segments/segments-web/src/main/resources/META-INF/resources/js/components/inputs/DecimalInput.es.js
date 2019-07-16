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

class DecimalInput extends React.Component {
	static propTypes = {
		disabled: propTypes.bool,
		onChange: propTypes.func.isRequired,
		value: propTypes.oneOfType([propTypes.string, propTypes.number])
	};

	_handleDecimalBlur = event => {
		const value = Number.parseFloat(event.target.value).toFixed(2);

		this.props.onChange({value});
	};

	_handleDecimalChange = event => {
		this.props.onChange({value: event.target.value});
	};

	render() {
		const {disabled, value} = this.props;

		return (
			<input
				className="criterion-input form-control"
				data-testid="decimal-number"
				disabled={disabled}
				onBlur={this._handleDecimalBlur}
				onChange={this._handleDecimalChange}
				step="0.01"
				type="number"
				value={value}
			/>
		);
	}
}

export default DecimalInput;
