import InstanceListItem from './InstanceListItem';
import React from 'react';

const InstanceListTable = ({items}) => {
	return (
		<div className="table-responsive">
			<table
				className="show-quick-actions-on-hover table table-fixed table-heading-nowrap table-hover table-list"
				style={{minWidth: '1061px'}}
			>
				<thead>
					<tr>
						<th style={{width: '4%'}} />

						<th className="table-head-title" style={{width: '9%'}}>
							{Liferay.Language.get('id')}
						</th>

						<th
							className="table-cell-expand table-head-title"
							style={{width: '31%'}}
						>
							{Liferay.Language.get('item-subject')}
						</th>

						<th className="table-head-title" style={{width: '23%'}}>
							{Liferay.Language.get('process-step')}
						</th>

						<th className="table-head-title" style={{width: '15%'}}>
							{Liferay.Language.get('created-by')}
						</th>

						<th
							className="pr-4 table-head-title text-right"
							style={{width: '18%'}}
						>
							{Liferay.Language.get('creation-date')}
						</th>
					</tr>
				</thead>

				<tbody>
					{items.map((item, index) => (
						<InstanceListItem {...item} key={index} />
					))}
				</tbody>
			</table>
		</div>
	);
};

export default InstanceListTable;
