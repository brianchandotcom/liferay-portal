/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

import NavigationBar from '../../../../../common/components/navigation-bar';
import {ClaimComponentsType} from '../Types';
import InsuranceInfo from './insurance-info';

import './index.scss';
import DamageSummary from './damage-summary';
import IncidentDetail from './incident-details';

enum NavBarLabel {
	InsuranceInfo = 'Insurance Info',
	IncidentDetail = 'Incident Detail',
	DamageSummary = 'Damage Summary',
}

const navbarLabel = [
	NavBarLabel.InsuranceInfo,
	NavBarLabel.IncidentDetail,
	NavBarLabel.DamageSummary,
];

const ClaimNavigator = ({claimData}: ClaimComponentsType) => {
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

			{active === NavBarLabel.IncidentDetail && (
				<IncidentDetail claimData={claimData} />
			)}

			{active === NavBarLabel.DamageSummary && <DamageSummary />}
		</div>
	);
};

export default ClaimNavigator;
