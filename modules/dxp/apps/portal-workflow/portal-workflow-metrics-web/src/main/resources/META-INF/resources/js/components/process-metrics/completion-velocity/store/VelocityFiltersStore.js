import React from 'react';
import {TimeRangeProvider} from './TimeRangeStore';
import {VelocityUnitProvider} from './VelocityUnitStore';

function VelocityFiltersProvider({children, timeRangeKeys, unitKeys}) {
	return (
		<TimeRangeProvider timeRangeKeys={timeRangeKeys}>
			<VelocityUnitProvider unitKeys={unitKeys}>
				{children}
			</VelocityUnitProvider>
		</TimeRangeProvider>
	);
}

export {VelocityFiltersProvider};
