import React from 'react';
import ProcessListManagementBar from './ProcessListManagementBar';

export default class ProcessListCard extends React.Component {
	render() {
		return (
			<div className="row">
				<div className="col-12">
					<ProcessListManagementBar />

					<div className="card">
						<div className="card-body">
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
												{'On Time'}
											</th>
											<th
												className="text-body table-cell-expand table-head-title"
												style={{width: '15%'}}
											>
												{'Overdue'}
											</th>
										</tr>
									</thead>

									<tbody>
										<tr>
											<td>{'Sales Quoting'}</td>
											<td>{'82 items'}</td>
											<td>{'33 items'}</td>
										</tr>
										<tr>
											<td>{'Refund Request'}</td>
											<td>{'12 items'}</td>
											<td>{'25 items'}</td>
										</tr>
										<tr>
											<td>{'Travel Reimbursement'}</td>
											<td>{'5 items'}</td>
											<td>{'23 items'}</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		);
	}
}