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

import PropTypes from 'prop-types';
import React from 'react';

import InvisibleFieldset from '../../../common/components/InvisibleFieldset';
import SpacingBox from '../../../common/components/SpacingBox';
import useControlledState from '../../../core/hooks/useControlledState';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';

export function SpacingBoxField({disabled, field, onValueSelect, value}) {
	const [nextValue, setNextValue] = useControlledState(value);

	const handleChange = (key, value) => {
		setNextValue((previousvalue) => ({...previousvalue, [key]: value}));
		onValueSelect(key, value);
	};

	return (
		<>
			<InvisibleFieldset disabled={disabled}>
				<h5 className="sr-only">{field.label}</h5>

				<SpacingBox
					defaultValue={field.defaultValue}
					onChange={handleChange}
					options={field.validValues}
					value={nextValue}
				/>
			</InvisibleFieldset>
		</>
	);
}

SpacingBoxField.propTypes = {
	className: PropTypes.string,
	disabled: PropTypes.bool,
	field: PropTypes.shape({
		ConfigurationFieldPropTypes,
	}),
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.object,
};
