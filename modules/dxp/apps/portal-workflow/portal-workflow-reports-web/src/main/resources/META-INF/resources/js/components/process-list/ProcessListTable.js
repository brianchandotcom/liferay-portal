import ProcessListItem from './ProcessListItem';
import React from 'react';

export default class ProcessListTable extends React.Component {
	render() {
		const {processes} = this.props;

		return (
			<div className="table-responsive">
				<table className="show-quick-actions-on-hover table table-autofit table-hover table-heading-nowrap table-list">
					<thead>
						<tr>
							<th
								className="table-cell-expand table-head-title"
								style={{width: '70%'}}
							>
								{Liferay.Language.get('process-name')}
							</th>
							<th
								className="table-cell-expand table-head-title"
								style={{width: '15%'}}
							>
								{Liferay.Language.get('total-open')}
							</th>
							<th
								className="table-cell-expand table-head-title"
								style={{width: '15%'}}
							>
								{Liferay.Language.get('on-time')}
							</th>
							<th
								className="table-cell-expand table-head-title"
								style={{width: '15%'}}
							>
								{Liferay.Language.get('overdue')}
							</th>
						</tr>
					</thead>

					<tbody>
						{processes.map(({instancesCount, title}, index) => (
							<ProcessListItem
								instancesCount={instancesCount}
								key={index}
								onTime="-"
								overdue="-"
								processName={title}
							/>
						))}
					</tbody>
				</table>
			</div>
		);
	}
}