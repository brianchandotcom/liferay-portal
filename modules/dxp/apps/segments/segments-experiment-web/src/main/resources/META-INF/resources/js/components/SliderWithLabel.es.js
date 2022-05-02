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

import ClaySlider from '@clayui/slider';
import PropTypes from 'prop-types';
import React from 'react';

function SliderWithLabel({
	label,
	max = 99,
	min = 1,
	onValueChange,
	subTitle,
	value,
}) {
	return (
		<label className="form-group-autofit">
			<span className="form-group-item">
				{label}

				{subTitle && (
					<span className="font-weight-normal form-text">
						{subTitle}
					</span>
				)}
			</span>

			<div className="flex-row form-group-item">
				<ClaySlider
					className="w-100"
					max={max}
					min={min}
					onChange={onValueChange}
					showTooltip={false}
					value={value}
				/>

				<small className="font-weight-normal form-text ml-3">
					{value + '%'}
				</small>
			</div>
		</label>
	);
}

SliderWithLabel.propTypes = {
	label: PropTypes.string.isRequired,
	max: PropTypes.number,
	min: PropTypes.number,
	onValueChange: PropTypes.func.isRequired,
	subTitle: PropTypes.string,
	value: PropTypes.number.isRequired,
};

export {SliderWithLabel};
