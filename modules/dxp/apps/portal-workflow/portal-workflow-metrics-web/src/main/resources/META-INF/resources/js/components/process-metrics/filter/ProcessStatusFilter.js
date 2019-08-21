import React, {useContext} from 'react';
import Filter from '../../../shared/components/filter/Filter';
import {ProcessStatusContext} from './store/ProcessStatusStore';

const ProcessStatusFilter = ({
	filterKey = 'statuses',
	hideControl = false,
	position = 'left'
}) => {
	const {processStatuses} = useContext(ProcessStatusContext);

	return (
		<Filter
			filterKey={filterKey}
			hideControl={hideControl}
			items={processStatuses}
			multiple={true}
			name={Liferay.Language.get('process-status')}
			position={position}
		/>
	);
};

export default ProcessStatusFilter;
