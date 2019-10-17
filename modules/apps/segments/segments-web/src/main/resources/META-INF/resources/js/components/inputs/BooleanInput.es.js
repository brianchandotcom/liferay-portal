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
import getCN from 'classnames';
import propTypes from 'prop-types';
import React from 'react';

import {BOOLEAN_OPTIONS} from '../../utils/constants.es';

class BooleanInput extends React.Component {
	static propTypes = {
		className: propTypes.string,
		disabled: propTypes.bool,
		onChange: propTypes.func.isRequired,
		value: propTypes.string
	};

	_handleChange = event => {
		this.props.onChange({value: event.target.value});
	};

	render() {
		const {className, disabled, value} = this.props;

		const classes = getCN('criterion-input', 'form-control', className);

		return (
			<ClaySelectWithOption
				className={classes}
				data-testid="options-boolean"
				disabled={disabled}
				onChange={this._handleChange}
				options={BOOLEAN_OPTIONS}
				value={value}
			/>
		);
	}
}

export default BooleanInput;
