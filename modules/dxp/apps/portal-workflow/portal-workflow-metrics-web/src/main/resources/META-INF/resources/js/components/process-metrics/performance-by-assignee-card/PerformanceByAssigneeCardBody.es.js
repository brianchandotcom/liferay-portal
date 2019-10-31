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
import PerformanceByAssigneeCard from './PerformanceByAssigneeCard.es';

const Body = ({data}) => {
	const {items, totalCount} = data;

	return (
		<Panel.Body>
			<PromisesResolver.Pending>
				<div className={`border-1 pb-6 pt-6 sheet`}>
					<LoadingState />
				</div>
			</PromisesResolver.Pending>

			<PromisesResolver.Resolved>
				{data && items && items.length > 0 ? (
					<>
						<PerformanceByAssigneeCard.Table items={items} />

						<PerformanceByAssigneeCard.Footer
							totalCount={totalCount}
						/>
					</>
				) : (
					<EmptyState
						className="border-0"
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
					className="border-0"
					hideAnimation={true}
					message={Liferay.Language.get(
						'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
					)}
					messageClassName="small"
					type="error"
				/>
			</PromisesResolver.Rejected>
		</Panel.Body>
	);
};

const Footer = ({totalCount}) => {
	return (
		<div className="mb-1 text-right">
			<button className="border-0 btn btn-secondary btn-sm">
				<span className="mr-2" data-testid="viewAllAssignees">
					{`${Liferay.Language.get(
						'view-all-assignees'
					)} (${totalCount})`}
				</span>

				<Icon iconName="caret-right-l" />
			</button>
		</div>
	);
};

export {Body, Footer};
