import React from 'react';

export default class WorkloadByStepItem extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		const {
			instanceCount = '-',
			name,
			onTimeInstanceCount = '-',
			overdueInstanceCount = '-'
		} = this.props;

		return (
			<tr>
				<td className="lfr-title-column table-cell-expand table-cell-minw-200 table-title">
					{name}
				</td>

				<td className="text-right">{overdueInstanceCount}</td>

				<td className="text-right">{onTimeInstanceCount}</td>

				<td className="text-right">{instanceCount}</td>
			</tr>
		);
	}
}