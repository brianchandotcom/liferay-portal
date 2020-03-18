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

import React, {useMemo} from 'react';

import Panel from '../../../shared/components/Panel.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import {useFilter} from '../../../shared/hooks/useFilter.es';
import TimeRangeFilter from '../../filter/TimeRangeFilter.es';
import VelocityUnitFilter from '../../filter/VelocityUnitFilter.es';
import {getTimeRangeParams} from '../../filter/util/timeRangeUtil.es';
import {getVelocityUnits} from '../../filter/util/velocityUnitUtil.es';
import {Body} from './CompletionVelocityCardBody.es';

const CompletionVelocityCard = ({routeParams}) => {
	const {processId} = routeParams;
	const filterKeys = ['timeRange', 'velocityUnit'];
	const prefixKey = 'completion';
	const prefixKeys = [prefixKey];

	const {
		filterValues: {
			completionDateEnd,
			completionDateStart,
			completionVelocityUnit: [velocity] = [],
		},
		filtersError,
	} = useFilter({filterKeys, prefixKeys});

	const timeRange = useMemo(
		() => getTimeRangeParams(completionDateStart, completionDateEnd),
		[completionDateEnd, completionDateStart]
	);

	const velocityUnits = useMemo(() => getVelocityUnits(timeRange), [
		timeRange,
	]);

	const defaultUnit = useMemo(
		() => velocityUnits.find(unit => unit.defaultVelocityUnit) || {},
		[velocityUnits]
	);

	const velocityUnit = useMemo(
		() => velocityUnits.find(unit => unit.key === velocity),
		[velocity, velocityUnits]
	);

	const currentVelocityUnit = velocityUnit || defaultUnit;

	const {key: unit} = currentVelocityUnit;

	const {data, fetchData} = useFetch({
		params: {
			...timeRange,
			unit,
		},
		url: `processes/${processId}/histograms/metrics`,
	});

	const promises = useMemo(() => {
		if (timeRange.dateEnd && timeRange.dateStart && unit) {
			return [fetchData()];
		}

		return [new Promise((_, reject) => reject(filtersError))];
	}, [fetchData, filtersError, timeRange.dateEnd, timeRange.dateStart, unit]);

	return (
		<PromisesResolver promises={promises}>
			<Panel>
				<CompletionVelocityCard.Header
					disableFilters={filtersError}
					prefixKey={prefixKey}
					timeRange={timeRange}
				/>

				<CompletionVelocityCard.Body
					data={data}
					timeRange={timeRange}
					velocityUnit={currentVelocityUnit}
				/>
			</Panel>
		</PromisesResolver>
	);
};

const Header = ({disableFilters, prefixKey, timeRange}) => {
	return (
		<Panel.HeaderWithOptions
			description={Liferay.Language.get(
				'completion-velocity-description'
			)}
			elementClasses="dashboard-panel-header pb-0"
			title={Liferay.Language.get('completion-velocity')}
		>
			<div className="autofit-col m-0 management-bar management-bar-light navbar">
				<ul className="navbar-nav">
					<TimeRangeFilter
						disabled={disableFilters}
						options={{position: 'right'}}
						prefixKey={prefixKey}
					/>

					<VelocityUnitFilter
						className={'pl-3'}
						disabled={disableFilters}
						prefixKey={prefixKey}
						timeRange={timeRange}
					/>
				</ul>
			</div>
		</Panel.HeaderWithOptions>
	);
};

CompletionVelocityCard.Header = Header;
CompletionVelocityCard.Body = Body;

export default CompletionVelocityCard;
