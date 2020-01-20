/* eslint-disable react-hooks/exhaustive-deps */
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

import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useContext, useEffect, useMemo} from 'react';

import Icon from '../../../shared/components/Icon.es';
import Panel from '../../../shared/components/Panel.es';
import EmptyState from '../../../shared/components/empty-state/EmptyState.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import LoadingState from '../../../shared/components/loading/LoadingState.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import {AppContext} from '../../AppContext.es';
import {isValidDate} from '../../filter/util/timeRangeUtil.es';
import PANELS from './Panels.es';
import SummaryCard from './SummaryCard.es';

function ProcessItemsCard({
	children,
	completed,
	description,
	processId,
	timeRange,
	title
}) {
	const {setTitle} = useContext(AppContext);

	const {dateEnd, dateStart} = timeRange || {};

	let timeRangeParams = {};
	if (isValidDate(dateEnd) && isValidDate(dateStart)) {
		timeRangeParams = {
			dateEnd: dateEnd.toISOString(),
			dateStart: dateStart.toISOString()
		};
	}

	const {data, fetchData} = useFetch({
		params: {
			completed,
			...timeRangeParams
		},
		url: `/processes/${processId}`
	});

	useEffect(() => {
		setTitle(data.title);
	}, [data.title]);

	const promises = useMemo(() => {
		if (
			!timeRange ||
			(timeRangeParams.dateEnd && timeRangeParams.dateStart)
		) {
			return [fetchData()];
		}

		return [new Promise(() => {})];
	}, [fetchData, timeRangeParams.dateEnd, timeRangeParams.dateStart]);

	return (
		<PromisesResolver promises={promises}>
			<Panel>
				<ProcessItemsCard.Header
					data={data}
					description={description}
					title={title}
				>
					{children}
				</ProcessItemsCard.Header>

				<ProcessItemsCard.Body
					completed={completed}
					data={data}
					processId={processId}
					timeRange={timeRange}
				/>
			</Panel>
		</PromisesResolver>
	);
}

const Body = ({completed = false, data, processId, timeRange}) => {
	return (
		<Panel.Body>
			<PromisesResolver.Resolved>
				{data ? (
					<div className={'d-flex pb-4 pt-1'}>
						{PANELS.map((panel, index) => (
							<SummaryCard
								{...panel}
								completed={completed}
								key={index}
								processId={processId}
								timeRange={timeRange}
								total={
									panel.addressedToField === panel.totalField
								}
								totalValue={data[panel.totalField]}
								value={data[panel.addressedToField]}
							/>
						))}
					</div>
				) : null}
			</PromisesResolver.Resolved>

			<PromisesResolver.Rejected>
				<EmptyState
					actionButton={<ReloadButton />}
					className="border-0 mt-7"
					hideAnimation={true}
					message={Liferay.Language.get(
						'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
					)}
				/>
			</PromisesResolver.Rejected>

			<PromisesResolver.Pending>
				<LoadingState className="pb-6 pt-5" />
			</PromisesResolver.Pending>
		</Panel.Body>
	);
};

const Header = ({children, data, description, title}) => (
	<Panel.Header
		elementClasses={['dashboard-panel-header', children && 'pb-0']}
	>
		<div className="autofit-row">
			<div className="autofit-col autofit-col-expand flex-row">
				<span className="mr-2">{title}</span>

				<ClayTooltipProvider>
					<span>
						<span
							className="workflow-tooltip"
							data-tooltip-align={'right'}
							title={description}
						>
							<Icon iconName={'question-circle-full'} />
						</span>
					</span>
				</ClayTooltipProvider>
			</div>

			{children && data && (
				<div className="autofit-col m-0 management-bar management-bar-light navbar">
					<ul className="navbar-nav">{children}</ul>
				</div>
			)}
		</div>
	</Panel.Header>
);

ProcessItemsCard.Body = Body;
ProcessItemsCard.Header = Header;

export default ProcessItemsCard;
