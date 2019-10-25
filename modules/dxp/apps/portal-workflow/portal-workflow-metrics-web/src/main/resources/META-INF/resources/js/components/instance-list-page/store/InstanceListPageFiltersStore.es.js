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

import {AssigneeProvider} from '../../process-metrics/filter/store/AssigneeStore.es';
import {ProcessStatusProvider} from '../../process-metrics/filter/store/ProcessStatusStore.es';
import {ProcessStepProvider} from '../../process-metrics/filter/store/ProcessStepStore.es';
import {SLAStatusProvider} from '../../process-metrics/filter/store/SLAStatusStore.es';
import {TimeRangeProvider} from '../../process-metrics/filter/store/TimeRangeStore.es';

const InstanceFiltersProvider = ({
	assigneeKeys,
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
						<AssigneeProvider
							assigneeKeys={assigneeKeys}
							processId={processId}
						>
							{children}
						</AssigneeProvider>
					</ProcessStepProvider>
				</TimeRangeProvider>
			</ProcessStatusProvider>
		</SLAStatusProvider>
	);
};

export {InstanceFiltersProvider};
