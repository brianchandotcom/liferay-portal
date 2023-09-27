/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelectWithOption} from '@clayui/form';
import React, {ChangeEvent} from 'react';

interface Props {
	disabled?: boolean;
	onChange: (payload: {value: string}) => void;
	options?: Array<{
		disabled: boolean;
		label: string;
		value: string;
	}>;
	propertyLabel?: string;
	value?: number | string;
}

function IntegerInput({
	disabled,
	onChange,
	options,
	propertyLabel,
	value,
}: Props) {
	const handleIntegerChange = (
		event: ChangeEvent<HTMLInputElement | HTMLSelectElement>
	) => {
		const value = parseInt(event.target.value, 10);

		if (!isNaN(value)) {
			onChange({
				value: value.toString(),
			});
		}
	};

	return options?.length ? (
		<ClaySelectWithOption
			className="criterion-input form-control"
			data-testid="options-integer"
			disabled={disabled}
			onChange={handleIntegerChange}
			options={options}
			value={value}
		/>
	) : (
		<input
			aria-label={`${propertyLabel}: ${Liferay.Language.get(
				'input-a-value'
			)}`}
			className="criterion-input form-control"
			data-testid="integer-number"
			disabled={disabled}
			onChange={handleIntegerChange}
			type="number"
			value={value}
		/>
	);
}

export default IntegerInput;
