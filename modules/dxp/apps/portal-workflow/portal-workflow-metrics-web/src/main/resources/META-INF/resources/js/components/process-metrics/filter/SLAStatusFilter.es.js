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

import Filter from '../../../shared/components/filter/Filter.es';
import {SLAStatusContext} from './store/SLAStatusStore.es';

const SLAStatusFilter = ({
	className,
	filterKey = 'slaStatuses',
	hideControl = false,
	position = 'left'
}) => {
	const {slaStatuses} = useContext(SLAStatusContext);

	return (
		<Filter
			elementClasses={className}
			filterKey={filterKey}
			hideControl={hideControl}
			items={slaStatuses}
			multiple={true}
			name={Liferay.Language.get('sla-status')}
			position={position}
		/>
	);
};

export default SLAStatusFilter;
