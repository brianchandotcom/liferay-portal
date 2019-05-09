import ListHeadItem from '../../shared/components/list/ListHeadItem';
import ProcessListItem from './ProcessListItem';
import React from 'react';

/**
 * @class
 * @memberof process-list
 */
export default class ProcessListTable extends React.Component {
	render() {
		const { items } = this.props;
		const onTimeTitle = Liferay.Language.get('on-time');
		const overdueTitle = Liferay.Language.get('overdue');
		const processNameTitle = Liferay.Language.get('process-name');
		const totalPendingTitle = Liferay.Language.get('total-pending');

		return (
			<div className="table-responsive">
				<table className="show-quick-actions-on-hover table table-autofit table-heading-nowrap table-hover table-list">
					<thead>
						<tr>
							<th
								className="table-cell-expand table-head-title"
								style={{ width: '70%' }}
							>
								<ListHeadItem name="title" title={processNameTitle} />
							</th>

							<th
								className="table-cell-expand table-head-title"
								style={{ width: '15%' }}
							>
								<ListHeadItem
									name="overdueInstanceCount"
									title={overdueTitle}
								/>
							</th>

							<th
								className="table-cell-expand table-head-title"
								style={{ width: '15%' }}
							>
								<ListHeadItem name="onTimeInstanceCount" title={onTimeTitle} />
							</th>

							<th
								className="table-cell-expand table-head-title"
								style={{ width: '15%' }}
							>
								<ListHeadItem name="instanceCount" title={totalPendingTitle} />
							</th>
						</tr>
					</thead>

					<tbody>
						{items.map((process, index) => (
							<ProcessListItem {...process} key={index} />
						))}
					</tbody>
				</table>
			</div>
		);
	}
}