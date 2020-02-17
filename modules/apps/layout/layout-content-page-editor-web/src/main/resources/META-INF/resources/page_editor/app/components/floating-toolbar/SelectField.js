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

import {ConfigurationFieldPropTypes} from '../../../prop-types/index';

export const SelectField = ({field, onValueSelect, value = ''}) => (
	<ClayForm.Group small>
		<label htmlFor={field.name}>{field.label}</label>

		<ClaySelectWithOption
			aria-label={field.label}
			defaultValue={field.defaultValue}
			id={field.name}
			onChange={event => {
				onValueSelect(
					field.name,
					event.target.options[event.target.selectedIndex].value
				);
			}}
			options={field.typeOptions.validValues}
			value={value}
		/>
	</ClayForm.Group>
);

SelectField.propTypes = {
	field: PropTypes.shape({
		...ConfigurationFieldPropTypes,
		typeOptions: PropTypes.shape({
			validValues: PropTypes.arrayOf(
				PropTypes.shape({
					label: PropTypes.string.isRequired,
					value: PropTypes.string.isRequired
				})
			).isRequired
		}).isRequired
	}),

	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string
};
