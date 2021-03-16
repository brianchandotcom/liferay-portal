/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayInput} from '@clayui/form';
import getCN from 'classnames';
import React, {useState} from 'react';

import {toNumber} from '../../utils/utils';

function NumberInput({
	configKey,
	defaultValue,
	disabled,
	id,
	initialValue,
	label,
	max,
	min,
	onChange,
	step,
	unit,
}) {
	const [value, setValue] = useState(initialValue || defaultValue);

	const _handleBlur = () => {
		onChange(configKey, toNumber(value));
	};

	const _handleChange = (event) => {
		setValue(event.target.value);
	};

	return (
		<ClayInput.Group small>
			<ClayInput.GroupItem
				className={getCN({
					'arrowless-input':
						unit || (configKey && configKey.includes('id')),
				})}
				prepend
			>
				<ClayInput
					aria-label={label}
					disabled={disabled}
					id={id}
					max={max}
					min={min}
					onBlur={_handleBlur}
					onChange={_handleChange}
					step={step}
					type="number"
					value={value}
				/>
			</ClayInput.GroupItem>

			{unit && (
				<ClayInput.GroupItem append shrink>
					<ClayInput.GroupText
						className={getCN({secondary: disabled})}
					>
						{unit}
					</ClayInput.GroupText>
				</ClayInput.GroupItem>
			)}
		</ClayInput.Group>
	);
}

export default NumberInput;
