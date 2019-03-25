import ProcessListItem from './ProcessListItem';
import React from 'react';

/**
 * @class
 * @memberof processes-list
 */
export default class ProcessListTable extends React.Component {
	render() {
		const {items} = this.props;

		return (
			<div className="table-responsive">
				<table className="show-quick-actions-on-hover table table-autofit table-heading-nowrap table-hover table-list">
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
								{Liferay.Language.get('overdue')}
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
								{Liferay.Language.get('total-pending')}
							</th>

							<th />
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