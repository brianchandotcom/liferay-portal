/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {useState} from 'react';

import NavigationBar from '../../../../../common/components/navigation-bar';
import {ClaimComponentsType} from '../Types';
import InsuranceInfo from './insurance-info';

import './index.scss';

enum NavBarLabel {
	InsuranceInfo = 'Insurance Info',
	IncidentDetail = 'Incident Detail',
	DamageSumary = 'Damage Sumary',
}

const ClaimNavigator = ({claimData}: ClaimComponentsType) => {
	const navbarLabel = [
		NavBarLabel.InsuranceInfo,
		NavBarLabel.IncidentDetail,
		NavBarLabel.DamageSumary,
	];
	const [active, setActive] = useState(navbarLabel[0]);

	return (
		<div className="bg-neutral-0 claims-details-nav-container h-100 mb-6 rounded w-100">
			<h5 className="bg-neutral-0 claims-detail-nav-title pt-3 px-5 rounded-top">
				Claims Detail
			</h5>

			<div className="d-flex flex-row rounded w-100">
				<NavigationBar
					active={active}
					navbarLabel={navbarLabel}
					setActive={setActive}
				/>
			</div>

			{active === NavBarLabel.InsuranceInfo && (
				<InsuranceInfo claimData={claimData} />
			)}
		</div>
	);
};

export default ClaimNavigator;
