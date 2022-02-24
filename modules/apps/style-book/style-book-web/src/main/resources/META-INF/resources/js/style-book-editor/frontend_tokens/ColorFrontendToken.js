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

import {ColorPicker} from '@liferay/layout-content-page-editor-web';
import PropTypes from 'prop-types';
import React from 'react';

export default function ColorFrontendToken({
	frontendToken,
	frontendTokensValues,
	onValueSelect,
	tokenValues,
	value,
}) {
	return (
		<ColorPicker
			editedTokenValues={frontendTokensValues}
			field={frontendToken}
			onValueSelect={onValueSelect}
			tokenValues={tokenValues}
			value={value}
		/>
	);
}

ColorFrontendToken.propTypes = {
	frontendToken: PropTypes.shape({label: PropTypes.string.isRequired})
		.isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string,
};
