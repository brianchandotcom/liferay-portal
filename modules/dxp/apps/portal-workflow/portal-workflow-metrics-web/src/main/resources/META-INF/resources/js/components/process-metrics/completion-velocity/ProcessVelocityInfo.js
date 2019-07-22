import React, {useContext} from 'react';
import {VelocityDataContext} from './store/VelocityDataStore';
import {VelocityUnitContext} from './store/VelocityUnitStore';

function ProcessVelocityInfo() {
	const {velocityData} = useContext(VelocityDataContext);
	const {getSelectedVelocityUnit} = useContext(VelocityUnitContext);

	const selectedVelocityUnit = getSelectedVelocityUnit() || {};

	return (
		velocityData && (
			<div className="pb-2">
				<span className="velocity-value">{velocityData.value}</span>
				<span className="velocity-unit">
					{selectedVelocityUnit.name}
				</span>
			</div>
		)
	);
}

export {ProcessVelocityInfo};
