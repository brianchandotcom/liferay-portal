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
import PanelComponent from '../../../../../common/components/panel';
import sortedByDate from '../../../../../common/utils/sortedByDate';
import DriverInfo from '../PolicyDetail/policies-details-navigator/driver-info';
import VehicleInfo from '../PolicyDetail/policies-details-navigator/vehicle-info';
import arrayOfHistory from './policyPanelDataHistory';

type ApplicationPolicyDetailsType = {
	dataJSON: string;
	email: string;
	phone: string;
};

export type PolicyDetailsType = {
	annualMileage: number;
	creditRating: string;
	features: string;
	firstName: string;
	gender: string;
	highestEducation: string;
	make: string;
	maritalStatus: string;
	model: string;
	occupation: string;
	ownership: string;
	primaryUsage: string;
	year: string;
};

export type InfoPanelType = {[keys: string]: string};

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
	const [applicationData, setApplicationData] = useState<any>();

	const [showPanel, setShowPanel] = useState<boolean[]>([]);

	useEffect(() => {
		try {
			const newDataJSON = JSON.parse(dataJSON);
			setApplicationData(newDataJSON);
		} catch (error) {
			console.warn(error);
		}
	}, [dataJSON, email, phone]);

	const displayHistoryPanel = (index: number) => {
		const supportArray = [...showPanel];

		supportArray[index] = !supportArray[index];

		setShowPanel(supportArray);
	};

	const arraySortedByDate = arrayOfHistory.sort(sortedByDate);

	useEffect(() => {
		const supportArray = arrayOfHistory.map(() => {
			return false;
		});

		setShowPanel(supportArray);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div className="bg-neutral-0 h-100 policy-detail-container rounded">
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
					<VehicleInfo dataJSON={applicationData} />
				)}
				{active === NavBarLabel.Drivers && applicationData && (
					<DriverInfo
						dataJSON={applicationData}
						email={email}
						phone={phone}
					/>
				)}
				{active === NavBarLabel.History &&
					arraySortedByDate?.map(
						(item: InfoPanelType, index: number) => {
							return (
								<div
									className="bg-neutral-0 dotted-line flex-row history-detail-border p-6 position-relative"
									key={index}
								>
									<div>
										<div className="align-items-center d-flex data-panel-hide float-left justify-content-between w-25">
											{item.date}
										</div>
										<div className="w-100">
											<PanelComponent
												Description={
													<ContentDescription
														description={
															item.description
														}
													/>
												}
												hasExpandedButton={true}
												isPanelExpanded={
													showPanel[index]
												}
												key={index}
												setIsPanelExpanded={() =>
													displayHistoryPanel(index)
												}
											>
												<div className="justify-content-between layout-show-details ml-auto mt-4 p-3 w-75">
													<div className="d-flex flex-row justify-content-between">
														<div className="align-self-start mt-2">
															<p className="mb-1 text-neutral-7 w-25">
																Amount
															</p>
															<div>
																{
																	item.detail_Amount
																}
															</div>
														</div>
														<div className="align-self-start mt-2 w-50">
															<p className="mb-1 text-neutral-7">
																Injuries Or
																Fatalities
															</p>
															<div>
																{
																	item.detail_Injuries
																}
															</div>
														</div>
													</div>
													<div className="align-self-start mt-3">
														<div>
															{
																item.detail_details
															}
														</div>
													</div>
												</div>
											</PanelComponent>
										</div>
									</div>
								</div>
							);
						}
					)}
			</div>
		</div>
	);
};

export default PolicyDetail;
