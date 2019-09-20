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

import {ClaySelectWithOption} from '@clayui/form';
import propTypes from 'prop-types';
import React from 'react';

class StringInput extends React.Component {
	static propTypes = {
		disabled: propTypes.bool,
		onChange: propTypes.func.isRequired,
		options: propTypes.array,
		value: propTypes.oneOfType([propTypes.string, propTypes.number])
	};

	static defaultProps = {
		options: []
	};

	_handleChange = event => {
		this.props.onChange({value: event.target.value});
	};

	render() {
		const {disabled, options, value} = this.props;

		return options.length === 0 ? (
			<input
				className="criterion-input form-control"
				data-testid="simple-string"
				disabled={disabled}
				onChange={this._handleChange}
				type="text"
				value={value}
			/>
		) : (
			<ClaySelectWithOption
				className="criterion-input form-control"
				data-testid="options-string"
				disabled={disabled}
				onChange={this._handleChange}
				options={options.map(o => ({
					label: o.label,
					value: o.value
				}))}
				value={value}
			/>
		);
	}
}

export default StringInput;
