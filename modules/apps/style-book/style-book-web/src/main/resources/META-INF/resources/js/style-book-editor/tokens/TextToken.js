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

import ClayForm, {ClayInput} from '@clayui/form';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

import {config} from '../config';
import {TOKEN_TYPES} from '../constants/tokenTypes';

const TOKEN_TYPE_TO_PROPS = {
	[TOKEN_TYPES.integer]: {pattern: '\\d+', step: 1, type: 'number'},
	[TOKEN_TYPES.number]: {type: 'number'},
	[TOKEN_TYPES.string]: {type: 'text'},
};

const debouncedOnValueSelect = debounce(
	(onValueSelect, value) => onValueSelect(value),
	300
);

export default function TextToken({onValueSelect, token, value}) {
	const {label, name} = token;

	const id = `${config.namespace}_tokenId_${name}`;

	return (
		<ClayForm.Group small>
			<label htmlFor={id}>{label}</label>
			<ClayInput
				defaultValue={value}
				id={id}
				onChange={(event) =>
					debouncedOnValueSelect(onValueSelect, event.target.value)
				}
				{...TOKEN_TYPE_TO_PROPS[token.type]}
			/>
		</ClayForm.Group>
	);
}

TextToken.propTypes = {
	onValueSelect: PropTypes.func.isRequired,
	token: PropTypes.shape({
		label: PropTypes.string.isRequired,
	}).isRequired,
	value: PropTypes.any,
};
