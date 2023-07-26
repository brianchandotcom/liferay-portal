/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm from '@clayui/form';
import {useControlledState} from '@liferay/layout-js-components-web';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React from 'react';

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
