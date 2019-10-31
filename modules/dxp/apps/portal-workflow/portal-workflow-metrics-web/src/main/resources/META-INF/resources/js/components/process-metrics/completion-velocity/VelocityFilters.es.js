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

import {TimeRangeFilter} from '../filter/TimeRangeFilter.es';
import {VelocityUnitFilter} from '../filter/VelocityUnitFilter.es';

const VelocityFilters = () => {
	return (
		<div className="autofit-col m-0 management-bar management-bar-light navbar">
			<ul className="navbar-nav">
				<TimeRangeFilter
					filterKey="velocityTimeRange"
					hideControl={true}
					position="right"
					showFilterName={false}
				/>

				<VelocityUnitFilter
					className={'pl-3'}
					hideControl={true}
					position="right"
				/>
			</ul>
		</div>
	);
};

export default VelocityFilters;
