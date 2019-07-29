import Filter from '../../../shared/components/filter/Filter';
import React, {useContext} from 'react';
import {TimeRangeContext} from './store/TimeRangeStore';
import {VelocityUnitContext} from './store/VelocityUnitStore';

export default function VelocityFilters() {
	return (
		<div className="autofit-col m-0 management-bar management-bar-light navbar">
			<ul className="navbar-nav">
				<TimeRangeFilter multiple={false} />

				<VelocityUnitFilter multiple={false} />
			</ul>
		</div>
	);
}

function TimeRangeFilter() {
	const {defaultTimeRange, getSelectedTimeRange, timeRanges} = useContext(
		TimeRangeContext
	);

	const selectedTimeRange = getSelectedTimeRange() || {};

	return (
		<Filter
			defaultItem={defaultTimeRange}
			filterKey="velocityTimeRange"
			hideControl={true}
			items={[...timeRanges]}
			multiple={false}
			name={selectedTimeRange.name}
			position="right"
		/>
	);
}

function VelocityUnitFilter() {
	const {
		defaultVelocityUnit,
		getSelectedVelocityUnit,
		velocityUnits
	} = useContext(VelocityUnitContext);

	const selectedVelocityUnit = getSelectedVelocityUnit() || {};

	return (
		<Filter
			defaultItem={defaultVelocityUnit}
			elementClasses="pl-3"
			filterKey="velocityUnit"
			hideControl={true}
			items={[...velocityUnits]}
			multiple={false}
			name={selectedVelocityUnit.name}
			position="right"
		/>
	);
}
