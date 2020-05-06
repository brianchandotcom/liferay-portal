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

const ClayCheckboxWithState = ({onValueChange, ...otherProps}) => {
	const [value, setValue] = useState(false);

	return (
		<ClayCheckbox
			checked={value}
			onChange={({target: {checked}}) => {
				setValue((val) => !val);
				onValueChange(checked);
			}}
			{...otherProps}
		/>
	);
};

export const RowConfigurationCheckboxField = ({
	config,
	identifier,
	label,
	onValueChange,
}) => (
	<ClayForm.Group>
		<ClayCheckboxWithState
			aria-label={label}
			checked={config}
			label={label}
			onValueChange={(value) => onValueChange(identifier, value)}
		/>
	</ClayForm.Group>
);

RowConfigurationCheckboxField.propTypes = {
	config: PropTypes.bool,
	identifier: PropTypes.string,
	label: PropTypes.string,
	onValueChange: PropTypes.func.isRequired,
};
