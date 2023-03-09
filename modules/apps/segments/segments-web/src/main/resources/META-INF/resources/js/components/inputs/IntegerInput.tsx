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
