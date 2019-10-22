import React from 'react';
import PropTypes from 'prop-types';
import ClaySlider from '@clayui/slider';

function SliderWithLabel({
	label,
	subTitle,
	value,
	onValueChange,
	max = 99,
	min = 1
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
