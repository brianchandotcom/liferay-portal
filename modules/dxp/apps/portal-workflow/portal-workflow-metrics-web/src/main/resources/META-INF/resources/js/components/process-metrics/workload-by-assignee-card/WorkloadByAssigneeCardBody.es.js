/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import React, {useContext} from 'react';

import ContentView from '../../../shared/components/content-view/ContentView.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import ChildLink from '../../../shared/components/router/ChildLink.es';
import {AppContext} from '../../AppContext.es';
import Table from './WorkloadByAssigneeCardTable.es';

function Body({currentTab, items, processId, processStepKey, totalCount}) {
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

	const statesProps = {
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
	};

	return (
		<ClayPanel.Body>
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
		</ClayPanel.Body>
	);
}

function Footer({processId, processStepKey, totalCount}) {
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
				<span className="mr-2">
					{`${Liferay.Language.get(
						'view-all-assignees'
					)} (${totalCount})`}
				</span>

				<ClayIcon symbol="caret-right-l" />
			</ChildLink>
		</div>
	);
}

Body.Footer = Footer;
Body.Table = Table;

export default Body;
