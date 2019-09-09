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
import {formatNumber} from '../../../shared/util/numeral.es';
import {VelocityDataContext} from './store/VelocityDataStore.es';
import {VelocityUnitContext} from '../filter/store/VelocityUnitStore.es';

const ProcessVelocityInfo = () => {
	const {getSelectedVelocityUnit} = useContext(VelocityUnitContext);
	const {velocityData = {}} = useContext(VelocityDataContext);

	const formattedValue = formatNumber(velocityData.value, '0[.]00');
	const selectedVelocityUnit = getSelectedVelocityUnit() || {};

	return (
		velocityData && (
			<div className="pb-2">
				<span className="velocity-value">{formattedValue}</span>
				<span className="velocity-unit">
					{selectedVelocityUnit.name}
				</span>
			</div>
		)
	);
};

export {ProcessVelocityInfo};
