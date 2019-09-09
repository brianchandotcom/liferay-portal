/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React, {useContext} from 'react';
import Icon from '../../../shared/components/Icon.es';
import {InstanceListContext} from './store/InstanceListStore.es';
import moment from '../../../shared/util/moment.es';

const getStatusIcon = status => {
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
};

const InstanceListItem = ({
	assetTitle,
	assetType,
	dateCreated,
	id,
	slaStatus,
	taskNames = [],
	userName
}) => {
	const {setInstanceId} = useContext(InstanceListContext);
	const statusIcon = getStatusIcon(slaStatus);

	const updateInstanceId = () => setInstanceId(id);

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
					onClick={updateInstanceId}
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
					.format(Liferay.Language.get('mmm-dd-yyyy-lt'))}
			</td>
		</tr>
	);
};

export default InstanceListItem;
