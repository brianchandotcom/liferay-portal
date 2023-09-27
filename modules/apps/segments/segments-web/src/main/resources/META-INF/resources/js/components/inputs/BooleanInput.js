/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelectWithOption} from '@clayui/form';
import classNames from 'classnames';
import propTypes from 'prop-types';
import React from 'react';

import {BOOLEAN_OPTIONS} from '../../utils/constants';

function BooleanInput({className, disabled, onChange, propertyLabel, value}) {
	const handleChange = (event) => {
		onChange({value: event.target.value});
	};

	return (
		<ClaySelectWithOption
			aria-label={`${propertyLabel}: ${Liferay.Language.get(
				'select-option'
			)}`}
			className={classNames('criterion-input form-control', className)}
			data-testid="options-boolean"
			disabled={disabled}
			onChange={handleChange}
			options={BOOLEAN_OPTIONS}
			value={value}
		/>
	);
}

BooleanInput.propTypes = {
	className: propTypes.string,
	disabled: propTypes.bool,
	onChange: propTypes.func.isRequired,
	propertyLabel: propTypes.string.isRequired,
	value: propTypes.string,
};

export default BooleanInput;
