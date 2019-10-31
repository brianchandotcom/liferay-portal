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
import {VelocityUnitContext} from './store/VelocityUnitStore.es';

const VelocityUnitFilter = ({
	className,
	filterKey = 'velocityUnit',
	hideControl = false,
	position = 'left'
}) => {
	const {
		defaultVelocityUnit,
		getSelectedVelocityUnit,
		velocityUnits
	} = useContext(VelocityUnitContext);

	const selectedVelocityUnit = getSelectedVelocityUnit() || {};

	return (
		<Filter
			defaultItem={defaultVelocityUnit}
			elementClasses={className}
			filterKey={filterKey}
			hideControl={hideControl}
			items={[...velocityUnits]}
			multiple={false}
			name={selectedVelocityUnit.name}
			position={position}
		/>
	);
};

export {VelocityUnitFilter};
