import React from 'react';
import {TimeRangeProvider} from '../../filter/store/TimeRangeStore';
import {VelocityUnitProvider} from '../../filter/store/VelocityUnitStore';

const VelocityFiltersProvider = ({children, timeRangeKeys, unitKeys}) => {
	return (
		<TimeRangeProvider timeRangeKeys={timeRangeKeys}>
			<VelocityUnitProvider unitKeys={unitKeys}>
				{children}
			</VelocityUnitProvider>
		</TimeRangeProvider>
	);
};

export {VelocityFiltersProvider};
