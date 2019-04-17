import PendingItemsCard from './pending-items/PendingItemsCard';
import React from 'react';
import WorkloadByStepCard from './workload-by-step/WorkloadByStepCard';

const CLASS_NAME = 'workflow-process-dashboard';

export default class ProcessDashboard extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		const { processId, ...props } = this.props;

		return (
			<div className={CLASS_NAME}>
				<PendingItemsCard processId={processId} />
				<WorkloadByStepCard processId={processId} {...props} />
			</div>
		);
	}
}