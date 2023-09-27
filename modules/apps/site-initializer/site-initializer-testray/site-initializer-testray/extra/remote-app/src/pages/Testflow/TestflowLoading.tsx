/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Container} from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEffect} from 'react';
import {KeyedMutator} from 'swr';
import {useHeader} from '~/hooks';
import i18n from '~/i18n';
import {TestrayTask} from '~/services/rest';

const POOLING_INTERVAL = 15000;

type TestflowLoadingProps = {
	mutateTask: KeyedMutator<TestrayTask>;
	testrayTask: TestrayTask;
};

const TestflowLoading: React.FC<TestflowLoadingProps> = ({
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
		<Container className="tr-testflow-loading">
			<span className="my-3">
				<ClayLoadingIndicator displayType="secondary" size="md" />
			</span>

			<span className="tr-testflow-loading__processing">
				<div className="tr-testflow-loading__processing__bar" />
			</span>

			<ClayButton
				className="mt-3"
				displayType="secondary"
				onClick={() => mutateTask(testrayTask)}
			>
				{i18n.translate('refresh')}
			</ClayButton>

			<p className="tr-testflow-loading__message">
				{i18n.translate('preparing-your-task')}
			</p>
		</Container>
	);
};

export default TestflowLoading;
