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

import {Collapse} from '@liferay/layout-content-page-editor-web';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {config} from '../style-book-editor/config';
import {StyleBookContext} from './StyleBookContext';
import {FRONTEND_TOKEN_TYPES} from './constants/frontendTokenTypes';
import BooleanFrontendToken from './frontend_tokens/BooleanFrontendToken';
import ColorFrontendToken from './frontend_tokens/ColorFrontendToken';
import SelectFrontendToken from './frontend_tokens/SelectFrontendToken';
import TextFrontendToken from './frontend_tokens/TextFrontendToken';

const getColorFrontendTokens = (
	{frontendTokenCategories},
	frontendTokensValues
) => {
	let tokens = {};

	for (const category of frontendTokenCategories) {
		for (const tokenSet of category.frontendTokenSets) {
			for (const token of tokenSet.frontendTokens) {
				tokens = {
					...tokens,
					[token.name]: {
						editorType: token.editorType,
						label: token.label,
						name: token.name,
						tokenCategoryLabel: category.label,
						tokenSetLabel: tokenSet.label,
						value:
							frontendTokensValues[token.name]?.value ||
							token.defaultValue,
						[token.mappings[0].type]: token.mappings[0].value,
					},
				};
			}
		}
	}

	return tokens;
};

export default function FrontendTokenSet({frontendTokens, label}) {
	const {frontendTokensValues = {}, setFrontendTokensValues} = useContext(
		StyleBookContext
	);

	const tokenValues = getColorFrontendTokens(
		config.frontendTokenDefinition,
		frontendTokensValues
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
					name: tokenValues[value]?.name,
					value: tokenValues[value]?.value || value,
				},
			});
		}
	};

	return (
		<Collapse label={label} open>
			{frontendTokens.map((frontendToken) => {
				const FrontendTokenComponent = getFrontendTokenComponent(
					frontendToken
				);

				let props = {
					frontendToken,
					onValueSelect: (value) =>
						updateFrontendTokensValues(frontendToken, value),
					value:
						frontendTokensValues[frontendToken.name]?.name ||
						frontendTokensValues[frontendToken.name]?.value ||
						frontendToken.defaultValue,
				};

				if (frontendToken.editorType === 'ColorPicker') {
					props = {
						...props,
						frontendTokensValues,
						onValueSelect: (_, value) => {
							updateFrontendTokensValues(frontendToken, value);
						},
						tokenValues,
					};
				}

				return (
					<FrontendTokenComponent
						key={frontendToken.name}
						{...props}
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
