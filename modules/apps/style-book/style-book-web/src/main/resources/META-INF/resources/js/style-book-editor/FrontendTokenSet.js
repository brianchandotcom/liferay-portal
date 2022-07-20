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
import React, {useCallback} from 'react';

import {FRONTEND_TOKEN_TYPES} from './constants/frontendTokenTypes';
import {
	useFrontendTokensValues,
	useSaveTokenValue,
} from './contexts/StyleBookEditorContext';
import BooleanFrontendToken from './frontend_tokens/BooleanFrontendToken';
import ColorFrontendToken from './frontend_tokens/ColorFrontendToken';
import LengthFrontendToken from './frontend_tokens/LengthFrontendToken';
import SelectFrontendToken from './frontend_tokens/SelectFrontendToken';
import TextFrontendToken from './frontend_tokens/TextFrontendToken';

export default function FrontendTokenSet({
	frontendTokens,
	label,
	open,
	tokenValues,
}) {
	const frontendTokensValues = useFrontendTokensValues();
	const saveTokenValue = useSaveTokenValue();

	const updateFrontendTokensValues = useCallback(
		(frontendToken, value) => {
			const {mappings = [], label, name} = frontendToken;

			const cssVariableMapping = mappings.find(
				(mapping) => mapping.type === 'cssVariable'
			);

			if (value) {
				saveTokenValue({
					label,
					name,
					value: {
						cssVariableMapping: cssVariableMapping.value,
						name: tokenValues[value]?.name,
						value: tokenValues[value]?.value || value,
					},
				});
			}
		},
		[saveTokenValue, tokenValues]
	);

	return (
		<Collapse label={label} open={open}>
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
						onValueSelect: (name, value) =>
							updateFrontendTokensValues(frontendToken, value),
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

	if (
		Liferay.FeatureFlags['LPS-143206'] &&
		frontendToken.editorType === 'Length'
	) {
		return LengthFrontendToken;
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
	name: PropTypes.string,
};
