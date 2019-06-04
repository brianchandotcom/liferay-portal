import {AppContext} from '../AppContext';
import {ChildLink} from '../../shared/components/router/routerWrapper';
import React from 'react';

/**
 * @class
 * @memberof process-list
 */
class ProcessListItem extends React.Component {
	render() {
		const {
			id,
			instanceCount = '-',
			onTimeInstanceCount = '-',
			overdueInstanceCount = '-',
			title
		} = this.props;

		return (
			<tr>
				<td className='table-cell-expand table-cell-minw-200 table-title lfr-title-column'>
					<ChildLink to={`/dashboard/${id}`}>
						<span>{title}</span>
					</ChildLink>
				</td>

				<td>{overdueInstanceCount}</td>

				<td>{onTimeInstanceCount}</td>

				<td>{instanceCount}</td>
			</tr>
		);
	}
}

ProcessListItem.contextType = AppContext;
export default ProcessListItem;