/* eslint-disable @liferay/empty-line-between-elements */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './index.scss';

import {useEffect, useState} from 'react';

import NavigationBar from '../../../../../common/components/navigation-bar';
import sortedDateByDescOrder from '../../../../../common/utils/sortedDateByDescOrder';
import DriverInfo from './driver-info';
import HistoryInfo from './history-info';
import policyHistory from './policyNavigatorDataHistory';
import {ApplicationPolicyDetailsType, dataJSONType} from './types';
import VehicleInfo from './vehicle-info';

enum NavBarLabel {
	Drivers = 'Drivers',
	Vehicles = 'Vehicles',
	History = 'History',
}

const PolicyDetail = ({
	dataJSON,
	email,
	phone,
}: ApplicationPolicyDetailsType) => {
	const navbarLabel = [
		NavBarLabel.Vehicles,
		NavBarLabel.Drivers,
		NavBarLabel.History,
	];
	const [active, setActive] = useState(navbarLabel[0]);
	const [applicationData, setApplicationData] = useState<dataJSONType>();
	const [showPanel, setShowPanel] = useState<boolean[]>([]);

	useEffect(() => {
		try {
			setApplicationData(JSON.parse(dataJSON));
		}
		catch (error) {
			console.warn(error);
		}
	}, [dataJSON, email, phone]);

	const policyHistorySortedByDate = policyHistory.sort(sortedDateByDescOrder);

	useEffect(() => {
		setShowPanel(policyHistory.map(() => false));
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div className="bg-neutral-0 h-100 rounded w-100">
			<div className="bg-neutral-0 policy-detail-title pt-3 px-5 rounded-top">
				<h5 className="m-0">Policy Detail</h5>
			</div>

			<div className="d-flex flex-row">
				<NavigationBar
					active={active}
					navbarLabel={navbarLabel}
					setActive={setActive}
				/>
			</div>

			<div>
				{active === NavBarLabel.Vehicles && applicationData && (
					<VehicleInfo dataJSON={applicationData.vehicleInfo} />
				)}
				{active === NavBarLabel.Drivers && applicationData && (
					<DriverInfo
						dataJSON={applicationData}
						email={email}
						phone={phone}
					/>
				)}
				{active === NavBarLabel.History && (
					<HistoryInfo
						InfoPanels={policyHistorySortedByDate}
						setShowPanel={setShowPanel}
						showPanel={showPanel}
					/>
				)}
			</div>
		</div>
	);
};

export default PolicyDetail;
