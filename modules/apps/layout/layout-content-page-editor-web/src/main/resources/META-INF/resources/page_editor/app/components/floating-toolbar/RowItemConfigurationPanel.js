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

import ClayForm, {ClayCheckbox, ClaySelectWithOption} from '@clayui/form';
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

const selectOptions = (options, optionsLabel) =>
	Array.isArray(options)
		? options.map((value) => ({
				label: optionsLabel
					? Liferay.Util.sub(optionsLabel(value), value)
					: Number(value),
				value,
		  }))
		: Object.keys(options).map((key) => ({
				label: options[key],
				value: key,
		  }));

export const RowSelectConfigurationPanel = ({
	config,
	id,
	identifier,
	label,
	onValueChange,
	options,
	optionsLabel,
}) => (
	<ClayForm.Group small>
		<label htmlFor={id}>{label}</label>
		<ClaySelectWithOption
			aria-label={label}
			id={id}
			onChange={({target: {value}}) => {
				const parseValue = Number(value) || value;
				onValueChange(identifier, parseValue);
			}}
			options={selectOptions(options, optionsLabel)}
			value={String(config)}
		/>
	</ClayForm.Group>
);

RowSelectConfigurationPanel.propTypes = {
	config: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
	id: PropTypes.string,
	identifier: PropTypes.string,
	label: PropTypes.string,
	onValueChange: PropTypes.func.isRequired,
	options: PropTypes.oneOfType([PropTypes.array, PropTypes.object]),
};

export const RowCheckboxConfigurationPanel = ({
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

RowCheckboxConfigurationPanel.propTypes = {
	config: PropTypes.bool,
	identifier: PropTypes.string,
	label: PropTypes.string,
	onValueChange: PropTypes.func.isRequired,
};
