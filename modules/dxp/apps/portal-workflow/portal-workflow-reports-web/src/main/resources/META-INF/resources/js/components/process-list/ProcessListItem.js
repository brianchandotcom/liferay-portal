import React from 'react';
import {sub} from '../../shared/util/lang';

export default class ProcessListItem extends React.Component {
	render() {
		const {instancesCount, onTime, overdue, processName} = this.props;

		let instanceMessage = Liferay.Language.get('x-item');

		if (instancesCount > 1) {
			instanceMessage = Liferay.Language.get('x-items');
		}

		return (
			<tr>
				<td className="text-semi-bold">{processName}</td>
				<td>{sub(instanceMessage, [instancesCount])}</td>
				<td>{onTime}</td>
				<td>{overdue}</td>
			</tr>
		);
	}
}