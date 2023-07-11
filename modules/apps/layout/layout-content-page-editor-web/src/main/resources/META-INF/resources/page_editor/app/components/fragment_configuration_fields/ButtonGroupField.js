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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm from '@clayui/form';
import {useControlledState} from '@liferay/layout-js-components-web';
import PropTypes from 'prop-types';
import React from 'react';

import {useId} from '../../../common/hooks/useId';
import {ConfigurationFieldPropTypes} from '../../../prop_types/index';

export function ButtonGroupField({field, onValueSelect, value}) {
	const helpTextId = useId();
	const inputId = useId();
	const [nextValue, setNextValue] = useControlledState(value);

	const updateNextValue = (value) => {
		onValueSelect(field.name, value);
		setNextValue(value);
	};

	return (
		<ClayForm.Group>
			<label className="sr-only" htmlFor={inputId}>
				{field.label}
			</label>

			{field.typeOptions?.validValues.map((validValue) => (
				<ClayButtonWithIcon
					aria-label={validValue.label}
					aria-pressed={nextValue === validValue.value}
					className={
						nextValue === validValue.value
							? 'bg-light'
							: 'text-secondary'
					}
					displayType="unstyled"
					key={validValue.value}
					onClick={() => updateNextValue(validValue.value)}
					size="sm"
					symbol={validValue.icon}
					title={validValue.label}
					value={validValue.value}
				/>
			))}

			{field.description ? (
				<p className="m-0 mt-1 small text-secondary" id={helpTextId}>
					{field.description}
				</p>
			) : null}
		</ClayForm.Group>
	);
}

ButtonGroupField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string,
};
