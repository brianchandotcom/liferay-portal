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
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {ConfigurationFieldPropTypes} from '../../../prop-types/index';

export const CheckboxField = ({field, onValueSelect, value}) => {
	const [nextValue, setNextValue] = useState(!!value);

	return (
		<ClayForm.Group className="mt-1">
			<ClayCheckbox
				aria-label={field.label}
				checked={nextValue}
				label={field.label}
				onChange={(event) => {
					setNextValue(event.target.checked);
					onValueSelect(field.name, event.target.checked);
				}}
			/>
		</ClayForm.Group>
	);
};

CheckboxField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.bool, PropTypes.string]),
};
