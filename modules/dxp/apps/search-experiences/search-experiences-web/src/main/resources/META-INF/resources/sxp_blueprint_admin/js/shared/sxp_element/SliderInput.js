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
