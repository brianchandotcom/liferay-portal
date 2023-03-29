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
import {getWebDavUrl} from '../../../../../common/utils/webdav';
import arrayOfHistory from './policyPanelDataHistory';

type ApplicationPolicyDetailsType = {
	dataJSON: string;
	email: string;
	phone: string;
};

type PolicyDetailsType = {
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

type InfoPanelType = {[keys: string]: string};

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

	function calculatedAge(dateOfBirth: string) {
		return Math.floor(
			Math.ceil(
				Math.abs(Date.parse(dateOfBirth) - Date.now()) /
					(1000 * 3600 * 24)
			) / 365.25
		);
	}

	useEffect(() => {
		try {
			const newDataJSON = JSON.parse(dataJSON);
			setApplicationData(newDataJSON);
		} catch (error) {
			console.warn(error);
		}
	}, [dataJSON]);

	const ContentDescription = ({description}: {description: string}) => (
		<div className="d-flex justify-content-between">
			<div>{description}</div>
		</div>
	);

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
				{active === NavBarLabel.Vehicles &&
					applicationData?.vehicleInfo?.form.map(
						(
							currentVehicle: PolicyDetailsType,
							indexVehicle: number
						) => (
							<div
								className="bg-neutral-0 h-100 pl-6 policy-detail-border pr-6 pt-6"
								key={indexVehicle}
							>
								<div className="d-flex flex-row flex-wrap justify-content-between">
									{indexVehicle !== 0 && (
										<div className="align-self-start col-12 layout-line mb-6 mt-1"></div>
									)}
									<div className="align-self-start w-25">
										<h5>
											{currentVehicle?.year}{' '}
											{currentVehicle?.make}{' '}
											{currentVehicle?.model
												? currentVehicle?.model
												: 'No data'}
										</h5>
										<img
											className="w-75"
											src={`${getWebDavUrl()}/${currentVehicle?.model
												.replace(/ /g, '')
												.toLocaleLowerCase()}.svg`}
										/>
									</div>
									<div className="align-self-start">
										<p className="mb-1 text-neutral-7">
											Primary Use
										</p>
										<div>
											{currentVehicle?.primaryUsage
												? currentVehicle?.primaryUsage
												: 'No data'}
										</div>
									</div>
									<div className="align-self-start">
										<p className="mb-1 text-neutral-7 w-100">
											Est. Annual Mileage
										</p>
										<div>
											{currentVehicle?.annualMileage
												? currentVehicle?.annualMileage
												: 'No data'}
										</div>
									</div>
									<div className="align-self-start">
										<p className="mb-1 text-neutral-7 w-100">
											Ownership Status
										</p>
										<div>
											{currentVehicle?.ownership
												? currentVehicle?.ownership
												: 'No data'}
										</div>
									</div>
								</div>
								<div>
									<div className="align-self-start mt-3">
										<p className="mb-1 text-neutral-7 w-100">
											Features
										</p>
										<div>
											{currentVehicle?.features
												? currentVehicle?.features
												: 'No data'}
										</div>
									</div>
								</div>
							</div>
						)
					)}
				{active === NavBarLabel.Drivers &&
					applicationData?.driverInfo?.form.map(
						(
							curentDriver: PolicyDetailsType,
							indexDriver: number
						) => (
							<div
								className="bg-neutral-0 pl-6 policy-detail-border pr-6 pt-6"
								key={indexDriver}
							>
								<div className="d-flex flex-row flex-wrap justify-content-between">
									{indexDriver !== 0 && (
										<div className="align-self-start col-12 layout-line mb-6 mt-1"></div>
									)}
									<div className="align-self-start">
										<h5>
											{curentDriver?.firstName
												? curentDriver?.firstName
												: 'No data'}
											,{' '}
											{calculatedAge(
												applicationData?.contactInfo
													?.dateOfBirth
											)
												? calculatedAge(
														applicationData
															?.contactInfo
															?.dateOfBirth
												  )
												: 'No data'}
										</h5>
									</div>
									<div className="align-self-start">
										<p className="mb-1 text-neutral-7 w-100">
											DOB
										</p>
										<div className="mb-3">
											{applicationData?.contactInfo
												?.dateOfBirth
												? applicationData?.contactInfo
														?.dateOfBirth
												: 'No data'}
										</div>
										<p className="mb-1 text-neutral-7 w-100">
											Education
										</p>
										<div className="mb-3">
											{curentDriver?.highestEducation
												? curentDriver?.highestEducation
												: 'No data'}
										</div>
										<p className="mb-1 text-neutral-7 w-100">
											Email
										</p>
										<a
											className="mb-3 text-break"
											href={email}
										>
											{email ? email : 'No data'}
										</a>
									</div>
									<div className="align-self-start">
										<p className="mb-1 text-neutral-7 w-100">
											Gender
										</p>
										<div className="mb-3">
											{curentDriver?.gender
												? curentDriver?.gender
												: 'No data'}
										</div>
										<p className="mb-1 text-neutral-7 w-100">
											Occupation
										</p>
										<div className="mb-3">
											{curentDriver?.occupation
												? curentDriver?.occupation
												: 'No data'}
										</div>
										<p className="mb-1 text-neutral-7 w-100">
											Phone
										</p>
										<div className="mb-3">
											{phone ? phone : 'No data'}
										</div>
									</div>
									<div className="align-self-start">
										<p className="mb-1 text-neutral-7 w-100">
											Marital Status
										</p>
										<div className="mb-3">
											{curentDriver?.maritalStatus
												? curentDriver?.maritalStatus
												: 'No data'}
										</div>
										<p className="mb-1 text-neutral-7 w-100">
											Credit rating
										</p>
										<div className="mb-3">
											{curentDriver?.creditRating
												? curentDriver?.creditRating
												: 'No data'}
										</div>
									</div>
								</div>
							</div>
						)
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
