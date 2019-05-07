import Icon from '../../../shared/components/Icon';
import moment from 'moment';
import React from 'react';

export default class InstanceListItem extends React.Component {
	getStatusIcon(status) {
		if (status === 'OnTime') {
			return {
				bgColor: 'bg-success-light',
				iconColor: 'text-success',
				iconName: 'check-circle'
			};
		}

		if (status === 'Overdue') {
			return {
				bgColor: 'bg-danger-light',
				iconColor: 'text-danger',
				iconName: 'exclamation-circle'
			};
		}

		if (status === 'Untracked') {
			return {
				bgColor: 'bg-info-light',
				iconColor: 'text-info',
				iconName: 'hr'
			};
		}

		return null;
	}

	render() {
		const {
			assetTitle,
			assetType,
			dateCreated,
			id,
			slaStatus,
			taskNames = [],
			userName
		} = this.props;

		const statusIcon = this.getStatusIcon(slaStatus);

		return (
			<tr>
				<td>
					{statusIcon && (
						<span className={`mr-3 sticker sticker-sm ${statusIcon.bgColor}`}>
							<span className="inline-item">
								<Icon
									elementClasses={statusIcon.iconColor}
									iconName={statusIcon.iconName}
								/>
							</span>
						</span>
					)}
				</td>

				<td className="lfr-title-column table-title">
					<strong>{id}</strong>
				</td>

				<td>{`${assetType}: ${assetTitle}`}</td>

				<td>
					{taskNames.length
						? taskNames.join(', ')
						: Liferay.Language.get('completed')}
				</td>

				<td>{userName}</td>

				<td className="pr-4 text-right">
					{moment(dateCreated).format('MMM DD, LT')}
				</td>
			</tr>
		);
	}
}