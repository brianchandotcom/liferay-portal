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
import classNames from 'classnames';
import propTypes from 'prop-types';
import React from 'react';

import {unescapeSingleQuotes} from '../../utils/odata.es';
class StringInput extends React.Component {
	static propTypes = {
		disabled: propTypes.bool,
		onChange: propTypes.func.isRequired,
		options: propTypes.array,
		renderEmptyValueErrors: propTypes.bool,
		value: propTypes.oneOfType([propTypes.string, propTypes.number]),
	};

	static defaultProps = {
		options: [],
	};

	constructor(props) {
		super(props);

		this.state = {
			value: props.value,
		};
	}

	componentDidMount() {
		this.setState({
			value: unescapeSingleQuotes(this.props.value),
		});
	}

	_handleChange = (event) => {
		this.setState({
			value: event.target.value,
		});

		this.props.onChange({value: event.target.value});
	};

	render() {
		const {disabled, options, renderEmptyValueErrors, value} = this.props;

		return options.length === 0 ? (
			<input
				className={classNames('criterion-input form-control', {
					'criterion-input--error': !value && renderEmptyValueErrors,
				})}
				data-testid="simple-string"
				disabled={disabled}
				onChange={this._handleChange}
				type="text"
				value={this.state.value}
			/>
		) : (
			<ClaySelectWithOption
				className={classNames('criterion-input form-control', {
					'criterion-input--error': !value,
				})}
				data-testid="options-string"
				disabled={disabled}
				onChange={this._handleChange}
				options={options.map((o) => ({
					disabled: o.disabled,
					label: o.label,
					value: o.value,
				}))}
				value={value}
			/>
		);
	}
}

export default StringInput;
