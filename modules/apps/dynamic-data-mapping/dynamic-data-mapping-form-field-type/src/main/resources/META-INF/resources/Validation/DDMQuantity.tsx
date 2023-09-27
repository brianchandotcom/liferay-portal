/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import React, {
	ChangeEvent,
	FocusEvent,
	KeyboardEvent,
	useEffect,
	useState,
} from 'react';

// @ts-ignore

import {limitValue} from '../util/numericalOperations';

const MAX_QUANTITY = 999;
const MIN_QUANTITY = 1;

const DDMQuantity: React.FC<IProps> = ({
	label,
	name,
	onQuantityChange,
	readOnly,
	value: initialValue,
}) => {
	const [value, setValue] = useState<string>(initialValue.toString());

	useEffect(() => {
		setValue(initialValue.toString());
	}, [initialValue]);

	const handleBlur = ({target}: FocusEvent<HTMLInputElement>) => {
		const value = limitValue({
			defaultValue: MIN_QUANTITY,
			max: MAX_QUANTITY,
			min: MIN_QUANTITY,
			value: parseInt(target.value, 10),
		});

		setValue(value.toString());
		onQuantityChange(value);
	};

	const handleChange = ({target: {value}}: ChangeEvent<HTMLInputElement>) => {
		setValue(value);
	};

	const normalizeValue = (event: KeyboardEvent<HTMLInputElement>) => {
		if (/[-.+,]/.test(event.key)) {
			event.preventDefault();
		}
	};

	return (
		<label>
			{label}

			<ClayInput
				disabled={readOnly}
				max={MAX_QUANTITY}
				min={MIN_QUANTITY}
				name={name}
				onBlur={handleBlur}
				onChange={handleChange}
				onKeyPress={normalizeValue}
				type="number"
				value={value}
			/>
		</label>
	);
};

export default DDMQuantity;

interface IProps {
	label: string;
	name?: string;
	onQuantityChange: (quantity: number) => void;
	readOnly?: boolean;
	value: number;
}
