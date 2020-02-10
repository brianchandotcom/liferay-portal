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
import Panel from '../../../shared/components/Panel.es';
import EmptyState from '../../../shared/components/empty-state/EmptyState.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import LoadingState from '../../../shared/components/loading/LoadingState.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {ChildLink} from '../../../shared/components/router/routerWrapper.es';
import {AppContext} from '../../AppContext.es';
import {Table} from './WorkloadByAssigneeCardTable.es';

const Body = ({currentTab, data, processId, processStepKey}) => {
	const {items, totalCount} = data;

	return (
		<Panel.Body>
			<PromisesResolver.Pending>
				<Body.Loading />
			</PromisesResolver.Pending>

			<PromisesResolver.Resolved>
				{totalCount > 0 ? (
					<>
						<Body.Table
							currentTab={currentTab}
							items={items}
							processId={processId}
							processStepKey={processStepKey}
						/>

						<Body.Footer
							processId={processId}
							processStepKey={processStepKey}
							totalCount={totalCount}
						/>
					</>
				) : (
					<Body.Empty currentTab={currentTab} />
				)}
			</PromisesResolver.Resolved>

			<PromisesResolver.Rejected>
				<Body.Error />
			</PromisesResolver.Rejected>
		</Panel.Body>
	);
};

const EmptyView = ({currentTab}) => {
	const getEmptyMessage = () => {
		switch (currentTab) {
			case 'onTime':
				return Liferay.Language.get(
					'there-are-no-assigned-items-on-time-at-the-moment'
				);
			case 'overdue':
				return Liferay.Language.get(
					'there-are-no-assigned-items-overdue-at-the-moment'
				);
			default:
				return Liferay.Language.get(
					'there-are-no-items-assigned-to-users-at-the-moment'
				);
		}
	};

	return (
		<EmptyState
			className="border-0 mb-0"
			hideAnimation={true}
			message={getEmptyMessage()}
			messageClassName="small"
		/>
	);
};

const ErrorView = () => {
	return (
		<EmptyState
			actionButton={<ReloadButton />}
			className="border-0"
			hideAnimation={true}
			message={Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			)}
			messageClassName="small"
		/>
	);
};

const Footer = ({processId, processStepKey, totalCount}) => {
	const {defaultDelta} = useContext(AppContext);

	const filters = {};

	if (processStepKey && processStepKey !== 'allSteps') {
		filters.taskKeys = [processStepKey];
	}

	const viewAllAssigneesQuery = {filters};
	const viewAllAssigneesUrl = `/workload/assignee/${processId}/${defaultDelta}/1/overdueTaskCount:desc`;

	return (
		<div className="mb-1 text-right">
			<ChildLink
				className="border-0 btn btn-secondary btn-sm"
				query={viewAllAssigneesQuery}
				to={viewAllAssigneesUrl}
			>
				<span className="mr-2" data-testid="viewAllAssignees">
					{`${Liferay.Language.get(
						'view-all-assignees'
					)} (${totalCount})`}
				</span>

				<Icon iconName="caret-right-l" />
			</ChildLink>
		</div>
	);
};

const LoadingView = () => {
	return <LoadingState className="border-0 pb-6 pt-6" />;
};

Body.Empty = EmptyView;
Body.Error = ErrorView;
Body.Footer = Footer;
Body.Loading = LoadingView;
Body.Table = Table;

export {Body};
