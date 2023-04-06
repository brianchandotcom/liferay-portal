/* eslint-disable @liferay/empty-line-between-elements */
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

import './index.scss';

import {useEffect, useState} from 'react';

import NavigationBar from '../../../../../common/components/navigation-bar';
import sortedByDate from '../../../../../common/utils/sortedByDate';
import DriverInfo from './driver-info';
import HistoryInfo from './history-info';
import arrayOfHistory from './policyNavigatorDataHistory';
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
			const newDataJSON = JSON.parse(dataJSON);
			setApplicationData(newDataJSON);
		}
		catch (error) {
			console.warn(error);
		}
	}, [dataJSON, email, phone]);

	const arraySortedByDate = arrayOfHistory.sort(sortedByDate);

	useEffect(() => {
		const supportArray = arrayOfHistory.map(() => {
			return false;
		});

		setShowPanel(supportArray);
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
						arraySortedByDate={arraySortedByDate}
						setShowPanel={setShowPanel}
						showPanel={showPanel}
					/>
				)}
			</div>
		</div>
	);
};

export default PolicyDetail;
