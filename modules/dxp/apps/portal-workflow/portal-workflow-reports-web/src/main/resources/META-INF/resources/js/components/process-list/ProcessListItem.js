import React from 'react';

export default class ProcessListItem extends React.Component {
	render() {
		const {instancesCount, onTime, overdue, processName} = this.props;

		return (
			<tr>
				<td className="text-semi-bold">{processName}</td>
				<td>
					{instancesCount > 1
						? `${instancesCount} items`
						: `${instancesCount} item`}
				</td>
				<td>{onTime}</td>
				<td>{overdue}</td>
			</tr>
		);
	}
}