import ProcessListItem from './ProcessListItem';
import React from 'react';

export default class ProcessListTable extends React.Component {
	render() {
		const {processes} = this.props;

		return (
			<div className="table-responsive">
				<table className="show-quick-actions-on-hover table table-autofit table-hover table-list table-nowrap">
					<thead>
						<tr>
							<th
								className="table-cell-expand table-head-title"
								style={{width: '70%'}}
							>
								{'Process Name'}
							</th>
							<th
								className="table-cell-expand table-head-title"
								style={{width: '15%'}}
							>
								{'Total Open'}
							</th>
							<th
								className="table-cell-expand table-head-title"
								style={{width: '15%'}}
							>
								{'On Time'}
							</th>
							<th
								className="table-cell-expand table-head-title"
								style={{width: '15%'}}
							>
								{'Overdue'}
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