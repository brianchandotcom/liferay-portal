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

import {Loading} from 'app-builder-web/js/components/loading/Loading.es';
import {getItem} from 'app-builder-web/js/utils/client.es';
import {formatDate} from 'app-builder-web/js/utils/time.es';
import moment from 'moment';
import React, {useEffect, useState} from 'react';

import ActivityCollapsableList from './ActivityCollapsableList.es';

import '../../../../css/ActivitySection.scss';

function ActivitySection({workflowInstanceId}) {
	const [isLoading, setLoading] = useState(true);
	const [activities, setActivities] = useState([]);

	const groupActivities = (activities) => {
		const isToday = (date) => moment().isSame(date, 'day');

		const formattedItems = activities.map((activity, index) => {
			const formattedDate = isToday(activity.dateCreated)
				? Liferay.Language.get('today')
				: formatDate(activity.dateCreated, 'LL');

			if (
				index < activities.length - 1 &&
				activity.type == 'TaskAssign' &&
				activity.role?.id
			) {
				activity.commentLog = '';
			}

			return {...activity, formattedDate};
		});

		return Object.entries(
			formattedItems.reduce((groupedActivities, activity) => {
				(groupedActivities[activity.formattedDate] =
					groupedActivities[activity.formattedDate] || []).push(
					activity
				);

				return groupedActivities;
			}, {})
		);
	};

	useEffect(() => {
		setLoading(true);

		getItem(
			`/o/headless-admin-workflow/v1.0/workflow-instances/${workflowInstanceId}/workflow-logs`,
			{
				page: -1,
				pageSize: -1,
				types: [
					'TaskAssign',
					'TaskCompletion',
					'TaskUpdate',
					'Transition',
				],
			}
		)
			.then(({items}) => {
				setLoading(false);

				setActivities(groupActivities(items));
			})
			.catch(() => {
				setLoading(false);
			});
	}, [workflowInstanceId]);

	return (
		<>
			<h3 className="mt-4">{Liferay.Language.get('activity')}</h3>

			<div className="activity-section sheet">
				<Loading isLoading={isLoading}>
					{activities.map(([title, items], index) => (
						<ActivitySection.CollapsableList
							items={items}
							key={index}
							title={title}
						/>
					))}
				</Loading>
			</div>
		</>
	);
}

ActivitySection.CollapsableList = ActivityCollapsableList;
export default ActivitySection;
