import React from 'react';
import {TimeRangeProvider} from '../../filter/store/TimeRangeStore';
import {VelocityUnitProvider} from '../../filter/store/VelocityUnitStore';

const VelocityFiltersProvider = ({
	children,
	timeRangeKeys,
	velocityUnitKeys
}) => {
	return (
		<TimeRangeProvider timeRangeKeys={timeRangeKeys}>
			<VelocityUnitProvider velocityUnitKeys={velocityUnitKeys}>
				{children}
			</VelocityUnitProvider>
		</TimeRangeProvider>
	);
};

export {VelocityFiltersProvider};
