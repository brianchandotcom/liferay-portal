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
import React, {useContext, useMemo} from 'react';

import Panel from '../../../shared/components/Panel.es';
import ContentView from '../../../shared/components/content-view/ContentView.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import ChildLink from '../../../shared/components/router/ChildLink.es';
import {AppContext} from '../../AppContext.es';
import {Table} from './WorkloadByAssigneeCardTable.es';

const Body = ({currentTab, items, processId, processStepKey, totalCount}) => {
	const getEmptyMessage = (tab) => {
		switch (tab) {
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

	const statesProps = useMemo(
		() => ({
			emptyProps: {
				className: 'py-6',
				hideAnimation: true,
				message: getEmptyMessage(currentTab),
				messageClassName: 'small',
			},
			errorProps: {
				actionButton: <ReloadButton />,
				className: 'py-6',
				hideAnimation: true,
				message: Liferay.Language.get(
					'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
				),
				messageClassName: 'small',
			},
			loadingProps: {className: 'py-6'},
		}),
		[currentTab]
	);

	return (
		<Panel.Body>
			<ContentView {...statesProps}>
				{totalCount > 0 && (
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
				)}
			</ContentView>
		</Panel.Body>
	);
};

const Footer = ({processId, processStepKey, totalCount}) => {
	const {defaultDelta} = useContext(AppContext);

	const filters = {};

	if (processStepKey && processStepKey !== 'allSteps') {
		filters.taskNames = [processStepKey];
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

				<ClayIcon symbol="caret-right-l" />
			</ChildLink>
		</div>
	);
};

Body.Footer = Footer;
Body.Table = Table;

export {Body};
