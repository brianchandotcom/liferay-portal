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

import {ClayInput, ClaySelectWithOption} from '@clayui/form';
import classNames from 'classnames';
import propTypes from 'prop-types';
import React, {useState} from 'react';

import {unescapeSingleQuotes} from '../../utils/odata';

function StringInput({
	disabled,
	onChange,
	options = [],
	propertyLabel,
	renderEmptyValueErrors,
	value: initialValue,
}) {
	const [value, setValue] = useState(unescapeSingleQuotes(initialValue));

	const handleChange = (event) => {
		setValue(event.target.value);

		onChange({value: event.target.value});
	};

	return !options.length ? (
		<ClayInput
			aria-label={`${propertyLabel}: ${Liferay.Language.get(
				'input-a-value'
			)}`}
			className={classNames('criterion-input', {
				'criterion-input--error': !value && renderEmptyValueErrors,
			})}
			data-testid="simple-string"
			disabled={disabled}
			onChange={handleChange}
			type="text"
			value={value}
		/>
	) : (
		<ClaySelectWithOption
			aria-label={`${propertyLabel}: ${Liferay.Language.get(
				'select-option'
			)}`}
			className={classNames('criterion-input', {
				'criterion-input--error': !value,
			})}
			data-testid="options-string"
			disabled={disabled}
			onChange={handleChange}
			options={options.map((o) => ({
				disabled: o.disabled,
				label: o.label,
				value: o.value,
			}))}
			value={value}
		/>
	);
}

StringInput.propTypes = {
	disabled: propTypes.bool,
	initialValue: propTypes.oneOfType([propTypes.string, propTypes.number]),
	onChange: propTypes.func.isRequired,
	options: propTypes.array,
	propertyLabel: propTypes.string.isRequired,
	renderEmptyValueErrors: propTypes.bool,
};

export default StringInput;
