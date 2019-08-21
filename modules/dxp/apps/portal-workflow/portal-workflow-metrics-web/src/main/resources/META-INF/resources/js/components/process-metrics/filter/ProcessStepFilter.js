import React, {useContext} from 'react';
import Filter from '../../../shared/components/filter/Filter';
import {ProcessStepContext} from './store/ProcessStepStore';

const ProcessStepFilter = ({
	filterKey = 'taskKeys',
	hideControl = false,
	position = 'left'
}) => {
	const {processSteps} = useContext(ProcessStepContext);

	return (
		<Filter
			filterKey={filterKey}
			hideControl={hideControl}
			items={processSteps}
			multiple={true}
			name={Liferay.Language.get('process-step')}
			position={position}
		/>
	);
};

export default ProcessStepFilter;
