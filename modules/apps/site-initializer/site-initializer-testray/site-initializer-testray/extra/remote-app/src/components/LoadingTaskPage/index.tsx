/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEffect} from 'react';
import {KeyedMutator} from 'swr';
import {useHeader} from '~/hooks';
import i18n from '~/i18n';
import {TestrayTask} from '~/services/rest';

const POOLING_INTERVAL = 15000;

const LoadingProgressBar = () => (
	<span className="loading-progress-task my-4">
		<div className="loading-progress-task-bar" />
	</span>
);

type LoadingTaskPageProps = {
	mutateTask: KeyedMutator<TestrayTask>;
	testrayTask: TestrayTask;
};

const LoadingTaskPage: React.FC<LoadingTaskPageProps> = ({
	mutateTask,
	testrayTask,
}) => {
	useHeader({
		heading: [
			{
				category: i18n.translate('task').toUpperCase(),
				title: testrayTask.name,
			},
		],
		tabs: [],
	});

	useEffect(() => {
		const interval = setInterval(
			() => mutateTask(testrayTask),
			POOLING_INTERVAL
		);

		return () => {
			clearInterval(interval);
		};
	}, [mutateTask, testrayTask]);

	return (
		<div className="align-items-center container d-flex flex-column justify-content-center mt-5">
			<span className="my-3">
				<ClayLoadingIndicator displayType="secondary" size="md" />
			</span>

			<LoadingProgressBar />

			<ClayButton
				className="mt-3"
				displayType="secondary"
				onClick={() => mutateTask(testrayTask)}
			>
				{i18n.translate('refresh')}
			</ClayButton>

			<p className="loading-progress-task-message my-5">
				{i18n.translate('preparing-your-task')}
			</p>
		</div>
	);
};

export default LoadingTaskPage;
