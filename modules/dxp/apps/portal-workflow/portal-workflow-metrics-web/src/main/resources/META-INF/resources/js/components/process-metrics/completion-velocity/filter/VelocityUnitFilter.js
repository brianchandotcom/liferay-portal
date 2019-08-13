import Filter from '../../../../shared/components/filter/Filter';
import React from 'react';
import {VelocityUnitContext} from '../store/VelocityUnitStore';
import {useContext} from 'react';

const VelocityUnitFilter = () => {
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
};

export {VelocityUnitFilter};
