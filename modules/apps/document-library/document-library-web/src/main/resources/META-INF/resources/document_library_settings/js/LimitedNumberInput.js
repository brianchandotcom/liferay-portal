/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const LimitedNumberInput = ({
	errorMessage,
	label,
	limitValue,
	minimumValue,
	name,
	unlimitedValue,
	value,
}) => {
	const [error, setError] = useState(false);
	const [inputValue, setInputValue] = useState(value);

	const onChange = (event) => {
		if (event.target.value === '') {
			setInputValue('');

			return;
		}

		const numberValue = parseInt(event.target.value, 10);

		setInputValue(numberValue);

		const numberLimitValue = parseInt(limitValue, 10);
		const numberUnlimitedValue = parseInt(unlimitedValue, 10);

		setError(
			numberLimitValue !== numberUnlimitedValue &&
				(numberValue === numberUnlimitedValue ||
					numberValue > numberLimitValue)
		);
	};

	return (
		<ClayForm.Group className={error ? 'has-error' : ''}>
			<label htmlFor={name}>{label}</label>

			<ClayInput
				aria-label={label}
				className="form-control"
				min={minimumValue}
				name={name}
				onChange={onChange}
				type="number"
				value={inputValue}
			/>

			{error && (
				<ClayAlert
					className="mt-1"
					displayType="danger"
					title={errorMessage}
					variant="feedback"
				/>
			)}
		</ClayForm.Group>
	);
};

LimitedNumberInput.propTypes = {
	errorMessage: PropTypes.string.isRequired,
	label: PropTypes.string.isRequired,
	limitValue: PropTypes.number.isRequired,
	minimumValue: PropTypes.number.isRequired,
	name: PropTypes.string.isRequired,
	unlimitedValue: PropTypes.number.isRequired,
	value: PropTypes.number.isRequired,
};

export default LimitedNumberInput;
