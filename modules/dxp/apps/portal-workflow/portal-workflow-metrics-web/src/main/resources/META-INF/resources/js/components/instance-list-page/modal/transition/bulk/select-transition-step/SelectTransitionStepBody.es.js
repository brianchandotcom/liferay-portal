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

import ClayModal from '@clayui/modal';
import ClayPanel from '@clayui/panel';
import React, {useMemo} from 'react';

import EmptyState from '../../../../../../shared/components/empty-state/EmptyState.es';
import RetryButton from '../../../../../../shared/components/list/RetryButton.es';
import LoadingState from '../../../../../../shared/components/loading/LoadingState.es';
import PromisesResolver from '../../../../../../shared/components/promises-resolver/PromisesResolver.es';
import {capitalize} from '../../../../../../shared/util/util.es';
import {Card} from './SelectTransitionStepCard.es';

const Body = ({data, setRetry, tasks}) => {
	const {workflowTaskTransitions = []} = data;

	const taskSteps = useMemo(() => {
		const versions = {};

		tasks.forEach(task => {
			if (
				versions[task.workflowDefinitionVersion] &&
				versions[task.workflowDefinitionVersion][task.name]
			) {
				versions[task.workflowDefinitionVersion][task.name].push(task);
			}
			else if (versions[task.workflowDefinitionVersion]) {
				versions[task.workflowDefinitionVersion][task.name] = [task];
			}
			else {
				versions[task.workflowDefinitionVersion] = {
					[task.name]: [task],
				};
			}
		});

		return versions;

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [tasks]);

	const versionedCards = useMemo(
		() => Object.entries(taskSteps).map(array => Object.entries(array[1])),
		[taskSteps]
	);

	const nextTransitions = useMemo(() => {
		const taskTransitions = {};

		workflowTaskTransitions.forEach(
			({transitions, workflowDefinitionVersion, workflowTaskName}) => {
				if (!taskTransitions[workflowDefinitionVersion]) {
					taskTransitions[workflowDefinitionVersion] = {
						[workflowTaskName]: transitions,
					};
				}
				else if (taskTransitions[workflowDefinitionVersion]) {
					taskTransitions[workflowDefinitionVersion][
						workflowTaskName
					] = transitions;
				}
			}
		);

		return Object.entries(taskTransitions).map(array => array[1]);
	}, [workflowTaskTransitions]);

	return (
		<ClayModal.Body data-testid="selectTransitionStep">
			<PromisesResolver.Pending>
				<Body.Loading />
			</PromisesResolver.Pending>

			<PromisesResolver.Resolved>
				{versionedCards.map((versionedCard, versionIndex) =>
					versionedCard.map(([taskLabel, tasks], cardIndex) => (
						<ClayPanel key={`${versionIndex}_${cardIndex}`}>
							<ClayPanel.Header>
								<h4 className="mt-2">
									{capitalize(taskLabel)}
								</h4>
							</ClayPanel.Header>

							<Body.Card
								cardIndex={`${versionIndex}_${cardIndex}`}
								nextTransitions={
									nextTransitions[versionIndex]
										? nextTransitions[versionIndex][
												taskLabel
										  ]
										: []
								}
								tasks={tasks}
							/>
						</ClayPanel>
					))
				)}
			</PromisesResolver.Resolved>

			<PromisesResolver.Rejected>
				<Body.Error onClick={() => setRetry(retry => retry + 1)} />
			</PromisesResolver.Rejected>
		</ClayModal.Body>
	);
};

const ErrorView = ({onClick}) => {
	return (
		<EmptyState
			actionButton={
				<RetryButton data-testid="retryBtn" onClick={onClick} />
			}
			className="border-0 pb-7 pt-8"
			hideAnimation={true}
			message={Liferay.Language.get('failed-to-retrieve-assignees')}
			messageClassName="small"
		/>
	);
};

const LoadingView = () => {
	return (
		<LoadingState
			className="border-0 mb-4 mt-6 pb-8 pt-8"
			message={Liferay.Language.get('retrieving-all-transitions')}
			messageClassName="small"
		/>
	);
};

Body.Card = Card;
Body.Error = ErrorView;
Body.Loading = LoadingView;

export {Body};
