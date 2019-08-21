import React, {useContext} from 'react';
import Filter from '../../../shared/components/filter/Filter';
import {VelocityUnitContext} from './store/VelocityUnitStore';

const VelocityUnitFilter = ({
	filterKey = 'velocityUnit',
	hideControl = false,
	position = 'left'
}) => {
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
			filterKey={filterKey}
			hideControl={hideControl}
			items={[...velocityUnits]}
			multiple={false}
			name={selectedVelocityUnit.name}
			position={position}
		/>
	);
};

export {VelocityUnitFilter};
