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

const selectOptions = (options, getOptionLabel) =>
	Array.isArray(options)
		? options.map((value) => ({
				label: getOptionLabel
					? Liferay.Util.sub(getOptionLabel(value), value)
					: Number(value),
				value,
		  }))
		: Object.keys(options).map((key) => ({
				label: options[key],
				value: key,
		  }));

export const RowConfigurationSelectField = ({
	fieldValue,
	getOptionLabel,
	id,
	identifier,
	label,
	onValueChange,
	options,
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
			options={selectOptions(options, getOptionLabel)}
			value={String(fieldValue)}
		/>
	</ClayForm.Group>
);

RowConfigurationSelectField.propTypes = {
	fieldValue: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
	id: PropTypes.string,
	identifier: PropTypes.string,
	label: PropTypes.string,
	onValueChange: PropTypes.func.isRequired,
	options: PropTypes.oneOfType([PropTypes.array, PropTypes.object]),
};
