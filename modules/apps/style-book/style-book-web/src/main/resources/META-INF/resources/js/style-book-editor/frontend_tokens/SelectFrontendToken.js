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

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

import {useId} from '../useId';

export default function SelectFrontendToken({
	frontendToken,
	onValueSelect,
	value,
}) {
	const {label, validValues} = frontendToken;

	const id = useId();

	return (
		<ClayForm.Group small>
			<label htmlFor={id}>{label}</label>

			<ClaySelectWithOption
				defaultValue={value}
				id={id}
				onChange={(event) => {
					const value =
						event.target.options[event.target.selectedIndex].value;

					onValueSelect(value);
				}}
				options={validValues}
			/>
		</ClayForm.Group>
	);
}

SelectFrontendToken.propTypes = {
	frontendToken: PropTypes.shape({
		label: PropTypes.string.isRequired,
		validValues: PropTypes.arrayOf(
			PropTypes.shape({
				label: PropTypes.string.isRequired,
				value: PropTypes.any.isRequired,
			})
		),
	}).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.any,
};
