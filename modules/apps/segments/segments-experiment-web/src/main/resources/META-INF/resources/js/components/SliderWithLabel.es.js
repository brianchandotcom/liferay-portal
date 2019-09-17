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

import React from 'react';
import PropTypes from 'prop-types';
import ClaySlider from '@clayui/slider';

function SliderWithLabel({
	label,
	subTitle,
	value,
	onValueChange,
	max = 100,
	min = 0
}) {
	return (
		<label className="form-group-autofit">
			<span className="form-group-item">
				{label}
				{subTitle && (
					<span className="form-text font-weight-normal">
						{subTitle}
					</span>
				)}
			</span>
			<div className="form-group-item flex-row">
				<ClaySlider
					className="w-100"
					max={max}
					min={min}
					onValueChange={onValueChange}
					showTooltip={false}
					value={value}
				/>
				<small className="form-text font-weight-normal ml-3">
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
	value: PropTypes.number.isRequired
};

export {SliderWithLabel};
