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
	const viewAllStepsUrl = `/performance/${processId}/${defaultDelta}/1/overdueInstanceCount:desc`;

	return (
		<Panel.Body>
			{data.totalCount ? (
				<>
					<PerformanceByStepCard.Table items={data.items} />

					<div className="mb-1 text-right">
						<ChildLink
							query={viewAllStepsQuery}
							to={viewAllStepsUrl}
						>
							<button className="border-0 btn btn-secondary btn-sm">
								<span data-testid="viewAllSteps">
									{Liferay.Language.get('view-all-steps') +
										` (${data.totalCount})`}
								</span>

								<Icon iconName="caret-right-l" />
							</button>
						</ChildLink>
					</div>
				</>
			) : (
				<PerformanceByStepCard.Empty />
			)}
		</Panel.Body>
	);
};

const Empty = () => {
	return (
		<EmptyState
			className="border-0"
			hideAnimation={true}
			message={Liferay.Language.get('there-is-no-data-at-the-moment')}
			messageClassName="small"
		/>
	);
};

export {Body, Empty};
