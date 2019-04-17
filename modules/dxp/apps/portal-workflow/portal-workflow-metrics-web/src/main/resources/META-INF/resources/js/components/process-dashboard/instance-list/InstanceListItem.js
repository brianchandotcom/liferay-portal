import Icon from '../../../shared/components/Icon';
import moment from 'moment';
import React from 'react';

export default class InstanceListItem extends React.Component {
	getStatusIcon(status) {
		if (status === 'overdue') {
			return {
				bgColor: 'bg-danger-light',
				iconColor: 'text-danger',
				iconName: 'exclamation-circle'
			};
		}

		if (status === 'on-time') {
			return {
				bgColor: 'bg-success-light',
				iconColor: 'text-success',
				iconName: 'check-circle'
			};
		}

		return {
			bgColor: 'bg-info-light',
			iconColor: 'text-info',
			iconName: 'hr'
		};
	}

	render() {
		const {
			assetName,
			assetType,
			createdBy,
			creationDate,
			id,
			processSteps = [],
			status
		} = this.props;

		const statusIcon = this.getStatusIcon(status);

		return (
			<tr>
				<td className="lfr-title-column table-title text-center">
					<span className={`mr-3 sticker sticker-sm ${statusIcon.bgColor}`}>
						<span className="inline-item">
							<Icon
								elementClasses={statusIcon.iconColor}
								iconName={statusIcon.iconName}
							/>
						</span>
					</span>

					<strong>{id}</strong>
				</td>

				<td>{`${assetType}: ${assetName}`}</td>

				<td>{processSteps.join(', ')}</td>

				<td>{createdBy}</td>

				<td className="pr-4 text-right">
					{moment(creationDate).format('MMM DD, LT')}
				</td>
			</tr>
		);
	}
}