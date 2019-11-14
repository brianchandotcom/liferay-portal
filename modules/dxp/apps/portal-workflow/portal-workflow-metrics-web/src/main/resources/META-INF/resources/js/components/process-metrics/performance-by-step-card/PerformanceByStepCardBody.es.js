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
import EmptyState from '../../../shared/components/list/EmptyState.es';
import {ChildLink} from '../../../shared/components/router/routerWrapper.es';
import {AppContext} from '../../AppContext.es';
import {formatQueryDate} from '../util/timeRangeUtil.es';
import PerformanceByStepCard from './PerformanceByStepCard.es';

const Body = ({data, processId, timeRange}) => {
	return (
		<>
			<Panel.Body>
				{data.totalCount ? (
					<PerformanceByStepCard.Table items={data.items} />
				) : (
					<PerformanceByStepCard.Empty />
				)}
			</Panel.Body>
			{data.totalCount && (
				<PerformanceByStepCard.Footer
					processId={processId}
					timeRange={timeRange}
					totalCount={data.totalCount}
				/>
			)}
		</>
	);
};

const Empty = () => {
	return (
		<EmptyState
			className="border-0 mt-8"
			hideAnimation={true}
			message={Liferay.Language.get('there-is-no-data-at-the-moment')}
			messageClassName="small"
		/>
	);
};

const Footer = ({processId, timeRange, totalCount}) => {
	const {defaultDelta} = useContext(AppContext);

	const viewAllStepsQuery = timeRange
		? {
				filters: {
					dateEnd: formatQueryDate(timeRange.dateEnd),
					dateStart: formatQueryDate(timeRange.dateStart),
					performanceTimeRange: timeRange.key
				}
		  }
		: {};
	const viewAllStepsUrl = `/performance/step/${processId}/${defaultDelta}/1/breachedInstancePercentage:desc`;

	return (
		<Panel.Footer elementClasses="fixed-bottom">
			<div className="mb-1 text-right">
				<ChildLink query={viewAllStepsQuery} to={viewAllStepsUrl}>
					<button className="border-0 btn btn-secondary btn-sm">
						<span className="mr-2" data-testid="viewAllSteps">
							{`${Liferay.Language.get(
								'view-all-steps'
							)} (${totalCount})`}
						</span>

						<Icon iconName="caret-right-l" />
					</button>
				</ChildLink>
			</div>
		</Panel.Footer>
	);
};

export {Body, Empty, Footer};
