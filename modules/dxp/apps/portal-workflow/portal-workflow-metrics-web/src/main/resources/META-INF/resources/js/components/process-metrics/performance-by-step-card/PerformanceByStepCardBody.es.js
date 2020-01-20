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
import {formatQueryDate} from '../../filter/util/timeRangeUtil.es';
import {Table} from './PerformanceByStepCardTable.es';

const Body = ({data}) => {
	return (
		<>
			<PromisesResolver.Pending>
				<Body.Loading />
			</PromisesResolver.Pending>

			<PromisesResolver.Resolved>
				<Panel.Body>
					{data.totalCount ? (
						<Body.Table items={data.items} />
					) : (
						<Body.Empty />
					)}
				</Panel.Body>
			</PromisesResolver.Resolved>

			<PromisesResolver.Rejected>
				<Body.Error />
			</PromisesResolver.Rejected>
		</>
	);
};

const EmptyView = () => {
	return (
		<EmptyState
			className="border-0 mt-8"
			hideAnimation={true}
			message={Liferay.Language.get('there-is-no-data-at-the-moment')}
			messageClassName="small"
		/>
	);
};

const ErrorView = () => {
	return (
		<EmptyState
			actionButton={<ReloadButton />}
			className="border-0 mt-7"
			hideAnimation={true}
			message={Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			)}
			messageClassName="small"
		/>
	);
};

const Footer = ({processId, timeRange, totalCount}) => {
	const {defaultDelta} = useContext(AppContext);
	const filters = {};

	const {dateEnd, dateStart, key} = timeRange;
	if (dateEnd && dateStart && key) {
		filters.dateEnd = formatQueryDate(dateEnd, true);
		filters.dateStart = formatQueryDate(dateStart);
		filters.timeRange = [key];
	}

	const viewAllStepsQuery = {filters};
	const viewAllStepsUrl = `/performance/step/${processId}/${defaultDelta}/1/breachedInstancePercentage:desc`;

	return (
		<Panel.Footer elementClasses="fixed-bottom">
			<div className="mb-1 text-right">
				<ChildLink
					className="border-0 btn btn-secondary btn-sm"
					query={viewAllStepsQuery}
					to={viewAllStepsUrl}
				>
					<span className="mr-2" data-testid="viewAllSteps">
						{`${Liferay.Language.get(
							'view-all-steps'
						)} (${totalCount})`}
					</span>

					<Icon iconName="caret-right-l" />
				</ChildLink>
			</div>
		</Panel.Footer>
	);
};

const LoadingView = () => {
	return <LoadingState className="border-0 mt-8 pb-5 pt-5 sheet" />;
};

Body.Empty = EmptyView;
Body.Error = ErrorView;
Body.Loading = LoadingView;
Body.Table = Table;

export {Body, Footer};
