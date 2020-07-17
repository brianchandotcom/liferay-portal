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

import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import Collapse from './Collapse';
import {StyleBookContext} from './StyleBookContext';
import {TOKEN_TYPES} from './constants/tokenTypes';
import BooleanToken from './tokens/BooleanToken';
import ColorToken from './tokens/ColorToken';
import SelectToken from './tokens/SelectToken';
import TextToken from './tokens/TextToken';

export default function TokenSet({name, tokens}) {
	const {tokensValues = {}, setTokensValues} = useContext(StyleBookContext);

	const updateTokensValues = (token, value) => {
		const {mappings = [], name} = token;

		const cssVariableMapping = mappings.find(
			(mapping) => mapping.type === 'cssVariable'
		);

		if (value) {
			setTokensValues({
				...tokensValues,
				[name]: {
					cssVariableMapping: cssVariableMapping.value,
					value,
				},
			});
		}
	};

	return (
		<Collapse label={name}>
			{tokens.map((token) => {
				const TokenComponent = getTokenComponent(token);

				return (
					<TokenComponent
						key={token.name}
						onValueSelect={(value) =>
							updateTokensValues(token, value)
						}
						token={token}
						value={tokensValues[token.name]?.value}
					/>
				);
			})}
		</Collapse>
	);
}

function getTokenComponent(token) {
	if (token.editorType === 'ColorPicker') {
		return ColorToken;
	}

	if (token.validValues) {
		return SelectToken;
	}

	if (token.type === TOKEN_TYPES.boolean) {
		return BooleanToken;
	}

	return TextToken;
}

TokenSet.propTypes = {
	name: PropTypes.string.isRequired,
	tokens: PropTypes.arrayOf(
		PropTypes.shape({
			name: PropTypes.string.isRequired,
		})
	),
};
