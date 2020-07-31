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
import {FRONTEND_TOKEN_TYPES} from './constants/frontendTokenTypes';
import BooleanFrontendToken from './frontend_tokens/BooleanFrontendToken';
import ColorFrontendToken from './frontend_tokens/ColorFrontendToken';
import SelectFrontendToken from './frontend_tokens/SelectFrontendToken';
import TextFrontendToken from './frontend_tokens/TextFrontendToken';

export default function FrontendTokenSet({frontendTokens, label}) {
	const {frontendTokensValues = {}, setFrontendTokensValues} = useContext(
		StyleBookContext
	);

	const updateFrontendTokensValues = (frontendToken, value) => {
		const {mappings = [], name} = frontendToken;

		const cssVariableMapping = mappings.find(
			(mapping) => mapping.type === 'cssVariable'
		);

		if (value) {
			setFrontendTokensValues({
				...frontendTokensValues,
				[name]: {
					cssVariableMapping: cssVariableMapping.value,
					value,
				},
			});
		}
	};

	return (
		<Collapse label={label}>
			{frontendTokens.map((frontendToken) => {
				const FrontendTokenComponent = getFrontendTokenComponent(
					frontendToken
				);

				return (
					<FrontendTokenComponent
						frontendToken={frontendToken}
						key={frontendToken.name}
						onValueSelect={(value) =>
							updateFrontendTokensValues(frontendToken, value)
						}
						value={
							frontendTokensValues[frontendToken.name]?.value ||
							frontendToken.defaultValue
						}
					/>
				);
			})}
		</Collapse>
	);
}

function getFrontendTokenComponent(frontendToken) {
	if (frontendToken.editorType === 'ColorPicker') {
		return ColorFrontendToken;
	}

	if (frontendToken.validValues) {
		return SelectFrontendToken;
	}

	if (frontendToken.type === FRONTEND_TOKEN_TYPES.boolean) {
		return BooleanFrontendToken;
	}

	return TextFrontendToken;
}

FrontendTokenSet.propTypes = {
	frontendTokens: PropTypes.arrayOf(
		PropTypes.shape({
			name: PropTypes.string.isRequired,
		})
	),
	name: PropTypes.string.isRequired,
};
