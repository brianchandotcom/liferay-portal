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

import ProcessStepFilter from '../filter/ProcessStepFilter.es';
import {TimeRangeFilter} from '../filter/TimeRangeFilter.es';

const Filter = () => {
	return (
		<div className="autofit-col m-0 management-bar management-bar-light navbar">
			<ul className="navbar-nav">
				<ProcessStepFilter
					filterKey="assigneeProcessStep"
					hideControl={true}
					multiple={false}
					position="right"
					showFilterName={false}
				/>

				<TimeRangeFilter
					className={'pl-3'}
					filterKey="assigneeTimeRange"
					hideControl={true}
					position="right"
					showFilterName={false}
				/>
			</ul>
		</div>
	);
};

export {Filter};
