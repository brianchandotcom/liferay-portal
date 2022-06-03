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

import ClayForm, {ClayCheckbox} from '@clayui/form';
import {useControlledState} from '@liferay/layout-content-page-editor-web';
import PropTypes from 'prop-types';
import React from 'react';

export default function BooleanFrontendToken({
	frontendToken,
	onValueSelect,
	value,
}) {
	const {label} = frontendToken;
	const [nextValue, setNextValue] = useControlledState(value || false);

	return (
		<ClayForm.Group small>
			<ClayCheckbox
				checked={nextValue}
				label={label}
				onChange={(event) => {
					const nextValue = event.target.checked;

					setNextValue(nextValue);
					onValueSelect(nextValue);
				}}
			/>
		</ClayForm.Group>
	);
}

BooleanFrontendToken.propTypes = {
	frontendToken: PropTypes.shape({label: PropTypes.string.isRequired})
		.isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.bool,
};
