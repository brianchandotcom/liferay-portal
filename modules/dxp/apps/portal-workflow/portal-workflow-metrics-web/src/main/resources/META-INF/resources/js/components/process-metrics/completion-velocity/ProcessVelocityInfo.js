import React, {useContext} from 'react';
import {formatNumber} from 'shared/util/numeral';
import {VelocityDataContext} from './store/VelocityDataStore';
import {VelocityUnitContext} from './store/VelocityUnitStore';

function ProcessVelocityInfo() {
	const {getSelectedVelocityUnit} = useContext(VelocityUnitContext);
	const {velocityData = {}} = useContext(VelocityDataContext);

	const formattedValue = formatNumber(velocityData.value, '0[.]00');
	const selectedVelocityUnit = getSelectedVelocityUnit() || {};

	return (
		velocityData && (
			<div className="pb-2">
				<span className="velocity-value">{formattedValue}</span>
				<span className="velocity-unit">
					{selectedVelocityUnit.name}
				</span>
			</div>
		)
	);
}

export {ProcessVelocityInfo};
