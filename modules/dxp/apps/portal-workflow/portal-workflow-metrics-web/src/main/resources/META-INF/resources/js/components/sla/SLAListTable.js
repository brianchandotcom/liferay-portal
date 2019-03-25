import React from 'react';
import SLAListItem from './SLAListItem';

export default class SLAListTable extends React.Component {
	render() {
		const { sla } = this.props;

		return (
			<div className="table-responsive">
				<table className="show-quick-actions-on-hover table table-autofit table-heading-nowrap table-hover table-list">
					<thead>
						<tr>
							<th />

							<th
								className="table-cell-expand table-head-title"
								style={{ width: '40%' }}
							>
								{Liferay.Language.get('sla-name')}
							</th>

							<th
								className="table-cell-expand table-head-title"
								style={{ width: '35%' }}
							>
								{Liferay.Language.get('description')}
							</th>

							<th
								className="table-cell-expand table-head-title"
								style={{ width: '25%' }}
							>
								{Liferay.Language.get('duration')}
							</th>

							<th />
						</tr>
					</thead>

					<tbody>
						{sla.map((sla, index) => (
							<SLAListItem {...sla} key={index} />
						))}
					</tbody>
				</table>
			</div>
		);
	}
}