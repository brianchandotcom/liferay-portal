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
import React from 'react';

function NumberInput({
	configKey,
	disabled,
	id,
	label,
	max,
	min,
	name,
	onBlur,
	onChange,
	step,
	unit,
	value,
}) {
	const _handleKeyDown = (event) => {
		if (event.key === 'Enter') {
			event.preventDefault();
		}
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
					name={name}
					onBlur={onBlur}
					onChange={onChange}
					onKeyDown={_handleKeyDown}
					step={step}
					type="number"
					value={value}
				/>
			</ClayInput.GroupItem>

			{unit && (
				<ClayInput.GroupItem append shrink>
					<ClayInput.GroupText
						className={getCN({
							secondary: disabled,
						})}
					>
						{unit}
					</ClayInput.GroupText>
				</ClayInput.GroupItem>
			)}
		</ClayInput.Group>
	);
}

export default NumberInput;
