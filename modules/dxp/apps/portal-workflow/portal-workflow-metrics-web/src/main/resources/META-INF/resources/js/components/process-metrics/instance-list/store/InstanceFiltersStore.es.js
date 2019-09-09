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

import {ProcessStatusProvider} from '../../filter/store/ProcessStatusStore';
import {ProcessStepProvider} from '../../filter/store/ProcessStepStore';
import React from 'react';
import {SLAStatusProvider} from '../../filter/store/SLAStatusStore';
import {TimeRangeProvider} from '../../filter/store/TimeRangeStore';

const InstanceFiltersProvider = ({
	children,
	processId,
	processStatusKeys,
	processStepKeys,
	slaStatusKeys,
	timeRangeKeys
}) => {
	return (
		<SLAStatusProvider slaStatusKeys={slaStatusKeys}>
			<ProcessStatusProvider processStatusKeys={processStatusKeys}>
				<TimeRangeProvider timeRangeKeys={timeRangeKeys}>
					<ProcessStepProvider
						processId={processId}
						processStepKeys={processStepKeys}
					>
						{children}
					</ProcessStepProvider>
				</TimeRangeProvider>
			</ProcessStatusProvider>
		</SLAStatusProvider>
	);
};

export {InstanceFiltersProvider};
