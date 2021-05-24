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

import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import React, {useMemo} from 'react';

import PanelHeaderWithOptions from '../../../shared/components/panel-header-with-options/PanelHeaderWithOptions.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import {useFilter} from '../../../shared/hooks/useFilter.es';
import TimeRangeFilter from '../../filter/TimeRangeFilter.es';
import VelocityUnitFilter from '../../filter/VelocityUnitFilter.es';
import {getTimeRangeParams} from '../../filter/util/timeRangeUtil.es';
import {getVelocityUnits} from '../../filter/util/velocityUnitUtil.es';
import Body from './CompletionVelocityCardBody.es';

function CompletionVelocityCard({routeParams}) {
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

	const defaultUnit =
		velocityUnits.find((unit) => unit.defaultVelocityUnit) || {};

	const velocityUnit =
		velocityUnits.find((unit) => unit.key === velocity) || defaultUnit;

	const {key: unit} = velocityUnit;

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
			<ClayPanel className="mt-4">
				<CompletionVelocityCard.Header
					disableFilters={filtersError}
					prefixKey={prefixKey}
					timeRange={timeRange}
				/>

				<CompletionVelocityCard.Body
					data={data}
					timeRange={timeRange}
					velocityUnit={velocityUnit}
				/>
			</ClayPanel>
		</PromisesResolver>
	);
}

function Header({disableFilters, prefixKey, timeRange}) {
	return (
		<PanelHeaderWithOptions
			className="pb-0 tabs-panel-header"
			description={Liferay.Language.get(
				'completion-velocity-description'
			)}
			title={Liferay.Language.get('completion-velocity')}
		>
			<ClayLayout.ContentCol className="m-0 management-bar management-bar-light navbar">
				<div className="navbar-nav">
					<TimeRangeFilter
						disabled={disableFilters}
						prefixKey={prefixKey}
					/>

					<VelocityUnitFilter
						className="pl-3"
						disabled={disableFilters}
						prefixKey={prefixKey}
						timeRange={timeRange}
					/>
				</div>
			</ClayLayout.ContentCol>
		</PanelHeaderWithOptions>
	);
}

CompletionVelocityCard.Header = Header;
CompletionVelocityCard.Body = Body;

export default CompletionVelocityCard;
