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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useState} from 'react';

import ActivityItem from './ActivityItem.es';

function ActivityCollapsableList({items, title}) {
	const [expanded, setExpanded] = useState(true);

	return (
		<div className="activity-collapsable">
			<div
				className="activity-collapsable__header"
				onClick={() => setExpanded(!expanded)}
			>
				<span className="activity-collapsable__title">{title}</span>

				<ClayIcon
					className={classNames(
						'activity-collapsable__arrow',
						!expanded && 'collapsed'
					)}
					symbol="angle-down"
				/>
			</div>

			<div
				className={classNames(
					'activity-collapsable__content',
					!expanded && 'collapsed'
				)}
			>
				{items.map((item, index) => (
					<ActivityCollapsableList.Item key={index} {...item} />
				))}
			</div>
		</div>
	);
}

ActivityCollapsableList.Item = ActivityItem;
export default ActivityCollapsableList;
