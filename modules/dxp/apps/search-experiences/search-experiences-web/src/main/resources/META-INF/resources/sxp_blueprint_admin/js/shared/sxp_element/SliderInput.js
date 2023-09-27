/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClaySlider from '@clayui/slider';
import React, {useState} from 'react';

function SliderInput({
	disabled,
	id,
	label,
	max,
	min,
	name,
	onBlur,
	onChange,
	setFieldTouched,
	setFieldValue,
	step,
	value,
}) {
	const [active, setActive] = useState(false);

	const _handleSliderChange = (value) => {
		setFieldValue(name, value);
	};

	const _handleKeyDown = (event) => {
		if (event.key === 'Enter') {
			event.preventDefault();
		}
	};

	return (
		<>
			<ClayInput.Group small>
				<ClayInput.GroupItem className="arrowless-input">
					<ClayInput
						aria-label={label}
						disabled={disabled}
						id={id}
						insetAfter
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

					<ClayInput.GroupInsetItem after>
						<ClayButton
							aria-label={Liferay.Language.get('slider')}
							disabled={disabled}
							displayType="unstyled"
							onClick={() => setActive(!active)}
						>
							<ClayIcon symbol="control-panel" />
						</ClayButton>
					</ClayInput.GroupInsetItem>
				</ClayInput.GroupItem>
			</ClayInput.Group>

			{active && (
				<div className="slider-configuration">
					<ClaySlider
						max={max}
						min={min}
						onBlur={() => setFieldTouched(name)}
						onChange={_handleSliderChange}
						step={step}
						value={value}
					/>
				</div>
			)}
		</>
	);
}

export default SliderInput;
