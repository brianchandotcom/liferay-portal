/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
