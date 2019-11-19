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

import React from 'react';

import Icon from '../../../shared/components/Icon.es';
import Panel from '../../../shared/components/Panel.es';
import EmptyState from '../../../shared/components/list/EmptyState.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import LoadingState from '../../../shared/components/loading/LoadingState.es';
import PromisesResolver from '../../../shared/components/request/PromisesResolver.es';
import {ChildLink} from '../../../shared/components/router/routerWrapper.es';
import PerformanceByAssigneeCard from './PerformanceByAssigneeCard.es';

const Body = ({data, defaultDelta, processId}) => {
	const {items, totalCount} = data;

	return (
		<>
			<Panel.Body>
				<PromisesResolver.Pending>
					<LoadingState className="border-0 mt-8 pb-5 pt-5 sheet" />
				</PromisesResolver.Pending>

				<PromisesResolver.Resolved>
					{items && items.length > 0 ? (
						<PerformanceByAssigneeCard.Table items={items} />
					) : (
						<EmptyState
							className="border-0 mt-8"
							data-testid="emptyState"
							hideAnimation={true}
							message={Liferay.Language.get(
								'there-is-no-data-at-the-moment'
							)}
							messageClassName="small"
						/>
					)}
				</PromisesResolver.Resolved>

				<PromisesResolver.Rejected>
					<EmptyState
						actionButton={<ReloadButton />}
						className="border-0 mt-7"
						hideAnimation={true}
						message={Liferay.Language.get(
							'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
						)}
						messageClassName="small"
						type="error"
					/>
				</PromisesResolver.Rejected>
			</Panel.Body>

			<PromisesResolver.Resolved>
				{items && items.length > 0 ? (
					<PerformanceByAssigneeCard.Footer
						defaultDelta={defaultDelta}
						processId={processId}
						totalCount={totalCount}
					/>
				) : null}
			</PromisesResolver.Resolved>
		</>
	);
};

const Footer = ({defaultDelta, processId, totalCount}) => {
	const viewAllAssigneesUrl = `/performance/assignee/${processId}/${defaultDelta}/1/durationTaskAvg:desc/`;
	return (
		<Panel.Footer elementClasses="fixed-bottom">
			<div className="mb-1 text-right">
				<ChildLink to={viewAllAssigneesUrl}>
					<button className="border-0 btn btn-secondary btn-sm">
						<span className="mr-2" data-testid="viewAllAssignees">
							{`${Liferay.Language.get(
								'view-all-assignees'
							)} (${totalCount})`}
						</span>

						<Icon iconName="caret-right-l" />
					</button>
				</ChildLink>
			</div>
		</Panel.Footer>
	);
};

export {Body, Footer};
