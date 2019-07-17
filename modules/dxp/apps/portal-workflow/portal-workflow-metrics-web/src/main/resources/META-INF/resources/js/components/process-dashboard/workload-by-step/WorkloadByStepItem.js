import {filterKeys, processStatusKeys} from '../instance-list/filterConstants';
import {AppContext} from '../../AppContext';
import {ChildLink} from '../../../shared/components/router/routerWrapper';
import React from 'react';

class WorkloadByStepItem extends React.Component {
	constructor(props) {
		super(props);
	}

	getFiltersQuery(slaStatusFilter) {
		const {taskKey} = this.props;

		return {
			[filterKeys.processStatus]: [processStatusKeys.pending],
			[filterKeys.processStep]: [taskKey],
			[filterKeys.slaStatus]: [slaStatusFilter]
		};
	}

	render() {
		const {defaultDelta} = this.context;
		const {
			instanceCount = '-',
			name,
			onTimeInstanceCount = '-',
			overdueInstanceCount = '-',
			processId
		} = this.props;

		const instancesListPath = `/instances/${processId}/${defaultDelta}/1`;

		return (
			<tr>
				<td className="lfr-title-column table-cell-expand table-cell-minw-200 table-title">
					{name}
				</td>

				<td className="text-right">
					<ChildLink
						className="workload-by-step-link"
						query={{filters: this.getFiltersQuery('Overdue')}}
						to={instancesListPath}
					>
						{overdueInstanceCount}
					</ChildLink>
				</td>

				<td className="text-right">
					<ChildLink
						className="workload-by-step-link"
						query={{filters: this.getFiltersQuery('OnTime')}}
						to={instancesListPath}
					>
						{onTimeInstanceCount}
					</ChildLink>
				</td>

				<td className="text-right">
					<ChildLink
						className="workload-by-step-link"
						query={{filters: this.getFiltersQuery()}}
						to={instancesListPath}
					>
						{instanceCount}
					</ChildLink>
				</td>
			</tr>
		);
	}
}

WorkloadByStepItem.contextType = AppContext;
export default WorkloadByStepItem;
