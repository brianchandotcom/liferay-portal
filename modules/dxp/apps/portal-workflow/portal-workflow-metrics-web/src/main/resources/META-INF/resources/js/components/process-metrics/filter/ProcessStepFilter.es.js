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

import React, {useContext, useMemo} from 'react';

import Filter from '../../../shared/components/filter/Filter.es';
import {ProcessStepContext} from './store/ProcessStepStore.es';

const ProcessStepFilter = ({
	className,
	filterKey = 'taskKeys',
	hideControl = false,
	multiple = true,
	position = 'left',
	showFilterName = true
}) => {
	const {
		defaultProcessStep,
		getSelectedProcessSteps,
		processSteps
	} = useContext(ProcessStepContext);

	const selectedProcessSteps = getSelectedProcessSteps();

	const filterName = useMemo(() => {
		if (
			!multiple &&
			!showFilterName &&
			selectedProcessSteps &&
			selectedProcessSteps.length
		) {
			return selectedProcessSteps[0].name;
		}

		return Liferay.Language.get('process-step');
	}, [multiple, selectedProcessSteps, showFilterName]);

	return (
		<Filter
			defaultItem={defaultProcessStep}
			elementClasses={className}
			filterKey={filterKey}
			hideControl={hideControl}
			items={processSteps}
			multiple={multiple}
			name={filterName}
			position={position}
		/>
	);
};

export default ProcessStepFilter;
