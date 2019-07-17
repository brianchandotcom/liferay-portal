import Icon from '../../../shared/components/Icon';
import {InstanceContext} from './InstanceContext';
import moment from '../../../shared/util/moment';
import React from 'react';

class InstanceListItem extends React.Component {
	updateId() {
		const {id} = this.props;

		this.context.setInstanceId(id);
	}

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
						<span
							className={`mr-3 sticker sticker-sm ${statusIcon.bgColor}`}
						>
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
					<a
						data-target="#instanceDetailModal"
						data-toggle="modal"
						href="javascript:;"
						onClick={this.updateId.bind(this)}
						tabIndex="-1"
					>
						<strong>{id}</strong>
					</a>
				</td>

				<td>{`${assetType}: ${assetTitle}`}</td>

				<td>
					{taskNames.length
						? taskNames.join(', ')
						: Liferay.Language.get('completed')}
				</td>

				<td>{userName}</td>

				<td className="pr-4 text-right">
					{moment
						.utc(dateCreated)
						.format(Liferay.Language.get('mmm-dd-lt'))}
				</td>
			</tr>
		);
	}
}

InstanceListItem.contextType = InstanceContext;
export default InstanceListItem;
