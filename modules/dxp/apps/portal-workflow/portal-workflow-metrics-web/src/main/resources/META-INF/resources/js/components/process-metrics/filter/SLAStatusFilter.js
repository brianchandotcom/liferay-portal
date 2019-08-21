import React, {useContext} from 'react';
import Filter from '../../../shared/components/filter/Filter';
import {SLAStatusContext} from './store/SLAStatusStore';

const SLAStatusFilter = ({
	filterKey = 'slaStatuses',
	hideControl = false,
	position = 'left'
}) => {
	const {slaStatuses} = useContext(SLAStatusContext);

	return (
		<Filter
			filterKey={filterKey}
			hideControl={hideControl}
			items={slaStatuses}
			multiple={true}
			name={Liferay.Language.get('sla-status')}
			position={position}
		/>
	);
};

export default SLAStatusFilter;
