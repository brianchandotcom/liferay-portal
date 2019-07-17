import React from 'react';
import SLAListItem from './SLAListItem';

export default class SLAListTable extends React.Component {
	render() {
		const {items} = this.props;

		const blockedItems = items.filter(({status}) => status === 2);

		const showBlockedDivider = !!blockedItems.length;
		const unblockedItems = items.filter(({status}) => status !== 2);

		const showRunningDivider =
			showBlockedDivider && !!unblockedItems.length;

		return (
			<div className="table-responsive">
				<table className="show-quick-actions-on-hover table table-autofit table-heading-nowrap table-hover table-list">
					<thead>
						<tr>
							<th
								className="table-cell-expand table-head-title"
								style={{width: '40%'}}
							>
								{Liferay.Language.get('sla-name')}
							</th>

							<th
								className="table-cell-expand table-head-title"
								style={{width: '35%'}}
							>
								{Liferay.Language.get('description')}
							</th>

							<th
								className="table-cell-expand table-head-title"
								style={{width: '15%'}}
							>
								{Liferay.Language.get('status')}
							</th>

							<th
								className="table-cell-expand table-head-title"
								style={{width: '25%'}}
							>
								{Liferay.Language.get('duration')}
							</th>

							<th
								className="table-cell-expand table-head-title"
								style={{width: '25%'}}
							>
								{Liferay.Language.get('last-modified')}
							</th>

							<th />
						</tr>
					</thead>

					<tbody>
						{showBlockedDivider && (
							<tr className="table-divider">
								<td colSpan="9">
									{Liferay.Language.get(
										'blocked'
									).toUpperCase()}
								</td>
							</tr>
						)}

						{blockedItems.map((sla, index) => (
							<SLAListItem
								{...sla}
								key={`blocked_${index}`}
								status={2}
							/>
						))}

						{showRunningDivider && (
							<tr className="table-divider">
								<td colSpan="9">
									{Liferay.Language.get(
										'running'
									).toUpperCase()}
								</td>
							</tr>
						)}

						{unblockedItems.map((sla, index) => (
							<SLAListItem {...sla} key={`unblocked_${index}`} />
						))}
					</tbody>
				</table>
			</div>
		);
	}
}
