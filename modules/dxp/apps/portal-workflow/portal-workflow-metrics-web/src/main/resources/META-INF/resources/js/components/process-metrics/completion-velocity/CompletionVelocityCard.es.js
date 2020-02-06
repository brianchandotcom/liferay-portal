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
import {isValidDate} from '../../filter/util/timeRangeUtil.es';
import {getVelocityUnits} from '../../filter/util/velocityUnitUtil.es';
import {Body} from './CompletionVelocityCardBody.es';

const CompletionVelocityCard = ({routeParams}) => {
	const {processId} = routeParams;

	const filterKeys = ['timeRange', 'velocityUnit'];
	const prefixKey = 'completion';
	const prefixKeys = [prefixKey];
	const {filterState = {}} = useFilter({filterKeys, prefixKeys});

	const {
		completionVelocityUnit: velocityUnit = [],
		completionTimeRange: timeRange = []
	} = filterState;

	const timeRangeValues = timeRange.length ? timeRange[0] : {};
	const {dateEnd, dateStart} = timeRangeValues;

	let timeRangeParams = {};
	if (isValidDate(dateEnd) && isValidDate(dateStart)) {
		timeRangeParams = {
			dateEnd: dateEnd.toISOString(),
			dateStart: dateStart.toISOString()
		};
	}

	const velocityUnitKeys = velocityUnit.length ? velocityUnit[0] : {};

	const velocityUnits = useMemo(
		() => getVelocityUnits({dateEnd, dateStart}),
		[dateEnd, dateStart]
	);

	const defaultUnit = useMemo(
		() =>
			velocityUnits.find(
				velocityUnit => velocityUnit.defaultVelocityUnit
			) || {},
		[velocityUnits]
	);

	const velocityUnitValues = useMemo(
		() =>
			velocityUnits.find(
				velocityUnit => velocityUnit.key === velocityUnitKeys.key
			) || defaultUnit,
		[defaultUnit, velocityUnits, velocityUnitKeys.key]
	);
	const {key: unit} = velocityUnitValues;

	const {data, fetchData} = useFetch({
		params: {
			...timeRangeParams,
			unit
		},
		url: `processes/${processId}/metric`
	});

	const promises = useMemo(() => {
		if (timeRangeParams.dateEnd && timeRangeParams.dateStart && unit) {
			return [fetchData()];
		}

		return [new Promise(() => {})];
	}, [timeRangeParams.dateEnd, timeRangeParams.dateStart, fetchData, unit]);

	return (
		<PromisesResolver promises={promises}>
			<Panel>
				<CompletionVelocityCard.Header
					prefixKey={prefixKey}
					timeRange={timeRangeValues}
				/>

				<CompletionVelocityCard.Body
					data={data}
					timeRange={timeRangeValues}
					velocityUnit={velocityUnitValues}
				/>
			</Panel>
		</PromisesResolver>
	);
};

const Header = ({prefixKey, timeRange: {dateEnd, dateStart}}) => {
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
						options={{position: 'right'}}
						prefixKey={prefixKey}
					/>

					{dateEnd && dateStart && (
						<VelocityUnitFilter
							className={'pl-3'}
							prefixKey={prefixKey}
							timeRange={{dateEnd, dateStart}}
						/>
					)}
				</ul>
			</div>
		</Panel.HeaderWithOptions>
	);
};

CompletionVelocityCard.Header = Header;
CompletionVelocityCard.Body = Body;

export default CompletionVelocityCard;
