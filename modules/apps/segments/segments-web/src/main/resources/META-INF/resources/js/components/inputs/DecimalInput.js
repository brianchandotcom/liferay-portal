/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput, ClaySelectWithOption} from '@clayui/form';
import propTypes from 'prop-types';
import React from 'react';

function DecimalInput({
	disabled,
	onChange,
	options = [],
	propertyLabel,
	value: initialValue,
}) {
	const onDecimalBlur = (event) => {
		const value = Number.parseFloat(event.target.value).toFixed(2);

		onChange({value});
	};

	const onDecimalChange = (event) => {
		onChange({value: event.target.value});
	};

	return !options.length ? (
		<ClayInput
			aria-label={`${propertyLabel}: ${Liferay.Language.get(
				'input-a-value'
			)}`}
			className="criterion-input"
			data-testid="decimal-number"
			disabled={disabled}
			onBlur={onDecimalBlur}
			onChange={onDecimalChange}
			step="0.01"
			type="number"
			value={initialValue}
		/>
	) : (
		<ClaySelectWithOption
			className="criterion-input"
			data-testid="options-decimal"
			disabled={disabled}
			onChange={onDecimalChange}
			options={options.map((o) => ({
				disabled: o.disabled,
				label: o.label,
				value: o.value,
			}))}
			value={initialValue}
		/>
	);
}

DecimalInput.propTypes = {
	disabled: propTypes.bool,
	onChange: propTypes.func.isRequired,
	options: propTypes.array,
	propertyLabel: propTypes.string.isRequired,
	value: propTypes.oneOfType([propTypes.string, propTypes.number]),
};

export default DecimalInput;
