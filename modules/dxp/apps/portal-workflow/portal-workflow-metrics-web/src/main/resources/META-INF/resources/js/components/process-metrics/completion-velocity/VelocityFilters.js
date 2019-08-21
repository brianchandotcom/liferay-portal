import React from 'react';
import {TimeRangeFilter} from '../filter/TimeRangeFilter';
import {VelocityUnitFilter} from '../filter/VelocityUnitFilter';

const VelocityFilters = () => {
	return (
		<div className="autofit-col m-0 management-bar management-bar-light navbar">
			<ul className="navbar-nav">
				<TimeRangeFilter
					filterKey="velocityTimeRange"
					hideControl={true}
					position="right"
					showFilterName={false}
				/>

				<VelocityUnitFilter hideControl={true} position="right" />
			</ul>
		</div>
	);
};

export default VelocityFilters;
