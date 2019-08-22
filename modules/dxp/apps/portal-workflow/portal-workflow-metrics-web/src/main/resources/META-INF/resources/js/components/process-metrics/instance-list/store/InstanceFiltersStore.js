import {ProcessStatusProvider} from '../../filter/store/ProcessStatusStore';
import {ProcessStepProvider} from '../../filter/store/ProcessStepStore';
import React from 'react';
import {SLAStatusProvider} from '../../filter/store/SLAStatusStore';
import {TimeRangeProvider} from '../../filter/store/TimeRangeStore';

const InstanceFiltersProvider = ({
	children,
	processId,
	processStatusKeys,
	processStepKeys,
	slaStatusKeys,
	timeRangeKeys
}) => {
	return (
		<SLAStatusProvider slaStatusKeys={slaStatusKeys}>
			<ProcessStatusProvider processStatusKeys={processStatusKeys}>
				<TimeRangeProvider timeRangeKeys={timeRangeKeys}>
					<ProcessStepProvider
						processId={processId}
						processStepKeys={processStepKeys}
					>
						{children}
					</ProcessStepProvider>
				</TimeRangeProvider>
			</ProcessStatusProvider>
		</SLAStatusProvider>
	);
};

export {InstanceFiltersProvider};
