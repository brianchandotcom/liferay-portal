/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
