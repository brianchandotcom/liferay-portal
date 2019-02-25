import React from 'react';
import {sub} from '../../shared/util/lang';

/**
 * @class
 * @memberof processes-list
 * */
export default class ProcessListItem extends React.Component {
	render() {
		const {
			instanceCount,
			ontimeInstanceCount,
			overdueInstanceCount,
			title
		} = this.props;

		let instanceMessage = Liferay.Language.get('x-items');

		if (instanceCount === 1) {
			instanceMessage = Liferay.Language.get('x-item');
		}

		return (
			<tr>
				<td className="table-cell-expand">
					<div className="table-list-title">
						<span className="text-truncate-inline">
							<span title={title}>{title}</span>
						</span>
					</div>
				</td>
				<td>{sub(instanceMessage, [instanceCount])}</td>
				<td>{ontimeInstanceCount}</td>
				<td>{overdueInstanceCount}</td>
			</tr>
		);
	}
}