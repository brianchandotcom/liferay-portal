import ListHeadItem from '../../../shared/components/list/ListHeadItem';
import React from 'react';
import WorkloadByStepItem from './WorkloadByStepItem';

export default class WorkloadByStepTable extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		const {items, processId} = this.props;
		const onTimeTitle = Liferay.Language.get('on-time');
		const overdueTitle = Liferay.Language.get('overdue');
		const stepNameTitle = Liferay.Language.get('step-name');
		const totalPendingTitle = Liferay.Language.get('total-pending');

		return (
			<div className='table-responsive'>
				<table className='show-quick-actions-on-hover table table-autofit table-heading-nowrap table-hover table-list'>
					<thead>
						<tr>
							<th className='table-cell-expand table-head-title'>
								{stepNameTitle}
							</th>

							<th className='table-head-title text-right'>
								<ListHeadItem
									iconColor='danger'
									iconName='exclamation-circle'
									name='overdueInstanceCount'
									title={overdueTitle}
								/>
							</th>

							<th className='table-head-title text-right'>
								<ListHeadItem
									iconColor='success'
									iconName='check-circle'
									name='onTimeInstanceCount'
									title={onTimeTitle}
								/>
							</th>

							<th className='table-head-title text-right'>
								<ListHeadItem
									name='instanceCount'
									title={totalPendingTitle}
								/>
							</th>
						</tr>
					</thead>

					<tbody>
						{items.map((step, index) => (
							<WorkloadByStepItem
								{...step}
								key={index}
								processId={processId}
								taskKey={step.key}
							/>
						))}
					</tbody>
				</table>
			</div>
		);
	}
}
