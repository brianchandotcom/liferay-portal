/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import useAnalyticsQuery from '../../../common/hooks/useAnalyticsQuery';

import './../../../../css/components/ActivityLog.scss';
import ActivityLogQuery from '../queries/ActivityLogQuery';
import {
	IActivityLogEntry,
	ILogEntry,
	IUserLogsEntry,
	TActivityLog,
} from '../types';
import AnalyticsFrame from './AnalyticsFrame';
import Loader from './Loader';
import UserLogEntry from './UserLogEntry';

export const TYPES = [
	{
		icon: 'comments',
		key: 'comment',
		label: Liferay.Language.get('commented-on'),
	},
	{
		icon: 'upload',
		key: 'upload',
		label: Liferay.Language.get('uploaded-a-x'),
	},
	{
		icon: 'view',
		key: 'view',
		label: Liferay.Language.get('viewed-a-x'),
	},
];

const formatData = (data: IActivityLogEntry[]) => {
	return data.reduce((activityLog: TActivityLog, item: IActivityLogEntry) => {
		const date = new Date(item.createDate);

		const dateKey = date.toISOString().split('T')[0];

		const timeString = date.toLocaleTimeString(
			Liferay.ThemeDisplay.getBCP47LanguageId(),
			{
				hour: 'numeric',
				hour12: true,
				minute: '2-digit',
			}
		);

		if (!activityLog[dateKey]) {
			activityLog[dateKey] = [];
		}

		const type = TYPES.find((type) => type.key === item.type);

		const logEntry: ILogEntry = {
			...item,
			icon: type ? type.icon : '',
			label: type
				? sub(
						Liferay.Language.get(type.label),
						Liferay.Language.get(item.label || '')
					)
				: '',
			time: timeString,
		};

		const dayGroup = activityLog[dateKey];

		const lastUserBlock = dayGroup[dayGroup.length - 1];

		if (lastUserBlock && lastUserBlock.userName === item.userName) {
			lastUserBlock.logs.push(logEntry);
		}
		else {
			dayGroup.push({
				logs: [logEntry],
				userName: item.userName,
			});
		}

		return activityLog;
	}, {});
};

function ActivityLog({
	dsrDevEnvEnabled: useDevEnvData,
}: {
	dsrDevEnvEnabled: boolean;
}) {
	const [data, setData] = useState<TActivityLog>({});
	const [element, setElement] = useState<HTMLElement | null>(null);

	const {isLoading, response} = useAnalyticsQuery({
		element,
		query: ActivityLogQuery,
		settings: {
			checkViewportVisibility: true,
			useDevEnvData,
		},
		variables: {
			channelId: '',
			entityType: 'INDIVIDUAL',
			keywords: '',
			page: 1,
			rangeEnd: null,
			rangeKey: 7,
			rangeStart: null,
			size: 20,
		},
	});

	useEffect(() => {
		if (response) {
			const formattedData = formatData(response);

			setData(formattedData);
		}

		return () => {};
	}, [response]);

	return (
		<AnalyticsFrame
			icon="box-container"
			title={Liferay.Language.get('activity-log')}
		>
			<div ref={setElement}>
				{isLoading ? (
					<Loader />
				) : !Object.keys(data).length ? (
					<p className="mt-3 text-center text-muted">
						{Liferay.Language.get('no-data-available')}
					</p>
				) : (
					Object.entries(data).map(
						([date, userLogs]: [string, IUserLogsEntry[]]) => (
							<>
								<div className="activity-logs-date fw-600 mb-3 px-3 py-2 text-secondary">
									{date}
								</div>

								{userLogs.map(
									(
										userLogsEntry: IUserLogsEntry,
										index: number
									) => (
										<UserLogEntry
											key={index}
											{...userLogsEntry}
										/>
									)
								)}
							</>
						)
					)
				)}
			</div>
		</AnalyticsFrame>
	);
}

export default ActivityLog;
